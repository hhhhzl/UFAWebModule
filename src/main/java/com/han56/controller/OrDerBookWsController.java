package com.han56.controller;

import com.alibaba.fastjson.JSON;
import com.han56.entity.tcDataSource.vo.TcOrderBookVO;
import com.han56.service.subscribe.SubScribe;
import com.han56.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author han56
 * @description 功能描述
 * @create 2022/3/8 下午2:31
 */
@Component
@Slf4j
@ServerEndpoint("/orderbook/{marketCode}/{stockCode}")
public class OrDerBookWsController {

    private static RedissonClient redissonClient;

    private static SubScribe subScribe;

    private Session session;

    private static Utils utils;

    private static ConcurrentHashMap<String,OrDerBookWsController> singleOnlineMap = new ConcurrentHashMap<>();


    private static ConcurrentHashMap<String,ConcurrentHashMap<String,OrDerBookWsController>> orderBookOnlineMap
            = new ConcurrentHashMap<>();

    @Autowired
    public void setRedissonClient(RedissonClient redissonClient){
        OrDerBookWsController.redissonClient=redissonClient;
    }

    @Autowired
    public void setSubScribe(SubScribe subScribe){
        OrDerBookWsController.subScribe=subScribe;
    }

    @Autowired
    private void setUtils(Utils utils){
        OrDerBookWsController.utils = utils;
    }


    /*
    * 建立WebSocket连接
    * 之后直接向用户发送数据
    * 采用群组的模式发送
    * 分为两种模式：
    * 1.如果用户的连接时间处于交易所提供数据期间，则走正常订阅状态。
    * 2.如果用户的连接时间处于停市期间，流程为：带着 orderBook-marketCode-stockCode 参数请求数据源并存入缓存中
    * */
    @OnOpen
    public void onOpen(@PathParam(value = "marketCode") String marketCode,
                       @PathParam(value = "stockCode") String stockCode,Session session) throws IOException {
        /*
        * 判断当前时间的状态
        * 如果返回真 说明正在交易中，正常去读取消息队列的数据即可
        * 返回假，说明处于停市状态，需要去数据源/缓存 请求最后一条数据
        * */
        this.session = session;
        if (utils.dateIsValid()){
            //将订阅消息的客户端设置为群组模式
            String orderBookParam = "orderbook"+"-"+marketCode+"-"+stockCode;
            singleOnlineMap.put(session.getId(),this);
            orderBookOnlineMap.put(orderBookParam,singleOnlineMap);
            log.info("当前在线人数："+singleOnlineMap.size()+"当前被订阅股票数："+orderBookOnlineMap.size());
            //开启订阅模式
            subScribe.subscribeOrderBook(marketCode, stockCode);
        }else {
            //请求摆盘数据源
            //拼接 redis key值，查看缓存中是否存在 orderbook-marketCode-stockCode 的数据
            String redisKey = "orderbook-"+marketCode+"-"+stockCode;
            RBucket<String> keyObj = redissonClient.getBucket(redisKey);
            //如果缓存中存在需要的数据直接发送给用户
            if (keyObj.isExists()){
                log.info("缓存中存在"+redisKey+"，直接返回数据");
                String memValue = keyObj.get();
                session.getBasicRemote().sendText(memValue);
            }else {
                OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20,TimeUnit.SECONDS)
                    .build();
                //判断 marketCode 属于哪个市场 并拼接请求url
                StringBuilder stringBuffer = new StringBuilder();
                if (marketCode.equals("1")){
                    stringBuffer.append("http://qt.gtimg.cn/q=").append("sh").append(stockCode);
                }else if (marketCode.equals("51")){
                    stringBuffer.append("http://qt.gtimg.cn/q=").append("sz").append(stockCode);
                }
                String reqUrl = stringBuffer.toString();
                try {
                    Request request = new Request.Builder()
                            .url(reqUrl)
                            .method("GET",null)
                            .build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()&&response.body()!=null){
                        //log.info("请求数据成功");
                        /*
                         * 以下部分加锁 synchronized
                         * */
                        String resData = response.body().string();
                        /*
                         * 解析数据，存入实体类，并向Redis频道发布
                         * */
                        TcOrderBookVO tcOrderBookVO = new TcOrderBookVO();
                        tcOrderBookVO = utils.setTcOrderBookData(resData);
                        //组装数据
                        HashMap<String,Object> resMap = new HashMap<>();
                        resMap.put("code","200");resMap.put("errMsg","");
                        List<List<String>> asks = utils.setAsksList(tcOrderBookVO);
                        List<List<String>> bids = utils.setBidsList(tcOrderBookVO);
                        HashMap<String,Object> dataMap = new HashMap<>();
                        dataMap.put("asks",asks);dataMap.put("bids",bids);
                        dataMap.put("ts",tcOrderBookVO.getTimestamp());
                        resMap.put("data",dataMap);
                        //生成JSON字符串
                        String jsonRes = JSON.toJSONString(resMap);
                        //返回用户
                        session.getBasicRemote().sendText(jsonRes);
                        //sendMessage(jsonRes);
                        //存入缓存 设置生命周期为一天
                        keyObj.set(jsonRes,1,TimeUnit.DAYS);
                        log.warn("非交易时间获取摆盘数据执行完成");
                    }
                }catch (Exception e){
                    log.error("非交易时间获取摆盘数据报错，错误信息:"+e.getMessage());
                }
            }
        }
    }

    @OnError
    public void onError(Throwable error) throws Throwable {
        log.error(error.toString());
        throw error;
    }

    @OnClose
    public void onClose() {
        singleOnlineMap.remove(session.getId());
    }

    public void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    //广播：循环单播
    public static void broadCast(String msg,String orderParam){

        orderBookOnlineMap.get(orderParam).forEach((k,v)->{
            try {
                v.sendMessage(msg);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }


}

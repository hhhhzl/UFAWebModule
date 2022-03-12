package com.han56.ufawebmodule.controller;

import com.han56.ufawebmodule.service.subscribe.SubScribe;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author han56
 * @description 功能描述
 * @create 2022/3/8 下午2:31
 */
@Component
@ServerEndpoint("/orderbook/{marketCode}/{stockCode}")
public class OrDerBookWsController {

    private static RedissonClient redissonClient;

    private static SubScribe subScribe;

    private Session session;

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


    /*
    * 建立WebSocket连接
    * 之后直接向用户发送数据
    * 采用群组的模式发送
    * */
    @OnOpen
    public void onOpen(@PathParam(value = "marketCode") String marketCode,
                       @PathParam(value = "stockCode") String stockCode,Session session){
        this.session = session;
        //将订阅消息的客户端设置为群组模式
        String orderBookParam = "orderbook"+"-"+marketCode+"-"+stockCode;
        singleOnlineMap.put(session.getId(),this);
        orderBookOnlineMap.put(orderBookParam,singleOnlineMap);
        //开启订阅模式
        subScribe.subscribeOrderBook(marketCode, stockCode);
    }

    @OnError
    public void onError(Throwable error) throws Throwable {
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

        ConcurrentHashMap<String,OrDerBookWsController> tmpMap;
        tmpMap=orderBookOnlineMap.get(orderParam);

        tmpMap.forEach((k,v)->{
            try {
                v.sendMessage(msg);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }


}

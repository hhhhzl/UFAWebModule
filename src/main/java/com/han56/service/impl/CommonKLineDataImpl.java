package com.han56.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.han56.domain.ResponseResult;
import com.han56.entity.KLineBean.KLineResponseBean;
import com.han56.entity.KLineBean.vo.KLineVO;
import com.han56.service.CommonKLineData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
*@description 功能描述
*@author han56
*@create 2022/3/17 下午8:51
 *
*/
@Service
public class CommonKLineDataImpl implements CommonKLineData {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public String getDayAndWeekAndMonthKLine(String startDate, String endDate, String stockCode,String redisKey,String timeFrame) throws IOException {
        /*
        * 判断redis缓存中是否存在key值，若存在则直接返回
        * 不存在则将最新请求的数据解析后存进redis缓存
        * */
        RBucket<String> keyObj = redissonClient.getBucket(redisKey);
        //如果缓存中存在这个key 则直接返回  无需请求数据
        if (keyObj.isExists()){
            logger.info("缓存中存在"+redisKey+"，直接返回数据");
            return keyObj.get();
        }
        logger.info("缓存中不存在"+redisKey+"，请求数据源");
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        String baseUrl = "https://web.ifzq.gtimg.cn/appstock/app/fqkline/get?param=";
        StringBuilder stringBuffer = new StringBuilder();
        //组装请求url
        stringBuffer.append(baseUrl).append(stockCode).append(",").append(timeFrame).append(",").append(startDate).append(",").append(endDate).append(",600,qfq");
        logger.info("组装后的url连接："+ stringBuffer);
        //try catch 包围请求部分
        try {
            Request request = new Request.Builder()
                    .url(stringBuffer.toString())
                    .method("GET",null)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()&&response.body()!=null){
                logger.info("请求成功");
                //解析JSON数据，存进K线统一实体类，并存入redis
                JSONObject jsonObject =  JSONObject.parseObject(response.body().string());
                int dataSize = jsonObject.getJSONObject("data").getJSONObject(stockCode).getJSONArray("qfq"+timeFrame).size();
                Pattern pattern = Pattern.compile("\"(.*?)\"");
                List<List<String>> list = new ArrayList<>();
                for (int i=0;i<dataSize;i++){
                    List<String> tmpList = new ArrayList<>();
                    String string = jsonObject.getJSONObject("data").getJSONObject(stockCode).getJSONArray("qfq"+timeFrame).getString(i);
                    Matcher matcher = pattern.matcher(string);
                    while (matcher.find())
                        tmpList.add(matcher.group().trim().replace("\"","")+" ");
                    list.add(tmpList);
                }
                //将解析出来的数据塞进实体类列表中
                List<KLineResponseBean> kLineResponseBeans = new ArrayList<>();
                for (List<String> tmpList:list){
                    KLineResponseBean kLineResponseBean = new KLineResponseBean();
                    kLineResponseBean.setDay(tmpList.get(0));
                    kLineResponseBean.setOpen(tmpList.get(1));kLineResponseBean.setClose(tmpList.get(2));
                    kLineResponseBean.setHigh(tmpList.get(3));kLineResponseBean.setLow(tmpList.get(4));
                    kLineResponseBean.setVolumn(tmpList.get(5));
                    kLineResponseBeans.add(kLineResponseBean);
                }
                //进行最终封装操作-存入redis缓存，并返回给用户数据
                KLineVO kLineVO = new KLineVO("200","success",kLineResponseBeans);
                String res = JSON.toJSONString(kLineVO);
                //过期时间设置为1天 做到每天只请求一次
                keyObj.set(res,1, TimeUnit.DAYS);
                return res;
            }
        }catch (Exception e){
            logger.error("请求日线操作失败，请查看日志报错信息记录筛查问题："+e.getMessage());
        }
        //请求失败，返回请求失败状态码
        return errorHandle();
    }

    @Override
    public String getOneMinuteKLine(String stockCode,String scale,String redisKey) {
        RBucket<String> keyObj = redissonClient.getBucket(redisKey);
        //如果缓存中存在这个key 则直接返回  无需请求数据
        if (keyObj.isExists()){
            logger.info("缓存中存在"+redisKey+"，直接返回数据");
            return keyObj.get();
        }
        logger.info("缓存中不存在"+redisKey+"，请求数据源");
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        StringBuilder stringBuffer = new StringBuilder();
        //组装请求url
        stringBuffer.append("https://quotes.sina.cn/cn/api/jsonp_v2.php/var%20_")
                .append(stockCode).append("_60_1577432551767=/CN_MarketDataService.getKLineData?symbol=")
                .append(stockCode).append("&scale=").append(scale).append("&ma=no&datalen=1023");

        logger.info("组装后的url连接："+ stringBuffer);
        //try catch 包围请求部分
        try {
            Request request = new Request.Builder()
                    .url(stringBuffer.toString())
                    .method("GET",null)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()&&response.body()!=null){
                logger.info("请求成功");
                //解析JSON数据，存进K线统一实体类，并存入redis
                List<KLineResponseBean> kLineResponseBeans = setOneMinDataSet(response.body().string());
                KLineVO kLineVO = new KLineVO("200","success",kLineResponseBeans);
                String res = JSON.toJSONString(kLineVO);
                //过期时间设置为1天 做到每天只请求一次
                keyObj.set(res,1, TimeUnit.DAYS);
                return res;
            }
        }catch (Exception e){
            logger.error("请求日线操作失败，请查看日志报错信息记录筛查问题："+e.getMessage());
        }
        //请求失败，返回请求失败状态码
        return errorHandle();
    }

    /*
    * 错误代码返回处理器
    * */
    private String errorHandle() {
        HashMap<String,String> errorMap = new HashMap<>();
        errorMap.put("code","404");
        errorMap.put("msg","请求失败");
        errorMap.put("success","false");
        errorMap.put("data",null);
        return JSON.toJSONString(errorMap);
    }

    /*
    * 封装一分钟线方法
    * */
    private List<KLineResponseBean> setOneMinDataSet(String str){
        List<KLineResponseBean> KLineBeans = new ArrayList<>();
        int strFront = str.indexOf("(");
        int strBack = str.lastIndexOf(")");
        String str2 = str.substring(strFront+1,strBack);
        System.out.println(str2);
        JSONArray jsonArray = JSONArray.parseArray(str2);
        System.out.println(jsonArray.getJSONObject(0).get("day"));

        for (int i=0;i<jsonArray.size();i++){
            KLineResponseBean kLineResponseBean = new KLineResponseBean();
            kLineResponseBean.setDay(jsonArray.getJSONObject(i).get("day").toString());
            kLineResponseBean.setHigh(jsonArray.getJSONObject(i).get("high").toString());
            kLineResponseBean.setLow(jsonArray.getJSONObject(i).get("low").toString());
            kLineResponseBean.setClose(jsonArray.getJSONObject(i).get("close").toString());
            kLineResponseBean.setOpen(jsonArray.getJSONObject(i).get("open").toString());
            kLineResponseBean.setVolumn(jsonArray.getJSONObject(i).get("volume").toString());
            KLineBeans.add(kLineResponseBean);
        }
        return KLineBeans;
    }

}

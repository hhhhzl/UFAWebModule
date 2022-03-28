package com.han56.controller;

import com.alibaba.fastjson.JSON;
import com.han56.entity.KLineBean.KLRequestParam;
import com.han56.entity.tcDataSource.TcOrderBookRequestParam;
import com.han56.entity.tcDataSource.vo.TcOrderBookVO;
import com.han56.service.impl.CommonKLineDataImpl;
import com.han56.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author han56
 * @description 功能描述：普通K线接口
 * @create 2022/3/17 下午8:56
 */
@RestController
@Slf4j
public class CommonController {

    @Autowired
    private CommonKLineDataImpl commonKLineData;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private Utils utils;

    /*
    * 摆盘数据 http 接口
    * if 处于交易状态下：返回当前时间的数据，直接通过数据源请求，因为交易期间的数据是不断变化的，没有必要存redis了
    * else 处于停市状态下：返回最后一条数据，且存入缓存，如果缓存有，则直接返回。
    * */
    @RequestMapping(value = "/api/market/orderbook",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    public String getOrderBook(@RequestBody TcOrderBookRequestParam tcOrderBookRequestParam) {
        //如果返回结果==true 则代表在交易中，直接访问数据源 api 组装数据并返回即可
        if (utils.dateIsValid()) {
            return commonKLineData.getOrderBookForHttpApi(tcOrderBookRequestParam);
        } else {
            //将参数组装成 redisKey 如果缓存中存在则直接返回
            String redisKey = "orderbook-" + tcOrderBookRequestParam.getMarketCode() + "-" +
                    tcOrderBookRequestParam.getStockCode();
            RBucket<String> keyObj = redissonClient.getBucket(redisKey);
            //如果缓存中存在这只股票的数据，则直接返回
            if (keyObj.isExists()) {
                log.info("缓存中存在" + redisKey + "，直接返回数据");
                return keyObj.get();
            } else {
                String jsonRes = commonKLineData.getOrderBookForHttpApi(tcOrderBookRequestParam);
                //存redis
                keyObj.set(jsonRes,1,TimeUnit.DAYS);
                return jsonRes;
            }
        }
    }

    /*
    * K线统一接口
    * */
    @RequestMapping(value = "/api/market/kline",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String getKLine(@RequestBody KLRequestParam klRequestParam) throws IOException {
        //获取到前端传来的JSONObject
        System.out.println(klRequestParam.getStockCode());
        String timeFrame = klRequestParam.getTimeFrame();
        /*
        * 获取当前请求时刻
        * */
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //当前时间 为请求的截止时间
        Date endTime = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String redisKey;String endDate;String startDate;
        switch (timeFrame){
            case "1m":
                calendar.add(Calendar.YEAR,-1);
                Date startMinTime = calendar.getTime();
                endDate = simpleDateFormat.format(endTime);
                startDate = simpleDateFormat.format(startMinTime);
                log.info("触发分线API时的时间："+endDate+"结束时间："+startDate);
                log.info("组装成key值为:kline-"+klRequestParam.getStockCode()+"-1m-"+endDate);
                redisKey = "kline-"+klRequestParam.getStockCode()+"-1m-"+endDate;
                return commonKLineData.getOneMinuteKLine(klRequestParam.getStockCode(),"1",redisKey);
            case "1d":
                calendar.add(Calendar.YEAR,-1);
                Date startDayTime = calendar.getTime();
                endDate = simpleDateFormat.format(endTime);
                startDate = simpleDateFormat.format(startDayTime);
                log.info("触发日线API时的时间："+endDate+"结束时间："+startDate);
                log.info("组装成key值为:kline-"+klRequestParam.getStockCode()+"-1d-"+endDate);
                //将各个字段组装成 redis-key
                redisKey = "kline-"+klRequestParam.getStockCode()+"-1d-"+endDate;
                return commonKLineData.getDayAndWeekAndMonthKLine(startDate,endDate, klRequestParam.getStockCode(), redisKey,"day");
            case "1w":
                calendar.add(Calendar.YEAR,-10);
                Date startWeekTime = calendar.getTime();
                endDate = simpleDateFormat.format(endTime);
                startDate = simpleDateFormat.format(startWeekTime);
                log.info("触发周线API时的时间："+simpleDateFormat.format(endTime)+"结束时间："+simpleDateFormat.format(startWeekTime));
                log.info("组装成key值为:kline-"+klRequestParam.getStockCode()+"-1w-"+simpleDateFormat.format(endTime));
                redisKey = "kline-"+klRequestParam.getStockCode()+"-1w-"+simpleDateFormat.format(endTime);
                return commonKLineData.getDayAndWeekAndMonthKLine(startDate,endDate,klRequestParam.getStockCode(),redisKey,"week");
            case "1M":
                calendar.add(Calendar.YEAR,-10);
                Date startMonthTime = calendar.getTime();
                endDate = simpleDateFormat.format(endTime);
                startDate = simpleDateFormat.format(startMonthTime);
                log.info("触发月线API时的时间："+simpleDateFormat.format(endTime)+"结束时间："+simpleDateFormat.format(startMonthTime));
                log.info("组装成key值为:kline-"+klRequestParam.getStockCode()+"-1M-"+simpleDateFormat.format(endTime));
                redisKey = "kline-"+klRequestParam.getStockCode()+"-1M-"+simpleDateFormat.format(endTime);
                return commonKLineData.getDayAndWeekAndMonthKLine(startDate,endDate,klRequestParam.getStockCode(),redisKey,"month");
        }
        return errorHandle();
    }


    /*
    * 错误信息返回方法
    * */
    public String errorHandle(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("code","400");
        map.put("msg","获取数据失败");
        map.put("data","");
        map.put("success","false");
        return JSON.toJSONString(map);
    }

}

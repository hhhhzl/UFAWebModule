package com.han56.controller;

import com.han56.entity.KLineBean.KLRequestParam;
import com.han56.service.impl.CommonKLineDataImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author han56
 * @description 功能描述：普通K线接口
 * @create 2022/3/17 下午8:56
 */
@RestController
public class CommonController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommonKLineDataImpl commonKLineData;


    /*
    * 统一接口
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
                logger.info("触发日线API时的时间："+endDate+"结束时间："+startDate);
                logger.info("组装成key值为:kline-"+klRequestParam.getStockCode()+"-1m-"+endDate);
                redisKey = "kline-"+klRequestParam.getStockCode()+"-1m-"+endDate;
                return commonKLineData.getOneMinuteKLine(klRequestParam.getStockCode(),"1",redisKey);
            case "1d":
                calendar.add(Calendar.YEAR,-1);
                Date startDayTime = calendar.getTime();
                endDate = simpleDateFormat.format(endTime);
                startDate = simpleDateFormat.format(startDayTime);
                logger.info("触发日线API时的时间："+endDate+"结束时间："+startDate);
                logger.info("组装成key值为:kline-"+klRequestParam.getStockCode()+"-1d-"+endDate);
                //将各个字段组装成 redis-key
                redisKey = "kline-"+klRequestParam.getStockCode()+"-1d-"+endDate;
                return commonKLineData.getDayAndWeekAndMonthKLine(startDate,endDate, klRequestParam.getStockCode(), redisKey,"day");
            case "1w":
                calendar.add(Calendar.YEAR,-10);
                Date startWeekTime = calendar.getTime();
                endDate = simpleDateFormat.format(endTime);
                startDate = simpleDateFormat.format(startWeekTime);
                logger.info("触发周线API时的时间："+simpleDateFormat.format(endTime)+"结束时间："+simpleDateFormat.format(startWeekTime));
                logger.info("组装成key值为:kline-"+klRequestParam.getStockCode()+"-1w-"+simpleDateFormat.format(endTime));
                redisKey = "kline-"+klRequestParam.getStockCode()+"-1w-"+simpleDateFormat.format(endTime);
                return commonKLineData.getDayAndWeekAndMonthKLine(startDate,endDate,klRequestParam.getStockCode(),redisKey,"week");
            case "1M":
                calendar.add(Calendar.YEAR,-10);
                Date startMonthTime = calendar.getTime();
                endDate = simpleDateFormat.format(endTime);
                startDate = simpleDateFormat.format(startMonthTime);
                logger.info("触发周线API时的时间："+simpleDateFormat.format(endTime)+"结束时间："+simpleDateFormat.format(startMonthTime));
                logger.info("组装成key值为:kline-"+klRequestParam.getStockCode()+"-1M-"+simpleDateFormat.format(endTime));
                redisKey = "kline-"+klRequestParam.getStockCode()+"-1M-"+simpleDateFormat.format(endTime);
                return commonKLineData.getDayAndWeekAndMonthKLine(startDate,endDate,klRequestParam.getStockCode(),redisKey,"month");
        }
        return "active";
    }



}

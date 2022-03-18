package com.han56.controller;

import com.han56.service.impl.CommonKLineDataImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    * 日线接口
    * */
    @PostMapping("/api/getDayKLine")
    public String getDayKLine(HttpServletRequest request) throws IOException {
        /*
        * 获取股票代码参数
        * */
        String stockCode = request.getParameter("stock_code");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Date startTime = calendar.getTime();
        calendar.add(Calendar.YEAR,-1);
        Date endTime = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        logger.info("触发日线API时的时间："+simpleDateFormat.format(endTime)+"结束时间："+simpleDateFormat.format(startTime));
        return commonKLineData.getDayKLine(simpleDateFormat.format(endTime), simpleDateFormat.format(startTime),stockCode);
    }

    /*
    * 周线接口
    * */
    @PostMapping("/api/getWeekKLine")
    public String getWeekKLine(HttpServletRequest request){
        String stockCode = request.getParameter("stock_code");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Date startTime = calendar.getTime();
        calendar.add(Calendar.YEAR,-10);
        Date endTime = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        logger.info("触发周线API时的时间："+simpleDateFormat.format(endTime)+"结束时间："+simpleDateFormat.format(startTime));
        return commonKLineData.getWeekKLine(simpleDateFormat.format(endTime), simpleDateFormat.format(startTime),stockCode);
    }

    /*
    * 月线接口
    * */
    @PostMapping("/api/getMonthKLine")
    public String getMonthKLine(HttpServletRequest request){
        String stockCode = request.getParameter("stock_code");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Date startTime = calendar.getTime();
        calendar.add(Calendar.YEAR,-10);
        Date endTime = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        logger.info("触发月线API时的时间："+simpleDateFormat.format(endTime)+"结束时间："+simpleDateFormat.format(startTime));
        return commonKLineData.getMonthKLine(simpleDateFormat.format(endTime), simpleDateFormat.format(startTime),stockCode);
    }


}

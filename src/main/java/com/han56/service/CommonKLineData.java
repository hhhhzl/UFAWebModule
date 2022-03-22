package com.han56.service;

import java.io.IOException;

/**
 * @author han56
 * @description 功能描述：普通K线接口
 * 定义 日 周 月 年K线数据的获取
 * @create 2022/3/16 下午1:52
 */
public interface CommonKLineData {

    /*
    * 获取日K线
    * @param
    * 1.startDate:起始时间 2021-03-22
    * 2.endDate:终止时间  2022-03-22 （最近请求的）
    * 3.stockCode:股票代号 （sz/sh）002594
    * 4.redisKey：存到缓存的key值
    * 5.timeFrame:时间规格选择参数 1d（日线） 1w（周线） 1M（月线）
    * */
    String getDayAndWeekAndMonthKLine(String startDate,String endDate,String stockCode,String redisKey,String timeFrame) throws IOException;


    /*
    * 获取分时线 1m 5m 15m 30m 60m
    * 该接口也可以替代腾讯数据源的日线 周线 只需要将 scale 换成 240（天） 1200（周）
    * 注意：日线只能每天的 15:00 之后更新最新的数据。参数不需要时间限制，自动返回最近的 1023 个节点
    * */
    String getOneMinuteKLine(String stockCode,String scale,String redisKey);

}

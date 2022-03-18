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
    * 考虑到的情况：
    * 1.输入日期如果是星期六星期天，则返回空并附加提示信息
    * */
    String getDayKLine(String startDate,String endDate,String stockCode) throws IOException;

    //获取周K线
    String getWeekKLine(String startDate,String endDate,String stockCode);

    //获取月K线
    String getMonthKLine(String startDate,String endDate,String stockCode);

    //获取季度K线
    String getQuatKLine(String QuatDate);

    //获取年K线
    String getYearKLine(String yearDate);

}

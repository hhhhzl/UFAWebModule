package com.han56.entity.KLineBean;

import java.io.Serializable;

/**
 * @author han56
 * @description 功能描述
 * @create 2022/3/22 上午9:37
 */
public class KLineResponseBean implements Serializable {

    //交易时间
    private String day;

    //开盘价
    private String open;

    //最高价
    private String high;

    //最低价
    private String low;

    //收盘价
    private String close;

    //开盘价
    private String volumn;


    /*
    * 构造方法
    * */


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getVolumn() {
        return volumn;
    }

    public void setVolumn(String volumn) {
        this.volumn = volumn;
    }
}

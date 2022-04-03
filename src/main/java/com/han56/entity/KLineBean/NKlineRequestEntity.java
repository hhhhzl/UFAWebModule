package com.han56.entity.KLineBean;

import java.util.Date;

/**
 * @author han56
 * @description 功能描述：新版本K线请求前端返回json数据对应实体类
 * @create 2022/4/3 上午8:48
 */
public class NKlineRequestEntity {

    /*
    * 参数：
    * 1.市场代号： 1>代表沪市  51>代表深市
    * 2.股票代号
    * 3.start：起始时间
    * 4.end:截止时间
    * 5.num：请求数量
    * 6.time_frame：请求K线种类
    * */
    private String marketCode;

    private String stockCode;

    private Date startTime;

    private Date endTime;

    private Integer num;

    private String timeFrame;

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }
}

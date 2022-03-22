package com.han56.entity.KLineBean;

/**
 * @author han56
 * @description 功能描述：接收前端参数实体类
 * @create 2022/3/21 上午10:57
 */
public class KLRequestParam {

    private String stockCode;

    private String timeFrame;

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }
}

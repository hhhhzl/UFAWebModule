package com.han56.entity.tcDataSource;

/**
 * @author han56
 * @description 功能描述
 * @create 2022/3/28 下午2:25
 */
public class TcOrderBookRequestParam {

    //市场代号
    private String marketCode;

    //股票代号
    private String stockCode;

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
}

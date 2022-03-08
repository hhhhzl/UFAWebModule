package com.han56.ufawebmodule.entity.orderBookBean;

import java.io.Serializable;

/**
 * @author han56
 * @description 功能描述
 * @create 2022/3/4 下午5:21
 */
public class Security implements Serializable {

    private Integer marketCode;

    private String stockCode;

    public Integer getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(Integer marketCode) {
        this.marketCode = marketCode;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }
}

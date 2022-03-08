package com.han56.ufawebmodule.entity.orderBookBean;

import java.io.Serializable;

/**
 * @author han56
 * @description 功能描述
 * @create 2022/3/3 下午6:58
 */
public class OrderBookAsk implements Serializable {

    private Double askPrice;

    private Long askVolumn;

    private Integer askOrderCount;

    public Double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(Double askPrice) {
        this.askPrice = askPrice;
    }

    public Long getAskVolumn() {
        return askVolumn;
    }

    public void setAskVolumn(Long askVolumn) {
        this.askVolumn = askVolumn;
    }

    public Integer getAskOrderCount() {
        return askOrderCount;
    }

    public void setAskOrderCount(Integer askOrderCount) {
        this.askOrderCount = askOrderCount;
    }
}

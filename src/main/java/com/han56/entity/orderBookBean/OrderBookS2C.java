package com.han56.entity.orderBookBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author han56
 * @description 功能描述
 * @create 2022/3/9 上午10:52
 */
public class OrderBookS2C implements Serializable {

    private Security security;

    private List<OrderBookAsk> orderBookAskList;

    private List<OrderBookBid> orderBookBidList;

    private String askTimeString;

    private Integer askTimeStamp;

    private String bidTimeString;

    private Integer bidTimeStamp;

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public List<OrderBookAsk> getOrderBookAskList() {
        return orderBookAskList;
    }

    public void setOrderBookAskList(List<OrderBookAsk> orderBookAskList) {
        this.orderBookAskList = orderBookAskList;
    }

    public List<OrderBookBid> getOrderBookBidList() {
        return orderBookBidList;
    }

    public void setOrderBookBidList(List<OrderBookBid> orderBookBidList) {
        this.orderBookBidList = orderBookBidList;
    }

    public String getAskTimeString() {
        return askTimeString;
    }

    public void setAskTimeString(String askTimeString) {
        this.askTimeString = askTimeString;
    }

    public Integer getAskTimeStamp() {
        return askTimeStamp;
    }

    public void setAskTimeStamp(Integer askTimeStamp) {
        this.askTimeStamp = askTimeStamp;
    }

    public String getBidTimeString() {
        return bidTimeString;
    }

    public void setBidTimeString(String bidTimeString) {
        this.bidTimeString = bidTimeString;
    }

    public Integer getBidTimeStamp() {
        return bidTimeStamp;
    }

    public void setBidTimeStamp(Integer bidTimeStamp) {
        this.bidTimeStamp = bidTimeStamp;
    }
}

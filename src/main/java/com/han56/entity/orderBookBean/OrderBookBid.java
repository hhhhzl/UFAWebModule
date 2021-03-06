package com.han56.entity.orderBookBean;

import java.io.Serializable;

/**
 * @author han56
 * @description 功能描述
 * @create 2022/3/9 上午10:51
 */
public class OrderBookBid implements Serializable {

    private Double bidPrice;

    private Long bidVolumn;

    private Integer bidOrderCount;

    public Double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Long getBidVolumn() {
        return bidVolumn;
    }

    public void setBidVolumn(Long bidVolumn) {
        this.bidVolumn = bidVolumn;
    }

    public Integer getBidOrderCount() {
        return bidOrderCount;
    }

    public void setBidOrderCount(Integer bidOrderCount) {
        this.bidOrderCount = bidOrderCount;
    }
}

package com.han56.entity.tcDataSource;

import java.io.Serializable;

/**
 * @author han56
 * @description 功能描述
 * @create 2022/3/17 下午8:21
 */
public class TcOrderBookBean implements Serializable {

    /*
     * 交易所代号
     * 51表示 深圳sz
     * 1表示 上海sh
     * */
    private String marketCode;

    //股票名字
    private String stockName;

    //股票代号
    private String stockCode;

    //当前价格
    private String currentPrice;

    //昨日收盘价
    private String yesDayPrice;

    //开盘价
    private String openPrice;

    //成交量
    private String volumn;

    //外盘
    private String outerDisk;

    //内盘
    private String innerDisk;

    //买一
    private String bidOne;

    //买一量
    private String bidOneVolumn;

    //买二
    private String bidTwo;

    //买二量
    private String bidTwoVolumn;

    //买三
    private String bidThree;

    //买三量
    private String bidThreeVolumn;

    //买四
    private String bidFour;

    //买四量
    private String bidFourVolumn;

    //买五
    private String bidFive;

    //买五量
    private String bidFiveVolumn;

    //卖一
    private String askOne;

    //卖一量
    private String askOneVolumn;

    //卖二
    private String askTwo;

    //卖二量
    private String askTwoVolumn;

    //卖三
    private String askThree;

    //卖三量
    private String askThreeVolumn;

    //卖四
    private String askFour;

    //卖四量
    private String askFourVolumn;

    //卖五
    private String askFive;

    //卖五量
    private String askFiveVolumn;

    //时间戳
    private Long timestamp;

    //请求时间(秒级)
    private String reqTime;

    //涨跌
    private String upAndDown;

    //涨跌百分比
    private String percentUpAndDown;

    //最高价
    private String highestPrice;

    //最低价
    private String lowestPrice;

    //最新价/成交量（手）/成交额（元）
    private String newestPrice;

    //成交量
    private String makeDealVolumn;

    //成交额
    private String makeDealPrice;

    //换手率
    private String turnOverRate;

    //ttm市盈率
    private String pERate;

    //未知参数
    private String unKnowPara1;

    //最高价2重复参数
    private String highestPrice2;

    //最低价2重复参数
    private String lowestPrice2;

    //振幅
    private String amplitude;

    //流通市值
    private String marketValue;

    //总市值
    private String totalMarketValue;

    //lf市净率
    private String pBRate;

    //涨停价
    private String limitPrice;

    //跌停价
    private String priceLimit;

    //量比
    private String quantityRate;

    //未知参数2
    private String unKnownParam2;

    //均价
    private String averagePrice;

    //动态市盈率
    private String dynamicPriceEarningsRatio;

    //静态市盈率
    private String staticPriceEarningRatio;

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getYesDayPrice() {
        return yesDayPrice;
    }

    public void setYesDayPrice(String yesDayPrice) {
        this.yesDayPrice = yesDayPrice;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getVolumn() {
        return volumn;
    }

    public void setVolumn(String volumn) {
        this.volumn = volumn;
    }

    public String getOuterDisk() {
        return outerDisk;
    }

    public void setOuterDisk(String outerDisk) {
        this.outerDisk = outerDisk;
    }

    public String getInnerDisk() {
        return innerDisk;
    }

    public void setInnerDisk(String innerDisk) {
        this.innerDisk = innerDisk;
    }

    public String getBidOne() {
        return bidOne;
    }

    public void setBidOne(String bidOne) {
        this.bidOne = bidOne;
    }

    public String getBidOneVolumn() {
        return bidOneVolumn;
    }

    public void setBidOneVolumn(String bidOneVolumn) {
        this.bidOneVolumn = bidOneVolumn;
    }

    public String getBidTwo() {
        return bidTwo;
    }

    public void setBidTwo(String bidTwo) {
        this.bidTwo = bidTwo;
    }

    public String getBidTwoVolumn() {
        return bidTwoVolumn;
    }

    public void setBidTwoVolumn(String bidTwoVolumn) {
        this.bidTwoVolumn = bidTwoVolumn;
    }

    public String getBidThree() {
        return bidThree;
    }

    public void setBidThree(String bidThree) {
        this.bidThree = bidThree;
    }

    public String getBidThreeVolumn() {
        return bidThreeVolumn;
    }

    public void setBidThreeVolumn(String bidThreeVolumn) {
        this.bidThreeVolumn = bidThreeVolumn;
    }

    public String getBidFour() {
        return bidFour;
    }

    public void setBidFour(String bidFour) {
        this.bidFour = bidFour;
    }

    public String getBidFourVolumn() {
        return bidFourVolumn;
    }

    public void setBidFourVolumn(String bidFourVolumn) {
        this.bidFourVolumn = bidFourVolumn;
    }

    public String getBidFive() {
        return bidFive;
    }

    public void setBidFive(String bidFive) {
        this.bidFive = bidFive;
    }

    public String getBidFiveVolumn() {
        return bidFiveVolumn;
    }

    public void setBidFiveVolumn(String bidFiveVolumn) {
        this.bidFiveVolumn = bidFiveVolumn;
    }

    public String getAskOne() {
        return askOne;
    }

    public void setAskOne(String askOne) {
        this.askOne = askOne;
    }

    public String getAskOneVolumn() {
        return askOneVolumn;
    }

    public void setAskOneVolumn(String askOneVolumn) {
        this.askOneVolumn = askOneVolumn;
    }

    public String getAskTwo() {
        return askTwo;
    }

    public void setAskTwo(String askTwo) {
        this.askTwo = askTwo;
    }

    public String getAskTwoVolumn() {
        return askTwoVolumn;
    }

    public void setAskTwoVolumn(String askTwoVolumn) {
        this.askTwoVolumn = askTwoVolumn;
    }

    public String getAskThree() {
        return askThree;
    }

    public void setAskThree(String askThree) {
        this.askThree = askThree;
    }

    public String getAskThreeVolumn() {
        return askThreeVolumn;
    }

    public void setAskThreeVolumn(String askThreeVolumn) {
        this.askThreeVolumn = askThreeVolumn;
    }

    public String getAskFour() {
        return askFour;
    }

    public void setAskFour(String askFour) {
        this.askFour = askFour;
    }

    public String getAskFourVolumn() {
        return askFourVolumn;
    }

    public void setAskFourVolumn(String askFourVolumn) {
        this.askFourVolumn = askFourVolumn;
    }

    public String getAskFive() {
        return askFive;
    }

    public void setAskFive(String askFive) {
        this.askFive = askFive;
    }

    public String getAskFiveVolumn() {
        return askFiveVolumn;
    }

    public void setAskFiveVolumn(String askFiveVolumn) {
        this.askFiveVolumn = askFiveVolumn;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public String getUpAndDown() {
        return upAndDown;
    }

    public void setUpAndDown(String upAndDown) {
        this.upAndDown = upAndDown;
    }

    public String getPercentUpAndDown() {
        return percentUpAndDown;
    }

    public void setPercentUpAndDown(String percentUpAndDown) {
        this.percentUpAndDown = percentUpAndDown;
    }

    public String getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(String highestPrice) {
        this.highestPrice = highestPrice;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getNewestPrice() {
        return newestPrice;
    }

    public void setNewestPrice(String newestPrice) {
        this.newestPrice = newestPrice;
    }

    public String getMakeDealVolumn() {
        return makeDealVolumn;
    }

    public void setMakeDealVolumn(String makeDealVolumn) {
        this.makeDealVolumn = makeDealVolumn;
    }

    public String getMakeDealPrice() {
        return makeDealPrice;
    }

    public void setMakeDealPrice(String makeDealPrice) {
        this.makeDealPrice = makeDealPrice;
    }

    public String getTurnOverRate() {
        return turnOverRate;
    }

    public void setTurnOverRate(String turnOverRate) {
        this.turnOverRate = turnOverRate;
    }

    public String getpERate() {
        return pERate;
    }

    public void setpERate(String pERate) {
        this.pERate = pERate;
    }

    public String getUnKnowPara1() {
        return unKnowPara1;
    }

    public void setUnKnowPara1(String unKnowPara1) {
        this.unKnowPara1 = unKnowPara1;
    }

    public String getHighestPrice2() {
        return highestPrice2;
    }

    public void setHighestPrice2(String highestPrice2) {
        this.highestPrice2 = highestPrice2;
    }

    public String getLowestPrice2() {
        return lowestPrice2;
    }

    public void setLowestPrice2(String lowestPrice2) {
        this.lowestPrice2 = lowestPrice2;
    }

    public String getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(String amplitude) {
        this.amplitude = amplitude;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public String getTotalMarketValue() {
        return totalMarketValue;
    }

    public void setTotalMarketValue(String totalMarketValue) {
        this.totalMarketValue = totalMarketValue;
    }

    public String getpBRate() {
        return pBRate;
    }

    public void setpBRate(String pBRate) {
        this.pBRate = pBRate;
    }

    public String getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(String limitPrice) {
        this.limitPrice = limitPrice;
    }

    public String getPriceLimit() {
        return priceLimit;
    }

    public void setPriceLimit(String priceLimit) {
        this.priceLimit = priceLimit;
    }

    public String getQuantityRate() {
        return quantityRate;
    }

    public void setQuantityRate(String quantityRate) {
        this.quantityRate = quantityRate;
    }

    public String getUnKnownParam2() {
        return unKnownParam2;
    }

    public void setUnKnownParam2(String unKnownParam2) {
        this.unKnownParam2 = unKnownParam2;
    }

    public String getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(String averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getDynamicPriceEarningsRatio() {
        return dynamicPriceEarningsRatio;
    }

    public void setDynamicPriceEarningsRatio(String dynamicPriceEarningsRatio) {
        this.dynamicPriceEarningsRatio = dynamicPriceEarningsRatio;
    }

    public String getStaticPriceEarningRatio() {
        return staticPriceEarningRatio;
    }

    public void setStaticPriceEarningRatio(String staticPriceEarningRatio) {
        this.staticPriceEarningRatio = staticPriceEarningRatio;
    }
}

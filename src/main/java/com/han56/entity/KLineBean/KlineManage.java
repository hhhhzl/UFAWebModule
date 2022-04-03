package com.han56.entity.KLineBean;

/**
 * @author han56
 * @description 功能描述：K线数据库管理表实体类
 * @create 2022/3/31 下午3:35
 */
public class KlineManage {

    //key id
    private Integer id;

    //市场代号
    private String marketCode;

    //股票代号
    private String stockCode;

    //股票名称
    private String stockName;

    //K线种类
    private String klineType;

    //对应类别的K线数据表
    private String klineDataTableName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getKlineType() {
        return klineType;
    }

    public void setKlineType(String klineType) {
        this.klineType = klineType;
    }

    public String getKlineDataTableName() {
        return klineDataTableName;
    }

    public void setKlineDataTableName(String klineDataTableName) {
        this.klineDataTableName = klineDataTableName;
    }
}

package com.han56.service.subscribe;

import com.alibaba.fastjson.JSON;
import com.han56.controller.OrDerBookWsController;
import com.han56.entity.orderBookBean.OrderBookS2C;
import com.han56.entity.tcDataSource.TcOrderBookBean;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author han56
 * @description 功能描述
 * @create 2022/3/8 下午2:24
 */
@Slf4j
@Component
public class SubScribe {

    @Autowired
    private RedissonClient redissonClient;

    /*
    * @Param
    * 1.marketCode:String 市场代码 21沪市 22深股
    * 2.stockCode:String 股票代码
    * */
    public void subscribeOrderBook(String marketCode, String stockCode){
        String orderParm = "orderbook"+"-"+marketCode+"-"+stockCode;
        RTopic rTopic = redissonClient.getTopic(orderParm);
        // 接收订阅的消息
        rTopic.addListener(TcOrderBookBean.class, new MessageListener<TcOrderBookBean>() {
            // 接受订阅的消息
            @Override
            public void onMessage(CharSequence charSequence, TcOrderBookBean tcOrderBookBean) {
                //log.info("接受到消息主题={}，内容={}",charSequence,orderBookS2C.toString());
                OrDerBookWsController.broadCast( JSON.toJSONString(tcOrderBookBean),orderParm);
            }
        });
    }

}

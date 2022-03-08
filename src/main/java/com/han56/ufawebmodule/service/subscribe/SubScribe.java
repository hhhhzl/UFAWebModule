package com.han56.ufawebmodule.service.subscribe;

import com.alibaba.fastjson.JSON;
import com.han56.ufawebmodule.controller.OrDerBookWsController;
import com.han56.ufawebmodule.entity.orderBookBean.OrderBookS2C;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.websocket.Session;

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

    private Session session;
    /*
    * @Param
    * 1.marketCode:String 市场代码 21沪市 22深股
    * 2.stockCode:String 股票代码
    * */
    public void subscribeOrderBook(String marketCode, String stockCode, Session session){
        this.session=session;
        String orderParm = "orderbook"+"-"+marketCode+"-"+stockCode;
        RTopic rTopic = redissonClient.getTopic(orderParm);
        // 接收订阅的消息
        rTopic.addListener(OrderBookS2C.class, (charSequence, orderBookS2C) -> {
            log.info("接受到消息股票={}，内容={}",charSequence,orderBookS2C);
            OrDerBookWsController.broadCast(orderParm, JSON.toJSONString(orderBookS2C));
        });
    }

}

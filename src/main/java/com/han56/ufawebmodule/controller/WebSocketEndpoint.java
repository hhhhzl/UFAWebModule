package com.han56.ufawebmodule.controller;

import com.han56.ufawebmodule.utils.SpringUtils;
import com.han56.ufawebmodule.utils.listener.RedisMessageListener;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.web.bind.annotation.RestController;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author han56
 * @description 功能描述：用于与前端交互
 * @create 2022/3/3 上午9:45
 */
@ServerEndpoint("/socket/{userId}/{topic}")
@RestController
public class WebSocketEndpoint {

    /*
    * 用来记录当前连接数量的变量
    * */
    private static volatile int onlineCount = 0;

    /*
    * 采用线程安全的Set来存放每个客户端对应的MyWebSocket对象
    * */
    private static CopyOnWriteArraySet<WebSocketEndpoint> webSocketSet = new CopyOnWriteArraySet<>();

    /*
    * 与某个客户端的连接会话，需要通过它与客户端进行数据收发
    * */
    private Session session;

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEndpoint.class);

    //用来引入刚才在WebSocketConfig注入的类
    private RedisMessageListenerContainer container = SpringUtils.getBean("container");

    //自定义消息发送器
    private RedisMessageListener listener;

    @OnOpen
    public void onOpen(Session session, @PathParam("userId")String userId,
                       @PathParam("topic")String topic)throws Exception{
        LOGGER.info("打开Socke连接，userId:"+userId+",basicQot:00700"+topic);
        this.session=session;
        //webSocketSet存放当前对象
        webSocketSet.add(this);
        //在线人数加一
        addOnlineCount();
        listener=new RedisMessageListener();
        //放入session
        listener.setSession(session);
        //放入用户ID
        listener.setUserId(userId);
        //放入在线人数
        listener.setOnlineCount(getOnlineCount());
        container.addMessageListener(listener,new PatternTopic(topic));
    }

    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        subOnlineCount();
        getOnlineCount();
        container.removeMessageListener(listener);
        LOGGER.info("关闭了WebSocket连接");
    }

    @OnMessage
    public void onMessage(String message,Session session){
        getOnlineCount();
        LOGGER.info("收到一条数据消息"+message+session.getId());
        try {
            this.sendMessage(message);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("控制层发送消息出现异常");
        }
    }

    @OnError
    public void onError(Session session,Throwable errors){
        LOGGER.error("websocket连接错误",errors);
    }

    /*
    * 发送数据方法
    * */
    public void sendMessage(String msg) throws IOException {
        if (this.session.isOpen()){
            getOnlineCount();
            this.session.getBasicRemote().sendText("Send Message from Server"+msg);
        }
    }

    /*
    * 获取当前用户数量
    * */
    public static synchronized Integer getOnlineCount(){
        System.out.println(new Date()+"在线人数为:"+onlineCount);
        return onlineCount;
    }

    /*
    * 用户上线：在线活跃人数加一
    * */
    public static synchronized void addOnlineCount(){
        WebSocketEndpoint.onlineCount++;
    }

    /*
    * 用户下线：在线活跃人数减一
    * */
    public static synchronized void subOnlineCount(){
        WebSocketEndpoint.onlineCount--;
    }



}

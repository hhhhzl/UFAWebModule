package com.han56.ufawebmodule.utils.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;

/**
 * @author han56
 * @description 功能描述：监听频道发布的消息并传给WebSocket
 * @create 2022/3/3 上午9:27
 */
@Component
public class RedisMessageListener implements MessageListener {

    //用户Session 不同于httpSession
    private Session session;

    //用户id
    private String userId;

    //在线人数
    private Integer onlineCount;

    //上述属性的Getter And Setter


    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(Integer onlineCount) {
        this.onlineCount = onlineCount;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {

        String channel = new String(message.getChannel());

        String topic =  new String(pattern);

        String msg = new String(message.getBody());

        if (null!=session&&session.isOpen()){
            //用户在线且订阅了主题
            try {
                synchronized (session){
                    msg="用户ID"+userId+"你好，您正在与:"+onlineCount+"人在线，"+"你们共同订阅了基本行情"+topic
                            + "内容是:"+msg;
                    System.out.println(msg);
                    session.getBasicRemote().sendText(msg);
                }
            }catch (IOException e){
                e.printStackTrace();
                System.out.println("发送信息出现异常");
            }
        }else if (userId!=null){
            //用户不在线，但是也订阅了该话题
            System.out.println("用户"+userId+"当前不在线，所以不会推送消息");
            doLiXian(userId);
        }
    }

    public void doLiXian(String userId){
        System.out.println("不在线的话做出的操作，比如发送邮件提示上线等操作");
    }

}

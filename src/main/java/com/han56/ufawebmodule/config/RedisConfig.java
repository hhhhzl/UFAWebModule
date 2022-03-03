package com.han56.ufawebmodule.config;

import com.han56.ufawebmodule.utils.listener.RedisMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author han56
 * @description 功能描述：Spring 连接 Redis 服务器
 * @create 2022/2/20 下午12:27
 */
@Configuration
public class RedisConfig {

   @Bean("container")
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter){
       RedisMessageListenerContainer container = new RedisMessageListenerContainer();
       LettuceConnectionFactory lettuceConnectionFactory = (LettuceConnectionFactory) connectionFactory;
       //设置存储的节点
       lettuceConnectionFactory.setDatabase(0);
       container.setConnectionFactory(lettuceConnectionFactory);
       //这里要设定监听的主题是chat
       container.addMessageListener(listenerAdapter, new PatternTopic("basicQot:00700"));
       return container;
   }

   @Bean
   MessageListenerAdapter listenerAdapter(RedisMessageListener receiver) {
      return new MessageListenerAdapter(receiver);
   }
   @Bean
   StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
      return new StringRedisTemplate(connectionFactory);
   }


}

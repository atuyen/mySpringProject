package com.seasolutions.stock_management.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

/*********************************************************** 
 * REGIS CONFIGURATIONS
 ***********************************************************/

@Configuration
public class RedisConfiguration {
	@Value("${spring.redis.host}")
	private String redisHostName;

	@Value("${spring.redis.port}")
	private int redisPort;
	
	@Value("${spring.redis.timeout}")
	private int redisTimeOut;
	
//	@Value("${spring.redis.password}")
//	private String redisPassword;
	
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHostName, redisPort);
//	    redisStandaloneConfiguration.setPassword(RedisPassword.of(redisPassword));
//	    return new JedisConnectionFactory(redisStandaloneConfiguration);
		JedisConnectionFactory temp=	new JedisConnectionFactory(redisStandaloneConfiguration);
	    temp.setTimeout(redisTimeOut);
		return temp;
	}
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
	    final RedisTemplate<String, Object> template = new RedisTemplate<>();
	    template.setConnectionFactory(jedisConnectionFactory());
	    template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
	    return template;
	}
	
	@Bean
    ChannelTopic topic() {
        return new ChannelTopic("spring:boot:redis");
    }
	
	@Bean
    MessagePublisher redisPublisher() {
        return new MessagePublisherImpl(redisTemplate(), topic());
    }
	
	@Bean
	MessageListenerAdapter messageListener() {
	    return new MessageListenerAdapter(new MessageSubscriber());
	}
	
	@Bean
    RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(messageListener(), topic());
        return container;
    }
}
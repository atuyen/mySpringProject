package com.seasolutions.stock_management.redis;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

//@Repository
public class RedisRepositoryImpl implements RedisRepository{
	private static final String APP_KEY = "com.gotadi.b2b";
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Object> hashOperations;

    @Autowired
    public RedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }
    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }
    public void put(final String key, final Object item) {
        hashOperations.put(APP_KEY, key, item);
    }
    public void delete(final String key) {
        hashOperations.delete(APP_KEY, key);
    }
    public Object find(final String key){
        return hashOperations.get(APP_KEY, key);
    }
    public Map<String, Object> findAll(){
        return hashOperations.entries(APP_KEY);
    }
}
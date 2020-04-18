package com.seasolutions.stock_management.redis;

import java.util.Map;

public interface RedisRepository {
	Map<String, Object> findAll();
    void put(String key, Object item);
    void delete(String key);
    Object find(String key);
}

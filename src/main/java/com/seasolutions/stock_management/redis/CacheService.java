package com.seasolutions.stock_management.redis;

import java.util.List;
import java.util.Map;



public interface CacheService {
	void put(String key, Object item);

	void delete(String key);

	Object find(String key);

	Map<String, Object> findAll();

}

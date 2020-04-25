package com.seasolutions.stock_management.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



//@Service
public class CacheServiceImpl implements CacheService {

	@Autowired
	private RedisRepository redisRepository;

	@PostConstruct
	private void init() {
		final CacheService self = this;
		new Thread(new Runnable() {
			@Override
			public void run() {

			}
		});
	}
	@Override
	public void put(String key, Object item) {
		this.redisRepository.put(key, item);
	}

	@Override
	public void delete(String key) {
		this.redisRepository.delete(key);
	}

	@Override
	public Object find(String key) {
		return this.redisRepository.find(key);
	}

	@Override
	public Map<String, Object> findAll() {
		return this.redisRepository.findAll();
	}


}

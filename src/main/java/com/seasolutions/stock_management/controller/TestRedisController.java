package com.seasolutions.stock_management.controller;
import com.seasolutions.stock_management.redis.CacheService;
import com.seasolutions.stock_management.security.annotation.Authorized;
import com.seasolutions.stock_management.security.annotation.Unauthenticated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRedisController {
   // https://laptrinh.vn/books/redis

    @Autowired
    CacheService cacheService;



    @GetMapping(path = "/redis/{text}")
    @Unauthenticated
    public String testRedis(@PathVariable String text){
        cacheService.put("key1",text);
        String s = cacheService.find("key1").toString();
        return  s;
    }
}

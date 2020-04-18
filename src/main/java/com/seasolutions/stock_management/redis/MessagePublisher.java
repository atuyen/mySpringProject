package com.seasolutions.stock_management.redis;

public interface MessagePublisher {
    void publish(final String message);
}
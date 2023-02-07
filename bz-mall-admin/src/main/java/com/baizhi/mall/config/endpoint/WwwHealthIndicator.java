package com.baizhi.mall.config.endpoint;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;

@Component
public class WwwHealthIndicator extends AbstractHealthIndicator {
    private final static String BAIDU_HOST = "www.baidu.com";
    private final static int TIME_OUT = 3000;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        if (ping()) {
            builder.withDetail("message", "当前服务可以访问万维网").up();
        } else {
            builder.withDetail("message", "当前服务不可以访问万维网").outOfService();
        }
    }

    private boolean ping() {
        try {
            return InetAddress.getByName(BAIDU_HOST).isReachable(TIME_OUT);
        } catch (IOException e) {
            return false;
        }
    }
}
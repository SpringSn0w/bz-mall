package com.baizhi.mall.security.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "secure.ignored")
@Data
public class IgnoreUrlsProperties {
    private List<String> urls;
}

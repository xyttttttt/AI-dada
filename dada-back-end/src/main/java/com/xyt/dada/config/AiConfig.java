package com.xyt.dada.config;

import com.zhipu.oapi.ClientV4;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ai")
@Data
public class AiConfig {

    /**
     * apiKey,从平台获取
     */
    private String apiKey;

    //初始化客户端bean
    @Bean
    public ClientV4 getClientV4() {
       return new ClientV4.Builder(apiKey).build();
    }
}

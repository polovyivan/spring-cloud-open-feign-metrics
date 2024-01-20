package com.polovyi.ivan.configuration;


import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PatchConfigs {

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }
}

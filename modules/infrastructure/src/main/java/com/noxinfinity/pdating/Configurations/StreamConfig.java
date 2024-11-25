package com.noxinfinity.pdating.Configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StreamConfig {
    @Value("${stream.secret}")
    private String streamSecretKey;

    @Value("${stream.key}")
    private String streamKey;

    @Bean
    public String streamSecretKey() {
        return streamSecretKey;
    }

    @Bean
    public String streamKey(){
        return streamKey;
    }
}

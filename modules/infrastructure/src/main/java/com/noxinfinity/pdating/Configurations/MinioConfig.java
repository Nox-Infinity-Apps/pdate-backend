package com.noxinfinity.pdating.Configurations;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class MinioConfig {
        @Value("${minio.url}")
        private String url;

        @Value("${minio.access.name}")
        private String accessKey;

        @Value("${minio.access.secret}")
        private String accessSecret;

        @Bean
        public MinioClient minioClient() {
            return MinioClient.builder()
                    .endpoint(url)
                    .credentials(accessKey, accessSecret)
                    .build();
        }
}

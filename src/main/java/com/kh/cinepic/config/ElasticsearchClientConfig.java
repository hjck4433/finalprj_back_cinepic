package com.kh.cinepic.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

@Configuration
public class ElasticsearchClientConfig {
    @Value("${spring.elasticsearch.uris}")
    private String elasticsearchUrI;

    @Value("${spring.elasticsearch.username}")
    private String elasticsearchUsername;

    @Value("${spring.elasticsearch.password}")
    private String elasticsearchPassword;

    @Bean
    public RestHighLevelClient client() { // RestHighClient 객체를 생성하여 Bean으로 등록
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(elasticsearchUrI)
                .withBasicAuth(elasticsearchUsername, elasticsearchPassword)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }

}

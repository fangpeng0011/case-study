package com.cast.springbootelasticsearch.config;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName: RestHighConfig
 * @Author : fangpeng
 * @Date :2023/12/26  15:59
 * @Description: TODO
 * @Version :1.0
 */
@SpringBootConfiguration
public class RestHighConfig {

    @Value("${spring.elasticsearch.rest.uris}")
    private String serverUrl;

    @Value("${spring.elasticsearch.rest.username}")
    private String username;

    @Value("${spring.elasticsearch.rest.password}")
    private String password;

    @Bean
    public RestClient restClient() {
        return RestClient.builder(HttpHost.create(serverUrl))
            .setDefaultHeaders(new Header[]{
                new BasicHeader("username", username),
                new BasicHeader("password", password)
            }).build();
    }

}
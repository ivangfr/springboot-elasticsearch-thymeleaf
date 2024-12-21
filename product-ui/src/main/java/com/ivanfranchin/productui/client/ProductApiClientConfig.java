package com.ivanfranchin.productui.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ProductApiClientConfig {

    @Value("${product-api.url}")
    private String productApiUrl;

    @Bean
    ProductApiClient productApiClient(RestClient.Builder builder) {
        RestClient restClient = builder.baseUrl(productApiUrl).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(ProductApiClient.class);
    }
}

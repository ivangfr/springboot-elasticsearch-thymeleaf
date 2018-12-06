package com.mycompany.productapi.config;

import com.mycompany.productapi.properties.ApplicationProperties;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableConfigurationProperties(ApplicationProperties.class)
@Configuration
public class ElasticsearchConfig {

    private final ApplicationProperties applicationProperties;

    public ElasticsearchConfig(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean
    Client client() throws UnknownHostException {
        TransportAddress transportAddress = new TransportAddress(
                InetAddress.getByName(applicationProperties.getElasticsearch().getHost()),
                applicationProperties.getElasticsearch().getPort());

        Settings settings = Settings.builder()
                .put("cluster.name", applicationProperties.getElasticsearch().getClustername()).build();

        return new PreBuiltTransportClient(settings).addTransportAddress(transportAddress);
    }

}

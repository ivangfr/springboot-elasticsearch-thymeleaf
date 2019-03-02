package com.mycompany.productapi.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Validated
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    @Valid
    private Elasticsearch elasticsearch = new Elasticsearch();

    @Data
    public static class Elasticsearch {
        @NotBlank
        private String clustername;

        @NotBlank
        private String host;

        @NotNull
        private Integer port;
    }
}

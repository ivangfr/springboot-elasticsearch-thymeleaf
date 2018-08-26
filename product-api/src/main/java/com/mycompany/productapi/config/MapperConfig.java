package com.mycompany.productapi.config;

import com.mycompany.productapi.dto.UpdateProductDto;
import com.mycompany.productapi.model.Product;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    MapperFactory mapperFactory() {
        DefaultMapperFactory defaultMapperFactory = new DefaultMapperFactory.Builder().useAutoMapping(true).build();

        defaultMapperFactory.classMap(UpdateProductDto.class, Product.class).mapNulls(false).byDefault().register();

        return defaultMapperFactory;
    }

    @Bean
    MapperFacade mapperFacade() {
        return mapperFactory().getMapperFacade();
    }
}


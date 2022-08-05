package com.ivanfranchin.productui.mapper;

import com.ivanfranchin.productui.client.dto.ProductDto;
import com.ivanfranchin.productui.client.dto.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toProductDto(Product product);
}

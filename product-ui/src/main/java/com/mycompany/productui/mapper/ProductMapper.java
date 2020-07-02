package com.mycompany.productui.mapper;

import com.mycompany.productui.client.dto.Product;
import com.mycompany.productui.client.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toProductDto(Product product);

}

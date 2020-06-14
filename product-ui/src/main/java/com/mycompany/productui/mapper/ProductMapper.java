package com.mycompany.productui.mapper;

import com.mycompany.productui.client.dto.Product;
import com.mycompany.productui.client.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductMapper {

    ProductDto toProductDto(Product product);

}

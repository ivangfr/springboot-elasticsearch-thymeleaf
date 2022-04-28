package com.mycompany.productapi.mapper;

import com.mycompany.productapi.model.Product;
import com.mycompany.productapi.model.Review;
import com.mycompany.productapi.rest.dto.AddReviewRequest;
import com.mycompany.productapi.rest.dto.CreateProductRequest;
import com.mycompany.productapi.rest.dto.UpdateProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reference", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "created", ignore = true)
    Product toProduct(CreateProductRequest createProductRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reference", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "created", ignore = true)
    void updateProductFromRequest(UpdateProductRequest updateProductRequest, @MappingTarget Product product);

    @Mapping(target = "created", ignore = true)
    Review toReview(AddReviewRequest addReviewRequest);
}

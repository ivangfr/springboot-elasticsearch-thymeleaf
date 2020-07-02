package com.mycompany.productapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddReviewDto {

    @Schema(title = "comment about product", example = "This product is very good!")
    @NotBlank
    private String comment;

    @Schema(title = "product evaluation (from 0 to 5)", example = "5")
    @NotNull
    @Min(0)
    @Max(5)
    private Integer stars;

}

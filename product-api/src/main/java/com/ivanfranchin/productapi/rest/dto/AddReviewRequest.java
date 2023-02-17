package com.ivanfranchin.productapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddReviewRequest {

    @Schema(title = "comment about product", example = "This product is very good!")
    @NotBlank
    private String comment;

    @Schema(title = "product evaluation (from 0 to 5)", example = "5")
    @NotNull
    @Min(0)
    @Max(5)
    private Integer stars;
}

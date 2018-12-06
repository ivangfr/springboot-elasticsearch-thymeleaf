package com.mycompany.productapi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AddReviewDto {

    @ApiModelProperty(value = "comment about product", example = "This product is very good!")
    @NotNull
    @NotEmpty
    private String comment;

    @ApiModelProperty(position = 2, value = "product evaluation (from 0 to 5)", example = "5")
    @NotNull
    @Min(0)
    @Max(5)
    private Integer stars;

}

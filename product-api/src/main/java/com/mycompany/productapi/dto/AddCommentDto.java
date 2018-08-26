package com.mycompany.productapi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AddCommentDto {

    @ApiModelProperty(value = "comment about the product", example = "This product is very good!")
    @NotNull
    @NotEmpty
    private String text;

    @ApiModelProperty(value = "product rate", example = "5")
    @NotNull
    @Min(0)
    @Max(5)
    private Integer numStar;

}

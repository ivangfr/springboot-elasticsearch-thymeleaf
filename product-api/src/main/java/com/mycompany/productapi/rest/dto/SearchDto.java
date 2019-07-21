package com.mycompany.productapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SearchDto {

    @ApiModelProperty(value = "text to be searched", example = "DDR4")
    @NotBlank
    private String text;

}

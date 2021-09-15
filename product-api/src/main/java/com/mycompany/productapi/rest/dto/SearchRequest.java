package com.mycompany.productapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SearchRequest {

    @Schema(title = "text to be searched", example = "DDR4")
    @NotBlank
    private String text;
}

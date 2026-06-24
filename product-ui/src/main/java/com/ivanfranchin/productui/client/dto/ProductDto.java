package com.ivanfranchin.productui.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

  @NotBlank private String name;

  @NotBlank private String description;

  @NotNull @Positive private BigDecimal price;

  private Set<String> categories;
}

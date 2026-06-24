package com.ivanfranchin.productui.client.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Review {

  @NotBlank private String comment;

  @NotNull @Min(0) @Max(5) private Short stars;

  private Date created;
}

package com.ivanfranchin.productui.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.ivanfranchin.productui.client.ProductApiClient;
import com.ivanfranchin.productui.client.dto.Product;
import com.ivanfranchin.productui.client.dto.Review;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductReviewController.class)
class ProductReviewControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private ProductApiClient productApiClient;

  @Test
  void addReviewShouldRedirect() throws Exception {
    mockMvc
        .perform(post("/products/123/review").param("comment", "Great product").param("stars", "5"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/products/123/view"));

    verify(productApiClient).addProductReview(eq("123"), any(Review.class));
  }

  @Test
  void addReviewWithValidationErrorShouldReRenderView() throws Exception {
    Product product =
        new Product(
            "123", "REF-1", "Name", "Desc", BigDecimal.TEN, Set.of("cat"), List.of(), new Date());
    when(productApiClient.getProduct("123")).thenReturn(product);

    mockMvc
        .perform(post("/products/123/review").param("comment", "").param("stars", "5"))
        .andExpect(status().isOk())
        .andExpect(view().name("productView"));

    verify(productApiClient).getProduct("123");
    verifyNoMoreInteractions(productApiClient);
  }

  @Test
  void addReviewWithInvalidStarsShouldReRenderView() throws Exception {
    Product product =
        new Product(
            "123", "REF-1", "Name", "Desc", BigDecimal.TEN, Set.of("cat"), List.of(), new Date());
    when(productApiClient.getProduct("123")).thenReturn(product);

    mockMvc
        .perform(post("/products/123/review").param("comment", "Great").param("stars", "10"))
        .andExpect(status().isOk())
        .andExpect(view().name("productView"));

    verify(productApiClient).getProduct("123");
    verifyNoMoreInteractions(productApiClient);
  }
}

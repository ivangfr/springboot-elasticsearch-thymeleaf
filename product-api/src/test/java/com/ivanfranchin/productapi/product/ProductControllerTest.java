package com.ivanfranchin.productapi.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ivanfranchin.productapi.product.dto.CreateProductRequest;
import com.ivanfranchin.productapi.product.dto.SearchRequest;
import com.ivanfranchin.productapi.product.dto.UpdateProductRequest;
import com.ivanfranchin.productapi.product.exception.ProductNotFoundException;
import com.ivanfranchin.productapi.product.model.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private JsonMapper jsonMapper;

  @MockitoBean private ProductService productService;

  @Test
  void getProductsShouldReturnPage() throws Exception {
    when(productService.listProductsByPage(any())).thenReturn(new PageImpl<>(List.of()));

    mockMvc
        .perform(get("/api/products"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray());
  }

  @Test
  void getProductWhenFoundShouldReturnProduct() throws Exception {
    Product product = new Product();
    product.setId("123");
    product.setName("Test");
    when(productService.validateAndGetProductById("123")).thenReturn(product);

    mockMvc
        .perform(get("/api/products/123"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("123"))
        .andExpect(jsonPath("$.name").value("Test"));
  }

  @Test
  void getProductWhenNotFoundShouldReturn404() throws Exception {
    when(productService.validateAndGetProductById("123"))
        .thenThrow(new ProductNotFoundException("123"));

    mockMvc.perform(get("/api/products/123")).andExpect(status().isNotFound());
  }

  @Test
  void createProductShouldReturn201() throws Exception {
    Product product = new Product();
    product.setId("1");
    product.setName("New");
    product.setReference("SBES@abcd-12345");
    product.setPrice(BigDecimal.TEN);
    when(productService.createProduct(any())).thenReturn(product);

    CreateProductRequest request =
        new CreateProductRequest("New", "Desc", BigDecimal.TEN, Set.of("cat1"));

    mockMvc
        .perform(
            post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value("1"))
        .andExpect(jsonPath("$.reference").value("SBES@abcd-12345"));
  }

  @Test
  void updateProductWhenFoundShouldReturnUpdated() throws Exception {
    Product existing = new Product();
    existing.setId("123");
    existing.setName("Old");
    when(productService.validateAndGetProductById("123")).thenReturn(existing);
    when(productService.updateProduct(any())).thenReturn(existing);

    UpdateProductRequest request = new UpdateProductRequest("New name", null, null, null);

    mockMvc
        .perform(
            put("/api/products/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)))
        .andExpect(status().isOk());
  }

  @Test
  void deleteProductShouldReturn204() throws Exception {
    Product product = new Product();
    product.setId("123");
    when(productService.validateAndGetProductById("123")).thenReturn(product);

    mockMvc.perform(delete("/api/products/123")).andExpect(status().isNoContent());

    verify(productService).deleteProduct(product);
  }

  @Test
  void searchProductsShouldReturnPage() throws Exception {
    when(productService.search(any(), any())).thenReturn(new PageImpl<>(List.of()));

    SearchRequest request = new SearchRequest("test");

    mockMvc
        .perform(
            put("/api/products/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray());
  }
}

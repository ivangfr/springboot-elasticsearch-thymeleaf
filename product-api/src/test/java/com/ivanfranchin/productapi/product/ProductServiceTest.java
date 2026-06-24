package com.ivanfranchin.productapi.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ivanfranchin.productapi.product.exception.ProductNotFoundException;
import com.ivanfranchin.productapi.product.model.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(ProductService.class)
class ProductServiceTest {

  @Autowired private ProductService productService;

  @MockitoBean private ProductRepository productRepository;

  @Test
  void listProductsByPageShouldReturnPage() {
    Pageable pageable = PageRequest.of(0, 10);
    Page<Product> page = new PageImpl<>(List.of(new Product()));
    when(productRepository.findAll(pageable)).thenReturn(page);

    Page<Product> result = productService.listProductsByPage(pageable);

    assertThat(result).isSameAs(page);
  }

  @Test
  void validateAndGetProductByIdWhenFoundShouldReturnProduct() {
    Product product = new Product();
    product.setId("123");
    when(productRepository.findById("123")).thenReturn(Optional.of(product));

    Product result = productService.validateAndGetProductById("123");

    assertThat(result).isSameAs(product);
  }

  @Test
  void validateAndGetProductByIdWhenNotFoundShouldThrow() {
    when(productRepository.findById("123")).thenReturn(Optional.empty());

    assertThatThrownBy(() -> productService.validateAndGetProductById("123"))
        .isInstanceOf(ProductNotFoundException.class)
        .hasMessage("Product id '123' not found");
  }

  @Test
  void createProductShouldSetReferenceAndSave() {
    Product product = new Product();
    product.setName("Test");
    product.setPrice(BigDecimal.TEN);
    when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    Product result = productService.createProduct(product);

    assertThat(result.getReference()).startsWith("SBES@");
    assertThat(result.getName()).isEqualTo("Test");
    verify(productRepository).save(product);
  }

  @Test
  void updateProductShouldSaveWithoutChangingReference() {
    Product product = new Product();
    product.setId("123");
    product.setReference("SBES@abcd-12345");
    product.setName("Old name");
    when(productRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    Product result = productService.updateProduct(product);

    assertThat(result.getReference()).isEqualTo("SBES@abcd-12345");
    assertThat(result.getId()).isEqualTo("123");
    verify(productRepository).save(product);
  }

  @Test
  void deleteProductShouldDelete() {
    Product product = new Product();
    productService.deleteProduct(product);
    verify(productRepository).delete(product);
  }

  @Test
  void searchShouldDelegateToRepository() {
    String text = "test";
    Pageable pageable = PageRequest.of(0, 10);
    Page<Product> page = new PageImpl<>(List.of());
    when(productRepository.findByReferenceOrNameOrDescription(text, text, text, pageable))
        .thenReturn(page);

    Page<Product> result = productService.search(text, pageable);

    assertThat(result).isSameAs(page);
  }
}

package com.ivanfranchin.productui.controller;

import com.ivanfranchin.productui.client.ProductApiClient;
import com.ivanfranchin.productui.client.dto.MyPage;
import com.ivanfranchin.productui.client.dto.Product;
import com.ivanfranchin.productui.client.dto.SearchDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductApiClient productApiClient;

    @Test
    void getProductsShouldReturnProductsView() throws Exception {
        MyPage<Product> page = new MyPage<>(List.of(), 0, 0, 10, 0, true, true);
        when(productApiClient.listProductsByPage(isNull(), isNull(), eq("created,desc"))).thenReturn(page);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attributeExists("searchDto"))
                .andExpect(model().attributeExists("currentPage"));
    }

    @Test
    void getRootShouldReturnProductsView() throws Exception {
        MyPage<Product> page = new MyPage<>(List.of(), 0, 0, 10, 0, true, true);
        when(productApiClient.listProductsByPage(isNull(), isNull(), eq("created,desc"))).thenReturn(page);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"));
    }

    @Test
    void searchProductsWithTextShouldCallSearch() throws Exception {
        MyPage<Product> page = new MyPage<>(List.of(), 0, 0, 10, 0, true, true);
        when(productApiClient.searchProductsByPage(any(), isNull(), isNull(), eq("created,desc"))).thenReturn(page);

        mockMvc.perform(get("/").param("text", "laptop"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attributeExists("products"));

        verify(productApiClient).searchProductsByPage(any(SearchDto.class), isNull(), isNull(), eq("created,desc"));
    }

    @Test
    void searchProductsWithEmptyTextShouldCallList() throws Exception {
        MyPage<Product> page = new MyPage<>(List.of(), 0, 0, 10, 0, true, true);
        when(productApiClient.listProductsByPage(isNull(), isNull(), eq("created,desc"))).thenReturn(page);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attributeExists("products"));

        verify(productApiClient).listProductsByPage(isNull(), isNull(), eq("created,desc"));
    }

    @Test
    void editProductFormShouldReturnEditView() throws Exception {
        Product product = new Product("123", "REF-1", "Name", "Desc", BigDecimal.TEN, Set.of("cat"),
                List.of(), new Date());
        when(productApiClient.getProduct("123")).thenReturn(product);

        mockMvc.perform(get("/products/123/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("productEdit"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attributeExists("productDto"));
    }

    @Test
    void viewProductFormShouldReturnViewView() throws Exception {
        Product product = new Product("123", "REF-1", "Name", "Desc", BigDecimal.TEN, Set.of("cat"),
                List.of(), new Date());
        when(productApiClient.getProduct("123")).thenReturn(product);

        mockMvc.perform(get("/products/123/view"))
                .andExpect(status().isOk())
                .andExpect(view().name("productView"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attributeExists("review"));
    }

    @Test
    void createProductFormShouldReturnCreateView() throws Exception {
        mockMvc.perform(get("/products/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("productCreate"))
                .andExpect(model().attributeExists("productDto"));
    }

    @Test
    void createProductShouldRedirect() throws Exception {
        mockMvc.perform(post("/products")
                        .param("name", "New Laptop")
                        .param("description", "A great laptop")
                        .param("price", "999.99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(productApiClient).createProduct(any());
    }

    @Test
    void createProductWithValidationErrorShouldReturnForm() throws Exception {
        mockMvc.perform(post("/products")
                        .param("name", "")
                        .param("description", "Desc")
                        .param("price", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("productCreate"));
    }

    @Test
    void updateProductShouldRedirect() throws Exception {
        mockMvc.perform(post("/products/123")
                        .param("name", "Updated")
                        .param("description", "Updated desc")
                        .param("price", "49.99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(productApiClient).updateProduct(eq("123"), any());
    }

    @Test
    void updateProductWithValidationErrorShouldReturnForm() throws Exception {
        Product product = new Product("123", "REF-1", "Name", "Desc", BigDecimal.TEN,
                Set.of("cat"), List.of(), new Date());
        when(productApiClient.getProduct("123")).thenReturn(product);

        mockMvc.perform(post("/products/123")
                        .param("name", "")
                        .param("description", "Desc")
                        .param("price", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("productEdit"));
    }

    @Test
    void deleteProductShouldRedirect() throws Exception {
        mockMvc.perform(post("/products/123/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(productApiClient).deleteProduct("123");
    }
}

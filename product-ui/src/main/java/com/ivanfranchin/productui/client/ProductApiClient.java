package com.ivanfranchin.productui.client;

import com.ivanfranchin.productui.client.dto.MyPage;
import com.ivanfranchin.productui.client.dto.ProductDto;
import com.ivanfranchin.productui.client.dto.Review;
import com.ivanfranchin.productui.client.dto.SearchDto;
import com.ivanfranchin.productui.client.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("product-api")
public interface ProductApiClient {

    @GetMapping("/api/products")
    MyPage<Product> listProductsByPage(@RequestParam Integer page, @RequestParam Integer size,
                                       @RequestParam String sort);

    @PutMapping("/api/products/search")
    MyPage<Product> searchProductsByPage(@RequestBody SearchDto searchDto, @RequestParam Integer page,
                                         @RequestParam Integer size, @RequestParam String sort);

    @GetMapping("/api/products/{id}")
    Product getProduct(@PathVariable String id);

    @PostMapping("/api/products")
    Product createProduct(@RequestBody ProductDto productDto);

    @PutMapping("/api/products/{id}")
    Product updateProduct(@PathVariable String id, @RequestBody ProductDto productDto);

    @PostMapping("/api/products/{id}/reviews")
    Product addProductReview(@PathVariable String id, @RequestBody Review review);
}

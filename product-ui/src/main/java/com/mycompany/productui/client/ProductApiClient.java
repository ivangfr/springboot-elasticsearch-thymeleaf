package com.mycompany.productui.client;

import com.mycompany.productui.client.dto.MyPage;
import com.mycompany.productui.client.dto.Product;
import com.mycompany.productui.client.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("product-api")
public interface ProductApiClient {

    @GetMapping("/api/v1/products")
    MyPage<Product> listAllByPage(@RequestParam Integer page, @RequestParam Integer size);

    @GetMapping("/api/v1/products/{id}")
    Product getProduct(@PathVariable String id);

    @PostMapping("/api/v1/products")
    Product createProduct(@RequestBody ProductDto productDto);

    // TODO
//    @PatchMapping("/api/v1/products/{id}")
//    Product updateProduct(@PathVariable String id, @RequestBody ProductDto productDto);

}

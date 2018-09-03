package com.mycompany.productui.client;

import com.mycompany.productui.client.dto.MyPage;
import com.mycompany.productui.client.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("product-api")
public interface ProductApiClient {

    @GetMapping("/api/v1/products")
    MyPage<Product> listAllByPage(@RequestParam Integer page, @RequestParam Integer size);

}

package com.mycompany.productui.controller;

import com.mycompany.productui.client.ProductApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class ProductController {

    private final ProductApiClient productApiClient;

    public ProductController(ProductApiClient productApiClient) {
        this.productApiClient = productApiClient;
    }

    @GetMapping("/products")
    public String getProducts(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size, Model model) {
        model.addAttribute("products", productApiClient.listAllByPage(page, size));
        return "products";
    }

}

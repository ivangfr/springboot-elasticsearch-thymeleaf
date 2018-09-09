package com.mycompany.productui.controller;

import com.mycompany.productui.client.ProductApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class ProductController {

    private final ProductApiClient productApiClient;

    public ProductController(ProductApiClient productApiClient) {
        this.productApiClient = productApiClient;
    }

    @GetMapping(value = {"/products", "/"})
    public String getProducts(@RequestParam(required = false) Integer page,
                              @RequestParam(required = false) Integer size, Model model) {
        model.addAttribute("products", productApiClient.listAllByPage(page, size));
        return "products";
    }

    @GetMapping(value = "/products/{id}", params = "action")
    public String getProducts(@PathVariable String id, @RequestParam String action, Model model) {
        model.addAttribute("product", productApiClient.getProduct(id));
        return action.equals("edit") ? "productEdit" : "productView";
    }

}

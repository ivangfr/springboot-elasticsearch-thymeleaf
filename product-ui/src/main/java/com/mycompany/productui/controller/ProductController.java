package com.mycompany.productui.controller;

import com.mycompany.productui.client.ProductApiClient;
import com.mycompany.productui.client.dto.MyPage;
import com.mycompany.productui.client.dto.Product;
import com.mycompany.productui.client.dto.ProductDto;
import com.mycompany.productui.client.dto.Review;
import com.mycompany.productui.client.dto.SearchDto;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class ProductController {

    private final ProductApiClient productApiClient;
    private final MapperFacade mapperFacade;

    public ProductController(ProductApiClient productApiClient, MapperFacade mapperFacade) {
        this.productApiClient = productApiClient;
        this.mapperFacade = mapperFacade;
    }

    @GetMapping(value = {"/products", "/"})
    public String getProducts(@RequestParam(required = false) Integer page,
                              @RequestParam(required = false) Integer size,
                              @RequestParam(required = false, defaultValue = "created,desc") String sort,
                              Model model) {
        model.addAttribute("searchDto", new SearchDto());
        model.addAttribute("products", productApiClient.listProductsByPage(page, size, sort));
        return "products";
    }

    @PostMapping("/products/search")
    public String searchProducts(@RequestParam(required = false) Integer page,
                                 @RequestParam(required = false) Integer size,
                                 @RequestParam(required = false, defaultValue = "created,desc") String sort,
                                 @ModelAttribute SearchDto searchDto,
                                 Model model) {
        MyPage<Product> result;
        if (searchDto.getText().trim().isEmpty()) {
            result = productApiClient.listProductsByPage(page, size, sort);
        } else {
            result = productApiClient.searchProductsByPage(searchDto, page, size, sort);
        }
        model.addAttribute("products", result);
        return "products";
    }

    @GetMapping("/products/{id}/edit")
    public String editProductForm(@PathVariable String id, Model model) {
        Product product = productApiClient.getProduct(id);
        ProductDto productDto = mapperFacade.map(product, ProductDto.class);

        model.addAttribute("productDto", productDto);
        model.addAttribute("product", product);
        return "productEdit";
    }

    @GetMapping("/products/{id}/view")
    public String viewProductForm(@PathVariable String id, Model model) {
        model.addAttribute("review", new Review());
        model.addAttribute("product", productApiClient.getProduct(id));
        return "productView";
    }

    @GetMapping("/products/create")
    public String createProductForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "productCreate";
    }

    @PostMapping("/products")
    public String createProduct(@ModelAttribute ProductDto productDto) {
        productApiClient.createProduct(productDto);
        return "redirect:/";
    }

    @PostMapping("/products/{id}")
    public String updateProduct(@PathVariable String id, @ModelAttribute ProductDto productDto) {
        productApiClient.updateProduct(id, productDto);
        return "redirect:/";
    }

}

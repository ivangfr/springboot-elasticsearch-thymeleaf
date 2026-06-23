package com.ivanfranchin.productui.controller;

import com.ivanfranchin.productui.client.ProductApiClient;
import com.ivanfranchin.productui.client.dto.MyPage;
import com.ivanfranchin.productui.client.dto.Product;
import com.ivanfranchin.productui.client.dto.ProductDto;
import com.ivanfranchin.productui.client.dto.Review;
import com.ivanfranchin.productui.client.dto.SearchDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductApiClient productApiClient;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Set.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text == null || text.isBlank()) {
                    setValue(null);
                } else {
                    setValue(new LinkedHashSet<>(Arrays.asList(text.split(","))));
                }
            }
        });
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
        MyPage<Product> result = searchDto.getText() == null || searchDto.getText().trim().isEmpty() ?
                productApiClient.listProductsByPage(page, size, sort) :
                productApiClient.searchProductsByPage(searchDto, page, size, sort);
        model.addAttribute("products", result);
        return "products";
    }

    @GetMapping("/products/{id}/edit")
    public String editProductForm(@PathVariable String id, Model model) {
        Product product = productApiClient.getProduct(id);
        ProductDto productDto = new ProductDto(product.name(), product.description(), product.price(), product.categories());

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
    public String createProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult result) {
        if (result.hasErrors()) {
            return "productCreate";
        }
        productApiClient.createProduct(productDto);
        return "redirect:/";
    }

    @PostMapping("/products/{id}")
    public String updateProduct(@PathVariable String id, @Valid @ModelAttribute ProductDto productDto,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", productApiClient.getProduct(id));
            return "productEdit";
        }
        productApiClient.updateProduct(id, productDto);
        return "redirect:/";
    }
}

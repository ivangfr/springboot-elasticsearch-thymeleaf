package com.ivanfranchin.productui.controller;

import com.ivanfranchin.productui.client.ProductApiClient;
import com.ivanfranchin.productui.client.dto.MyPage;
import com.ivanfranchin.productui.client.dto.Product;
import com.ivanfranchin.productui.client.dto.ProductDto;
import com.ivanfranchin.productui.client.dto.Review;
import com.ivanfranchin.productui.client.dto.SearchDto;
import jakarta.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class ProductController {

  private final ProductApiClient productApiClient;

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(
        Set.class,
        new PropertyEditorSupport() {
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
  public String getProducts(
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size,
      @RequestParam(required = false, defaultValue = "created,desc") String sort,
      @RequestParam(required = false) String text,
      Model model) {
    SearchDto searchDto = new SearchDto();
    searchDto.setText(text);
    model.addAttribute("searchDto", searchDto);

    MyPage<Product> result =
        text == null || text.trim().isEmpty()
            ? productApiClient.listProductsByPage(page, size, sort)
            : productApiClient.searchProductsByPage(searchDto, page, size, sort);
    model.addAttribute("products", result);
    model.addAttribute("currentPage", page != null ? page : 0);
    return "products";
  }

  @GetMapping("/products/{id}/edit")
  public String editProductForm(@PathVariable String id, Model model) {
    Product product = productApiClient.getProduct(id);
    ProductDto productDto =
        new ProductDto(
            product.name(), product.description(), product.price(), product.categories());

    model.addAttribute("productDto", productDto);
    model.addAttribute("product", product);
    return "productEdit";
  }

  @GetMapping("/products/{id}/view")
  public String viewProductForm(@PathVariable String id, Model model) {
    Review review = new Review();
    review.setStars((short) 3);
    model.addAttribute("review", review);
    model.addAttribute("product", productApiClient.getProduct(id));
    return "productView";
  }

  @GetMapping("/products/create")
  public String createProductForm(Model model) {
    model.addAttribute("productDto", new ProductDto());
    return "productCreate";
  }

  @PostMapping("/products")
  public String createProduct(
      @Valid @ModelAttribute ProductDto productDto,
      BindingResult result,
      RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      return "productCreate";
    }
    productApiClient.createProduct(productDto);
    redirectAttributes.addFlashAttribute("toast", "Product created successfully!");
    return "redirect:/";
  }

  @PostMapping("/products/{id}")
  public String updateProduct(
      @PathVariable String id,
      @Valid @ModelAttribute ProductDto productDto,
      BindingResult result,
      Model model,
      RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      model.addAttribute("product", productApiClient.getProduct(id));
      return "productEdit";
    }
    productApiClient.updateProduct(id, productDto);
    redirectAttributes.addFlashAttribute("toast", "Product updated successfully!");
    return "redirect:/";
  }

  @PostMapping("/products/{id}/delete")
  public String deleteProduct(@PathVariable String id, RedirectAttributes redirectAttributes) {
    productApiClient.deleteProduct(id);
    redirectAttributes.addFlashAttribute("toast", "Product deleted successfully!");
    return "redirect:/";
  }
}

package com.ivanfranchin.productui.controller;

import com.ivanfranchin.productui.client.ProductApiClient;
import com.ivanfranchin.productui.client.dto.Review;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class ProductReviewController {

    private final ProductApiClient productApiClient;

    @PostMapping("/products/{id}/review")
    public String addReview(@PathVariable String id, @Valid @ModelAttribute Review review,
                            BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("product", productApiClient.getProduct(id));
            return "productView";
        }
        productApiClient.addProductReview(id, review);
        redirectAttributes.addFlashAttribute("toast", "Review added successfully!");
        return String.format("redirect:/products/%s/view", id);
    }
}

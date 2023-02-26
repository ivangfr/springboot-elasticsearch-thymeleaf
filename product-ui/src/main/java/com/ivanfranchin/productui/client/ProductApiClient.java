package com.ivanfranchin.productui.client;

import com.ivanfranchin.productui.client.dto.MyPage;
import com.ivanfranchin.productui.client.dto.Product;
import com.ivanfranchin.productui.client.dto.ProductDto;
import com.ivanfranchin.productui.client.dto.Review;
import com.ivanfranchin.productui.client.dto.SearchDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

@HttpExchange("/api/products")
public interface ProductApiClient {

    @GetExchange
    MyPage<Product> listProductsByPage(@RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) Integer size,
                                       @RequestParam(required = false) String sort);

    @PutExchange("/search")
    MyPage<Product> searchProductsByPage(@RequestBody SearchDto searchDto,
                                         @RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) Integer size,
                                         @RequestParam(required = false) String sort);

    @GetExchange("/{id}")
    Product getProduct(@PathVariable String id);

    @PostExchange
    Product createProduct(@RequestBody ProductDto productDto);

    @PutExchange("/{id}")
    Product updateProduct(@PathVariable String id, @RequestBody ProductDto productDto);

    @PostExchange("/{id}/reviews")
    Product addProductReview(@PathVariable String id, @RequestBody Review review);
}

package com.ivanfranchin.productapi.product;

import com.ivanfranchin.productapi.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<Product> listProductsByPage(Pageable pageable);

    Product validateAndGetProductById(String id);

    Product saveProduct(Product product);

    void deleteProduct(Product product);

    Page<Product> search(String text, Pageable pageable);
}

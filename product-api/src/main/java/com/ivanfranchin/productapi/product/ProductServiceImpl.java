package com.ivanfranchin.productapi.product;

import com.ivanfranchin.productapi.product.exception.ProductNotFoundException;
import com.ivanfranchin.productapi.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.RandomStringUtils.secure;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Page<Product> listProductsByPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product validateAndGetProductById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product saveProduct(Product product) {
        product.setReference("SBES@%s-%s"
                .formatted(secure().nextAlphanumeric(4), secure().nextNumeric(5)));
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Override
    public Page<Product> search(String text, Pageable pageable) {
        return productRepository.findByReferenceOrNameOrDescription(text, text, text, pageable);
    }
}

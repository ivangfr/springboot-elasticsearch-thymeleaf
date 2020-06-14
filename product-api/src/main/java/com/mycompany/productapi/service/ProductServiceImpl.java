package com.mycompany.productapi.service;

import com.mycompany.productapi.exception.ProductNotFoundException;
import com.mycompany.productapi.model.Product;
import com.mycompany.productapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;

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
        String reference = String.format("SBES@%s-%s", randomAlphanumeric(4), RandomStringUtils.randomNumeric(5));
        product.setReference(reference);
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

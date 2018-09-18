package com.mycompany.productapi.service;

import com.mycompany.productapi.exception.ProductNotFoundException;
import com.mycompany.productapi.model.Product;
import com.mycompany.productapi.repository.ProductRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> listAllProductsByPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product validateAndGetProductById(String id) throws ProductNotFoundException {
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
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchPhraseQuery("reference", text))
                .should(QueryBuilders.matchPhraseQuery("name", text))
                .should(QueryBuilders.matchPhraseQuery("description", text));

//        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(text, "reference", "name", "description");

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable)
                .build();

        return productRepository.search(searchQuery);
    }

}

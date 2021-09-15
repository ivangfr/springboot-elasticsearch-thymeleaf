package com.mycompany.productapi.repository;

import com.mycompany.productapi.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, String> {

    Page<Product> findByReferenceOrNameOrDescription(String reference, String name, String description, Pageable pageable);
}

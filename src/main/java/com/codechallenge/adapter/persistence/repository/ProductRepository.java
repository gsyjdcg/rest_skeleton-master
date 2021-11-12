package com.codechallenge.adapter.persistence.repository;

import com.codechallenge.adapter.persistence.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, String> {
}

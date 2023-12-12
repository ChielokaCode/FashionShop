package com.chielokacodes.ec.repository;

import com.chielokacodes.ec.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductNameIgnoreCaseStartingWith(String productName);
    List<Product> findByCategoryIgnoreCaseStartingWith(String category);

    List<Product> findByIdIn(List<String> productIds);
}

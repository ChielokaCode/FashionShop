package com.chielokacodes.ec.repository;

import com.chielokacodes.ec.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductNameIgnoreCaseStartingWith(String productName);
}

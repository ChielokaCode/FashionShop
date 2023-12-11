package com.chielokacodes.ec.repository;

import com.chielokacodes.ec.entity.Product;
import com.chielokacodes.ec.entity.SavedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedProductRepo extends JpaRepository<SavedProduct, Long> {
}

package com.chielokacodes.ec.service;

import com.chielokacodes.ec.entity.Product;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.List;

public interface ProductService {

    Product getProductById(Long id);
    List<Product> findAllProduct();
    Page<Product> findAllProductPageable(Pageable pageable);
    void deleteProductById(Long id);
    List<Product> getProductsByProductName(String productName);
    void addProductToCart(Long id, HttpServletRequest request);
    void checkOutCart(HttpSession session, Model model);
}

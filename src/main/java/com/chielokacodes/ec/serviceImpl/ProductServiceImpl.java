package com.chielokacodes.ec.serviceImpl;

import com.chielokacodes.ec.entity.Cart;
import com.chielokacodes.ec.entity.Order;
import com.chielokacodes.ec.entity.Product;
import com.chielokacodes.ec.repository.ProductRepository;
import com.chielokacodes.ec.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @Override
    public List<Product> findAllProduct(){
        return productRepository.findAll();
    }
    @Override
    public Page<Product> findAllProductPageable(Pageable pageable){
        return productRepository.findAll(pageable);
    }



    @Override
    public void addProductToCart(Long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart;
        if(session.getAttribute("cart") != null){
            cart = (Cart) session.getAttribute("cart");
            cart.setProductIds(cart.getProductIds()+"," + id);
            session.setAttribute("cartItems", cart.getProductIds().split(",").length);
        } else {
            cart = Cart.builder().productIds(id.toString())
                    .userId((Long) session.getAttribute("userID")).build();
            session.setAttribute("cart", cart);
            session.setAttribute("cartItems", cart.getProductIds().split(",").length);
        }
    }

    @Override
    public void checkOutCart(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");

            List<Product> productList = new ArrayList<>();
            List<String> productIds = Arrays.stream(cart.getProductIds().split(",")).toList();
            productIds.forEach(id -> {
                productList.add(productRepository.findById(Long.parseLong(id)).orElseThrow(() ->
                        new NullPointerException("No such product found with ID: " + id)));
            });

            final BigDecimal[] totalPrice = {new BigDecimal(0)};
            productList.forEach(product -> totalPrice[0] = totalPrice[0].add(product.getPrice()));
            model.addAttribute("totalPrice", "Total Price: $" + totalPrice[0]);
            session.setAttribute("cart", null);
            Order order = Order.builder()
                    .productList(productList)
                    .userId((Long) session.getAttribute("userID"))
                    .totalPrice(totalPrice[0])
                    .build();
            session.setAttribute("order", order);
            model.addAttribute("order", order);
    }

    public void addNewProduct(Product product) {
        // Create a new product with the provided details

        // Save the new product to the repository or perform other necessary actions
        this.productRepository.save(product);
    }


    @Override
    public Product getProductById(Long id) {
        Optional<Product> optional = productRepository.findById(id);
        Product product = null;
        if(optional.isPresent()){
            product = optional.get();
        }else{
            throw new RuntimeException("Product not found with id :: " + id);
        }
        return product;
    }

    @Override
    public List<Product> getProductsByProductName(String productName) {
        return productRepository.findByProductNameIgnoreCaseStartingWith(productName);
    }



    @Override
    public void deleteProductById(Long id) {
        this.productRepository.deleteById(id);
    }

}

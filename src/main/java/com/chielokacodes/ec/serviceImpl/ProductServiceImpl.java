package com.chielokacodes.ec.serviceImpl;

import com.chielokacodes.ec.entity.Cart;
import com.chielokacodes.ec.entity.Order;
import com.chielokacodes.ec.entity.Product;
import com.chielokacodes.ec.entity.User;
import com.chielokacodes.ec.repository.OrderRepository;
import com.chielokacodes.ec.repository.ProductRepository;
import com.chielokacodes.ec.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              OrderRepository orderRepository){
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
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
    public void addProductToCart(Long productId, HttpServletRequest request) {
        HttpSession session = request.getSession(true); // Ensure session exists or user is logged in

        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            // Create a new cart if it doesn't exist
            User user = getUserFromSession(session); // Assuming a method to retrieve the user from the session
            cart = Cart.builder()
                    .productIds(productId.toString())
                    .userId(user.getId())
                    .build();
            session.setAttribute("cart", cart);
        } else {
            // Append the product ID to the existing cart
            cart.setProductIds(cart.getProductIds() + "," + productId);
        }

        // Update the cart items count in the session
        session.setAttribute("cartItems", countCartItems(cart));

    }


    // Example method to retrieve the user from the session (adjust based on your setup)
    private User getUserFromSession(HttpSession session) {
        // Assuming you store the user in the session with a key like "user"
        return (User) session.getAttribute("user");
    }


    // Example method to count the items in the cart
    private int countCartItems(Cart cart) {
        //productIds is a String of comma-seperated values, so to an element,
        //you must split by comma and then calculate length
        //the purpose is to know the size of the products in the cart
        return cart.getProductIds() != null ? cart.getProductIds().split(",").length : 0;
    }





    //CheckOut user by returning the List of Products the user added to cart to the Checkout (Cart) Page
    @Override
    public List<Product> checkOutCart(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");

        // Initialize an empty list to store products
        List<Product> productList = new ArrayList<>();

        if (cart == null || cart.getProductIds() == null || cart.getProductIds().isEmpty()) {
            // Handle the case where the cart is empty or not properly initialized
            model.addAttribute("error", "The cart is empty or not properly initialized.");
        } else {
            List<String> productIds = Arrays.asList(cart.getProductIds().split(","));

            productList = getProductListByIds(productIds);

            if (productList.isEmpty()) {
                // Handle the case where no products are found for the given IDs
                model.addAttribute("error", "No products found for the given IDs.");
            } else {
                BigDecimal totalPrice = calculateTotalPrice(productList);

                // Store order information
                Order order = Order.builder()
                        .productList(productList)
                        .productIds(productIds.toString())
                        .userId((Long) session.getAttribute("userId"))
                        .totalPrice(totalPrice)
                        .build();
                orderRepository.save(order);

                // Clear the cart
                session.setAttribute("cart", null);

                // Store order in session and model
                session.setAttribute("order", order);
                model.addAttribute("order", order);

                // Store total price in model
                model.addAttribute("totalPrice", "Total Price: $" + totalPrice);
            }
        }

        // Return the list of products to be displayed in the cart
        return productList;
    }



    /*
      To summarize, this method takes a list of product IDs,
      retrieves the corresponding products from a repository,
      and returns a list of those products. If any product ID does not correspond to a product in the repository,
      it throws a NullPointerException with a custom error message.
     */
    private List<Product> getProductListByIds(List<String> productIds) {
        return productIds.stream()
                .map(id -> productRepository.findById(Long.parseLong(id))
                        .orElseThrow(() -> new NullPointerException("No such product found with ID: " + id)))
                .collect(Collectors.toList());
    }

    /*
     To sum up, this method takes a list of Product objects, converts it to a stream,
     extracts the price of each product,
     and then calculates the total price by reducing the stream using addition.
     The final result is a BigDecimal representing the total price of all products in the list.

     */
    private BigDecimal calculateTotalPrice(List<Product> productList) {
        return productList.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }






    //To Save a product to Database
    public void addNewProduct(Product product) {
        // Create a new product with the provided details
        // Save the new product to the repository or perform other necessary actions
        this.productRepository.save(product);
    }



    //To get Product by Id
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


    //To delete Products in Database based on Id
    @Override
    public void deleteProductById(Long id) {
        this.productRepository.deleteById(id);
    }


    //To get Products By ProductName used in the search Bar
    @Override
    public List<Product> getProductsByProductName(String productName) {
        return productRepository.findByProductNameIgnoreCaseStartingWith(productName);
    }


    //To get Products By Category used in Category clicking image search
    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCaseStartingWith(category);
    }


    //To get Products in Cart
    @Override
    public List<Product> getProductsInCart(HttpSession session){
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.getProductIds().isEmpty()) {
            return Collections.emptyList(); // Return an empty list if no products in the cart
        }

        List<String> productIds = Arrays.asList(cart.getProductIds().split(","));
        return productRepository.findByIdIn(productIds);
    }
}


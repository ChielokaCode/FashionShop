package com.chielokacodes.ec.controller;


import com.chielokacodes.ec.entity.Product;
import com.chielokacodes.ec.serviceImpl.OrderServiceImpl;
import com.chielokacodes.ec.serviceImpl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductServiceImpl productService;
    private final OrderServiceImpl orderService;

    @Autowired
    public ProductController(ProductServiceImpl productService, OrderServiceImpl orderService){
        this.productService = productService;
        this.orderService = orderService;

    }

    ////request to get admin-dashboard rendered with the list of products
    @GetMapping("/admin-dashboard")
    public ModelAndView adminProductPage(){
        List<Product> productList = productService.findAllProduct();
        return new ModelAndView("admin_dashboard")
                .addObject("products", productList);
    }

    //request to get liked page
    @GetMapping("/like-to-reg")
    public String likedIndexPage(){
       return "redirect:/user/reg";
    }


    //request to get add_products page rendered with the new products to add
    @GetMapping("/admin-add")
    public String adminAddProduct(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "add_products";
    }


///////////SAVE PRODUCTS post to database
    @PostMapping("/admin-add-product")
    public String addNewProductToStore(@ModelAttribute("product") Product product) {
            productService.addNewProduct(product);
        return "redirect:/product/admin-dashboard";
    }
//////Request get edit_products page and using (@PostMapping for adding) to update/edit product in database
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "edit_products";
    }
///////////Request to delete product in database and return to same page
    @GetMapping("/deleteProduct/{id}")
    public String deleteProductById(@PathVariable(value = "id") Long id){
        this.productService.deleteProductById(id);
        return "redirect:/product/admin-dashboard";
    }

    //Request to get liked_products page and rendered in it the product id to be shown in Single page view
    @GetMapping("/liked-product/{id}")
    public ModelAndView likedProductById(@PathVariable(value = "id") Long id){
        Product likedproduct = productService.getProductById(id);  ///findById

        return new ModelAndView("liked_product")
                .addObject("liked", likedproduct);
    }


    //Request to get search_products page depending if the product inputted by the user
    // in search bar is found in the database by Product Name
    @GetMapping("/search-product")
    public String searchProductByName(@RequestParam("productName") String productName, Model model) {
        // Retrieve a list of products based on the given product name
        List<Product> productList = productService.getProductsByProductName(productName);

        // Check if the list is empty (no matching products found)
        if (productList.isEmpty()) {
            model.addAttribute("productNotFound", true);
            return "redirect:/product/user-dashboard?productNotFound";
        } else {
            // Add the list of products to the model
            model.addAttribute("searchProducts", productList);
            return "search_product";
        }
    }


    //Request to get category_page depending if the category is empty or not
    //then based of the category button clicked on, it moves the products in the same category to be
    //shown in the category Page
    @GetMapping("/searchByCategory")
    public String searchProductByCategory(@RequestParam String category, Model model){
        List<Product> categoryList = productService.getProductsByCategory(category);

        if(categoryList.isEmpty()) {
            model.addAttribute("categoryNotFound", true);
            return "redirect:/product/user-dashboard?categoryNotFound";
        }else {
            model.addAttribute("searchCategory", categoryList);
            return "category_page";
        }
    }


    //Request to stay on the same user dashboard page after the "Add to cart" button is clicked
    @GetMapping("/add-cart")
    public String addToCart(@RequestParam(name = "cart") Long id, HttpServletRequest request){
        productService.addProductToCart(id, request);
        return "redirect:/product/user-dashboard";
    }

    /* To check if Cart is empty or not,
       If empty display a alert box on the same page
       If cart has products, move to checkout page
    */
    @GetMapping("/payment")
    public String checkout(HttpSession session, Model model, HttpServletRequest request) {
        Integer cartItems = (Integer) session.getAttribute("cartItems");

        if (cartItems == null) {
            model.addAttribute("error", true);
            return "redirect:/product/user-dashboard?error";
        }

        // Retrieve the products in the cart and add them to the model
        List<Product> cartProducts =  productService.checkOutCart(session, model);

        // Add the products directly to the model without the need for an additional method
        model.addAttribute("cartProducts", cartProducts);
        return "checkout";
    }




    //Request to get the "Successfull paid" Page or to remain on Checkout Page
    //based on the money balance of the user
    //if the user balance can cover total price of products - Successfull Paid
    //else It remains on Checkout Page
    @GetMapping("/pay")
    public String orderPayment(HttpSession session, Model model){
        return orderService.makePayment(session, model);
    }




    //Request to get user-dashboard Page transformed as a Paginated product list
    // Page<Product> is a list of Paginated Products
    //it also renders in it the cartItems to be sent to checkout Page
    @GetMapping("/user-dashboard")
    public String getProductPage(Model model, @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "6") int size,
                                 HttpServletRequest request) {
        HttpSession session = request.getSession();

        // Create a custom PageRequest with the specified page number and page size
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> products = productService.findAllProductPageable(pageable);
        model.addAttribute("products", products);
        model.addAttribute("cartItems", "Products added to Cart: " + session.getAttribute("cartItems"));
        return "dashboard";
    }

}

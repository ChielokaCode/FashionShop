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

    @GetMapping("/admin-dashboard")
    public ModelAndView adminProductPage(){
        List<Product> productList = productService.findAllProduct();
        return new ModelAndView("admin_dashboard")
                .addObject("products", productList);
    }

    //check
    @GetMapping("/like-to-reg")
    public String likedIndexPage(){
       return "redirect:/user/reg";
    }


    @GetMapping("/admin-add")
    public String adminAddProduct(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "add_products";
    }
///////////SAVE PRODUCTS
    @PostMapping("/admin-add-product")
    public String addNewProductToStore(@ModelAttribute("product") Product product) {
            productService.addNewProduct(product);
        return "redirect:/product/admin-dashboard";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "edit_products";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProductById(@PathVariable(value = "id") Long id){
        this.productService.deleteProductById(id);
        return "redirect:/product/admin-dashboard";
    }

    @GetMapping("/liked-product/{id}")
    public ModelAndView likedProductById(@PathVariable(value = "id") Long id){
        Product likedproduct = productService.getProductById(id);  ///findById

        return new ModelAndView("liked_product")
                .addObject("liked", likedproduct);
    }


    @GetMapping("/search-product")
    public String searchProductByNameOrCategory(@RequestParam("productName") String productName, Model model) {
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






    @GetMapping("/add-cart")
    public String addToCart(@RequestParam(name = "cart") Long id, HttpServletRequest request){
        productService.addProductToCart(id, request);
        return "redirect:/product/user-dashboard";
    }

    @GetMapping("/payment")
    public String checkout(HttpSession session, Model model, HttpServletRequest request) {
        Integer order1 = (Integer) session.getAttribute("cartItems");
        if (order1 == null) {
            model.addAttribute("error", true);
            request.setAttribute("error", true);
            return "redirect:/product/user-dashboard?error";
        }
            productService.checkOutCart(session, model);
            return "redirect:/product/checkout";

    }

    @GetMapping("/checkout")
    public String checkOutPage(Model model){
        model.addAttribute("paid", "");
        return "checkout";
    }

    @GetMapping("/pay")
    public String orderPayment(HttpSession session, Model model){
        return orderService.makePayment(session, model);
    }

    /////////PAGINATION //Un-comment this if things go south and delete any recently created methods
//    @GetMapping("/user-dashboard")
//    public ModelAndView userProductPage(HttpServletRequest request){
//        HttpSession session = request.getSession();
//        List<Product> productList = productService.findAllProduct();
//        return new ModelAndView("dashboard")
//                .addObject("products", productList)
//                .addObject("cartItems", "Products added to Cart: "+ session.getAttribute("cartItems"))
//                .addObject("user", new User());
//    }

    @GetMapping("/user-dashboard")
    public String getProductPage(Model model, @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size,
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

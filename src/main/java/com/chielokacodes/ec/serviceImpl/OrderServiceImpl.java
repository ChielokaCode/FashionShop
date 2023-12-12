package com.chielokacodes.ec.serviceImpl;

import com.chielokacodes.ec.entity.Order;
import com.chielokacodes.ec.entity.User;
import com.chielokacodes.ec.repository.OrderRepository;
import com.chielokacodes.ec.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserServiceImpl userService;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserServiceImpl userService){
        this.orderRepository = orderRepository;
        this.userService = userService;
    }


    /////////USER MAKE PAYMENT, CART-ITEMS RETURNS TO NULL, BALANCE IS SUBTRACTED BY TOTAL PRICE OF PRODUCT
    public String makePayment(HttpSession session, Model model) {
        User user = userService.findUserById((Long) session.getAttribute("userId"));
        Order order = (Order) session.getAttribute("order");

        //Check User Balance can buy the Product
        if (user.getBalance().doubleValue() < order.getTotalPrice().doubleValue()) {
            model.addAttribute("paid", "Insufficient balance na dey your account!");
            return "checkout";
        }

        //Substract User Balance
        user.setBalance(user.getBalance().subtract(order.getTotalPrice()));

        userService.saveUser(user);
        saveOrder(order);

        //Reset cartItem and order to null after purchase, so user can set a new cart and order for another purchase
        session.setAttribute("cartItems", null);
        session.setAttribute("order", null);
        model.addAttribute("paid", "Payment was successful!");
        return "successfully-paid";
    }



    private void saveOrder(Order order) {
        orderRepository.save(order);
    }


}

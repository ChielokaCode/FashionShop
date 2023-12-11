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

    private UserServiceImpl userService;
    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserServiceImpl userService){
        this.orderRepository = orderRepository;
        this.userService = userService;
    }
    @Override
    public String makePayment(HttpSession session, Model model) {
        User user = userService.findUserById((Long) session.getAttribute("userID"));
        if(user == null){
            throw new NullPointerException("No Product in Cart");
        }
        Order order = (Order) session.getAttribute("order");
        if (user.getBalance().doubleValue()<order.getTotalPrice().doubleValue()){
            model.addAttribute("paid", "Insufficient balance!");
            return "checkout";
        }
        user.setBalance(user.getBalance().subtract(order.getTotalPrice()));
        userService.saveUser(user);
        Order order1 = saveOrder(order);
        session.setAttribute( "order", null);
        model.addAttribute("paid", "Payment was successful!");
        return "successfully-paid";
    }

    private Order saveOrder(Order order) {
        return orderRepository.save(order);
    }


}

package com.chielokacodes.ec.service;

import com.chielokacodes.ec.entity.Order;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

public interface OrderService {
    String makePayment(HttpSession session, Model model);
//    void saveOrder(Order order);
}

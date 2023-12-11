package com.chielokacodes.ec.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

public interface OrderService {
    String makePayment(HttpSession session, Model model);

}

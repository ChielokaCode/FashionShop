package com.chielokacodes.ec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {


    //Request to get index Page by inputting "/" or "/index"
    @GetMapping("/index")
    public String homePage(){
        return "index";
    }
}

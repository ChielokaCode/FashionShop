package com.chielokacodes.ec.controller;

import com.chielokacodes.ec.dto.PasswordDto;
import com.chielokacodes.ec.dto.UserDto;
import com.chielokacodes.ec.entity.User;
import com.chielokacodes.ec.serviceImpl.UserServiceImpl;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService){
        this.userService = userService;
    }

   //the ModelAndView helps us to set (the "object" required in "th:object")
   // and (view that is the page that will be shown)
    //Request to get the Registration Page rendered in it the new user registering
    @GetMapping("/reg")
    public ModelAndView signUpPage(Model model){
        return new ModelAndView("register").addObject("user", new UserDto());
    }

    //@ModelAttribute is used to bind the form data (from the user's input) to the UserDto object.
    //the "/signup" in @PostMapping must be the same in form action="/user/signup" ,form method will be "post"
    @PostMapping("/signup")
    public String signup(@ModelAttribute UserDto userDto, Model model) {
        User user = userService.saveUser(new User(userDto));
        log.info("User created --->{} ", user);
        model.addAttribute("success", true);
        return "redirect:/user/user-login?success";
    }

    //Request to get User Login Page rendered in it the new User logged in
    @GetMapping("/user-login")
    public ModelAndView loginPage(){
        return new ModelAndView("login").addObject("user", new UserDto());
    }


    //Request that post or logs in a user, then ocreats a session for the user
    //and redirects the user to the user-dashboard
    //else if password is incorrect, keeps the user on the login form
    @PostMapping("/sign-in")
    public String login(@ModelAttribute UserDto userDto, HttpServletRequest request, Model model) {
        User user = userService.findUserByEmail(userDto.getEmail());

        if(user != null) {
            PasswordDto passwordDto = new PasswordDto();
            passwordDto.setPassword(userDto.getPassword());
            passwordDto.setHashPassword(user.getPassword());

            if (userService.verifyPassword(passwordDto)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", new User()); //just added this - check error
                session.setAttribute("userId", user.getId()); //create a session for a user that login to the app, that saves the id of the user into the seesion
                session.setAttribute("userName", user.getUsername());
                session.setAttribute("userImage", user.getImage());

                model.addAttribute("success", true);
                request.setAttribute("success", true);
                return "redirect:/product/user-dashboard?success";
            } else {
                //password is incorrect
                model.addAttribute("error", true);
                request.setAttribute("error", true);
                return "redirect:/user/user-login?error";
            }
        }else {
            //email is incorrect
            model.addAttribute("error", true);
            request.setAttribute("error", true);
            return "redirect:/user/user-login?error";

        }
    }


    //Request to invalidate or logout the user session and redirect the user to Index Page
    @GetMapping("/logout")
    public String logOut(HttpSession session, HttpServletRequest request, Model model){
        session.invalidate();
        model.addAttribute("logout", true);
        request.setAttribute("logout", true);
        return "redirect:/index?logout";
    }


}

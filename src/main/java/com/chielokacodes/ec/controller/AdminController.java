package com.chielokacodes.ec.controller;

import com.chielokacodes.ec.dto.AdminDto;
import com.chielokacodes.ec.dto.PasswordDto;
import com.chielokacodes.ec.entity.Admin;
import com.chielokacodes.ec.entity.Product;
import com.chielokacodes.ec.serviceImpl.AdminServiceImpl;
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
@RequestMapping("/admin")
public class AdminController {

    private AdminServiceImpl adminService;

    @Autowired
    public AdminController(AdminServiceImpl adminService){
        this.adminService = adminService;
    }

    //the ModelAndView helps us to set (the "object" required in "th:object")
    // and (view that is the page that will be shown)
    @GetMapping("/admin-reg")
    public ModelAndView signUpPage(Model model){
        return new ModelAndView("admin_register").addObject("admin", new AdminDto());
    }

    //@ModelAttribute is used to bind the form data (from the user's input) to the UserDto object.
    //the "/signup" in @PostMapping must be the same in form action="/signup" ,form method will be "post"
    @PostMapping("/admin-signup")
    public String signup(@ModelAttribute AdminDto adminDto, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        session.setAttribute("email", adminDto.getEmail());
            this.adminService.saveAdmin(new Admin(adminDto));
            model.addAttribute("success", true);
            return "redirect:/admin/admin-login?success";
    }

    @GetMapping("/admin-login")
    public ModelAndView loginPage(){
        return new ModelAndView("admin_login").addObject("admin", new AdminDto());
    }

    @PostMapping("/admin-sign-in")
    public String login(@ModelAttribute AdminDto adminDto, HttpServletRequest request, Model model) {
        Admin admin = adminService.findAdminByEmail(adminDto.getEmail());

        if (admin != null) {
            PasswordDto passwordDto = new PasswordDto();
            passwordDto.setPassword(adminDto.getPassword());
            passwordDto.setHashPassword(admin.getPassword());

            if (adminService.verifyPassword(passwordDto)) {
                HttpSession session = request.getSession();
                session.setAttribute("adminId", admin.getId());
                session.setAttribute("adminName", admin.getUsername());
                session.setAttribute("adminImage", admin.getImage());

                model.addAttribute("success", true);
                request.setAttribute("success", true);
                return "redirect:/product/admin-dashboard?success";
            } else {
                // Password is incorrect
                model.addAttribute("error", true);
                request.setAttribute("error", true);
                return "redirect:/admin/admin-login?error";
            }
        } else {
            // Email is incorrect
            model.addAttribute("error", true);
            request.setAttribute("error", true);
            return "redirect:/admin/admin-login?error";
        }
    }



    @GetMapping("/admin-logout")
    public String logOut(HttpSession session, HttpServletRequest request, Model model){
        session.invalidate();
        model.addAttribute("logout", true);
        request.setAttribute("logout", true);
        return "redirect:/index?logout";
    }

}

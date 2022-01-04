package com.example.dits.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SecurityController {

    @GetMapping(value = { "/", "/welcome" })
    public String welcomePage(Model model) {
        return "welcome";
    }

    @GetMapping("/admin")
    public String adminPage(ModelMap model){
        model.addAttribute("user",getPrincipal());
        return "admin";
    }


    @GetMapping("/user")
    public String userPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "user";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";}

    @GetMapping("/error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }


    @GetMapping("/accessDenied")
    public String accessDeniedGet(){
        return "accessDenied";
    }

    @GetMapping("/logoutSuccessful")
    public String logoutPage(Model model){
        return "logout";
    }


    static String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        if(principal instanceof UserDetails){
            userName = ((UserDetails) principal).getUsername();
        }
        else
            userName = principal.toString();
        return userName;
    }
}

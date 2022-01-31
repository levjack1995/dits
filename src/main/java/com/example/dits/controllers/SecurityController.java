package com.example.dits.controllers;

import com.example.dits.entity.User;
import com.example.dits.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class SecurityController {

    private final UserService userService;


    @GetMapping("/admin")
    public String adminPage(ModelMap model){
        model.addAttribute("user",getPrincipal());
        return "admin";
    }


    @GetMapping("/user")
    public String userPage(HttpSession session) {
        User user = userService.getUserByLogin(getPrincipal());
        session.setAttribute("user", user);
        return "user/user";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";}

//    @PostMapping("/login")
//    public String afterLogin(){
//        return "user";}


//    @GetMapping("/error")
//    public String loginError(Model model) {
//        model.addAttribute("loginError", true);
//        return "login";
//    }


    @GetMapping("/accessDenied")
    public String accessDeniedGet(){
        return "accessDenied";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null)
            new SecurityContextLogoutHandler().logout(request,response,auth);

        return "redirect:/login?logout";
    }


    private static String getPrincipal(){
        String userName;
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

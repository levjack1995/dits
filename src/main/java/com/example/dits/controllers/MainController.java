package com.example.dits.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    //TODO Check this method. Can we delete this class?
    @GetMapping
    public String getMain(){
        return "main";
    }

}

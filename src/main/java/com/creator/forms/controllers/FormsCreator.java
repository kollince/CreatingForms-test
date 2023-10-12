package com.creator.forms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormsCreator {
    @GetMapping("/")
    public String physicsTests(){
        return "index";
    }


}

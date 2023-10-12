package com.creator.forms.controllers;

import com.creator.forms.services.FormServiceImpl;
import com.creator.forms.services.interfaces.FormService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormsCreator {
    FormService formService = new FormServiceImpl();
    @GetMapping("/")
    public String allPhysicsTests(){
        return "index";
    }
    @PostMapping("/addform")
    public String addForm(@PathVariable int id) {
        return "editform/{id}";
    }


}

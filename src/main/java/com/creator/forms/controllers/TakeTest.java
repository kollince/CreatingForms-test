package com.creator.forms.controllers;

import com.creator.forms.dao.FormsDaoJsonImpl;
import com.creator.forms.dao.interfaces.FormsDao;
import com.creator.forms.models.Forms;
import com.creator.forms.services.FormServiceImpl;
import com.creator.forms.services.interfaces.FormService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class TakeTest {
    private final FormsDao formsDao = new FormsDaoJsonImpl();
    private final FormService formService = new FormServiceImpl(formsDao);
//    @GetMapping("/")
//    public String allPhysicsTests(Model model) throws IOException {
//        List<Forms> fmsAll = formService.listForms();
//        model.addAttribute("allForms", fmsAll);
//        return "index";
//    }
//    @GetMapping("/takeTest")
//    public String takeTest(Model model) throws IOException {
//        List<Forms> fmsAll = formService.listForms();
//        model.addAttribute("allForms", fmsAll);
//        return "takeTest";
//    }
}

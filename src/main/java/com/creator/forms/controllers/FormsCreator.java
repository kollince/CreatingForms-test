package com.creator.forms.controllers;

import com.creator.forms.dao.FormsDaoJsonImpl;
import com.creator.forms.dao.interfaces.FormsDao;
import com.creator.forms.models.Answers;
import com.creator.forms.models.Forms;
import com.creator.forms.models.Questions;
import com.creator.forms.services.FormServiceImpl;
import com.creator.forms.services.interfaces.FormService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FormsCreator {
    List<Forms> formsList = new ArrayList<>();
    FormsDao formsDao = new FormsDaoJsonImpl(formsList);
    FormService formService = new FormServiceImpl(formsDao);
    private final List<Answers> answersList = new ArrayList<>();
    private final List<Questions> questionsList = new ArrayList<>();


    @GetMapping("/")
    public String allPhysicsTests(Model model) throws IOException {
        List<Forms> fmsAll = formService.listForms();
        model.addAttribute("allForms", fmsAll);
        return "index";
    }
    @GetMapping("/addTestForm")
    public String addTestForm(){
        return "addTestForm";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(value="id") int id, Model model) {
        Forms fmsEdit = formService.getFormsById(id);
        model.addAttribute("editForm", fmsEdit);
        //System.out.println(fmsEdit.getId());
        return "edit";
    }
    @PostMapping("/addForm")
    public String add(@RequestParam String name, @RequestParam String description, @RequestParam boolean time) throws IOException {
        Forms forms = new Forms(0, name, description,questionsList, answersList,time);
        List<Forms> formsList = formService.addForms(forms);
        //formService.addForms(forms);
        int lastIndex = formsList.size() - 1;
//        for (int i = 0; i < formsList.size(); i++) {
//            System.out.println("i - "+i+", getId - "+formsList.get(i).getId());
//        }
        //System.out.println(lastIndex);
        //return "redirect:/edit";
        return "redirect:/edit/"+formsList.get(lastIndex).getId();
    }
    @GetMapping("/edit")
    public String editNewForm() {
        return "edit";
    }
    @GetMapping("/delete/{id}")
    public String getDelete(@PathVariable(value="id") int id) {
        formService.delete(id);
        return "redirect:/";
    }
}

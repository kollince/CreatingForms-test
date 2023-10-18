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
import java.util.Date;
import java.util.List;

@Controller
public class FormsCreator {
//    List<Forms> formsList = new ArrayList<>();
//    List<Questions> questionsList = new ArrayList<>();
//    List<Answers> answersList = new ArrayList<>();
    FormsDao formsDao = new FormsDaoJsonImpl();
    FormService formService = new FormServiceImpl(formsDao);

    @GetMapping("/")
    public String allPhysicsTests(Model model) throws IOException {
        List<Forms> fmsAll = formService.listForms();
        model.addAttribute("allForms", fmsAll);
        return "index";
    }
    @GetMapping("dashboard/")
    public String dashBoardTests(Model model) throws IOException {
        List<Forms> fmsList = formService.listForms();
        List<Questions> qstList = formService.listQuestions();
        List<Answers> ansList = formService.listAnswers();
        model.addAttribute("fmsList", fmsList);
        model.addAttribute("qstList", qstList);
        model.addAttribute("ansList", ansList);
        return "dashboard/index";
    }
    @GetMapping("/addTestForm")
    public String addTestForm1(){
        return "addTestForm";
    }
    @GetMapping("dashboard/addTestForm")
    public String addTestForm(){
        return "dashboard/addTestForm";
    }
    //Редактор Вопросов
    @GetMapping("/addQuestions/{id}")
    public String addQuestions ( @PathVariable(value="id") int id, Model model) {
        Forms fmsEdit = formService.getFormsById(id);
        List<Questions> qstAdd = formService.listQuestionsByFormId(id);
        model.addAttribute("editFormId", fmsEdit);
        model.addAttribute("questionsByFormId",qstAdd);
        return "addQuestions";
    }
    @GetMapping("dashboard/addQuestion")
    public String addQuestion (Model model) throws IOException {
        List<Forms> formsList= formService.listForms();
        List<Questions> questionsList = formService.listQuestions();
        model.addAttribute("formList", formsList);
        model.addAttribute("questionsList", questionsList);
        return "dashboard/addQuestion";
    }
    @GetMapping("dashboard/addAnswer")
    public String addAnswer (Model model) throws IOException {
        List<Forms> formsList= formService.listForms();
        List<Questions> questionsList = formService.listQuestions();
        List<Answers> answersList = formService.listAnswers();
        model.addAttribute("formList", formsList);
        model.addAttribute("questionsList", questionsList);
        model.addAttribute("answerList", answersList);
        System.out.println(questionsList);
        return "dashboard/addAnswer";
    }
    // Редактор теста
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(value="id") int id, Model model) {
        Forms formId = formService.getFormsById(id);
        model.addAttribute("editForm", formId);
        return "edit";
    }
    @GetMapping("/deleteQuestion/{id}")
    public String getDeleteQuestion(@PathVariable(value="id") int id) {
        int idForm = formService.getQuestionById(id).getIdForm();
        formService.deleteQuestion(id);
        return "redirect:/addQuestions/"+idForm;
    }
    @GetMapping("/delete/{id}")
    public String getDelete(@PathVariable(value="id") int id) {
        formService.delete(id);
        return "redirect:/";
    }
    @GetMapping("/edit")
    public String editNewForm() {
        return "edit";
    }

//    @PostMapping("/addForm")
//    public String add(@RequestParam String name, @RequestParam String description, @RequestParam boolean time) throws IOException {
//        int lastIndex;
//        Forms forms = new Forms(0, name, description, time);
//        formService.addForms(forms);
//        if (formService.listForms().size() > 0) {
//            lastIndex = formService.listForms().size()-1;
//        } else {
//            lastIndex= 0;
//        }
//        return "redirect:/edit/"+formService.listForms().get(lastIndex).getId();
//    }
    @PostMapping("/dashboard/addForm")
    public String addForm(@RequestParam String name, @RequestParam String description, @RequestParam boolean time) throws IOException {
        int lastIndex;
        Forms forms = new Forms(0, name, description, time);
        formService.addForms(forms);
        if (formService.listForms().size() > 0) {
            lastIndex = formService.listForms().size()-1;
        } else {
            lastIndex= 0;
        }
        return "redirect:/dashboard/";
    }

    @PostMapping("/dashboard/addQuestion")
    public String addQuestionN(@RequestParam int idForm, @RequestParam String question, Model model) {
        Questions questions = new Questions(0, idForm, question);
        formService.addQuestion(questions);
        return "redirect:/dashboard/";
    }
    @PostMapping("/dashboard/addAnswer")
    public String addAnswer(@RequestParam int idQuestion,  @RequestParam String answer,
                            @RequestParam boolean isTrue,Model model) {
        Answers answers = new Answers(0, idQuestion, answer, isTrue);
        formService.addAnswer(answers);
        return "redirect:/dashboard/";
    }
    @PostMapping("/addQuestions/{id}")
    public String addQuestion(@RequestParam String name, @RequestParam String description, @RequestParam boolean time,
                              @RequestParam int idForm, @RequestParam String question,
                              @PathVariable(value="id") int id, Model model) {
        Forms fmsEdit = formService.getFormsById(id);
        model.addAttribute("editQuestion", fmsEdit);
        Questions questions = new Questions(0, idForm, question);
        formService.addQuestion(questions);
        List<Forms> formsList =  formService.updateForm(id, name, description,  time);
        formService.updateForm(id,name,description, time);
        int lastIndex = formsList.size() - 1;

//        for (int i = 0; i < formsList.size(); i++) {
//            System.out.println("i - "+i+", getId - "+formsList.get(i).getId());
//        }
        //System.out.println(lastIndex);
        //return "redirect:/edit";
        return "redirect:/addQuestions/"+formsList.get(lastIndex).getId();
    }


//    @PostMapping("/addQuestion")
//    public String add(@RequestParam int idForm, @RequestParam String question ) throws IOException {
//        Questions questions = new Questions(0, idForm, question);
//        formService.addQuestion(questions);
//        int lastIndex = formsList.size() - 1;
//
//        return "redirect:/addQuestions/"+idForm;
//    }
    @PostMapping("/updateForm/{id}")
    public String update(@RequestParam String name, @RequestParam String description, @RequestParam boolean time,
                         @PathVariable(value="id") int id, Model model) throws IOException {
        formService.updateForm(id, name, description, time);
        return "redirect:/addQuestions/"+id;
    }





}

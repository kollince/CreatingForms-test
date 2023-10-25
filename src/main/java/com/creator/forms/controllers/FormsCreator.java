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
import java.util.List;

@Controller
public class FormsCreator {
//    List<Forms> formsList = new ArrayList<>();
//    List<Questions> questionsList = new ArrayList<>();
//    List<Answers> answersList = new ArrayList<>();
    private final FormsDao formsDao = new FormsDaoJsonImpl();
    private final FormService formService = new FormServiceImpl(formsDao);

    @GetMapping("/")
    public String allPhysicsTests(Model model) throws IOException {
        List<Forms> fmsAll = formService.listForms();
        List<Questions> countQst = formService.countQstForTest();
//        System.out.println("111 "+countQst);
        model.addAttribute("allForms", fmsAll);
        model.addAttribute("countQst", countQst);
        return "index";
    }
    @GetMapping("/takeTest/{id}/{numberQst}")
    public String physicsTest(@PathVariable(value="id") int id, @PathVariable(value="numberQst") int numberQst, Model model) throws IOException {
        Forms formsById = formService.getFormsById(id);
        //int countQstId = formService.listNumberForTest(id, numberQst);
        model.addAttribute("formsById", formsById);
        //model.addAttribute("countQstId", countQstId);
        return "takeTest";
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
    @GetMapping("dashboard/addTestForm")
    public String addTestForm(){
        return "dashboard/addTestForm";
    }
    // Редактор теста
    @GetMapping("dashboard/eForm/{id}")
    public String edit(@PathVariable(value="id") int id, Model model) {
        Forms formId = formService.getFormsById(id);
        List<Questions> questionsByFormId = formService.listQuestionsByFormId(id);
        model.addAttribute("editFormId", formId);
        model.addAttribute("questionsByFormId",questionsByFormId);
        return "dashboard/eForm";
    }
    //Редактор Вопросов
    @GetMapping("dashboard/eQuestion/{formId}/{questionId}")
    public String addQuestions ( @PathVariable(value="formId") int formId,
                                 @PathVariable(value="questionId") int questionId, Model model) {
        Forms fmsListId = formService.getFormsById(formId);
        List<Questions> qstListByFormId = formService.listQuestionsByFormId(formId);
        Questions qstListId = formService.getQuestionById(questionId);
//        List<Answers> ansListByFormId = formService.listAnswersByFormId(formId);
        List<Answers> ansListByQuestionId = formService.listAnswersByQuestionId(questionId);
        model.addAttribute("fmsListId", fmsListId);
//        model.addAttribute("qstListByFormId",qstListByFormId);
//        model.addAttribute("ansListByFormId",ansListByFormId);
        model.addAttribute("ansListByQuestionId",ansListByQuestionId);
        model.addAttribute("qstListId",qstListId);
        return "dashboard/eQuestion";
    }
//    @GetMapping("dashboard/addQuestion")
//    public String addQuestion (Model model) throws IOException {
//        List<Forms> formsList= formService.listForms();
//        List<Questions> questionsList = formService.listQuestions();
//
//        model.addAttribute("formList", formsList);
//        model.addAttribute("questionsList", questionsList);
//        return "dashboard/addQuestion";
//    }
//    @GetMapping("dashboard/addAnswer")
//    public String addAnswer (Model model) throws IOException {
//        List<Forms> formsList= formService.listForms();
//        List<Questions> questionsList = formService.listQuestions();
//        List<Answers> answersList = formService.listAnswers();
//        model.addAttribute("formList", formsList);
//        model.addAttribute("questionsList", questionsList);
//        model.addAttribute("answerList", answersList);
//        System.out.println(questionsList);
//        return "dashboard/addAnswer";
//    }
@GetMapping("dashboard/deleteAnswer/{idAnswer}")
public String deleteAnswer(@PathVariable(value="idAnswer") int idAnswer) {
    int idForm = formService.getAnswersById(idAnswer).getIdForm();
    int idQuestion = formService.getAnswersById(idAnswer).getIdQuestion();
    formService.deleteAnswers(idAnswer);
    return "redirect:/dashboard/eQuestion/"+idForm+"/"+idQuestion;
}
    @GetMapping("dashboard/deleteQuestion/{id}")
    public String getDeleteQuestion(@PathVariable(value="id") int id) {
        int idForm = formService.getQuestionById(id).getIdForm();
        formService.deleteQuestion(id);
        return "redirect:/dashboard/eForm/"+idForm;
    }

    @GetMapping("dashboard/deleteForm/{id}")
    public String getDelete(@PathVariable(value="id") int id) {
        formService.delete(id);
        return "redirect:/dashboard/";
    }
//    @GetMapping("/edit")
//    public String editNewForm() {
//        return "edit";
//    }

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
    public String addAnswer(@RequestParam int idForm, @RequestParam int idQuestion,  @RequestParam String answer,
                            @RequestParam boolean isTrue,Model model) {
        Answers answers = new Answers(0, idForm,idQuestion,answer, isTrue);
        formService.addAnswer(answers);
        return "redirect:/dashboard/eQuestion/"+idForm+"/"+idQuestion;
    }
    @PostMapping("/dashboard/addQuestions/{id}")
    public String addQuestion(@RequestParam int idForm, @RequestParam String question,
                              @PathVariable(value="id") int id, Model model) {
        Forms fmsEdit = formService.getFormsById(id);
        model.addAttribute("editQuestion", fmsEdit);
        Questions questions = new Questions(0, idForm, question);
        formService.addQuestion(questions);
        return "redirect:/dashboard/eForm/"+idForm;
    }


//    @PostMapping("/addQuestion")
//    public String add(@RequestParam int idForm, @RequestParam String question ) throws IOException {
//        Questions questions = new Questions(0, idForm, question);
//        formService.addQuestion(questions);
//        int lastIndex = formsList.size() - 1;
//
//        return "redirect:/addQuestions/"+idForm;
//    }
    @PostMapping("dashboard/updateForm/{id}")
    public String update(@RequestParam String name, @RequestParam String description, @RequestParam boolean time,
                         @PathVariable(value="id") int id) throws IOException {
        formService.updateForm(id, name, description, time);
        return "redirect:/dashboard/eForm/"+id;
    }
    @PostMapping("dashboard/updateQuestion/{formId}/{questionId}")
    public String updateQuestion(@RequestParam String question, @PathVariable(value="formId") int formId,
                                 @PathVariable(value="questionId") int questionId) {
        formService.updateQuestions(questionId,question);
        return "redirect:/dashboard/eQuestion/"+formId+"/"+questionId;
    }
    @PostMapping("dashboard/updateAnswer/{formId}/{answerId}")
    public String updateAnswer(@RequestParam String answer, @RequestParam int idQuestion, @RequestParam boolean isTrue,
                               @PathVariable(value="formId") int formId,
                               @PathVariable(value="answerId") int answerId) {
        formService.updateAnswers(answerId,answer, isTrue);
        return "redirect:/dashboard/eQuestion/"+formId+"/"+idQuestion;
    }

}

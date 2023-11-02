package com.creator.forms.controllers;

import com.creator.forms.dao.FormsDaoJsonImpl;
import com.creator.forms.dao.interfaces.FormsDao;
import com.creator.forms.models.Answers;
import com.creator.forms.models.Forms;
import com.creator.forms.models.Passing;
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
import java.util.Arrays;
import java.util.HashSet;
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
        List<Passing> psnAll = formService.listPassing();
//        System.out.println("111 "+countQst);
        model.addAttribute("allForms", fmsAll);
        model.addAttribute("countQst", countQst);
        model.addAttribute("psnAll", psnAll);
        formService.addForms(new Forms(0,"Николай","desc",true));
        formService.addQuestion(new Questions(0,1,"Барабанов?"));
        formService.addQuestion(new Questions(0,1,"Барабанова?"));
        formService.addAnswer(new Answers(0,1,1,"Да", true));
        formService.addAnswer(new Answers(0,1,1,"Нет", false));
        formService.addAnswer(new Answers(0,1,1,"Дааа", true));
        formService.addAnswer(new Answers(0,1,2,"Да 2", true));
        formService.addAnswer(new Answers(0,1,2,"Нет 2", false));
        return "index";
    }
    @GetMapping("/takeTest/{id}")
    public String takeTest(@PathVariable(value="id") int id, Model model) {
        Forms formsById = formService.getFormsById(id);
        List<Questions> questionsByFormId = formService.listQuestionsByFormId(id);
        List<Answers> answersByFormId = formService.listAnswersByFormId(id);
        Questions questionsOneFormId = formService.getQuestionOneByFormId(id);
        //Questions questions = formService.psgListIdFrmIdQst(id);
//        int countQstId = formService.listNumberForTest(id, numberQst);
        model.addAttribute("formsById", formsById);
        model.addAttribute("questionsByFormId", questionsByFormId);
        model.addAttribute("answersByFormId", answersByFormId);
        //model.addAttribute("questionsOneFormId", questionsOneFormId);

        //System.out.println("questionsOneFormId: "+questionsOneFormId);
        return "takeTest";
    }
    @GetMapping("/beginTakeTest/{id}/{idQuestion}")
    public String beginTakeTest(@PathVariable(value="id") int idForm, @PathVariable(value="idQuestion") int idQuestion, Model model) {
        Forms formsById = formService.getFormsById(idForm);
        Questions qstIdFrmIdQst = formService.psgListIdFrmIdQst(idForm, idQuestion);
        Questions questionsOneFormId = formService.getQuestionOneByFormId(idForm);
        List<Answers> ansListByQuestionId = formService.listAnswersByQuestionId(idQuestion);
        model.addAttribute("formsById", formsById);
        model.addAttribute("qstIdFrmIdQst", qstIdFrmIdQst);
        model.addAttribute("questionsOneFormId", questionsOneFormId);
        return "beginTakeTest";
    }
    @GetMapping("/endTakeTest/{id}")
    public String endTakeTest(@PathVariable(value="id") int idForm, Model model) {
        List<Answers> allAnswers = formService.listAnswers();
        return "endTakeTest";
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
    @PostMapping("/endTakeTest/{formId}")
    public String endTakeTest(@RequestParam("answer") List<Integer> answerList,
                              @PathVariable(value="formId") int formId, Model model) throws IOException {
        int points = 0;
        int doubling = 0;
        int questions = 0;
        int unique = 0;
        int incorrect = 0;
        int correct = 0;
        int correctPoint = 0;
        int result = 0;
        System.out.println("answer: " + answerList);
        List<Questions> questionsFormId = formService.listQuestionsByFormId(formId);
        List<Answers> answersByFormId = formService.listAnswersByFormId(formId);
        List<Answers> newAnswers = new ArrayList<>();
        List<Answers> correctAnswers = new ArrayList<>();
        for (int l = 0; l < answersByFormId.size(); l++) {
            for (int j = 0; j < answerList.size(); j++) {
                if (answerList.get(j) == answersByFormId.get(l).getId()) {
                    newAnswers.add(answersByFormId.get(l));
                }
            }
        }
        for (int i = 0; i < answersByFormId.size(); i++) {
            if (answersByFormId.get(i).isTrue()) {
                correctAnswers.add(answersByFormId.get(i));
            }
        }
        for (int i = 0; i < newAnswers.size(); i++) {
            System.out.println("idQst: " + newAnswers.get(i).getIdQuestion() + ", Ответ: "
                    + newAnswers.get(i).getAnswer() + ", Верно? " + newAnswers.get(i).isTrue());

        }
        for (int i = 0; i < correctAnswers.size(); i++) {
            System.out.println("idQst: " + correctAnswers.get(i).getIdQuestion() + ", Ответ: "
                    + correctAnswers.get(i).getAnswer() + ", Верно? " + correctAnswers.get(i).isTrue());
        }
//        for (int i = 0, n = 1; i < questionsFormId.size(); i++, n++) {
//            for (int j = 0; j < newAnswers.size(); j++) {
//                if(questionsFormId.get(i).getId()==newAnswers.get(j).getIdQuestion()){
//
//                    System.out.println("Вопрос: "+questionsFormId.get(i).getQuestion()+", Ответ: "
//                            +newAnswers.get(j).getAnswer()+", j: "+j+", "+questions);
//                }
//            }
//        }
        for (Answers answer : newAnswers) {
            if (correctAnswers.contains(answer)) {
                System.out.println("верный ответ: "+answer.getAnswer()+", idQst: "+answer.getIdQuestion());
                correct++;
            } else {
                System.out.println("неверный ответ: "+answer.getAnswer()+", idQst: "+answer.getIdQuestion());
                incorrect++;
            }
        }
        for (Questions value : questionsFormId) {
            for (int j = 0; j < newAnswers.size(); j++) {
                if (value.getId() == newAnswers.get(j).getIdQuestion()) {
                    System.out.println("Ответы пользователя: "+ newAnswers.get(j).getAnswer()
                            + ", "+newAnswers.get(j).getIdQuestion());
                }
            }
        }
        int count=0;

        for (int i = 0,j = 1; i < questionsFormId.size(); i++, j++) {
            for (int q = 0; q < correctAnswers.size(); q++) {
                if (correctAnswers.get(q).getIdQuestion()==questionsFormId.get(i).getId()) {
                    count = j;
                }
            }
        }
        int newCount=0;
        int correctCount=0;
        int incorrectCount=0;
        int countTrue = 0;
        int countFalse = 0;
        for (int i = 0; i < questionsFormId.size(); i++) {
            for (int k = 0; k < newAnswers.size(); k++) {
                if(newAnswers.get(k).getIdQuestion()==questionsFormId.get(i).getId()){
                    if (newAnswers.get(k).isTrue()){
                         countTrue++;

                        System.out.println("countTrue "+countTrue);
                    } else {
                        countFalse++;

                        System.out.println("countFalse "+countFalse);
                    }
                }
                countFalse=0;
                countTrue=0;
            }
            System.out.println("Вопрос: "+i+", countTrue: "+ countTrue+", countFalse: "+ countFalse );
        }
        for (int i = 0,j = 1, inc = 1,cor=1; i < questionsFormId.size(); i++, j++, inc++,cor++) {
            for (int q = 0; q < newAnswers.size(); q++) {
                if (newAnswers.get(q).getIdQuestion()==questionsFormId.get(i).getId()) {
                    newCount = j;
                    if (!newAnswers.get(q).isTrue()){
                        incorrectCount=inc;
                        System.out.println("444");
                    }
                    if (newAnswers.get(q).isTrue()){
                        correctCount=cor;

                        System.out.println("555");
                    }
                    System.out.println("тест "+newAnswers);
                }
            }
        }
        System.out.println("Вопросов всего : "+count);
        System.out.println("Вопросы, отвеченные пользоватлем : "+newCount
                +", из них "+ incorrectCount+ " отмечено не правильно. "+ correctCount + " всего отмечено правильно.");


        System.out.println("Количество правильных ответов: "+correct);
        System.out.println("Количество неправильных ответов: "+incorrect);
//        if (new HashSet<>(correctAnswers).containsAll(newAnswers)) {
//            System.out.println("Все ответы пользователя правильные!");
//        } else {
//            System.out.println("Есть неправильные ответы пользователя!");
//        }
        for (int j = 0; j < correctAnswers.size(); j++) {
                if (!correctAnswers.get(j).isTrue()) {
                    correct = correct + 1;
                } else {
                    correctPoint=correctPoint+1;
            }
        }

        int countQst = questionsFormId.size();
        System.out.println(newAnswers);
        System.out.println(correctAnswers);
        System.out.println("questions: "+questions);
        System.out.println("Неправильных ответов"+incorrect);
        result = doubling*100/countQst;
        System.out.println("Верных ответов: "+ (points) +", из них дубли: "+doubling+", уникальных: "+unique);
//        System.out.println("Результат:"+ result);
//        System.out.println("Из "+countQst+" вопросов "+(points)+" верных ответов ("+result+"% из 100).");
        return "redirect:/endTakeTest";
    }

}

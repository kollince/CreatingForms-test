package com.creator.forms.services;

import com.creator.forms.dao.FormsDaoJsonImpl;
import com.creator.forms.dao.interfaces.FormsDao;
import com.creator.forms.models.Answers;
import com.creator.forms.models.Forms;
import com.creator.forms.services.interfaces.FormService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@AllArgsConstructor
@Service
public class FormServiceImpl implements FormService {
    private final FormsDao formsDao = new FormsDaoJsonImpl();
    private final List<Forms> formsList = new ArrayList<>();
    private final List<Answers> answersList = new ArrayList<>();
    private int counter;
    private void initStorages() {

    }

    public FormServiceImpl() {
        answersList.add(new Answers(1,"Я Вася", false));
        answersList.add(new Answers(2,"Я Коля", true));
        answersList.add(new Answers(3,"Я Иван", false));
        formsList.add(new Forms(1,"Коля", "Простой вопрос","Кто?", false, answersList));
        System.out.println(Arrays.toString(formsList.toArray()));
    }

    @Override
    public List<Forms> listForms() {
        return formsList;
    }
    @Override
    public void addForms(Forms form) {
       formsList.add(form);
    }

    @Override
    public List<Forms> updateForm(int id, String name, String description, String question, boolean isForTime, List<Answers> answersList) {
        for (int i = 0; i < formsList.size(); i++) {
            if(formsList.get(i).getId()==id){
                formsList.get(i).setName(name);
                formsList.get(i).setDescription(description);
                formsList.get(i).setQuestion(question);
                formsList.get(i).setForTime(isForTime);
                formsList.get(i).setAnswersList(answersList);
            }
        }
        return formsList;
    }


    @Override
    public Forms removeBook(int id) {
        for (Forms forms : formsList) {
            if(forms.getId()==id){
                formsList.remove(id);
            }
        }
        return formsList.remove(id);
    }

    @Override
    public Forms getFormsById(int id) {
        for (Forms forms : formsList) {
            if (forms.getId() == id) {
                return formsList.get(id);
            }
        }
        return formsList.get(id);
    }

}

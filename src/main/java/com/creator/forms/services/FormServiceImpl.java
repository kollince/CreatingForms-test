package com.creator.forms.services;

import com.creator.forms.dao.interfaces.FormsDao;
import com.creator.forms.dao.FormsDaoJsonImpl;
import com.creator.forms.models.Answers;
import com.creator.forms.models.Forms;
import com.creator.forms.services.interfaces.FormService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FormServiceImpl implements FormService {
    private final FormsDao formsDao = new FormsDaoJsonImpl();
    private final List<Forms> formsList = new ArrayList<>();
    @Override
    public List<Forms> listForms() {
        return formsList;
    }
    @Override
    public void addForms(Forms form) {
       formsList.add(form);
    }

    @Override
    public List<Forms> updateForm(int id, String name, String question, boolean isForTime, List<Answers> answersList) {
        for (int i = 0; i < formsList.size(); i++) {
            if(formsList.get(i).getId()==id){
                formsList.get(i).setName(name);
                formsList.get(i).setQuestion(question);
                formsList.get(i).setForTime(isForTime);
                formsList.get(i).setAnswersList(answersList);
            }
        }
        return formsList;
    }


    @Override
    public List<Forms> removeBook(int id) {
        return null;
    }

    @Override
    public List<Forms> getFormsById(int id) {
        return null;
    }

}

package com.creator.forms.services;

import com.creator.forms.dao.interfaces.FormsDao;
import com.creator.forms.models.Answers;
import com.creator.forms.models.Forms;
import com.creator.forms.models.Questions;
import com.creator.forms.services.interfaces.FormService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@AllArgsConstructor
@Service
public class FormServiceImpl implements FormService {
    private FormsDao formsDao;
//    private final List<Answers> answersList = new ArrayList<>();
    private int counter;
    private void initStorages() {

    }

    public FormServiceImpl(FormsDao formsDao) {
        this.formsDao = formsDao;
    }
    public FormServiceImpl() {
    }

    @Override
    public List<Forms> listForms() throws IOException {
        return formsDao.listForms();
//        return formsList;
    }
    @Override
    public List<Forms> addForms(Forms form) throws IOException {
        //formsDao.addForm(form);
        return formsDao.addForm(form);
       //formsList.add(form);
    }

    @Override
    public List<Forms> updateForm(int id, String name, String description, List<Questions> questionsList, List<Answers> answersList, boolean isForTime) {
        return formsDao.updateForm(id, name, description,questionsList, answersList, isForTime);
    }


    @Override
    public List<Forms> delete(int id) {
        return formsDao.delete(id);
    }

    @Override
    public Forms getFormsById(int id) {
        return formsDao.getFormsById(id);
    }

}

package com.creator.forms.services.interfaces;

import com.creator.forms.models.Answers;
import com.creator.forms.models.Forms;

import java.util.List;

public interface FormService {
    List<Forms> listForms();
    void addForms(Forms form);
    List<Forms> updateForm(int id, String name, String question, boolean isForTime, List<Answers> answersList);
    List<Forms> removeBook(int id);
    List<Forms>  getFormsById(int id);

}

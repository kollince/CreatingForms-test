package com.creator.forms.services.interfaces;

import com.creator.forms.models.Answers;
import com.creator.forms.models.Forms;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface FormService {
    List<Forms> listForms();
    void addForms(Forms form);
    List<Forms> updateForm(int id, String name, String description, String question, boolean isForTime, List<Answers> answersList);
    Forms removeBook(int id);
    Forms getFormsById(int id);

}

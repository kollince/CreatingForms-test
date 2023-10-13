package com.creator.forms.services.interfaces;

import com.creator.forms.models.Answers;
import com.creator.forms.models.Forms;
import com.creator.forms.models.Questions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
public interface FormService {
    List<Forms> listForms() throws IOException;
    int addForms(Forms form) throws IOException;
    List<Forms> updateForm(int id, String name, String description, List<Questions> questionsList, List<Answers> answersList, boolean isForTime);
    List<Forms> delete(int id);
    Forms getFormsById(int id);

}

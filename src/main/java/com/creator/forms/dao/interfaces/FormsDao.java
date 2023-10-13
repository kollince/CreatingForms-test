package com.creator.forms.dao.interfaces;

import com.creator.forms.models.Answers;
import com.creator.forms.models.Forms;
import com.creator.forms.models.Questions;
import org.springframework.stereotype.Repository;


import java.io.IOException;
import java.util.List;
@Repository
public interface FormsDao {
    public int addForm (Forms form) throws IOException;
    public List<Forms> updateForm(int id, String name, String description, List<Questions> questionsList, List<Answers> answersList, boolean isForTime);
    public List<Forms> delete(int id);
    public Forms getFormsById(int id);
    public List<Forms> listForms() throws IOException;
}

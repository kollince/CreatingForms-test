package com.creator.forms.dao.interfaces;

import com.creator.forms.models.Answers;
import com.creator.forms.models.Forms;
import com.creator.forms.models.Questions;
import org.springframework.stereotype.Repository;


import java.io.IOException;
import java.util.List;
@Repository
public interface FormsDao {
    public List<Answers> addAnswer(Answers answers);
    public List<Answers> listAnswers();
    public List<Answers> updateAnswers(int id, String answer, boolean isTrue);
    public List<Answers> deleteAnswers(int id);
    public Answers getAnswersById(int id);
    public List<Questions> addQuestion(Questions questions);
    public List<Questions> listQuestionsByFormId(int formId);
    public List<Questions> listQuestions();
    public List<Questions> updateQuestions(int id, int idForm, String question);
    public List<Questions> deleteQuestion(int id);
    public Questions getQuestionById(int id);
    public List<Forms> addForm (Forms form) throws IOException;
    public List<Forms> updateForm(int id, String name, String description, boolean isForTime);
    public List<Forms> delete(int id);
    public Forms getFormsById(int id);
    public List<Forms> listForms() throws IOException;
}

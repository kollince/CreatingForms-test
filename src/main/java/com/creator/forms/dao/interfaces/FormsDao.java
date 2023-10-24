package com.creator.forms.dao.interfaces;

import com.creator.forms.models.Answers;
import com.creator.forms.models.Forms;
import com.creator.forms.models.Questions;
import org.springframework.stereotype.Repository;


import java.io.IOException;
import java.util.List;
@Repository
public interface FormsDao {
    List<Answers> addAnswer(Answers answers);
    List<Answers> listAnswers();
    List<Answers> listAnswersByFormId(int formId);
    List<Answers> listAnswersByQuestionId(int questionId);
    List<Answers> updateAnswers(int id, String answer, boolean isTrue);
    List<Answers> deleteAnswers(int id);
    Answers getAnswersById(int id);
    List<Questions> addQuestion(Questions questions);
    List<Questions> listQuestionsByFormId(int formId);
    List<Questions> countQstForTest();
    List<Questions> listQuestions();
    List<Questions> updateQuestions(int id, String question);
    List<Questions> deleteQuestion(int id);
    Questions getQuestionById(int id);
    List<Forms> addForm (Forms form) throws IOException;
    List<Forms> updateForm(int id, String name, String description, boolean isForTime);
    List<Forms> delete(int id);
    Forms getFormsById(int id);
    List<Forms> listForms() throws IOException;

}

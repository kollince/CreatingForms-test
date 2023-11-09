package com.creator.forms.dao.interfaces;

import com.creator.forms.models.Answers;
import com.creator.forms.models.CorrectQuestions;
import com.creator.forms.models.Forms;
import com.creator.forms.models.Questions;
import org.springframework.stereotype.Repository;


import java.io.IOException;
import java.util.List;
import java.util.Map;

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
//    List<Passing> addPassing(Passing passing);
//    List<Passing> deletePassing(int id);
    Questions getQuestionOneByFormId(int formId);
    Map<Questions, List<Answers>> addCorrectQuestions(Answers answer, int idQuestion);
    List<CorrectQuestions> updateCorrectQuestions(int idQuestion, int id, int idForm, String answer, boolean isTrue);
    Map<Questions, List<Answers>> listCorrectQuestions();
    Map<Questions, List<Answers>> deleteQuestionsAns(int id);
}

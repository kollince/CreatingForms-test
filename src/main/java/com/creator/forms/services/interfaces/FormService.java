package com.creator.forms.services.interfaces;

import com.creator.forms.models.Answers;
import com.creator.forms.models.Forms;
import com.creator.forms.models.Questions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Service
public interface FormService {
    List<Answers> addAnswer(Answers answers) throws IOException;
    List<Answers> listAnswers() throws IOException;
    List<Answers> listAnswersByFormId(int formId) throws IOException;
    List<Answers> listAnswersByQuestionId(int questionId);
    List<Answers> updateAnswers(int id, String answer, boolean isTrue) throws IOException;
    List<Answers> deleteAnswers(int id) throws IOException;
    List<String> getIncorrectQuestionsUser();
    Answers getAnswersById(int id);
    List<Questions> addQuestion(Questions questions, MultipartFile image) throws IOException, URISyntaxException;
    List<Questions> listQuestions() throws IOException;
    List<Questions> listQuestionsByFormId(int formId) throws IOException;
    List<Questions> countQstForTest();
    List<Questions> updateQuestions(int id, String question, MultipartFile image, boolean del) throws IOException, URISyntaxException;
    List<Questions> deleteQuestion(int id) throws IOException;
    Questions getQuestionById(int id);
    List<Forms> listForms() throws IOException;
    List<Forms> addForms(Forms form, MultipartFile image) throws IOException, URISyntaxException;
    List<Forms> updateForm(int id, String name, String description, boolean isForTime, MultipartFile image, boolean isDelImage) throws IOException, URISyntaxException;
    List<Forms> delete(int id) throws IOException, URISyntaxException;
    Forms getFormsById(int id);
    Questions getQuestionOneByFormId(int formId);
    Map<Questions, List<Answers>> addCorrectQuestions(Answers answer, int idQuestion);
    Map<Questions, List<Answers>> updateCorrectAnswers(int id);
    Map<Questions, List<Answers>> updateCorrectQuestions(int id);
    Map<Questions, List<Answers>> listCorrectQuestions();
    Map<Questions, List<Answers>> deleteQuestionsAns(int id);
    Map<Questions, List<Answers>> deleteAnswersAns(int id);
    Map<Questions, List<Answers>> deleteQuestionsByFormId(int id);
    Map<Questions, List<Answers>> userQuestionsAndAnswers(List<Integer> answerList, int formId);
    boolean getIsValidCheckboxMark();
    int getResult();
    int getCountUserAnswer();
    int getSizeQuestion(int formId);
}

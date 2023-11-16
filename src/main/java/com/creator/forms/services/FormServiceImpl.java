package com.creator.forms.services;

import com.creator.forms.dao.interfaces.FormsDao;
import com.creator.forms.models.Answers;
import com.creator.forms.models.Forms;
import com.creator.forms.models.Questions;
import com.creator.forms.services.interfaces.FormService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@AllArgsConstructor
@Service
public class FormServiceImpl implements FormService {
    private FormsDao formsDao;
    private int counter;
    private void initStorages() {
    }

    public FormServiceImpl(FormsDao formsDao) {
        this.formsDao = formsDao;
    }
    public FormServiceImpl() {
    }

    @Override
    public List<Answers> addAnswer(Answers answers) {
        return formsDao.addAnswer(answers);
    }

    @Override
    public List<Answers> listAnswers() {
        return formsDao.listAnswers();
    }

    @Override
    public List<Answers> listAnswersByFormId(int formId) {
        return formsDao.listAnswersByFormId(formId);
    }

    @Override
    public List<Answers> listAnswersByQuestionId(int questionId) {
        return formsDao.listAnswersByQuestionId(questionId);
    }


    @Override
    public List<Answers> updateAnswers(int id, String answer, boolean isTrue) {
        return formsDao.updateAnswers(id,answer,isTrue);
    }

    @Override
    public List<Answers> deleteAnswers(int id) {
        return formsDao.deleteAnswers(id);
    }

    @Override
    public Answers getAnswersById(int id) {
        return formsDao.getAnswersById(id);
    }

    @Override
    public List<Questions> addQuestion(Questions questions) {
        return formsDao.addQuestion(questions);
    }

    @Override
    public List<Questions> listQuestions() {
        return formsDao.listQuestions();
    }
    @Override
    public List<Questions> listQuestionsByFormId(int formId){
        return formsDao.listQuestionsByFormId(formId);
    }
    @Override
    public List<Questions> countQstForTest(){
        return formsDao.countQstForTest();
    }
    @Override
    public List<Questions> updateQuestions(int id, String question) {
        return formsDao.updateQuestions(id, question);
    }

    @Override
    public List<Questions> deleteQuestion(int id) {
        return formsDao.deleteQuestion(id);
    }

    @Override
    public Questions getQuestionById(int id) {
        return formsDao.getQuestionById(id);
    }

    @Override
    public List<Forms> listForms() throws IOException {
        return formsDao.listForms();
    }
    @Override
    public List<Forms> addForms(Forms form) throws IOException {
        return formsDao.addForm(form);
    }

    @Override
    public List<Forms> updateForm(int id, String name, String description, boolean isForTime) throws IOException {
       return formsDao.updateForm(id, name, description,isForTime);
    }

    @Override
    public List<Forms> delete(int id) throws IOException {
        return formsDao.delete(id);
    }

    @Override
    public Forms getFormsById(int id) {
        return formsDao.getFormsById(id);
    }

    @Override
    public Questions getQuestionOneByFormId(int formId){
        return formsDao.getQuestionOneByFormId(formId);
    }
    @Override
    public Map<Questions, List<Answers>> addCorrectQuestions(Answers answer, int idQuestion){
        return formsDao.addCorrectQuestions(answer, idQuestion);
    }
    @Override
    public Map<Questions, List<Answers>> listCorrectQuestions(){
        return formsDao.listCorrectQuestions();
    }
    public Map<Questions, List<Answers>> updateCorrectAnswers(int id){
        return formsDao.updateCorrectAnswers(id);
    }
    public Map<Questions, List<Answers>> updateCorrectQuestions(int id){
        return formsDao.updateCorrectQuestions(id);
    }
    @Override
    public Map<Questions, List<Answers>> deleteQuestionsAns(int id){
        return formsDao.deleteQuestionsAns(id);
    }
    @Override
    public Map<Questions, List<Answers>> deleteAnswersAns(int id){
        return formsDao.deleteAnswersAns(id);
    }
    @Override
    public Map<Questions, List<Answers>> deleteQuestionsByFormId(int id){
        return formsDao.deleteQuestionsByFormId(id);
    }

    @Override
    public Map<Questions, List<Answers>> userQuestionsAndAnswers(List<Integer> answerList, int formId) {
        return formsDao.userQuestionsAndAnswers(answerList, formId);
    }
    @Override
    public int getResult() {
        return formsDao.getResult();
    }
    @Override
    public int getCountUserAnswer() {
        return formsDao.getCountUserAnswer();
    }
    @Override
    public int getSizeQuestion() {
        return formsDao.getSizeQuestion();
    }
}

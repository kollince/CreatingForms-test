package com.creator.forms.services;

import com.creator.forms.dao.interfaces.FormsDao;
import com.creator.forms.models.Answers;
import com.creator.forms.models.Forms;
import com.creator.forms.models.Questions;
import com.creator.forms.services.interfaces.FormService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public List<Answers> addAnswer(Answers answers) throws IOException {
        return formsDao.addAnswer(answers);
    }

    @Override
    public List<Answers> listAnswers() throws IOException {
        return formsDao.listAnswers();
    }

    @Override
    public List<Answers> listAnswersByFormId(int formId) throws IOException {
        return formsDao.listAnswersByFormId(formId);
    }

    @Override
    public List<Answers> listAnswersByQuestionId(int questionId) {
        return formsDao.listAnswersByQuestionId(questionId);
    }


    @Override
    public List<Answers> updateAnswers(int id, String answer, boolean isTrue) throws IOException {
        return formsDao.updateAnswers(id,answer,isTrue);
    }

    @Override
    public List<Answers> deleteAnswers(int id) throws IOException {
        return formsDao.deleteAnswers(id);
    }

    @Override
    public Answers getAnswersById(int id) {
        return formsDao.getAnswersById(id);
    }

    @Override
    public List<Questions> addQuestion(Questions questions, MultipartFile image) throws IOException {
        return formsDao.addQuestion(questions, image);
    }

    @Override
    public List<Questions> listQuestions() throws IOException {
        return formsDao.listQuestions();
    }
    @Override
    public List<Questions> listQuestionsByFormId(int formId) throws IOException {
        return formsDao.listQuestionsByFormId(formId);
    }
    @Override
    public List<Questions> countQstForTest(){
        return formsDao.countQstForTest();
    }
    @Override
    public List<Questions> updateQuestions(int id, String question, MultipartFile image, boolean isDelImage) throws IOException {
        return formsDao.updateQuestions(id, question, image, isDelImage);
    }

    @Override
    public List<Questions> deleteQuestion(int id) throws IOException {
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
    public List<Forms> addForms(Forms form, MultipartFile image) throws IOException {
        return formsDao.addForm(form, image);
    }

    @Override
    public List<Forms> updateForm(int id, String name, String description, boolean isForTime, MultipartFile image, boolean isDelImage) throws IOException {
       return formsDao.updateForm(id, name, description, isForTime, image, isDelImage);
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
    public int getSizeQuestion(int formId) {
        return formsDao.getSizeQuestion(formId);
    }

}

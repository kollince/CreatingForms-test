package com.creator.forms.dao;

import com.creator.forms.dao.interfaces.FormsDao;
import com.creator.forms.models.Answers;
import com.creator.forms.models.Forms;
import com.creator.forms.models.Questions;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;
import java.util.*;

import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
@Getter
@Setter
public class FormsDaoJsonImpl implements FormsDao {
    private final List<Forms> formsList = new ArrayList<>();
    private final List<Questions> questionsList = new ArrayList<>();
    private final List<Answers> answersList = new ArrayList<>();
    Map<Questions, List<Answers>> questionsAndAnswers = new HashMap<>();
    Map<Questions, List<Answers>> userQuestionsAndAnswers = new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();
    private Path fileListForms = Path.of("src/main/resources/data/listForms.json");
    private Path fileListQuestions = Path.of("src/main/resources/data/listQuestions.json");
    private Path fileListAnswers = Path.of("src/main/resources/data/listAnswers.json");
    private int idCount = 0;
    private int idCountQuestion = 0;
    private int idCountPassing = 0;
    private int idCountAnswer = 0;
    private int idAnswer = 0;
    private int idCountUserAnswer = 0;
    private int countUserAnswer = 0;
    private int result = 0;
    private int countQst;


//    public FormsDaoJsonImpl() {
//    }
public FormsDaoJsonImpl() throws IOException {
        openFileListForms(formsList, fileListForms);
        openFileListQuestions(questionsList,fileListQuestions);
        openFileListAnswers(answersList,fileListAnswers);
    }
    private void fileExist(Path path) throws IOException {
       File file = new File(path.toUri());
       if (!file.exists()) {
           file.createNewFile();
       }
    }
    private void saveFileListForms(List<Forms> formsList, Path path) throws IOException {
        fileExist(path);
        mapper.writeValue(fileListForms.toFile(), formsList);
    }
    private void saveFileListQuestions(List<Questions> questionsList, Path path) throws IOException {
        fileExist(path);
        mapper.writeValue(fileListQuestions.toFile(), questionsList);
    }
    private void saveFileListAnswers(List<Answers> AnswersList, Path path) throws IOException {
        fileExist(path);
        mapper.writeValue(fileListAnswers.toFile(), AnswersList);
    }
    private void openFileListForms(List<Forms> formsList, Path path) throws IOException {
        fileExist(path);
        List<Forms> formsJson = mapper.readValue(path.toFile(), new TypeReference<List<Forms>>() {});
        if (formsList.isEmpty()){
            formsList.addAll(formsJson);

        }
    }
    private void openFileListQuestions(List<Questions> questionsList, Path path) throws IOException {
        fileExist(path);
        List<Questions> questionsJson = mapper.readValue(path.toFile(), new TypeReference<List<Questions>>() {});
        if (questionsList.isEmpty()){
            questionsList.addAll(questionsJson);
        }
    }
    private List<Answers> filterAnswers(int idQuestion){
        return answersList.stream().filter(answers -> answers.getIdQuestion() == idQuestion && answers.isTrue())
                .collect(Collectors.toCollection(ArrayList::new));
    }
    private void openFileListAnswers(List<Answers> answersList, Path path) throws IOException {
        fileExist(path);
        List<Answers> answersJson = mapper.readValue(path.toFile(), new TypeReference<List<Answers>>() {});
        if(answersList.isEmpty()){
           answersList.addAll(answersJson);
            for (int i = 0; i < questionsList.size(); i++) {
                List<Answers> filterAnswers = filterAnswers(questionsList.get(i).getId());
                questionsAndAnswers.put(questionsList.get(i), filterAnswers);
            }
        }
    }
    @Override
    public List<Answers> addAnswer(Answers answers) throws IOException {
        int max = 0;
        if (answersList.size() == 0) {
            idCountAnswer = 1;
            answers.setId(idCountAnswer);
        } else {
            for (int i = 0; i < answersList.size(); i++) {
                max = Math.max(max, answersList.get(i).getId());
                if (answersList.get(i).getId() > answersList.size()) {
                    idCountAnswer = max;
                }
            }
            idCountAnswer++;
            answers.setId(idCountAnswer);
        }
        answersList.add(answers);
        saveFileListAnswers(answersList,fileListAnswers);
        return answersList;
    }
    @Override
    public List<Answers> listAnswers() throws IOException {
        openFileListAnswers(answersList, fileListAnswers);
        for (Answers answer : answersList) {
            if (answersList.size() < idCountAnswer) {
                idCountAnswer = answersList.size()+1;
                break;
            }
        }
        return answersList;
    }

    @Override
    public List<Answers> listAnswersByFormId(int formId) throws IOException {
        return answersList.stream()
                .filter(answers -> answers.getIdForm() == formId)
                .collect(Collectors.toCollection(ArrayList::new));
    }


    @Override
    public List<Answers> listAnswersByQuestionId(int questionId) {
        return answersList.stream()
                .filter(answers -> answers.getIdQuestion() == questionId)
                .collect(Collectors.toCollection(ArrayList::new));
    }


    @Override
    public List<Answers> updateAnswers(int id, String answer, boolean isTrue) throws IOException {
        for (Answers answers : answersList) {
            if (answers.getId() == id) {
                answers.setAnswer(answer);
                answers.setTrue(isTrue);
            }
        }
        saveFileListAnswers(answersList,fileListAnswers);
        return answersList;
    }
    @Override
    public List<Answers> deleteAnswers(int id) throws IOException {
        for (int i = 0; i < answersList.size(); i++) {
            if (answersList.get(i).getId()==id) {
                answersList.remove(i);
                idCountAnswer = idCountAnswer - 1;
            }
        }
        saveFileListAnswers(answersList,fileListAnswers);
        return answersList;
    }
    @Override
    public Answers getAnswersById(int id) {
        Answers element = null;
        for (Answers answers : answersList) {
            if (answers.getId() == id) {
                element = answers;
            }
        }
        return element;
    }
    @Override
    public List<Questions> addQuestion(Questions questions) throws IOException {
        int max = 0;
        if (questionsList.size() == 0) {
            idCountQuestion = 1;
            questions.setId(idCountQuestion++);
        } else {
            for (int i = 0; i < questionsList.size(); i++) {
                max = Math.max(max, questionsList.get(i).getId());
                if (questionsList.get(i).getId() > questionsList.size()) {
                    idCountQuestion = max+1;
                }

            }
            questions.setId(idCountQuestion++);
        }
        questionsList.add(questions);
        saveFileListQuestions(questionsList,fileListQuestions);
        return questionsList;
    }

    @Override
    public List<Questions> listQuestionsByFormId(int formId) {
        List<Questions> qstByFormId = questionsList.stream()
                .filter(questions -> questions.getIdForm() == formId)
                .collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < questionsList.size(); i++) {
            if (questionsList.size() < idCountQuestion) {
                idCountQuestion = questionsList.size() + 1;
                break;
            }
        }
        return qstByFormId;
    }

    @Override
    public List<Questions> countQstForTest() {
        int min = 1;
        int max = 0;
        List<Questions> qstByFormId = new ArrayList<>();
        for (int i = 0; i < formsList.size(); i++) {
            for (int j = 0; j < questionsList.size(); j++) {
                if (formsList.get(i).getId()==questionsList.get(j).getIdForm()){
                    min = Math.min(min, j);
                    max = Math.max(max, questionsList.get(j).getId());
                    if (questionsList.get(j)!=null) {
                        qstByFormId.add(questionsList.get(j));
                        break;
                    }

                }
            }
        }
        return qstByFormId;
    }

    @Override
    public List<Questions> listQuestions() throws IOException {
        openFileListQuestions(questionsList, fileListQuestions);
        for (Questions questions : questionsList) {
            if (questionsList.size() < idCountQuestion) {
                idCountQuestion = questionsList.size()+1;
            }
        }
        return questionsList;
    }
    @Override
    public List<Questions> updateQuestions(int id, String question) throws IOException {
        for (Questions questions : questionsList) {
            if (questions.getId() == id) {
                  questions.setQuestion(question);
            }
        }
        saveFileListQuestions(questionsList,fileListQuestions);
        return questionsList;
    }
    @Override
    public List<Questions> deleteQuestion(int id) throws IOException {
        for (int i = 0; i < questionsList.size(); i++) {
            if (questionsList.get(i).getId()==id) {
                questionsList.remove(i);
            }
        }
        for (int k = 0; k < answersList.size(); k++) {
            if (answersList.get(k).getIdQuestion() == id) {
                answersList.remove(k);
                idCountAnswer = idCountAnswer - 1;
                k--;
            }
        }
        saveFileListAnswers(answersList,fileListAnswers);
        saveFileListQuestions(questionsList,fileListQuestions);
        return questionsList;
    }

    @Override
    public Questions getQuestionById(int id) {
        Questions element = null;
        for (int i = 0; i < questionsList.size(); i++) {
            if (questionsList.get(i).getId() == id){
                element = questionsList.get(i);
            }
        }
        return element;
    }
    @Override
    public Questions getQuestionOneByFormId(int formId) {
        Questions element = null;
        for (int i = 0; i < questionsList.size(); i++) {
            if (questionsList.get(i).getIdForm() == formId){
                element = questionsList.get(i);
                break;

            }
        }
        return element;
    }

    @Override
    public List<Forms> addForm(Forms form) throws IOException {
        int max = 0;
        if (formsList.size() == 0) {
            idCount = 1;
            form.setId(idCount++);

         } else {
            for (int i = 0; i < formsList.size(); i++) {
                max = Math.max(max, formsList.get(i).getId());
                if (formsList.get(i).getId() > formsList.size()) {
                    idCount = max+1;
                }
            }
            form.setId(idCount++);
        }
        formsList.add(form);
        saveFileListForms(formsList,fileListForms);
        return formsList;
    }

    @Override
    public List<Forms> listForms() throws IOException {
        openFileListForms(formsList, fileListForms);
        for (Forms forms : formsList) {
            if (formsList.size() < idCount) {
                idCount = formsList.size()+1;
            }
        }
        return formsList;
//        return mapper.readValue(FILEPATH.toFile(), new TypeReference<>() {
//        });
    }

    @Override
    public List<Forms> updateForm(int id, String name, String description, boolean isForTime) throws IOException {
        for (Forms forms : formsList) {
            if (forms.getId() == id) {
                forms.setName(name);
                forms.setDescription(description);
                forms.setForTime(isForTime);
            }
        }
        saveFileListForms(formsList,fileListForms);
        return formsList;
    }

    @Override
    public List<Forms> delete(int id) throws IOException {
        for (int i = 0; i < formsList.size(); i++) {
            if (formsList.get(i).getId()==id) {
                formsList.remove(i);
                for (int j = 0; j < questionsList.size(); j++) {
                    if (questionsList.get(j).getIdForm() == id) {
                        questionsList.remove(j);
                        j--;
                    }
                }
                for (int k = 0; k < answersList.size(); k++) {
                    if (answersList.get(k).getIdForm() == id) {
                        answersList.remove(k);
                        k--;
                    }
                }
            }
        }
        saveFileListForms(formsList,fileListForms);
        saveFileListAnswers(answersList,fileListAnswers);
        saveFileListQuestions(questionsList,fileListQuestions);
        return formsList;
    }

    @Override
    public Forms getFormsById(int id) {
        Forms element = null;
        for (Forms forms : formsList) {
            if (forms.getId() == id) {
                element = forms;
            }
        }
        return element;
    }
    @Override
    public Map<Questions, List<Answers>> addCorrectQuestions(Answers answer, int idQuestion){
        if (idCountAnswer == 0) {
            idAnswer++;
        } else {
            idAnswer = idCountAnswer;
        }
        List<Answers> ans = new ArrayList<>();
        Questions qst = null;
        for (Questions questions : questionsList) {
            ans = answersList.stream()
                    .filter(answers -> answers.getIdQuestion() == idQuestion && answers.isTrue())
                    .collect(Collectors.toCollection(ArrayList::new));
            if (questions.getId() == idQuestion) {
                qst = questions;
            }
        }
        questionsAndAnswers.put(qst, ans);
        return questionsAndAnswers;
    }
    @Override
    public Map<Questions, List<Answers>> updateCorrectAnswers(int id) {
        Questions qst = null;
        List<Answers> ans;
        ans = answersList.stream()
                    .filter(answers -> answers.getId() == id && answers.isTrue())
                    .collect(Collectors.toCollection(ArrayList::new));
        if(!ans.isEmpty()) {
            List<Answers> newAns = answersList.stream()
                    .filter(Answers::isTrue)
                    .collect(Collectors.toCollection(ArrayList::new));
            for (int i = 0; i < newAns.size(); i++) {
                if (newAns.get(i).getId()==id){
                    if (ans.get(0).getId() == newAns.get(i).getId()){
                        newAns.set(i, ans.get(0));
                    }
                }
            }
            int idQuestion = ans.get(0).getIdQuestion();
            List<Answers> newAns2 = newAns.stream()
                    .filter(answers -> answers.getIdQuestion() == idQuestion)
                    .collect(Collectors.toCollection(ArrayList::new));
            for (int i = 0; i < questionsList.size(); i++) {
                if (questionsList.get(i).getId() == idQuestion) {
                    qst = questionsList.get(i);
                }
            }
            questionsAndAnswers.put(qst,newAns2);
        } else {
            deleteAnswersAns(id);
        }
        return questionsAndAnswers;
    }
    @Override
    public Map<Questions, List<Answers>> updateCorrectQuestions(int id) {
        List<Answers> ans = new ArrayList<>();
        Questions qst = null;
        for (Questions questions : questionsList){
            ans = answersList.stream()
                    .filter(answers -> answers.getIdQuestion() == id && answers.isTrue())
                    .collect(Collectors.toCollection(ArrayList::new));
            if (questions.getId() == id) {
                qst = questions;
                break;
            }
        }
        questionsAndAnswers.put(qst,ans);
        questionsAndAnswers.remove(qst);
        return questionsAndAnswers;
    }
    @Override
    public Map<Questions, List<Answers>> listCorrectQuestions(){
         return questionsAndAnswers;
    }
    @Override
    public Map<Questions, List<Answers>> deleteQuestionsAns(int id) {
        Map<Questions, List<Answers>> newMap = new HashMap<>(questionsAndAnswers);
        for (Map.Entry<Questions, List<Answers>> entry : questionsAndAnswers.entrySet()) {
            if (entry.getKey().getId()==id) {
                newMap.remove(entry.getKey());
            }
        }
        questionsAndAnswers = newMap;
        return questionsAndAnswers;
    }
    @Override
    public Map<Questions, List<Answers>> deleteAnswersAns(int id) {
        Map<Questions, List<Answers>> newMap = new HashMap<>(questionsAndAnswers);
        for (Map.Entry<Questions, List<Answers>> entry : newMap.entrySet()) {
            for (Answers answer : entry.getValue()) {
                if (answer.getId()==(id)) {
                    entry.getValue().remove(answer);
                    break;
                }
            }
        }
        return questionsAndAnswers;
    }
    @Override
    public Map<Questions, List<Answers>> deleteQuestionsByFormId(int id) {
        Map<Questions, List<Answers>> newMap = new HashMap<>(questionsAndAnswers);
        for (Map.Entry<Questions, List<Answers>> entry : questionsAndAnswers.entrySet()) {
            if (entry.getKey().getIdForm()==id) {
                newMap.remove(entry.getKey());
            }
        }
        questionsAndAnswers = newMap;
        return questionsAndAnswers;
    }

    @Override
    public Map<Questions, List<Answers>> userQuestionsAndAnswers(List<Integer> answer, int formId) {
        List<Answers> newAnswers = new ArrayList<>();
        for (Integer integer : answer) {
            for (Answers answers : answersList) {
                if (integer == answers.getId()) {
                        newAnswers.add(answers);
                }
            }
        }
        List<Answers> ans;
        for (int i = 0; i < newAnswers.size(); i++) {
            for (int j = 0; j < questionsList.size(); j++) {
                if (questionsList.get(j).getId()==newAnswers.get(i).getIdQuestion()){
                    int finalI = j;
                    ans = newAnswers.stream()
                            .filter(answers -> answers.getIdQuestion() == questionsList.get(finalI).getId())
                            .collect(Collectors.toCollection(ArrayList::new));
                    userQuestionsAndAnswers.put(questionsList.get(j),ans);
                }
            }
        }
        countQst = questionsList.size();
        for (Map.Entry<Questions, List<Answers>> entry : questionsAndAnswers.entrySet()) {
            Questions question = entry.getKey();
            List<Answers> correctAnswers = entry.getValue();
            for (Map.Entry<Questions, List<Answers>> otherEntry : userQuestionsAndAnswers.entrySet()) {
                Questions otherQuestion = otherEntry.getKey();
                List<Answers> userAnswers = otherEntry.getValue();
                if (question.equals(otherQuestion)) {
                    if (correctAnswers.equals(userAnswers)) {
                        countUserAnswer++;
                        result = countUserAnswer*100/countQst;
                    }
                }
            }
        }
        return userQuestionsAndAnswers;
    }
    @Override
    public int getResult(){
        int res = result;
        if (result>0) result=0;
        if (countUserAnswer>0)countUserAnswer=0;
        return res;
    }
    @Override
    public int getCountUserAnswer() {
        return countUserAnswer;
    }
    @Override
    public int getSizeQuestion(){
        return questionsList.size();
    }
}

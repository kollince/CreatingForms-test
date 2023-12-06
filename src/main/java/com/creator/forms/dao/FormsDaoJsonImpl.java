package com.creator.forms.dao;

import com.creator.forms.dao.interfaces.FormsDao;
import com.creator.forms.models.Answers;
import com.creator.forms.models.Forms;
import com.creator.forms.models.Questions;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URISyntaxException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    //private Path fileListForms = Path.of("src/main/resources/data/listForms.json");
    private Path fileListForms = Path.of("target/classes/data/listForms.json");
    //private Path fileListQuestions = Path.of("src/main/resources/data/listQuestions.json");
    private Path fileListQuestions = Path.of("target/classes/data/listQuestions.json");
    //private Path fileListAnswers = Path.of("src/main/resources/data/listAnswers.json");
    private Path fileListAnswers = Path.of("target/classes/data/listAnswers.json");
    private String pathImages = "/images";
    private String pathFile = System.getProperty("user.home")+File.separator+"images";
    //private String pathImages = "src/main/resources/static/images";

    boolean debug;

    private int idCount = 0;
    private int idCountQuestion = 0;
    private int idCountAnswer = 0;
    private int idAnswer = 0;
    private int idCountUserAnswer = 0;
    private int countUserAnswer = 0;
    private int result = 0;
    private int countQst;

    public FormsDaoJsonImpl() throws IOException {
        openFileListForms(formsList, fileListForms);
        openFileListQuestions(questionsList,fileListQuestions);
        openFileListAnswers(answersList,fileListAnswers);
    }

    private void fileExist(Path path) throws IOException {
        File file = new File(path.toAbsolutePath().toUri());
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
    private void counterFormsReadFile(List<Forms> formsJson){
        int max = 0;
        for (Forms forms : formsJson) {
            max = Math.max(max, forms.getId());
            idCount = max + 1;
        }
    }
    private void counterQuestionsReadFile(List<Questions> questionsJson){
        int max = 0;
        for (Questions questions : questionsJson) {
            max = Math.max(max, questions.getId());
            idCountQuestion = max + 1;
        }
    }
    private void counterAnswersReadFile(List<Answers> answersJson){
        int max = 0;
        for (Answers answers : answersJson) {
            max = Math.max(max, answers.getId());
            idCountAnswer = max;
        }
    }
    private void openFileListForms(List<Forms> formsList, Path path) throws IOException {
        System.out.println(Paths.get(System.getProperty("java.io.tmpdir")+File.separator+"images"));
        System.out.println(Paths.get(System.getProperty("user.home")));
        System.out.println(pathImages);
        //Path rootDir = this.rootLocation.resolve(pathImages);
        //System.out.println(rootDir);
        fileExist(path);
        try {
            List<Forms> formsJson = mapper.readValue(path.toFile(), new TypeReference<List<Forms>>() {});
            if (formsList.isEmpty()){
                formsList.addAll(formsJson);
                counterFormsReadFile(formsJson);
            }
        } catch (Exception e){
            System.out.println(e);
        }

    }
    private void openFileListQuestions(List<Questions> questionsList, Path path) throws IOException {
        fileExist(path);
        List<Questions> questionsJson = mapper.readValue(path.toFile(), new TypeReference<List<Questions>>() {});
        if (questionsList.isEmpty()){
            questionsList.addAll(questionsJson);
            counterQuestionsReadFile(questionsJson);
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
            counterAnswersReadFile(answersJson);
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
    public List<Answers> listAnswers() {
        for (Answers answer : answersList) {
            if (answersList.size() < idCountAnswer) {
                idCountAnswer = answersList.size()+1;
                break;
            }
        }
        return answersList;
    }

    @Override
    public List<Answers> listAnswersByFormId(int formId) {
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
    public List<Questions> addQuestion(Questions questions, MultipartFile image) throws IOException, URISyntaxException {
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
        addImage(image);
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
//        userQuestionsAndAnswers.remove(listQuestionsByFormId(formId),listAnswersByFormId(formId));
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
    public List<Questions> listQuestions() {
        for (Questions questions : questionsList) {
            if (questionsList.size() < idCountQuestion) {
                idCountQuestion = questionsList.size()+1;
            }
        }
        return questionsList;
    }
    @Override
    public List<Questions> updateQuestions(int id, String question, MultipartFile image, boolean isDelImage) throws IOException, URISyntaxException {
        for (Questions questions : questionsList) {
            if (questions.getId() == id) {
                if(!image.isEmpty()) {
                    delImage(questions.getImage());
                    addImage(image);
                    questions.setImage(image.getOriginalFilename());
                }
                if (isDelImage){
                    delImage(questions.getImage());
                    questions.setImage("");
                }
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
    public List<Forms> addForm(Forms form, MultipartFile image) throws IOException, URISyntaxException {
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

        addImage(image);
        formsList.add(form);
        saveFileListForms(formsList,fileListForms);
        return formsList;
    }

    @Override
    public List<Forms> listForms() {
        for (Forms forms : formsList) {
            if (formsList.size() < idCount) {
                idCount = formsList.size()+1;
            }
        }
        return formsList;
    }

    @Override
    public List<Forms> updateForm(int id, String name, String description, boolean isForTime, MultipartFile image, boolean isDelImage) throws IOException, URISyntaxException {
        for (Forms forms : formsList) {
            if (forms.getId() == id) {
                if (!image.isEmpty()){
                    delImage(forms.getImage());
                    addImage(image);
                    forms.setImage(image.getOriginalFilename());
                }
                if (isDelImage){
                    delImage(forms.getImage());
                    forms.setImage("");
                }
                forms.setName(name);
                forms.setDescription(description);
                forms.setForTime(isForTime);
            }
        }
        saveFileListForms(formsList,fileListForms);
        return formsList;
    }

    @Override
    public List<Forms> delete(int id) throws IOException, URISyntaxException {
        for (int i = 0; i < formsList.size(); i++) {
            if (formsList.get(i).getId()==id) {
                delImage(formsList.get(i).getImage());
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
            questionsAndAnswers.remove(qst);
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
        List<Questions> questionsForByFormId = questionsList.stream()
                .filter(q -> q.getIdForm() == formId)
                .collect(Collectors.toCollection(ArrayList::new));
        List<Answers> ans;
        if (userQuestionsAndAnswers.size()>0){
            userQuestionsAndAnswers.clear();
        }
        for (int i = 0; i < newAnswers.size(); i++) {
            for (int j = 0; j < questionsForByFormId.size(); j++) {
                if (questionsForByFormId.get(j).getId()==newAnswers.get(i).getIdQuestion()){
                    int finalI = j;
                    ans = newAnswers.stream()
                            .filter(answers -> answers.getIdQuestion() == questionsForByFormId.get(finalI).getId())
                            .collect(Collectors.toCollection(ArrayList::new));
                    userQuestionsAndAnswers.put(questionsForByFormId.get(j),ans);
                }
            }

        }
        Map<Questions, List<Answers>> filteredCorrectMapAnswers = questionsAndAnswers.entrySet()
                .stream().filter(questions -> questions.getKey().getIdForm()==formId)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        countQst = filteredCorrectMapAnswers.size();
        for (Map.Entry<Questions, List<Answers>> entry : filteredCorrectMapAnswers.entrySet()) {
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
    public int getSizeQuestion(int formId){
        return questionsList.stream().filter(q -> q.getIdForm() == formId)
                            .collect(Collectors.toCollection(ArrayList::new)).size();
    }

    public void addImage(MultipartFile image) throws IOException, URISyntaxException {
        Path ps = Paths.get(Objects.requireNonNull(this.getClass().getResource(pathImages)).toURI());
        File dir = new File(ps.toUri());
        if (!dir.exists()) {
            FileUtils.forceMkdir(dir);
        }
        if (image != null && !image.isEmpty()) {
            try {
                byte[] bytes = image.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(dir + File.separator + image.getOriginalFilename()));
                System.out.println("bytes " + dir +File.separator+ image.getOriginalFilename());
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void delImage(String nameImage) throws IOException, URISyntaxException {
        Path ps = Paths.get(Objects.requireNonNull(this.getClass().getResource(pathImages)).toURI());
        Path file = Paths.get(ps+File.separator+nameImage).toAbsolutePath();
        if (Files.exists(file) && !Files.isDirectory(file)) {
            Files.delete(file);
        }

    }
}

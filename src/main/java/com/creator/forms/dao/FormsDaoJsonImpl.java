package com.creator.forms.dao;

import com.creator.forms.dao.interfaces.FormsDao;
import com.creator.forms.models.Answers;
import com.creator.forms.models.Forms;
import com.creator.forms.models.CorrectQuestions;
import com.creator.forms.models.Questions;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import java.util.List;

import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
@Getter
@Setter
public class FormsDaoJsonImpl implements FormsDao {
    private final List<Forms> formsList = new ArrayList<>();
    private final List<Questions> questionsList = new ArrayList<>();
    private final List<Answers> answersList = new ArrayList<>();
    private final List<CorrectQuestions> passingsList = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();
    final Path FILEPATH = Path.of("src/main/resources/data/listForms.json");
    private int idCount = 0;
    private int idCountQuestion = 0;
    private int idCountPassing = 0;
    private int idCountAnswer = 0;

    public FormsDaoJsonImpl() {

    }
    private void fileExist()  {
        File file = new File(FILEPATH.toUri());
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e){
            System.err.println("file not found");
        }
    }
    @Override
    public List<Answers> addAnswer(Answers answers) {
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
        return answersList;
    }
    @Override
    public List<Answers> listAnswers() {
//        System.out.println("idCountAnswer_list_1: "+idCountAnswer);
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
        List<Answers> ansByFormId = answersList.stream()
                .filter(answers -> answers.getIdForm() == formId)
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println(ansByFormId);
        return ansByFormId;
    }


    @Override
    public List<Answers> listAnswersByQuestionId(int questionId) {
        List<Answers> ansByQuestionId = answersList.stream()
                .filter(answers -> answers.getIdQuestion() == questionId)
                .collect(Collectors.toCollection(ArrayList::new));
        return ansByQuestionId;
    }


    @Override
    public List<Answers> updateAnswers(int id, String answer, boolean isTrue) {
        for (Answers answers : answersList) {
            if (answers.getId() == id) {
                answers.setAnswer(answer);
                answers.setTrue(isTrue);
            }
        }
        return answersList;
    }
    @Override
    public List<Answers> deleteAnswers(int id) {
        for (int i = 0; i < answersList.size(); i++) {
            if (answersList.get(i).getId()==id) {
                answersList.remove(i);
                idCountAnswer = idCountAnswer - 1;

            }
        }
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
    public List<Questions> addQuestion(Questions questions) {
        int max = 0;
        if (questionsList.size() == 0) {
            idCountQuestion = 1;
            questions.setId(idCountQuestion++);
            //updatePassing(questions.getIdForm(),1, idCountQuestion);
            //addPassing(questions.getIdForm(),idCountQuestion,1);
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
        //updatePassing(questions.getIdForm(),questions.getId(), idCountQuestion);
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
        System.out.println("Всего вопросов: "+questionsList.size());
        for (int i = 0; i < formsList.size(); i++) {
            for (int j = 0; j < questionsList.size(); j++) {
                if (formsList.get(i).getId()==questionsList.get(j).getIdForm()){
                    min = Math.min(min, j);
                    max = Math.max(max, questionsList.get(j).getId());

                    if (questionsList.get(j)!=null) {
                        System.out.println("Тест: "+formsList.get(i).getName()+", idQuestion: "+questionsList.get(j).getId());
                        qstByFormId.add(questionsList.get(j));
                        System.out.println("qstByFormId"+qstByFormId);
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
    public List<Questions> updateQuestions(int id, String question) {

        for (Questions questions : questionsList) {
            if (questions.getId() == id) {
                  questions.setQuestion(question);
            }
        }
        return questionsList;
    }
    @Override
    public List<Questions> deleteQuestion(int id) {
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
            //addPassing(idCount,0,1);
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
        System.out.println(formsList);
        return formsList;
        //fileExist();
        //mapper.writeValue(FILEPATH.toFile(), form);

    }

    @Override
    public List<Forms> listForms() throws IOException {
        for (Forms forms : formsList) {
            if (formsList.size() < idCount) {
                idCount = formsList.size()+1;
            }
        }
        System.out.println("=================");
        return formsList;
//        return mapper.readValue(FILEPATH.toFile(), new TypeReference<>() {
//        });
    }

    @Override
    public List<Forms> updateForm(int id, String name, String description, boolean isForTime) {
        for (Forms forms : formsList) {
            if (forms.getId() == id) {
                forms.setName(name);
                forms.setDescription(description);
                forms.setForTime(isForTime);
            }
        }
        return formsList;
    }

    @Override
    public List<Forms> delete(int id) {
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

//    public void updatePassing(int idForm, int idQuestion, int number) {
//        for (Passing passing : passingsList) {
//            if (passing.getId() == idForm) {
//                passing.setIdQuestion(idQuestion);
//                passing.setNumber(number);
//             }
//        }
//        System.out.println(passingsList);
////        return passingsList;
//    }

}

package com.creator.forms.dao;

import com.creator.forms.dao.interfaces.FormsDao;
import com.creator.forms.models.Answers;
import com.creator.forms.models.Forms;
import com.creator.forms.models.Questions;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
@Repository
public class FormsDaoJsonImpl implements FormsDao {
    private final List<Forms> formsList;
    private final ObjectMapper mapper = new ObjectMapper();
    final Path FILEPATH = Path.of("src/main/resources/data/listForms.json");
    private int idCount = 0;

    public FormsDaoJsonImpl(List<Forms> formsList) {
        this.formsList = formsList;
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
    public List<Forms> addQuestion(Forms form) {
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
        return formsList;
        //fileExist();
        //mapper.writeValue(FILEPATH.toFile(), form);

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
        return formsList;
//        return mapper.readValue(FILEPATH.toFile(), new TypeReference<>() {
//        });
    }

    @Override
    public List<Forms> updateForm(int id, String name, String description, List<Questions> questionsList, List<Answers> answersList, boolean isForTime) {
        int maxCount = 0;
        int count = 0;
        for (int i = 0; i < formsList.size(); i++) {
            if(formsList.get(i).getId()==id){
                formsList.get(i).setName(name);
                formsList.get(i).setDescription(description);
                if (formsList.get(i).getQuestionsList().size()!=0){
                    for (int j = 0; j < formsList.get(i).getQuestionsList().size(); j++) {
                        maxCount = Math.max(maxCount, formsList.get(i).getQuestionsList().get(j).getId());
                        if (formsList.get(i).getQuestionsList().get(j).getId() > formsList.size()) {
                            count = maxCount+1;
                            System.out.println("max: "+maxCount);
                        }
                        formsList.get(i).getQuestionsList().get(j).setIdForm(count);
                    }
                }
                formsList.get(i).setQuestionsList(questionsList);
                formsList.get(i).setForTime(isForTime);
                formsList.get(i).setAnswersList(answersList);
            }
        }
        return formsList;
    }

    @Override
    public List<Forms> delete(int id) {
        for (int i = 0; i < formsList.size(); i++) {
            if (formsList.get(i).getId()==id) {
                formsList.remove(i);
            }

        }
       //idCount = formsList.size()-1;
        return formsList;
    }

    @Override
    public Forms getFormsById(int id) {
        Forms element = null;
        for (int i = 0; i < formsList.size(); i++) {
            if (formsList.get(i).getId() == id){
                element = formsList.get(i);
            }
        }
        return element;
    }


}

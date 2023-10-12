package com.creator.forms.dao;

import com.creator.forms.dao.interfaces.FormsDao;
import com.creator.forms.models.Forms;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
@Repository
public class FormsDaoJsonImpl implements FormsDao {
    private final ObjectMapper mapper = new ObjectMapper();
    final Path FILEPATH = Path.of("src/main/resources/data/listForms.json");
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
    public void addForm(Forms form) throws IOException {
        fileExist();
        mapper.writeValue(FILEPATH.toFile(), form);
    }

    @Override
    public List<Forms> listForms() throws IOException {
        return mapper.readValue(FILEPATH.toFile(), new TypeReference<>() {
        });
    }

    @Override
    public void updateForm(Forms form) {

    }

    @Override
    public void removeBook(int id) {
        return;
    }

    @Override
    public Forms getFormsById(int id) {
        return null;
    }


}

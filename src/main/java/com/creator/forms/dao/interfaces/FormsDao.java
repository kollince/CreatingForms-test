package com.creator.forms.dao.interfaces;

import com.creator.forms.models.Forms;
import org.springframework.stereotype.Repository;


import java.io.IOException;
import java.util.List;
@Repository
public interface FormsDao {
    public void addForm (Forms form) throws IOException;
    public void updateForm(Forms form);
    public void removeBook(int id);
    public Forms getFormsById(int id);
    public List<Forms> listForms() throws IOException;
}

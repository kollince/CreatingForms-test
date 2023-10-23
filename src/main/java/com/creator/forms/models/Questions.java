package com.creator.forms.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@Data
public class Questions {
    private int id;
    private int idForm;
    private String question;
}

package com.creator.forms.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CorrectQuestions {
    private int id;
    private int idForm;
    private Answers answers;

}

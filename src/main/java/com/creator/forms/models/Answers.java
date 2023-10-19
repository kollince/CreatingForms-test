package com.creator.forms.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Answers {
    private int id;
    private int idForm;
    private int idQuestion;
    private String answer;
    private boolean isTrue;
}

package com.creator.forms.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CorrectAnswer {
    private int id;
    private int idForm;
    private String answer;
    private boolean isTrue;
}

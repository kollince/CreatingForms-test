package com.creator.forms.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Answers {
    private int id;
    private int ifForm;
    private String answer;
    private boolean isTrue;
}

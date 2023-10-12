package com.creator.forms.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@AllArgsConstructor
@Data
public class Forms {
    private int id;
    private String name;
    private String question;
    private boolean isForTime;
    private List<Answers> answersList;

}

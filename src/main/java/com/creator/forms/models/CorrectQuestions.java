package com.creator.forms.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class CorrectQuestions {
    private int idQuestion;
    private List<Answers> ansList;
}

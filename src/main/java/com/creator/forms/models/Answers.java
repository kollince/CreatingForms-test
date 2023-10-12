package com.creator.forms.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Answers {
    int id;
    String answer;
    boolean isTrue;
}

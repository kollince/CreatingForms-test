package com.creator.forms.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Forms {
    private int id;
    private String name;
    private String description;
//    private List<Questions> questionsList;
//    private List<Answers> answersList;
    private boolean isForTime;

}

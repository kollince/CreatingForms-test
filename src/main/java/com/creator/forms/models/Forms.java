package com.creator.forms.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Forms {
    private int id;
    private String name;
    private String description;
    private String image;
//    private List<Questions> questionsList;
//    private List<Answers> answersList;
    private boolean isForTime;

}

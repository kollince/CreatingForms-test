package com.creator.forms.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Questions {
    private int id;
    private int idForm;
    private String question;
    private String image;
}

package ru.stepup.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Department {
    private int id;
    private String name;

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

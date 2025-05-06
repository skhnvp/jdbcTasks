package ru.stepup.dto;

import lombok.Data;

@Data
public class Employee {
    private int id;
    private String name;
    private int departmentId;

    public Employee(int id, String name, int departmentId) {
        this.id = id;
        this.name = name;
        this.departmentId = departmentId;
    }
}

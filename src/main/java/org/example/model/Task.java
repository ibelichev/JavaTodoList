package org.example.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Task {
    private String name;
    private String description;
    private StatusEnum status;
    private LocalDate deadLine;
}
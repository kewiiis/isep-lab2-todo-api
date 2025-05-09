package org.isep.cleancode;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Todo {
    private String name;
    private LocalDate dueDate;

    public Todo(String name, String dueDate) {
        validateName(name);
        this.name = name;
        this.dueDate = parseDueDate(dueDate);
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom est requis");
        }
        if (name.length() >= 64) {
            throw new IllegalArgumentException("Le nom doit faire moins de 64 caractères");
        }
    }

    private LocalDate parseDueDate(String dueDate) {
        if (dueDate == null || dueDate.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dueDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("La date doit être au format YYYY-MM-DD ou vide");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = parseDueDate(dueDate);
    }
}
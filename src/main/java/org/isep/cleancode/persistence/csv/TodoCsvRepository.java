package org.isep.cleancode.persistence.csv;

import org.isep.cleancode.Todo;
import org.isep.cleancode.application.ports.ITodoRepository;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TodoCsvRepository implements ITodoRepository {
    private final Path csvPath;
    private static final String CSV_HEADER = "name,dueDate\n";

    public TodoCsvRepository() {
        String appData = System.getenv("APPDATA");
        this.csvPath = Paths.get(appData, "todo-app", "todos.csv");
        initFile();
    }

    private void initFile() {
        try {
            Files.createDirectories(csvPath.getParent());
            if (!Files.exists(csvPath)) {
                Files.write(csvPath, CSV_HEADER.getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize CSV file", e);
        }
    }

    @Override
    public void addTodo(Todo todo) {
        try {
            String line = String.format("%s,%s\n", 
                escapeCsv(todo.getName()),
                todo.getDueDate() != null ? todo.getDueDate() : "");
            Files.write(csvPath, line.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to CSV", e);
        }
    }

    @Override
    public List<Todo> getAllTodos() {
        try {
            List<Todo> todos = new ArrayList<>();
            List<String> lines = Files.readAllLines(csvPath);
            
            // Skip header
            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",", -1);
                String name = unescapeCsv(parts[0]);
                String dueDate = parts.length > 1 ? parts[1] : "";
                todos.add(new Todo(name, dueDate));
            }
            return todos;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV", e);
        }
    }

    @Override
    public boolean existsByName(String name) {
        return getAllTodos().stream()
            .anyMatch(t -> t.getName().equals(name));
    }

    private String escapeCsv(String value) {
        return value.replace(",", "\\,");
    }

    private String unescapeCsv(String value) {
        return value.replace("\\,", ",");
    }
}
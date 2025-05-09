package org.isep.cleancode.application;

import java.util.List;
import org.isep.cleancode.Todo;
import org.isep.cleancode.application.ports.ITodoRepository;

public class TodoManager {
    private final ITodoRepository repository;

    public TodoManager(ITodoRepository repository) {
        this.repository = repository;
    }

    public void createTodo(Todo todo) {
        validateTodo(todo);
        repository.addTodo(todo);
    }

    public List<Todo> getAllTodos() {
        return repository.getAllTodos();
    }

    private void validateTodo(Todo todo) {
        if (repository.existsByName(todo.getName())) {
            throw new IllegalArgumentException("Un Todo avec ce nom existe déjà");
        }
    }
}
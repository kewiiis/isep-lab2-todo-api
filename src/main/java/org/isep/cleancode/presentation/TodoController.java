package org.isep.cleancode.presentation;

import com.google.gson.Gson;
import org.isep.cleancode.Todo;
import org.isep.cleancode.application.TodoManager;
import org.isep.cleancode.application.ports.ITodoRepository;
import org.isep.cleancode.persistence.csv.TodoCsvRepository;
import org.isep.cleancode.persistence.inmemory.TodoInMemoryRepository;
import spark.Request;
import spark.Response;

public class TodoController {
    private static final Gson gson = new Gson();
    private final TodoManager todoManager;

    public TodoController() {
        // Changer cette ligne pour switcher entre les adapters:
        ITodoRepository repository = new TodoCsvRepository(); // Au lieu de new TodoInMemoryRepository()
        this.todoManager = new TodoManager(repository);
    }

    public Object getAllTodos(Request req, Response res) {
        res.type("application/json");
        return gson.toJson(todoManager.getAllTodos());
    }

    public Object createTodo(Request req, Response res) {
        try {
            Todo newTodo = gson.fromJson(req.body(), Todo.class);
            todoManager.createTodo(newTodo);
            
            res.status(201);
            res.type("application/json");
            return gson.toJson(newTodo);
        } catch (IllegalArgumentException e) {
            res.status(400);
            return gson.toJson(new ErrorResponse(e.getMessage()));
        }
    }

    private static class ErrorResponse {
        private final String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }
}
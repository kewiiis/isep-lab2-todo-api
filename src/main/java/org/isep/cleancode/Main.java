package org.isep.cleancode;

import static spark.Spark.*;
import org.isep.cleancode.presentation.TodoController;

public class Main {
    public static void main(String[] args) {
        // Configuration
        port(9090); // Port modifié pour éviter les conflits
        ipAddress("0.0.0.0");
        
        // Contrôleur
        TodoController controller = new TodoController();
        
        // Routes
        get("/todos", controller::getAllTodos);
        post("/todos", controller::createTodo);
        
        // Route test
        get("/ping", (req, res) -> "API OK");
        
        // Initialisation
        awaitInitialization();
        System.out.println("Server ready with all endpoints");
    }
}
package org.isep.cleancode;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        // Configuration réseau
        port(8080);
        ipAddress("0.0.0.0");  // Écoute sur toutes les interfaces
        
        // Configuration CORS
        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET,POST");
        });

        // Route test
        get("/ping", (req, res) -> "pong");

        // Initialisation
        awaitInitialization();
        System.out.println("Server running on http://localhost:8080");
    }
}


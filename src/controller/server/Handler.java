package controller.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.DataBase;
import model.User;
import controller.server.requests.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class Handler {

    static class Signup implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {

            // This is where you handle the /signup endpoint.
            // Read the request body
            InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String requestBody = br.readLine();

            // Parse the request body into a SignupRequest object
            Gson gson = new Gson();
            Request.SignupRequest signupRequest = gson.fromJson(requestBody, Request.SignupRequest.class);
            User user = new User(signupRequest.getEmail(), signupRequest.getName(), signupRequest.getLastName(), signupRequest.getPassword());
            boolean success = user.signUp();

            String response;
            if (success){
                response = "Signed up successfully";
                t.sendResponseHeaders(200, response.length());
            } else {
                response = "Failed to sign up";
                t.sendResponseHeaders(500, response.length());
            }

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class Login implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // This is where you handle the /login endpoint.
            // Read the request body
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String requestBody = br.readLine();

            // Parse the request body into a LoginRequest object
            Gson gson = new Gson();
            Request.LoginRequest loginRequest = gson.fromJson(requestBody, Request.LoginRequest.class);
            DataBase db = new DataBase();
            User user = db.searchUserInDataBase(loginRequest.getEmail(), loginRequest.getPassword());
            boolean success = user.logIn();

            String response;
            if (success){
                response = "Logged in successfully";
                exchange.sendResponseHeaders(200, response.length());
            } else {
                response = "Failed to log in";
                exchange.sendResponseHeaders(500, response.length());
            }
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }


    }

    public static class Search implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // This is where you handle the /search endpoint.
            // Read the request body
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String requestBody = br.readLine();

            // Parse the request body into a SearchRequest object
            Gson gson = new Gson();
            Request.SearchRequest searchRequest = gson.fromJson(requestBody, Request.SearchRequest.class);
            DataBase db = new DataBase();
            User user = db.searchUser(searchRequest.getQuery());
            boolean success = user != null;

            String response;
            if (success){
                response = "User found";
                exchange.sendResponseHeaders(200, response.length());
            } else {
                response = "User not found";
                exchange.sendResponseHeaders(500, response.length());
            }
            System.out.println("Request received.");
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}

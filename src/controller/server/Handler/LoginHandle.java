package controller.server.Handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.server.requests.Request;
import model.DataBase;
import model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class LoginHandle implements HttpHandler {
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

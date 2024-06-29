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

public  class ShowProfileHandle implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Read the request body
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String requestBody = br.readLine();

        // Parse the request body into a ShowProfileRequest object
        Gson gson = new Gson();
        Request.ShowProfileRequest showProfileRequest = gson.fromJson(requestBody, Request.ShowProfileRequest.class);

        // Search for the user in the database
        DataBase db = new DataBase();
        User user = db.searchUser(showProfileRequest.getUser().getEmail());

        String response;
        if (user != null){
            // If the user is found, return the user's profile as a JSON string
            response = gson.toJson(user);
            exchange.sendResponseHeaders(200, response.length());
        } else {
            // If the user is not found, return an error message
            response = "User not found";
            exchange.sendResponseHeaders(500, response.length());
        }

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

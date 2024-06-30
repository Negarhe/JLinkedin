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

public class SignupHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {

        // This is where you handle the /signup endpoint.
        // Read the request body
        InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        StringBuilder requestBodyBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            requestBodyBuilder.append(line);
        }
        String requestBody = requestBodyBuilder.toString();

        // Parse the request body into a SignupRequest object
        Gson gson = new Gson();
        Request.SignupRequest signupRequest = gson.fromJson(requestBody, Request.SignupRequest.class);
        User user = new User(signupRequest.getEmail(), signupRequest.getName(), signupRequest.getLastName(), signupRequest.getPassword());

        DataBase dataBase = new DataBase();
        //check if user exists
        String response;
        if (dataBase.userExists(signupRequest.getEmail())) {
            response = "Email already exists";
            t.sendResponseHeaders(409, response.length()); // 409 Conflict
        } else {
            boolean success = user.signUp();

            if (success){
                response = "Signed up successfully";
                t.sendResponseHeaders(200, response.length());
            } else {
                response = "Failed to sign up";
                t.sendResponseHeaders(500, response.length());
            }
        }

        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

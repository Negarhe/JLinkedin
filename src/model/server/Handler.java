package model.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.User;
import model.server.requests.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class Handler {

    static class Signup implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            System.out.println("Request received.");
            // This is where you handle the /signup endpoint.
            // Read the request body
            InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String requestBody = br.readLine();
            System.out.println("Request body:" + requestBody);

            // Parse the request body into a SignupRequest object
            Gson gson = new Gson();
            Request.SignupRequest signupRequest = gson.fromJson(requestBody, Request.SignupRequest.class);
            System.out.println("s: " + signupRequest);
            User user = new User(signupRequest.getEmail(), signupRequest.getName(), signupRequest.getLastName(), signupRequest.getPassword());
            System.out.println("Request received.");
            boolean success = user.signUp();

            String response;
            if (success){
                response = "Signed up successfully";
                t.sendResponseHeaders(200, response.length());
            } else {
                response = "Failed to sign up";
                t.sendResponseHeaders(500, response.length());
            }
            System.out.println("Request received.");
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}

package controller.server.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.server.requests.Request;
import model.DataBase;
import model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ShowContactInformationHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            // Read the request body
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder requestBodyBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                requestBodyBuilder.append(line);
            }
            String requestBody = requestBodyBuilder.toString();

            // Parse the request body into a ShowContactInformationRequest object
            Gson gson = new Gson();
            Request.ShowContactInformationRequest showContactInformationRequest;
            try {
                showContactInformationRequest = gson.fromJson(requestBody, Request.ShowContactInformationRequest.class);
            } catch (JsonSyntaxException e) {
                System.out.println("Invalid JSON string: " + e.getMessage());
                return;
            }

            // Search for the user in the database
            DataBase db = new DataBase();
            User user = db.searchUserWithEmail(showContactInformationRequest.getEmail());

            String response = "";
            if (user != null){
                // Get the user's contact information
                 Request.ContactInformation contactInformation = db.getUserContactInformation(user);
                response = gson.toJson(contactInformation);
            } else {
                response = "invalid email or password";
            }

            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

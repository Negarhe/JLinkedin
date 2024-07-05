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
import java.nio.charset.StandardCharsets;

public class UpdateContactInformationHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder requestBodyBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                requestBodyBuilder.append(line);
            }
            String requestBody = requestBodyBuilder.toString();

            //parse the request body to ContactInformation object
            Gson gson = new Gson();
            DataBase db = new DataBase();
            Request.ContactInformation contactInformation = gson.fromJson(requestBody, Request.ContactInformation.class);
            User user = db.searchUserInDataBase(contactInformation.getEmail(), contactInformation.getPassword());

            String response;
            if (user == null) {
                response = "invalid email or password";
                exchange.sendResponseHeaders(404, response.length());

                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

                return;
            }

            // Update the user's contact information
            db.updateUserContactInformation(user, contactInformation.getNewContactInformation());

            response = "Contact information updated successfully";
            exchange.sendResponseHeaders(200, response.length());

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
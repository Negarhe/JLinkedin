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

public class UpdateProfileHandler implements HttpHandler {
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
            System.out.println(requestBody);

            // Parse the request body to UpdateProfile object
            Gson gson = new Gson();
            DataBase db = new DataBase();
            Request.EditProfileRequest updateProfile = gson.fromJson(requestBody, Request.EditProfileRequest.class);
            User user = db.searchUserWithEmail(updateProfile.getEmail());

            String response;
            if (user == null) {
                response = "invalid email or password";
                exchange.sendResponseHeaders(404, response.length());

                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

                return;
            }

            // Update all fields
            user.setName(updateProfile.getName());
            user.setLastName(updateProfile.getLastName());
            user.setTitle(updateProfile.getTitle());
            user.setAdditionalName(updateProfile.getAdditionalName());
            user.setCity(updateProfile.getCity());
            user.setCountry(updateProfile.getCountry());

            // Update user in database
            db.updateUser(user);

            response = "Profile updated successfully";
            exchange.sendResponseHeaders(200, response.length());

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

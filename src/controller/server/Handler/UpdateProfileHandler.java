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

            //parse the request body to UpdateProfile object
            Gson gson = new Gson();
            DataBase db = new DataBase();
            Request.UpdateProfile updateProfile = gson.fromJson(requestBody, Request.UpdateProfile.class);
            User user = db.searchUserInDataBase(updateProfile.getEmail(), updateProfile.getPassword());

            String response;
            if (user == null) {
                response = "invalid email or password";
                exchange.sendResponseHeaders(404, response.length());

                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

                return;
            }


            String partToEdit = updateProfile.getPartToEdit();
            switch (partToEdit) {
                case "name":
                    user.setName(updateProfile.getNewValue());
                    break;

                case "lastName":
                    user.setLastName(updateProfile.getNewValue());
                    break;

                case "title":
                    user.setTitle(updateProfile.getNewValue());
                    break;

                case "additionalName":
                    user.setAdditionalName(updateProfile.getNewValue());
                    break;

                case "city":
                    user.setCity(updateProfile.getNewValue());
                    break;

                case "country":
                    user.setCountry(updateProfile.getNewValue());
                    break;

                case "status":
                    User.Status status = User.Status.valueOf(updateProfile.getNewValue());
                    user.setStatus(status);
                    break;


                case "profession":
                    user.setProfession(updateProfile.getNewValue());
                    break;

                case "profilePhoto":
                    user.setProfilePhoto(updateProfile.getNewValue());
                    break;

                case "backgroundPhoto":
                    user.setBackgroundPhoto(updateProfile.getNewValue());
                    break;

            }

            response = "Profile updated successfully";
            exchange.sendResponseHeaders(200, response.length());

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}

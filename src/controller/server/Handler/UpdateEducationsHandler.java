package controller.server.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.server.requests.Request;
import model.DataBase;
import model.Education;
import model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class UpdateEducationsHandler implements HttpHandler {
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

            //parse the request body to UpdateEducations object
            Gson gson = new Gson();
            DataBase db = new DataBase();

            Request.UpdateEducations updateEducations = gson.fromJson(requestBody, Request.UpdateEducations.class);
            User user = db.searchUserInDataBase(updateEducations.getEmail(), updateEducations.getPass());

            String response;
            if (user == null) {
                response = "invalid email or password";
                exchange.sendResponseHeaders(404, response.length());

                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

                return;
            }

            Type educationTypeList = new TypeToken<ArrayList<Education>>() {
            }.getType();
            ArrayList<Education> newEducations;
            if (updateEducations.getEducation() != null) {
                // If getEducation() returns a single Education object
                ArrayList<Education> educationList = new ArrayList<>();
                educationList.add(updateEducations.getEducation());
                newEducations = gson.fromJson(gson.toJson(educationList), educationTypeList);
            } else {
                // If getEducation() returns a list of Education objects
                newEducations = gson.fromJson(gson.toJson(updateEducations.getEducation()), educationTypeList);
            }            db.updateUserEducations(user.getEmail(), newEducations);

            response = "Educations updated successfully";
            exchange.sendResponseHeaders(200, response.length());

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

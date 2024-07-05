package controller.server.Handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.server.requests.Request;
import model.DataBase;
import model.Job;
import model.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class UpdateExperiencesHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder requestBodyBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            requestBodyBuilder.append(line);
        }
        String requestBody = requestBodyBuilder.toString();


        //parse the request body to the updateExperience object
        Gson gson = new Gson();
        DataBase db = new DataBase();

        Request.UpdateExperience updateExperience =gson.fromJson(requestBody, Request.UpdateExperience.class);
        User user = db.searchUserInDataBase(updateExperience.getEmail(), updateExperience.getPass());

        String response;
        if (user == null) {
            response = "invalid email or password";
            exchange.sendResponseHeaders(404, response.length());

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

            return;
        }

        //updateExperiences in dataBase
        Job newExperience = updateExperience.getExperience();
        ArrayList<Job> userExperiences = user.getExperiences();
        userExperiences.add(newExperience);
        user.setExperiences(userExperiences);

        db.updateUserExperiences(user.getEmail(), user.getExperiences());

        response = "Experiences updated successfully";
        exchange.sendResponseHeaders(200, response.length());

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

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

public class FollowHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Read the request body
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder requestBodyBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            requestBodyBuilder.append(line);
        }
        String requestBody = requestBodyBuilder.toString();

        // Parse the request body into a FollowRequest object
        Gson gson = new Gson();
        Request.FollowRequest followRequest = gson.fromJson(requestBody, Request.FollowRequest.class);

        // Search for the users in the database
        try {

            DataBase db = new DataBase();
            User follower = db.searchUserWithEmail(followRequest.getSender());
            User followed = db.searchUserWithEmail(followRequest.getReceiver());

            String response;
            if (follower != null && followed != null){
                // If both users are found, add the followed user to the follower's following list
                // and add the follower to the followed user's followers list


                // Update the database with the new follower and following lists
                if (db.updateFollowers(followed.getEmail(), follower) || db.updateFollowings(follower.getEmail(), followed)) {
                    response = "Follow operation was successful";
                    exchange.sendResponseHeaders(200, response.length());
                } else {
                    response = "you are already following this user";
                    exchange.sendResponseHeaders(500, response.length());
                }




            } else {
                // If either user is not found, send an error message back to the client
                response = "Follow operation failed: one or both users not found";
                exchange.sendResponseHeaders(500, response.length());
            }

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
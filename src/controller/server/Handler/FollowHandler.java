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

public class FollowHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Read the request body
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String requestBody = br.readLine();

        // Parse the request body into a FollowRequest object
        Gson gson = new Gson();
        Request.FollowRequest followRequest = gson.fromJson(requestBody, Request.FollowRequest.class);

        // Search for the users in the database
        DataBase db = new DataBase();
        User follower = db.searchUser(followRequest.getSender().getEmail());
        User followed = db.searchUser(followRequest.getReceiver().getEmail());

        String response;
        if (follower != null && followed != null){
            // If both users are found, add the followed user to the follower's following list
            // and add the follower to the followed user's followers list
            follower.getFollowing().add(followed);
            followed.getFollowers().add(follower);

            // Update the database with the new follower and following lists
            db.updateFollowers(follower.getEmail(), follower.getFollowers());
            db.updateFollowings(follower.getEmail(), follower.getFollowing());

            // Send a response back to the client indicating the follow operation was successful
            response = "Follow operation was successful";
            exchange.sendResponseHeaders(200, response.length());
        } else {
            // If either user is not found, send an error message back to the client
            response = "Follow operation failed: one or both users not found";
            exchange.sendResponseHeaders(500, response.length());
        }

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
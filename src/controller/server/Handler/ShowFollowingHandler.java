package controller.server.Handler;
import controller.server.requests.Request;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import model.DataBase;
import model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

public class ShowFollowingHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Step 1: Read the request body
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        StringBuilder requestBodyBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            requestBodyBuilder.append(line);
        }
        String requestBody = requestBodyBuilder.toString();
        // Step 2: Parse the request body into a ShowFollowingRequest object
        Gson gson = new Gson();
        Request.ShowFollowingRequest showFollowingRequest = gson.fromJson(requestBody, Request.ShowFollowingRequest.class);

        // Step 3: Search the user in the database
        DataBase db = new DataBase();
        User user = db.searchUserWithEmail(showFollowingRequest.getEmail());

        String response;
        if (user != null) {
            // Step 4: Get the list of users they are following
            List<User> followingUsers = user.getFollowing();

            // Step 5: Convert the list of following users into a JSON string
            response = gson.toJson(followingUsers);

            // Step 6: Send the JSON string as the response
            exchange.sendResponseHeaders(200, response.length());
        } else {
            response = "User not found";
            exchange.sendResponseHeaders(500, response.length());
        }

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

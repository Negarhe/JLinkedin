package controller.server.Handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.server.requests.Request;
import model.DataBase;
import model.Post;
import model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

public class showFeedHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            //read the request body
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder requestBodyBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                requestBodyBuilder.append(line);
            }
            String requestBody = requestBodyBuilder.toString();

            //parse the request body to into a showFeedRequest object
            Gson gson = new Gson();
            Request.ShowFeedRequest showFeedRequest = gson.fromJson(requestBody, Request.ShowFeedRequest.class);

            //search for the user in the database
            DataBase dataBase = new DataBase();
            User user = dataBase.searchUserWithEmail(showFeedRequest.getEmail());
            String response;
            if (user != null) {
                // If the user is found, get the user's feed
                List<Post> feed = dataBase.getUserFeed(user);
                response = gson.toJson(feed);
                byte[] responseBytes = response.getBytes("UTF-8");
                //Send a response back to the client with the JSON string
                exchange.sendResponseHeaders(200, responseBytes.length);
            } else {
                //if the user is not found, return an error message
                response = "User not found";
                exchange.sendResponseHeaders(500, response.length());
            }
            OutputStream os = exchange.getResponseBody();
            byte[] responseBytes = response.getBytes("UTF-8");
            os.write(responseBytes);
            os.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}

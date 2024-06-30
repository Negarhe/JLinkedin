package controller.server.Handler;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.DataBase;
import controller.server.requests.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import model.User;

public class ShowFollowersHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // This is where you handle the /showFollowers endpoint.
        // Read the request body
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String requestBody = br.readLine();

        // Parse the request body into a ShowFollowersRequest object
         Gson gson = new Gson();
         Request.ShowFollowersRequest showFollowersRequest = gson.fromJson(requestBody, Request.ShowFollowersRequest.class);
         DataBase db = new DataBase();
         User user = db.searchUser(showFollowersRequest.getEmail());
         boolean success = user != null;

         String response;
         if (success){
             response = "User found";
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

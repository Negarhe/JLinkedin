package controller.server.Handler;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.User;
import controller.server.requests.Request;
import model.DataBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ShowFollowersHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // This is where you handle the /showFollowers endpoint.
        // Read the request body
        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder requestBodyBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                requestBodyBuilder.append(line);
            }
            String requestBody = requestBodyBuilder.toString();

            // Parse the request body into a ShowFollowersRequest object
            Gson gson = new Gson();
            Request.ShowFollowersRequest showFollowersRequest = gson.fromJson(requestBody, Request.ShowFollowersRequest.class);
            DataBase db = new DataBase();
            User user = db.searchUserWithEmail(showFollowersRequest.getEmail());

            String response;
            if (user == null) {
                response = "user not found";
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            }

            //get followers from dataBase and show them as a json
            DataBase dataBase = new DataBase();
            if (dataBase.getFollowersFromDataBase(user.getEmail()) == null) {
                response = "No followers found";
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            }

            response = dataBase.getFollowersFromDataBase(user.getEmail());
            System.out.println(response);
            exchange.sendResponseHeaders(200, response.length());


            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

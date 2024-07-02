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
import java.nio.charset.StandardCharsets;

public class postHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
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

            // Parse the request body into a createPost object
            Gson gson = new Gson();
            Request.createPost createPostRequest = gson.fromJson(requestBody, Request.createPost.class);

            // Extract the Post object and the User object from the createPost object
            DataBase dataBase = new DataBase();
            Post post = new Post(createPostRequest.getPost());
            User user = dataBase.searchUserWithEmail(createPostRequest.getEmail());

            DataBase db = new DataBase();
            db.insertPost(user.getEmail(), post);

            //send a response
            String response = "Post created";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}

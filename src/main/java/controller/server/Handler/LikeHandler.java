package controller.server.Handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.server.requests.Request;
import model.DataBase;
import model.User;

import java.io.*;

public class LikeHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            InputStreamReader isr =new InputStreamReader(exchange.getRequestBody());
            BufferedReader br = new BufferedReader(isr);
            StringBuilder requestBodyBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                requestBodyBuilder.append(line);
            }
            String requestBody = requestBodyBuilder.toString();

            //parse the request body to Like object
            Gson gson = new Gson();
            DataBase db = new DataBase();
            Request.Like like = gson.fromJson(requestBody, Request.Like.class);
            User user = db.searchUserInDataBase(like.getEmail(), like.getPassword());

            String response;
            if (user == null) {
                response = "invalid email or password";
                exchange.sendResponseHeaders(404, response.length());

                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

                return;
            }

            // Like the post
            db.likePost(like.getPostId());

            response = "Post liked successfully";
            exchange.sendResponseHeaders(200, response.length());

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

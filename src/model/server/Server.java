package model.server;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8085), 0);
//        server.createContext("/getposts", new Handler.GetPostsHandler());
//        server.createContext("/createposts", new Handler.CreatePostsHandler());
        server.createContext("/signup", new Handler.Signup());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

}
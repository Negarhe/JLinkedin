package controller.server.requests;

import model.Post;
import model.User;

public class Request {
    public static class SignupRequest {
        private final String email;
        private final String password;
        private final String name;
        private final String lastName;

        public SignupRequest(String email, String password, String name, String lastName) {
            this.email = email;
            this.password = password;
            this.name = name;
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getName() {
            return name;
        }

        public String getLastName() {
            return lastName;
        }
    }

    public static class LoginRequest {
        private final String email;
        private final String password;

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class SearchRequest {
        private final String query;

        public SearchRequest(String query) {
            this.query = query;
        }

        public String getQuery() {
            return query;
        }


    }

    public static class ShowProfileRequest {
        private User user;

        public ShowProfileRequest(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

    public static class FollowRequest {
        private User sender;
        private User receiver;

        public FollowRequest(User sender, User receiver) {
            this.sender = sender;
            this.receiver = receiver;
        }

        public User getSender() {
            return sender;
        }

        public void setSender(User sender) {
            this.sender = sender;
        }

        public User getReceiver() {
            return receiver;
        }

        public void setReceiver(User receiver) {
            this.receiver = receiver;
        }
    }

    public static class ShowFollowersRequest {
        private String email;

        public ShowFollowersRequest(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class ShowFollowingRequest {
        private String email;

        public ShowFollowingRequest(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class createPost{
        private User user;
        private Post post;

        public createPost(User user, Post post) {
            this.user = user;
            this.post = post;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Post getPost() {
            return post;
        }

        public void setPost(Post post) {
            this.post = post;
        }
    }
}

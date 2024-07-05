package controller.server.responses;

import model.Post;

public class Response {
    public static class SearchPostResponse {
        private final String email;
        private final String name;
        private final Post post;

        public SearchPostResponse(String email, String name, Post post) {
            this.email = email;
            this.name = name;
            this.post = post;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

        public Post getPost() {
            return post;
        }
    }
}
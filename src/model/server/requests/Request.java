package model.server.requests;

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
}

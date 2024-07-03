package controller.server.requests;

import model.Education;
import model.Job;
import model.Post;

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
        private String email;
        private final String password;

        public ShowProfileRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class FollowRequest {
        private final String sender;
        private final String receiver;

        public FollowRequest(String sender, String receiver) {
            this.sender = sender;
            this.receiver = receiver;
        }

        public String getSender() {
            return sender;
        }


        public String getReceiver() {
            return receiver;
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
        private String email;
        private final Post post;

        public createPost(String email, Post post) {
            this.email = email;
            this.post = post;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Post getPost() {
            return post;
        }

    }

    public static class ShowFeedRequest{
        private String email;

        public ShowFeedRequest(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class SendConnectionRequest {
        private final String senderEmail;
        private final String receiverEmail;
        private final String connectionNote;

        public SendConnectionRequest(String senderEmail, String receiverEmail, String connectionNote) {
            this.senderEmail = senderEmail;
            this.receiverEmail = receiverEmail;
            this.connectionNote = connectionNote;
        }

        public String getSenderEmail() {
            return senderEmail;
        }

        public String getReceiverEmail() {
            return receiverEmail;
        }

        public String getConnectionNote() {
            return connectionNote;
        }

    }


    public static class ShowConnectionRequests {
        String email;

        public ShowConnectionRequests(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class UpdateProfile {
        private final String partToEdit;
        private String email;
        private final String password;
        private final String newValue;

        public UpdateProfile(String email, String password, String partToEdit, String newValue) {
            this.email = email;
            this.password = password;
            this.partToEdit = partToEdit;
            this.newValue = newValue;
        }

        public String getNewValue() {
            return newValue;
        }

        public String getPartToEdit() {
            return partToEdit;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

    }

    public static class UpdateEducations {
        private String email;
        private final String pass;
        private Education education;

        public UpdateEducations(String email, String pass, Education education) {
            this.email = email;
            this.pass = pass;
            this.education = education;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPass() {
            return pass;
        }

        public Education getEducation() {
            return education;
        }

        public void setEducation(Education education) {
            this.education = education;
        }
    }

    public static class UpdateExperience {
        private final String email;
        private final String pass;
        private final Job experience;

        public UpdateExperience(String email, String pass, Job experience) {
            this.email = email;
            this.pass = pass;
            this.experience = experience;
        }

        public String getEmail() {
            return email;
        }

        public String getPass() {
            return pass;
        }


        public Job getExperience() {
            return experience;
        }
    }
}

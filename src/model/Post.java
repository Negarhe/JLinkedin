package model;

import java.util.HashMap;
import java.util.*;

public class Post {
    private String text;//3000 char
    private String imageUrl;
    private String videoUrl;
    private ArrayList<User> likes;
    private HashMap<User, String> comments ; //each comment mapped to a user
    private final String timeStamp ;

    public Post(String text) {
        this.text = text;
        this.likes = new ArrayList<>();
        this.comments = new HashMap<>();
        this.imageUrl = null;
        this.videoUrl = null;
        Date d = new Date(); //time of creating the post
        this.timeStamp = d.toString() ;//d is a date but timeStamp is a String
    }



    public void displayPost() {
        System.out.println("-----------------------------");
        System.out.println("model.Post : " + text );
        System.out.println("Date : " + timeStamp );
        System.out.println("Number of likes: " + likes.size());
        System.out.println("Comments on this post: ");
        displayComments();
        System.out.println("-----------------------------");
    }

    public void displayComments() {
        //displaying all comments on a single post
        for (Map.Entry<User, String> set : comments.entrySet()) {
            System.out.println(set.getKey().getName() + "says: " + set.getValue());
        }
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void addComment(User user, String comment){
        comments.put(user, comment);
    }

    public void likePost(User user){
        likes.add(user);
    }

    public ArrayList<User> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<User> likes) {
        this.likes = likes;
    }

    public HashMap<User, String> getComments() {
        return comments;
    }

    public void setComments(HashMap<User, String> comments) {
        this.comments = comments;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

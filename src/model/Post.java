package model;

import java.util.HashMap;
import java.util.*;

public class Post {
    private String id;
    private String text;//3000 char
    private String imageUrl;
    private String videoUrl;
    private String caption;
    private HashSet<User> likes;
    private HashMap<User, ArrayList<String>> comments ; //each comment mapped to a user
    private final Date timeStamp ;

    public Post(String text, String imageUrl, String videoUrl, String caption) {
        this.text = text;
        this.likes = new HashSet<>();
        this.comments = new HashMap<>();
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.caption = caption;
        this.timeStamp = new Date() ;//d is a date but timeStamp is a String
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Post(Post post) {
        this.text = post.getText();
        this.likes = post.getLikes();
        this.comments = post.getComments();
        this.imageUrl = post.getImageUrl();
        this.videoUrl = post.getVideoUrl();
        this.caption = post.getCaption();
        this.timeStamp = new Date();//d is a date but timeStamp is a String
        this.id = UUID.randomUUID().toString();

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
        for (Map.Entry<User, ArrayList<String>> entry : comments.entrySet()) {
            User user = entry.getKey();
            ArrayList<String> comment = entry.getValue();
            System.out.println(user.getName() + " : " + comment);
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

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void addComment(User user, String comment){
        if(comments.containsKey(user)){
            comments.get(user).add(comment);
        }else{
            ArrayList<String> newComment = new ArrayList<>();
            newComment.add(comment);
            comments.put(user, newComment);
        }
    }

    public void likePost(User user){
        likes.add(user);
    }

    public HashSet<User> getLikes() {
        return likes;
    }

    public void setLikes(HashSet<User> likes) {
        this.likes = likes;
    }

    public HashMap<User, ArrayList<String>> getComments() {
        return comments;
    }

    public void setComments(HashMap<User, ArrayList<String>> comments) {
        this.comments = comments;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}

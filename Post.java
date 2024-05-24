import java.util.HashMap;

public class Post {
    private String text;//3000 char
    private HashMap<String, Integer> likes;
    private HashMap<String, String> comments;

    public Post(String text) {
        this.text = text;
        this.likes = new HashMap<>();
        this.comments = new HashMap<>();
    }

    public HashMap<String, Integer> getLikes() {
        return likes;
    }

    public void setLikes(HashMap<String, Integer> likes) {
        this.likes = likes;
    }

    public HashMap<String, String> getComments() {
        return comments;
    }

    public void setComments(HashMap<String, String> comments) {
        this.comments = comments;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

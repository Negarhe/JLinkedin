import java.util.ArrayList;

public class Clients {
    private ArrayList<User> users;

    public Clients() {
        this.users = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        for (User user1 : users) {
            if (user1.getEmail().equals(user.getEmail())) {
                System.out.println("This email is already taken");
                return;
            }
        }

        users.add(user);
    }
}

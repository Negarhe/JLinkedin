package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DataBase {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/ap_project";
    private static final String USER = "root";
    private static final String PASS = "5396Mhed";

    public DataBase() {
        try {
            //load MuSQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //establish connection
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertUser(User user) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            if (!userExists(user.getEmail())){
                String query = "INSERT INTO users (email, name, lastName, password) VALUES (?, ?, ?, ?)";

                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, user.getEmail());
                statement.setString(2, user.getName());
                statement.setString(3, user.getLastName());
                statement.setString(4, user.getPassword());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean userExists(String email) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM users WHERE email = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, email);
            System.out.println("this email is already taken, please LogIn!");
            return statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean checkPass(String email, String password) {

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public User getUser(String email) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM users WHERE email = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getString("email"),
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateUserTitle(String email, String title) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "UPDATE users SET title = ? WHERE email = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, email);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserAdditionalName(String email, String additionalName) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "UPDATE users SET additionalName = ? WHERE email = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, additionalName);
            statement.setString(2, email);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserCity (String email, String city) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "UPDATE users SET city = ? WHERE email = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, city);
            statement.setString(2, email);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserCountry(String email, String country) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "UPDATE users SET country = ? WHERE email = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, country);
            statement.setString(2, email);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserContactInfo(String email, ContactInformation contactInfo) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "INSERT INTO contactInfo (email, phoneNumber, kind, address, birthday, relationshipStatus) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, contactInfo.getEmail());
            statement.setString(2, contactInfo.getPhoneNumber());
            statement.setString(3, contactInfo.getKind().toString());
            statement.setString(4, contactInfo.getAddress());
            statement.setString(5, contactInfo.getBirthday());
            statement.setString(6, contactInfo.getRelationshipStatus());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();


        }
    }

    public void updateUserProfession(String email, String profession) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "UPDATE users SET profession = ? WHERE email = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, profession);
            statement.setString(2, email);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateUserStatus(String email, String status) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "UPDATE users SET status = ? WHERE email = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, status);
            statement.setString(2, email);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User searchUser(String fullName) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM users WHERE name = ? AND lastName = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, fullName.split(" ")[0]);
            statement.setString(2, fullName.split(" ")[1]);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getString("email"),
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void insertPost(String email, Post post) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "INSERT INTO posts (email, text) VALUES (?, ?)";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, post.getText());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    public List<User> getFollowings(String email) {
        List<User> followings = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM followings WHERE user_email = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User following = getUser(resultSet.getString("following_email"));
                followings.add(following);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return followings;
    }

    public List<Post> getPosts(String email) {
        List<Post> posts = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM posts WHERE email = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Post post = new Post(resultSet.getString("text"));
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    public List<User> getFollowers(String email) {
        List<User> followers = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM followers WHERE user_email = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User follower = getUser(resultSet.getString("follower_email"));
                followers.add(follower);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return followers;
    }


    public User searchUserInDataBase(String email, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getString("email"),
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateFollowers(String email, ArrayList<User> followers) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "INSERT INTO followers (user_email, follower_email) VALUES (?, ?)";

            for (User follower : followers) {
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, email);
                statement.setString(2, follower.getEmail());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFollowings(String email, ArrayList<User> following) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "INSERT INTO followings (user_email, following_email) VALUES (?, ?)";

            for (User follow : following) {
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, email);
                statement.setString(2, follow.getEmail());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveMessage(Message message) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "INSERT INTO messages (sender, receiver, text) VALUES (?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, message.getSender().toString());
            statement.setString(2, message.getRecipient().toString());
            statement.setString(3, message.getContent());



            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Post> getUserFeed(User user) {
        List<Post> feed = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // Get the list of users that the current user is following
            List<User> followings = getFollowings(user.getEmail());

            // For each user in the followings list, get their posts and add them to the feed
            for (User following : followings) {
                List<Post> posts = getPosts(following.getEmail());
                feed.addAll(posts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feed;
    }
}

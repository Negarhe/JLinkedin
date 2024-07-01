package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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

    public boolean updateFollowers(String email, User follower) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            // Get the current followers of the user
            String selectQuery = "SELECT followers FROM users WHERE email = ?";
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            selectStatement.setString(1, email);
            ResultSet resultSet = selectStatement.executeQuery();



            // Parse the followers from the JSON string
            ArrayList<User> followers = new ArrayList<>();
            if (resultSet.next()) {
                String followersJson = resultSet.getString("followers");
                if (followersJson != null && !followersJson.isEmpty()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<User>>(){}.getType();
                    followers = gson.fromJson(followersJson, type);
                }
            }

            for (User user : followers) {
                if (user.getEmail().equals(follower.getEmail())){
                    System.out.println("You are already following this user!");
                    return false;
                }
            }

            // Add the new follower user to the list
            followers.add(follower);

            // Convert the followers list back to a JSON string
            Gson gson = new Gson();
            String followersJson = gson.toJson(followers);

            // Update the followers in the database
            String updateQuery = "UPDATE users SET followers = ? WHERE email = ?";
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, followersJson);
            updateStatement.setString(2, email);
            updateStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateFollowings(String email, User following) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // Get the current followings of the user
            String selectQuery = "SELECT following FROM users WHERE email = ?";
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            selectStatement.setString(1, email);
            ResultSet resultSet = selectStatement.executeQuery();

            // Parse the followings from the JSON string
            ArrayList<User> followings = new ArrayList<>();
            if (resultSet.next()) {
                String followingJson = resultSet.getString("following");
                if (followingJson != null && !followingJson.isEmpty()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<User>>(){}.getType();
                    followings = gson.fromJson(followingJson, type);
                }
            }

            for (User user : followings) {
                if (user.getEmail().equals(following.getEmail())) {
                    System.out.println("You are already following this user!");
                    return false;
                }
            }

            // Add the new following user to the list
            followings.add(following);

            // Convert the followings list back to a JSON string
            Gson gson = new Gson();
            String followingJson = gson.toJson(followings);

            // Update the followings in the database
            String updateQuery = "UPDATE users SET following = ? WHERE email = ?";
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, followingJson);
            updateStatement.setString(2, email);
            updateStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
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

    public User searchUserWithEmail(String sender) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM users WHERE email = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, sender);

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

    public String getFollowersFromDataBase(String email) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT followers FROM users WHERE email = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("followers");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}

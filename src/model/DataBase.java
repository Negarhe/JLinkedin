package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.server.requests.Request;
import controller.server.responses.Response;

import java.lang.reflect.Type;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
            if (!userExists(user.getEmail())) {
                String query = "INSERT INTO users (email, name, lastName, password) VALUES (?, ?, ?, ?)";

                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, user.getEmail());
                statement.setString(2, user.getName());
                statement.setString(3, user.getLastName());
                statement.setString(4, user.getPassword());

                //add user to contactinformation table
                String query2 = "INSERT INTO contactinformation (email) VALUES (?)";

                PreparedStatement statement2 = conn.prepareStatement(query2);
                statement2.setString(1, user.getEmail());

                statement.executeUpdate();
                statement2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateContactInformationInUserTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "UPDATE users u SET u.contactInformationId = (SELECT c.email FROM contactinformation c WHERE c.email = u.email)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.executeUpdate();
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
                ArrayList<Post> posts = new ArrayList<>();
                String postsJson = resultSet.getString("posts");
                if (postsJson != null && !postsJson.isEmpty()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Post>>() {
                    }.getType();
                    posts = gson.fromJson(postsJson, type);
                }

                User user = new User(resultSet.getString("email"),
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getString("password"));
                user.setPosts(posts);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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
            // Get the current posts of the user
            String selectQuery = "SELECT posts FROM users WHERE email = ?";
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            selectStatement.setString(1, email);
            ResultSet resultSet = selectStatement.executeQuery();

            // Parse the posts from the JSON string
            ArrayList<Post> posts = new ArrayList<>();
            if (resultSet.next()) {
                String postsJson = resultSet.getString("posts");
                if (postsJson != null && !postsJson.isEmpty()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Post>>() {
                    }.getType();
                    posts = gson.fromJson(postsJson, type);
                }
            }

            // Add the new post to the list

            if (posts == null) {
                posts = new ArrayList<>();
            }
            posts.add(post);

            // Convert the posts list back to a JSON string
            Gson gson = new Gson();
            String postsJson = gson.toJson(posts);

            // Update the posts in the database
            String updateQuery = "UPDATE users SET posts = ? WHERE email = ?";
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, postsJson);
            updateStatement.setString(2, email);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<User> getFollowings(String email) {
        List<User> followings = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String selectQuery = "SELECT following FROM users WHERE email = ?";
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            selectStatement.setString(1, email);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                String followingJson = resultSet.getString("following");
                if (followingJson != null && !followingJson.isEmpty()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<User>>() {
                    }.getType();
                    followings = gson.fromJson(followingJson, type);
                }
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
                Post post = new Post(resultSet.getString("text"), resultSet.getString("imageUrl"),
                        resultSet.getString("videoUrl"), resultSet.getString("caption"));
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
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
                        resultSet.getString("password"),
                        resultSet.getString("title"),
                        resultSet.getString("additionalName"),
                        resultSet.getString("city"),
                        resultSet.getString("country"));
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
                    Type type = new TypeToken<ArrayList<User>>() {
                    }.getType();
                    followers = gson.fromJson(followersJson, type);
                }
            }

            for (User user : followers) {
                if (user.getEmail().equals(follower.getEmail())) {
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
                    Type type = new TypeToken<ArrayList<User>>() {
                    }.getType();
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
            System.out.println(followings.get(0).getEmail());
            // For each user in the followings list, get their posts and add them to the feed
            for (User following : followings) {
                User user1 = getUser(following.getEmail());
                List<Post> posts = user1.getPosts();
                feed.addAll(posts);
            }

            System.out.println(feed);

//            Collections.sort(feed, (p1, p2) -> p2.getTimestamp().compareTo(p1.getTimestamp());
            // convert timestamp string to timestamp object
            // sort the posts by timestamp
            feed.sort((p1, p2) -> p2.getTimeStamp().compareTo(p1.getTimeStamp()));
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


    public void insertConnectionRequest(Connect connect) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // Get the current connections of the user
            String selectQuery = "SELECT connection FROM users WHERE email = ?";
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            selectStatement.setString(1, connect.getSender());
            ResultSet resultSet = selectStatement.executeQuery();

            // Parse the connections from the JSON string
            ArrayList<Connect> connections = new ArrayList<>();
            if (resultSet.next()) {
                String connectionsJson = resultSet.getString("connection");
                if (connectionsJson != null && !connectionsJson.isEmpty()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Connect>>() {
                    }.getType();
                    connections = gson.fromJson(connectionsJson, type);
                }
            }

            for (Connect connection : connections) {
                if (connection.getSender().equals(connect.getSender())) {
                    return;
                }
            }

            // Add the new connection to the list
            connections.add(connect);

            // Convert the connections list back to a JSON string
            Gson gson = new Gson();
            String connectionsJson = gson.toJson(connections);

            // Update the connections in the database
            String updateQuery = "UPDATE users SET connection = ? WHERE email = ?";
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, connectionsJson);
            updateStatement.setString(2, connect.getSender());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<User> getConnectionRequests(String email) {
        ArrayList<User> connectionRequests = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String selectQuery = "SELECT connection FROM users WHERE email = ?";
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            selectStatement.setString(1, email);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                String connectionJson = resultSet.getString("connection");
                if (connectionJson != null && !connectionJson.isEmpty()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Connect>>() {
                    }.getType();
                    ArrayList<Connect> connections = gson.fromJson(connectionJson, type);

                    for (Connect connect : connections) {
                        User user = searchUserWithEmail(connect.getReceiver());
                        if (connect.getStatus().equals(Connect.Status.PENDING)) {
                            connectionRequests.add(user);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connectionRequests;
    }

    public void updateUserExperiences(String email, ArrayList<Job> newExperiences) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            //set the ArrayList to users' experiences
//            String alterTableQuery = "ALTER TABLE users ADD experiences json";
//            PreparedStatement selectStatement = conn.prepareStatement(alterTableQuery);
//            selectStatement.execute();
            String selectQuery = "SELECT experiences FROM users WHERE email = ?";
            PreparedStatement select = conn.prepareStatement(selectQuery);
            select.setString(1, email);

            ResultSet resultSet = select.executeQuery();

            // Convert the newExperiences list to a JSON string
            Gson gson = new Gson();
            String experiencesJson = gson.toJson(newExperiences);


            // Update the experiences in the database
            String updateQuery = "UPDATE users SET experiences = ? WHERE email = ?";
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, experiencesJson);
            updateStatement.setString(2, email);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserEducations(String email, ArrayList<Education> newEducation) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // Get the current educations of the user
            String alterTableQuery = "ALTER TABLE users ADD educations json";
            PreparedStatement selectStatement = conn.prepareStatement(alterTableQuery);
            selectStatement.execute();
            String selectQuery = "SELECT educations FROM users WHERE email = ?";
            PreparedStatement select = conn.prepareStatement(selectQuery);
            select.setString(1, email);

            ResultSet resultSet = select.executeQuery();

            // Parse the educations from the JSON string
            ArrayList<Education> educations = new ArrayList<>();
            if (resultSet.next()) {
                String educationsJson = resultSet.getString("educations");
                if (educationsJson != null && !educationsJson.isEmpty()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Education>>() {
                    }.getType();
                    educations = gson.fromJson(educationsJson, type);
                }
            }

            // Add the new education to the list
            educations.addAll(newEducation);

            // Convert the educations list back to a JSON string
            Gson gson = new Gson();
            String educationsJson = gson.toJson(educations);

            // Update the educations in the database
            String updateQuery = "UPDATE users SET educations = ? WHERE email = ?";
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, educationsJson);
            updateStatement.setString(2, email);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void updateStatus(String email, User.Status status) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "UPDATE users SET status = ? WHERE email = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, status.toString());
            statement.setString(2, email);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

//    public User.Status getStatus(String email) {
//        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
//            String query = "SELECT status FROM users WHERE email = ?";
//
//            PreparedStatement statement = conn.prepareStatement(query);
//            statement.setString(1, email);
//
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                return User.Status.valueOf(resultSet.getString("status"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    public void updateComments(String postId, String comment) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "INSERT INTO comment (postId, comment) VALUES (?, ?)";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, postId);
            statement.setString(2, comment);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public void sendMessageToUser(String sender, String recipientId, String message) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "INSERT INTO message (sender, recipient, content, timestamp) VALUES (?, ?, ?,?)";

            LocalTime time = LocalTime.now();

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, sender);
            statement.setString(2, recipientId);
            statement.setString(3, message);
            statement.setString(4, time.toString());


            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserContactInformation(User user, ContactInformation newContactInformation) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // Get the current contact information of the user
            String selectQuery = "SELECT * FROM contactinformation WHERE email = ?";
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            selectStatement.setString(1, user.getEmail());
            ResultSet resultSet = selectStatement.executeQuery();


            // Update the contact information in the database
            String updateQuery = "UPDATE contactinformation SET phoneNumber = ? WHERE email = ?";
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, newContactInformation.getPhoneNumber());
            updateStatement.setString(2, user.getEmail());
            updateStatement.executeUpdate();

            updateQuery = "UPDATE contactinformation SET kind = ? WHERE email = ?";
            updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, newContactInformation.getKind());
            updateStatement.setString(2, user.getEmail());
            updateStatement.executeUpdate();

            updateQuery = "UPDATE contactinformation SET address = ? WHERE email = ?";
            updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, newContactInformation.getAddress());
            updateStatement.setString(2, user.getEmail());
            updateStatement.executeUpdate();

            updateQuery = "UPDATE contactinformation SET birthday = ? WHERE email = ?";
            updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, newContactInformation.getBirthday());
            updateStatement.setString(2, user.getEmail());
            updateStatement.executeUpdate();

            updateQuery = "UPDATE contactinformation SET relationshipStatus = ? WHERE email = ?";
            updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, newContactInformation.getRelationshipStatus());
            updateStatement.setString(2, user.getEmail());
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //create like table
    public void createLikeTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "CREATE TABLE likes (postId VARCHAR(255))";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void likePost(String postId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            createLikeTable();
            String query = "INSERT INTO likes (postId) VALUES (?)";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, postId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Response.SearchPostResponse> searchPost(String requestBody) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM users";

            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            //parse the result into arrayList of user
            ArrayList<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User(resultSet.getString("email"),
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getString("password"));

                // convert post
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Post>>() {
                }.getType();
                ArrayList<Post> posts = gson.fromJson(resultSet.getString("posts"), type);
                user.setPosts(posts);
                users.add(user);
            }

            ArrayList<Response.SearchPostResponse> searchPostResponses = new ArrayList<>();

            // search for the post
            for (User user : users) {
                if (user.getPosts() == null) {
                    continue;
                }
                for (Post post : user.getPosts()) {
                    if (post.getText().toLowerCase().contains(requestBody.toLowerCase())) {
                        Response.SearchPostResponse r = new Response.SearchPostResponse(user.getEmail(), user.getName(), post);
                        searchPostResponses.add(r);
                    }
                }
            }

            return searchPostResponses;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateUser(User user) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "UPDATE users SET name = ?, lastName = ?, additionalName = ?, title = ?, country = ?, city = ? WHERE email = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getAdditionalName());
            statement.setString(4, user.getTitle());
            statement.setString(5, user.getCountry());
            statement.setString(6, user.getCity());
            statement.setString(7, user.getEmail());


            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Request.ContactInformation getUserContactInformation(User user) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM contactinformation WHERE email = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, user.getEmail());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Request.ContactInformation(user.getEmail(), user.getPassword(),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("address"),
                        resultSet.getString("kind"),
                        resultSet.getString("birthday"),
                        resultSet.getString("relationshipStatus"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;


    }
}

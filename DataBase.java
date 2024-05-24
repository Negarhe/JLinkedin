import java.sql.*;

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
}

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
}

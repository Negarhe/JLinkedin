public class LogIn {

    public LogIn (String email, String password) {
        //search in dataBase if you have a user with this email
        //if yes, check if the password is correct
        //if yes, show the user's profile
        //if no, show an error message

        DataBase dataBase = new DataBase();
        User user = dataBase.getUser(email);

        if (user != null) {
            if (dataBase.checkPass(email, password)) {
                user.displayProfile();
            } else {
                System.out.println("Wrong password");
            }
        } else {
            System.out.println("User not found");
        }

    }
}

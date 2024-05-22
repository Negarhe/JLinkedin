public class SignUp {

    public SignUp (String email, String pass, String pass2, String name, String lastName) {
        if (emailValidityCheck(email)) {
            if (checkPass(pass)) {
                if (pass.equals(pass2)) {
                    User user = new User(email, name, lastName, pass);
                    DataBase dataBase = new DataBase();
                    dataBase.insertUser(user);
                } else {
                    System.out.println("Passwords do not match");

                }
            }
            else
                System.out.println("please choose a stronger password");
        }

        System.out.println("invalid email.");
    }

    private boolean emailValidityCheck(String email) {
        return email.contains("@") && (email.contains(".ir") || email.contains(".com"));
    }

    public boolean checkPass(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = !password.matches("[A-Za-z0-9 ]*");
        return hasUppercase && hasLowercase && hasDigit && hasSpecial;
    }
}

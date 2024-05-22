import java.util.Scanner;



public class Main {
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("1. Sign up");
        System.out.println("2. Login");
        System.out.println("3. exit");

        Clients clients = new Clients();
        DataBase dataBase = new DataBase();

        int choice = in.nextInt();
        in.nextLine();


        if (choice == 1) {
            System.out.println("Email: ");
            String email = in.nextLine();

            if(email.contains("@") && email.contains(".")){
                //check if a user with this email is existing
                if (dataBase.userExists(email)) {
                    System.out.println("This email is already token.");
                    return;
                }

                //if user doesn't exist
                System.out.println("Name: ");
                String name = in.nextLine();
                System.out.println("last name: ");
                String lastName = in.nextLine();
                System.out.println("password: ");
                String password = in.nextLine();
                //check if the password is strong enough
                if (checkPass(password)) {
                    System.out.println("Enter your password again: ");
                    String password2 = in.nextLine();
                    if (password.equals(password2)) {
                        User user = new User(email, name, lastName, password);
                        dataBase.insertUser(user);
                    } else {
                        System.out.println("Passwords do not match");
                    }
                }

                //if password wasn't strong enough
                else
                    System.out.println("please choose a stronger password");
            }

            else{
                System.out.println("Invalid email");
            }
        }

        else if(choice == 2){
            System.out.println("email: ");
            String email = in.nextLine();
            System.out.println("password: ");
            String password = in.nextLine();

            if (email.contains("@") && email.contains(".")) {
                //find the user with this email and if it doesn't exist print an error
                if (dataBase.userExists(email)) {
                    //check passwords match
                    if (dataBase.checkPass(email, password)) {
                        System.out.println("Logged in successfully");
                    } else {
                        System.out.println("Incorrect password");
                    }
                    System.out.println("invalid pass");
                } else {
                    System.out.println("User does not exist");
                }
            } else {
                System.out.println("this email doesn't exist, please sign up first.");
                return;
            }
            displayMenu();


        }

        else if(choice == 3){
            System.exit(0);
        }

    }

    private static boolean checkPass(String password) {
        boolean allNumbers = true;
        for (int i = 0; i < password.length(); i++) {
            if (!Character.isDigit(password.charAt(i))) {
                allNumbers = false;
                break;
            }
        }

        return !allNumbers && password.length() >= 8;
    }

    //just a simple menu once the user logged in
    private static void displayMenu() {
        System.out.println("1- view your profile");
        System.out.println("2- create a post");
        System.out.println("3- search for a user");
        System.out.println("4- show the feed");
    }
}
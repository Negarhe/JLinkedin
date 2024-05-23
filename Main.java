import java.sql.SQLOutput;
import java.util.Locale;
import java.util.Scanner;



public class Main {
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("1. Sign up");
        System.out.println("2. Login");
        System.out.println("3. exit");



        int choice = in.nextInt();
        in.nextLine();


        while (true){
            if(choice == 1) {

                System.out.println("Email: ");
                String email = in.nextLine();
                System.out.println("Password: ");
                String pass = in.nextLine();
                System.out.println("double check passWord: ");
                String pass2 = in.nextLine();
                System.out.println("Name: ");
                String name = in.nextLine();
                System.out.println("Last name: ");
                String lastName = in.nextLine();

                SignUp signUp = new SignUp();
                if (signUp.signUp(email, pass, pass2, name, lastName)){
                    User user = new User(email, name, lastName, pass);
                    displayMenu();
                    int choice2 = in.nextInt();
                    in.nextLine();

                    if (choice2 == 1) {
                        //view profile
                        user.displayProfile(user.getEmail());
                        System.out.println("Do you want to edit your profile? (yes/no)");
                        String answer = in.nextLine();
                        if (answer.equalsIgnoreCase("yes")) {
                            user.editProfile();
                        }
                    } else if (choice2 == 2) {
                        //create a post
                    } else if (choice2 == 3) {
                        //search for a user
                    } else if (choice2 == 4) {
                        //show the feed
                    }
                }

            }

        else if (choice == 2) {
                System.out.println("email: ");
                String email = in.nextLine();
                System.out.println("password: ");
                String password = in.nextLine();

                LogIn logIn = new LogIn();
                if (logIn.logIn(email, password)) {
                    displayMenu();

                    int choice2 = in.nextInt();
                    in.nextLine();

                    if (choice2 == 1) {
                        //view profile
                    } else if (choice2 == 2) {
                        //create a post
                    } else if (choice2 == 3) {
                        //search for a user
                    } else if (choice2 == 4) {
                        //show the feed
                    }
                }


            } else if (choice == 3) {
                System.exit(0);
            }
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
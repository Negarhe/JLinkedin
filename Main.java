import java.util.Scanner;

public class Main {
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("1. Sign up");
        System.out.println("2. Login");
        System.out.println("3. exit");

        Clients clients = new Clients();

        int choice = in.nextInt();
        in.nextLine();
        String command = in.nextLine();

        if (choice == 1) {
            System.out.println("Enter your email: ");
            String email = in.nextLine();

            if(email.contains("@") && email.contains(".")){
                System.out.println("Enter your name: ");
                String name = in.nextLine();
                System.out.println("Enter your last name: ");
                String lastName = in.nextLine();
                System.out.println("Enter your password: ");
                String password = in.nextLine();
                //check if the password is strong enough
                if (checkPass(password)) {
                    System.out.println("Enter your password again: ");
                    String password2 = in.nextLine();
                    if (password.equals(password2)) {
                        User user = new User(email, name, lastName, password);
                        clients.addUser(user);
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
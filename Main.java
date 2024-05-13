import java.util.Scanner;

public class Main {
    public static Scanner in = new Scanner(System.in);


    public static void main(String[] args) {
        System.out.println("1. Sign up");
        System.out.println("2. Login");
        System.out.println("3. exit");

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
            }
            else{
                System.out.println("Invalid email");
            }
        }

        else if(choice == 2){

        }

        else if(choice == 3){
            System.exit(0);
        }


    }
}
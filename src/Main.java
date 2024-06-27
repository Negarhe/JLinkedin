import com.google.gson.internal.bind.util.ISO8601Utils;
import model.DataBase;
import model.Post;
import model.User;

import java.util.List;
import java.util.Scanner;



public class Main {
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("1. Sign up");
        System.out.println("2. Login");
        System.out.println("3. exit");


        int choice = in.nextInt();
        in.nextLine();


        while (true) {
            if (choice == 1) {

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


                User user = new User(email, name, lastName, pass);

                if (user.signUp()) {
                    displayMenu();
                    int choice2 = in.nextInt();
                    in.nextLine();

                    if (choice2 == 1) {
                        //view profile
                        viewProfile(user);

                    } else if (choice2 == 2) {
                        //create a post
                        user.createPost();
                    } else if (choice2 == 3) {
                        //search for a user in dataBase
                        System.out.println("Enter the name of the user you are looking for: ");
                        String fullName = in.nextLine();
                        DataBase dataBase = new DataBase();
                        User searchedUser = dataBase.searchUser(fullName);
                        if (searchedUser != null) {
                            searchedUser.displayProfile(searchedUser.getEmail());
                        } else {
                            System.out.println("model.User not found!");
                        }
                    } else if (choice2 == 4) {
                        //show the feed
                        DataBase dataBase = new DataBase();
                        List<User> followings = dataBase.getFollowings(user.getEmail());
                        if (followings == null) {
                            //print something that user understand
                            System.out.println("No followings found for this user!");
                        }else {
                            for (User following : followings) {
                                List<Post> posts = dataBase.getPosts(following.getEmail());
                                for (Post post : posts) {
                                    post.displayPost();
                                }
                            }
                        }

                    } else if(choice2 == 5) {
                        System.out.println("---Your Followings: --- ");
                        DataBase dataBase = new DataBase();
                        List<User> followings = dataBase.getFollowings(user.getEmail());
                        for (User current : followings)
                            current.displayProfile(current.getEmail());
                        System.out.println("---Your Followers: --- ");
                        List<User> followers = dataBase.getFollowers(user.getEmail());
                        for (User current2 : followers)
                            current2.displayProfile(current2.getEmail());
                    } else if (choice2 == 6) {


                    }

                }

            } else if (choice == 2) {
                System.out.println("email: ");
                String email = in.nextLine();
                System.out.println("password: ");
                String password = in.nextLine();

                DataBase dataBase = new DataBase();
                User user = dataBase.getUser(email);

                if (user.logIn(email, password)) {
                    displayMenu();

                    int choice2 = in.nextInt();
                    in.nextLine();

                    if (choice2 == 1) {
                        //view profile
                        viewProfile(user);
                      } else if (choice2 == 2) {
                        //create a post
                        user.createPost();
                    } else if (choice2 == 3) {
                        //search for a user in dataBase
                        System.out.println("Enter the name of the user you are looking for: ");
                        String fullName = in.nextLine();
                        User searchedUser = dataBase.searchUser(fullName);
                        if (searchedUser != null) {
                            searchedUser.displayProfile(searchedUser.getEmail());
                            System.out.println("Do you want to follow this user?");
                            String answer = in.nextLine();
                            if (answer.contains("Yes")){
                                followAUser(user, searchedUser);
                            }


                        } else {
                            System.out.println("model.User not found!");
                        }
                    } else if (choice2 == 4) {
                        //show the feed
                        List<User> followings = dataBase.getFollowings(user.getEmail());
                        if (followings == null) {
                            //print something that user understand
                            System.out.println("No followings found for this user!");
                        }else {
                            for (User following : followings) {
                                List<Post> posts = dataBase.getPosts(following.getEmail());
                                for (Post post : posts) {
                                    post.displayPost();
                                }
                            }
                        }
                    }
                     else if(choice2 == 5) {
                        System.out.println("---Your Followings: --- ");
                        List<User> followings = dataBase.getFollowings(user.getEmail());
                        for (User current : followings)
                            current.displayProfile(current.getEmail());
                        System.out.println("---Your Followers: --- ");
                        List<User> followers = dataBase.getFollowers(user.getEmail());
                        for (User current2 : followers)
                            current2.displayProfile(current2.getEmail());
                    }
                }


            } else if (choice == 3) {
                System.exit(0);
            }
        }
    }


    private static void viewProfile(User user) {
        user.displayProfile(user.getEmail());
        System.out.println("Do you want to edit your profile? (yes/no)");
        String answer = in.nextLine();
        if (answer.equalsIgnoreCase("yes")) {
            user.editProfile();
        }
        else
            return;
    }
    private static void followAUser(User user, User searchedUser){
        user.getFollowing().add(searchedUser);
        searchedUser.getFollowers().add(user);

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
        System.out.println("1- view my profile");
        System.out.println("2- create a post");
        System.out.println("3- search for a user & follow");
        System.out.println("4- show my feed");
        System.out.println("5- show my followers and followings");
        System.out.println("6- send request connection to a user");
        System.out.println("7- show my requests");
    }
}
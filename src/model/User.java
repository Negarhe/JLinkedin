package model;


import java.io.Serializable;
import java.util.*;

public class User implements Serializable {


    public enum Status {
        SEEKING_JOB, HIRING, PROVIDING_SERVICES
    }

    private String title; //220 character, a brief overview
    private String email; //need to check validity
    private String name; //20 character
    private String lastName; //40 character
    private String password; //need to check validity
    private String additionalName; //40 character
    private String city; //60 character
    private String country; //60 character
    private ContactInformation contactInfo; //phone number, address, birthday, relationship status
    //two fields for profile photo and background photo
    private String profilePhoto; //link to the photo
    private String backgroundPhoto; //link to the photo

    private String profession; //60 character, for example "software engineering"
    private Status status; //seeking job? hiring? providing services?
    private ArrayList<User> connections; //people that you are connected to
    private ArrayList<User> followers; //people that are following you
    private ArrayList<User> following; //people that you are following
    private ArrayList<Post> posts; //posts that you have created

    private ArrayList<Job> experiences; //from the oldest to the latest
    private ArrayList<Education> educations; //from the oldest to the latest

    /**
     * constructor = sign up method
     * constructing a user only consists of getting email, name, last name and password and adding the user to server's list
     * other fields should be completed later.
     **/

    public User(String email, String name, String lastName, String password) {

        if (emailValidityCheck(email)) {
            this.email = email;
            this.name = name;
            this.lastName = lastName;
            this.password = password;
            this.title = "";
            this.additionalName = "";
            this.city = "";
            this.country = "";
            this.contactInfo = new ContactInformation("", "", ContactInformation.Kind.MOBILE, "", "", "");
            this.profilePhoto = "";
            this.backgroundPhoto = "";
            this.profession = "";
            this.status = Status.HIRING;
            this.experiences = new ArrayList<>();
            this.educations = new ArrayList<>();
            this.connections = new ArrayList<>();
            this.followers = new ArrayList<>();
            this.following = new ArrayList<>();
            this.posts = new ArrayList<>();
        } else
            System.out.println("Invalid email");
    }

    //format checking methods, also we can use try-catch blocks

    public boolean emailValidityCheck(String email) {
        return email.contains("@") && email.contains(".");
    }

    public boolean signUp() {
        if (emailValidityCheck(email)) {
            if (checkPass(password)) {
//                if (pass.equals(pass2)) {
                User user = new User(email, name, lastName, password);
                DataBase dataBase = new DataBase();
                dataBase.insertUser(user);
                return true;
            } else {
                System.out.println("please choose a stronger password!");
                return false;
            }
        } else
            System.out.println("invalid email!");

        return false;
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

    public boolean logIn() {
        //search in dataBase if you have a user with this email
        //if yes, check if the password is correct
        //if yes, show the user's profile
        //if no, show an error message

        DataBase dataBase = new DataBase();
        User user = dataBase.getUser(email);

        if (user != null) {
            if (dataBase.checkPass(email, password)) {
                System.out.println("Welcome " + user.getName() + " " + user.getLastName());
                return true;
            } else {
                System.out.println("Wrong password");
                return false;
            }
        } else {
            System.out.println("model.User not found");
            return false;
        }

    }

    public void sendMessage(User recipient, String content, DataBase dataBase) {
        Message message = new Message(this, recipient, content);
        dataBase.saveMessage(message);
    }

    public void displayProfile(String email) {
        DataBase dataBase = new DataBase();
        User user = dataBase.getUser(email);
        System.out.println("Name: " + user.getName());
        System.out.println("Last name: " + user.getLastName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Title: " + user.getTitle());
        System.out.println("Additional name: " + user.getAdditionalName());
        System.out.println("City: " + user.getCity());
        System.out.println("Country: " + user.getCountry());
        System.out.println("Contact information: " + user.getContactInfo());
        System.out.println("Profession: " + user.getProfession());
        System.out.println("Status: " + user.getStatus());
        System.out.print("Experiences: ");
        for (Job job : user.getExperiences()) {
            System.out.print(job + ", ");
        }
        System.out.println();

        System.out.print("Educations: ");
        for (Education education : user.getEducations()) {
            System.out.print(education + ", ");
        }
        System.out.println();

        System.out.println("Profile photo: " + user.getProfilePhoto());

    }

    public void editProfile() {
//        DataBase dataBase = new DataBase();
//        System.out.println("What do you want to edit?");
//        System.out.println("1. Title");
//        System.out.println("2. Additional name");
//        System.out.println("3. City");
//        System.out.println("4. Country");
//        System.out.println("5. Contact information");
//        System.out.println("6. Profession");
//        System.out.println("7. Status");
//        System.out.println("8. Experiences");
//        System.out.println("9. Educations");
//        System.out.println("10. Profile photo");
//        System.out.println("11. Background photo");
//        System.out.println("12. none");
//
//        int choice = 0;
//        while (choice != 12) {
//            choice = scanner.nextInt();
//            scanner.nextLine();
//
//            switch (choice) {
//                case 1:
//                    System.out.println("Enter the new title: ");
//                    this.setTitle(scanner.nextLine());
//                    //set title in dataBase
//                    dataBase.updateUserTitle(this.getEmail(), this.getTitle());
//                    break;
//                case 2:
//                    System.out.println("Enter the new additional name: ");
//                    this.setAdditionalName(scanner.nextLine());
//                    //set additionalName in dataBase
//                    dataBase.updateUserAdditionalName(this.getEmail(), this.getAdditionalName());
//                    break;
//                case 3:
//                    System.out.println("Enter the new city: ");
//                    this.setCity(scanner.nextLine());
//                    //set city in dataBase
//                    dataBase.updateUserCity(this.getEmail(), this.getCity());
//                    break;
//                case 4:
//                    System.out.println("Enter the new country: ");
//                    this.setCountry(scanner.nextLine());
//                    //set country in dataBase
//                    dataBase.updateUserCountry(this.getEmail(), this.getCountry());
//                    break;
//                case 5:
//                    System.out.println("Enter the new contact information: ");
//                    System.out.println("Phone number: ");
//                    String phoneNumber = scanner.nextLine();
//                    System.out.println("Kind: ");
//                    System.out.println("1. Mobile");
//                    System.out.println("2. Home");
//                    System.out.println("3. Work");
//                    int kindChoice = scanner.nextInt();
//                    scanner.nextLine();
//                    ContactInformation.Kind kind = ContactInformation.Kind.MOBILE;
//                    switch (kindChoice) {
//                        case 1:
//                            kind = ContactInformation.Kind.MOBILE;
//                            break;
//                        case 2:
//                            kind = ContactInformation.Kind.HOME;
//                            break;
//                        case 3:
//                            kind = ContactInformation.Kind.JOB;
//                            break;
//                    }
//                    System.out.println("Address: ");
//                    String address = scanner.nextLine();
//                    System.out.println("Birthday: ");
//                    String birthday = scanner.nextLine();
//                    System.out.println("Relationship status: ");
//                    String relationshipStatus = scanner.nextLine();
//                    this.setContactInfo(new ContactInformation(this.email, phoneNumber, kind, address, birthday, relationshipStatus));
//                    //set contactInfo in dataBase
//                    dataBase.updateUserContactInfo(this.getEmail(), this.getContactInfo());
//                    break;
//                case 6:
//                    System.out.println("Enter the new profession: ");
//                    this.setProfession(scanner.nextLine());
//                    //set profession in dataBase
//                    dataBase.updateUserProfession(this.getEmail(), this.getProfession());
//                    break;
//                case 7:
//                    System.out.println("Enter the new status: ");
//                    System.out.println("1. Seeking job");
//                    System.out.println("2. Hiring");
//                    System.out.println("3. Providing services");
//                    int statusChoice = scanner.nextInt();
//                    scanner.nextLine();
//                    switch (statusChoice) {
//                        case 1:
//                            this.setStatus(Status.SEEKING_JOB);
//                            break;
//                        case 2:
//                            this.setStatus(Status.HIRING);
//                            break;
//                        case 3:
//                            this.setStatus(Status.PROVIDING_SERVICES);
//                            break;
//                    }
//                    //set status in dataBase
//                    dataBase.updateUserStatus(this.getEmail(), this.getStatus().toString());
//            }
//        }
    }

    //    public void createPost(String content, String imageUrl, String videoUrl, String caption) {
//        Post post = new Post(content, imageUrl, videoUrl, caption);
//        this.getPosts().add(post);
//        DataBase dataBase = new DataBase();
//        dataBase.insertPost(this.getEmail(), post);
//    }


    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<User> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<User> connections) {
        this.connections = connections;
    }

    public ArrayList<User> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<User> followers) {
        this.followers = followers;
    }

    public ArrayList<User> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<User> following) {
        this.following = following;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getBackgroundPhoto() {
        return backgroundPhoto;
    }

    public void setBackgroundPhoto(String backgroundPhoto) {
        this.backgroundPhoto = backgroundPhoto;
    }

    public String getAdditionalName() {
        return additionalName;
    }

    public void setAdditionalName(String additionalName) {
        this.additionalName = additionalName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ContactInformation getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInformation contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ArrayList<Job> getExperiences() {
        return experiences;
    }

    public void setExperiences(ArrayList<Job> experiences) {
        this.experiences = experiences;
    }

    public ArrayList<Education> getEducations() {
        return educations;
    }

    public void setEducations(ArrayList<Education> educations) {
        this.educations = educations;
    }


}


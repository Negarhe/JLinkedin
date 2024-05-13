import java.util.*;

public class User {

    private String title ; //220 character, a brief overview
    private String email ; //need to check validity
    private String name ; //20 character
    private String lastName ; //40 character
    private String password ; //need to check validity
    private String additionalName ; //40 character
    private String city ; //60 character
    private String country ; //60 character
    //two fields for profile photo and background photo

    private String profession ; //60 character, for example "software engineering"
    private String status ; //seeking job? hiring? providing services?

    private ArrayList<Job> experiences ; //from the oldest to the latest
    private ArrayList<Education> educations ; //from the oldest to the latest


    /**
    constructor = sign up method
    constructing a user only consists of getting email, name, last name and password and adding the user to server's list
    other fields should be completed later.
     **/

    public user(email, name, lastName, password ){

        this.name = name ;
        this.lastName = lastName ;
        if( emailValidityCheck() == true )
            this.email = email ;
        if( passwordDoubleCheck() == true )
            this.password = password ;
    }

    //format checking methods, also we can use try-catch blocks
    public boolean passwordDoubleCheck() {}
    public boolean emailValidityCheck() {}
    public void displayProfile() {}
    public void editProfile() {}




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
}

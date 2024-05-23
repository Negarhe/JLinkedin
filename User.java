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
    private ContactInformation contactInfo ; //phone number, address, birthday, relationship status
    //two fields for profile photo and background photo

    private String profession ; //60 character, for example "software engineering"
<<<<<<< Updated upstream
    private String status ; //seeking job? hiring? providing services?
=======
    private Status status ; //seeking job? hiring? providing services?
>>>>>>> Stashed changes

    private ArrayList<Job> experiences ; //from the oldest to the latest
    private ArrayList<Education> educations ; //from the oldest to the latest


    /**
     constructor = sign up method
     constructing a user only consists of getting email, name, last name and password and adding the user to server's list
     other fields should be completed later.
     **/

    public User(String email, String name, String lastName, String password ){

<<<<<<< Updated upstream
        this.name = name ;
        this.lastName = lastName ;
        this.password = password ;
        if(emailValidityCheck())
            this.email = email ;
=======
        if(emailValidityCheck(email)) {
            this.email = email;
            this.name = name;
            this.lastName = lastName;
            this.password = password;
        }
>>>>>>> Stashed changes
        else
            System.out.println("Invalid email");
    }

    //format checking methods, also we can use try-catch blocks
//    public boolean passwordDoubleCheck() {}
<<<<<<< Updated upstream
    public boolean emailValidityCheck() {
        return email.contains("@") && email.contains(".");
    }
    public void displayProfile() {}
=======
    public boolean emailValidityCheck(String email) {
        return email.contains("@") && email.contains(".");
    }
    public void displayProfile() {

    }
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
>>>>>>> Stashed changes
}
package model;

public class ContactInformation {
    public enum Kind {
        HOME, JOB, MOBILE
    }

    private String email;
    private String phoneNumber;
    private Kind kind;//home, job or mobile
    private String address;//220 character
    private String birthday;
    private String relationshipStatus;// telegram or whatsapp or skype ...

    public ContactInformation(String email, String phoneNumber, Kind kind, String address, String birthday, String relationshipStatus) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.kind = kind;
        this.address = address;
        this.birthday = birthday;
        this.relationshipStatus = relationshipStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getKind() {
        return kind.toString();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRelationshipStatus() {
        return relationshipStatus;
    }

    public void setRelationshipStatus(String relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }
}

package model;

public class Connect {
    private String sender;
    private String receiver;
    private String note; //500 char
    public enum Status {
        PENDING, ACCEPTED, REJECTED
    }

    private Status status;

    public Connect(String sender, String receiver, String note) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = Status.PENDING;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

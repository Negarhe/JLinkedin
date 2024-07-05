package model;

import java.util.ArrayList;

public class Job {
    private String title;
    private String manner;// full-time, part-time,trainee, conventional, trainee with salary, seasonal
    private String companyName;
    private String kindOfWork; //remote, hibrig, on-site
    private String isHire; //yes or no
    private String startDate;
    private String endDate;
    private String explanation;
    private ArrayList<String> skills;

    public Job(String title, String manner, String companyName, String kindOfWork, String isHire, String startDate, String endDate, String explanation) {
        this.title = title;
        this.manner = manner;
        this.companyName = companyName;
        this.kindOfWork = kindOfWork;
        this.isHire = isHire;
        this.startDate = startDate;
        this.endDate = endDate;
        this.explanation = explanation;
        this.skills = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManner() {
        return manner;
    }

    public void setManner(String manner) {
        this.manner = manner;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getKindOfWork() {
        return kindOfWork;
    }

    public void setKindOfWork(String kindOfWork) {
        this.kindOfWork = kindOfWork;
    }

    public String getIsHire() {
        return isHire;
    }

    public void setIsHire(String isHire) {
        this.isHire = isHire;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }
}

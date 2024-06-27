import java.util.ArrayList;

public class Education {
    private String schoolName;
    private String major;
    private String degree;
    private String startDate;
    private String endDate;
    private String activity;
    private String description;
    private ArrayList<String> skills;


    public Education(String schoolName, String major, String degree, String startDate, String endDate, String activity, String description) {
        this.schoolName = schoolName;
        this.major = major;
        this.degree = degree;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activity = activity;
        this.description = description;
        this.skills = new ArrayList<>();
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
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

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }
}

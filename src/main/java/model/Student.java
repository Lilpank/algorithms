package model;

import java.util.HashMap;


public class Student {
    private String firstname = "";
    private String lastname = "";

    public String getGroupnumber() {
        return groupnumber;
    }

    public void setGroupnumber(String groupnumber) {
        this.groupnumber = groupnumber;
    }

    private String groupnumber = "";

    private HashMap<String, Integer> subjects = new HashMap<>();

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", subjects=" + subjects +
                ", avg=" + avg +
                '}';
    }

    public void setSubjects(HashMap<String, Integer> subjects) {
        this.subjects = subjects;
    }

    public void setAvg(int avg) {
        this.avg = avg;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public HashMap<String, Integer> getSubjects() {
        return subjects;
    }

    public int getAvg() {
        return avg;
    }

    private int avg = 0;

    public Student() {

    }


}

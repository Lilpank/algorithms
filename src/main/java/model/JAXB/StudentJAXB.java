package model.JAXB;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@XmlRootElement(name = "student")
@XmlType(propOrder = {"firstname", "lastname", "groupNumber", "subject", "avg"})
public class StudentJAXB {
    private String firstname = "";
    private String lastname = "";
    private int avg = 0;

    private ArrayList<Subjects> subject;

    public ArrayList<Subjects> getSubject() {
        return subject;
    }

    @XmlElement(name = "subject")
    public void setSubject(ArrayList<Subjects> subject) {
        this.subject = subject;
    }

    private String groupNumber = "";


    public String getGroupNumber() {
        return groupNumber;
    }

    @XmlAttribute(name = "groupNumber")
    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }


    @XmlAttribute(name = "firstname")
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @XmlAttribute(name = "lastname")
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    @XmlElement(name = "average")
    public void setAvg(int avg) {
        this.avg = avg;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }


    public int getAvg() {
        return avg;
    }

    public StudentJAXB() {

    }

    @Override
    public String toString() {
        return "StudentJAXB{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", avg=" + avg +
                ", subject=" + subject +
                ", groupNumber='" + groupNumber + '\'' +
                '}';
    }
}

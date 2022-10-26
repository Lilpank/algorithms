package model.JAXB;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;

@XmlRootElement(name = "group")
@XmlType(propOrder = {"students"})
public class GroupJAXB {
    private ArrayList<StudentJAXB> students;

    @Override
    public String toString() {
        return "GroupJAXB{" +
                "students=" + students +
                '}';
    }

    public ArrayList<StudentJAXB> getStudents() {
        return students;
    }

    @XmlElement(name = "student")
    public void setStudents(ArrayList<StudentJAXB> students) {
        this.students = students;
    }

    public GroupJAXB() {

    }
}

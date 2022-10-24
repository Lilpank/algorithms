package model;

import java.util.ArrayList;

public class Group {

    private static ArrayList<Student> students = new ArrayList<>();

    public static void setStudents(ArrayList<Student> students) {
        Group.students = students;
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }

    public Group() {

    }

}

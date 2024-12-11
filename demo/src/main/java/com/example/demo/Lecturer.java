package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lecturer extends Person {
    private static Map<String, Lecturer> lecturersByName = new HashMap<>();
    private ArrayList<Course> courses;

    private Lecturer(String name) {
        super(name);
    }

    public static Lecturer findLecturerByName(String name) {
        if (lecturersByName.containsKey(name)) {
            return lecturersByName.get(name);
        } else {
            Lecturer lecturer = new Lecturer(name);
            lecturersByName.put(name, lecturer);
            return lecturer;
        }
    }


    public void addStudentToCourse(Course course,Student std){
        for(Course c : courses){
            if(c.getCourseID() == course.getCourseID()){
                ArrayList<Student> stdList  = c.getEnrolledStudentsList();
                stdList.add(std);
            }
        }
    }

    public void removeStudentToCourse(Course course,Student std){
        for(Course c : courses){
            if(c.getCourseID() == course.getCourseID()){
                ArrayList<Student> stdList  = c.getEnrolledStudentsList();
                for(Student s : stdList){
                    if(s.getName().equals(std.getName())){
                        stdList.remove(std);
                    }
                }

            }
        }
    }

    @Override
    public void add() {}

    @Override
    public void remove() {}

    @Override
    public Lecturer update(Object obj) {
        return null;
    }
}

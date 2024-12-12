package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lecturer extends Person {
    private static Map<String, Lecturer> lecturersByName = new HashMap<>();

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
        for(Course c : super.getCourses()){
            if(c.getCourseID() == course.getCourseID()){
                ArrayList<Student> stdList  = c.getEnrolledStudentsList();
                stdList.add(std);
            }
        }
    }

    public void removeStudentFromCourse(Course course,Student std){
        for(Course c : super.getCourses()){
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

    public void TransferStudentToAnotherCourse(Course enrolledCourse, Course transferCourse, Student std) {
        ArrayList<Student> stdList1  = enrolledCourse.getEnrolledStudentsList();
        ArrayList<Student> stdList2  = transferCourse.getEnrolledStudentsList();
        Classroom enrolledCourseClassroom = enrolledCourse.getAssignedClassroom();
        Classroom transferClassroom = transferCourse.getAssignedClassroom();
        int freePlaces = transferClassroom.getCapacity() - transferCourse.getEnrolledStudentsList().size();
        if(super.getCourses().contains(enrolledCourse) && super.getCourses().contains(transferCourse)) {
           if(std.getCourses().contains(enrolledCourse) && !std.getCourses().contains(transferCourse)) {
               if(freePlaces >= 1) {
                   std.getCourses().remove(enrolledCourse);
                   std.getCourses().add(transferCourse);
                   stdList1.remove(std);
                   stdList2.add(std);
                   System.out.println("Successfully transferred");
                   return;
               }
               System.out.println("Not enough place!");
           }
            System.out.println("Student did not transfer!");
        }
        System.out.println("This Lecturer cannot operate this transfer!");
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

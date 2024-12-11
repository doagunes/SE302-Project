package com.example.demo;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {

        CSV_Reader csv = new CSV_Reader();

        /*
        ClassroomDataAccessObject cdo = new ClassroomDataAccessObject();
        cdo.createTable();
        cdo.addClassroom(csv.readClassrooms());
        cdo.getClassrooms();

         */



        CourseDataAccessObject courseDataAccessObject = new CourseDataAccessObject();
        courseDataAccessObject.createTable();
        courseDataAccessObject.addCourse(csv.readCourses());
        courseDataAccessObject.getCourses();


    }
}

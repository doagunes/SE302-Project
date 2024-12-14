package com.example.demo;

import java.io.IOException;

public class DoaTest {
    public static void main(String[] args) throws IOException {
        CSV_Reader csv = new CSV_Reader();



        ClassroomDataAccessObject cdo = new ClassroomDataAccessObject();
        cdo.createTable();
        cdo.addClassroom(csv.readClassrooms());
        cdo.getCapacityWhereClassroomIs("C201");

       /*
        CourseDataAccessObject courseDataAccessObject = new CourseDataAccessObject();
        courseDataAccessObject.createTable();
        courseDataAccessObject.addCourse(csv.readCourses());
        //courseDataAccessObject.getCourses();
        courseDataAccessObject.getCourseWhereLecturerIs("Turhan Tunalı");

        */





    }
}

package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;

public class BenhurTest {
    public static void main(String[] args) throws IOException {
        CSV_Reader csv = new CSV_Reader();

        /*
        CourseDataAccessObject courseDataAccessObject = new CourseDataAccessObject();
        courseDataAccessObject.createTable();
        courseDataAccessObject.addCourse(csv.readCourses());

        System.out.println(courseDataAccessObject.getCoursesBasedOnStudent("DOĞA GÜNEŞ"));

         */

        AttendenceDatabase.dropTables();
        AttendenceDatabase.createTables();
        AttendenceDatabase.addStudentsFromCSV();
        AttendenceDatabase.addAttendancesWithInitialDatas();





     }
}

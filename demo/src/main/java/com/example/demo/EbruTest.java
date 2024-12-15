package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;

public class EbruTest {
    public static void main(String[] args) {
        CSV_Reader rdr = new CSV_Reader();

        try{
            //TODO students okurken String data istiyor tam olarak nasıl okuyacagım bunu
            ArrayList<Course> courses = rdr.readCourses();
            //ArrayList<Student> students = rdr.readStudents();
            Admin admin = new Admin();
            //admin.addStudentToCourse(courses.get(2),);


        }
        catch(IOException e){

        }
    }
}

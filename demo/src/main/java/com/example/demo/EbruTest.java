package com.example.demo;

import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class EbruTest {
    public static void main(String[] args) {
        CSV_Reader rdr = new CSV_Reader();

        try{
            ArrayList<Course> courses = rdr.readCourses();
            ArrayList<Classroom> classrooms = rdr.readClassrooms();
            Admin admin = new Admin();
            Course se115 = courses.get(0);
           for(Course course : courses){
               course.assignClassroom(classrooms);
           }

           for(Classroom classroom : classrooms){

               System.out.println(classroom.getClassroomName() + " " + classroom.getCapacity());
           }

           for(Classroom classroom : classrooms){
               if(classroom.getCapacity() >= 70){
                   admin.changeClassroom(se115,classroom);
                   break;
               }
           }

           int stdNmb = courses.get(0).getEnrolledStudentsList().size(); //se115in
            System.out.println(stdNmb);
            System.out.println(se115.getCourseID() + " " + se115.getAssignedClassroom().getClassroomName());


        }
        catch(IOException e){

        }
    }
}

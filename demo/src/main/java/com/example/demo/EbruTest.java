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

            for(Course course : courses){
                course.assignClassroom(classrooms);
            }
            System.out.println("************************");
            Course classidegisecek = courses.get(4);
            System.out.println(classidegisecek.getCourseID() + " " + classidegisecek.getAssignedClassroom().getClassroomName());

            /*
            for(Classroom classroom : classrooms){

                System.out.println(classroom.getClassroomName() + " " + classroom.getCapacity());
            }*/

            for(Classroom classroom : classrooms){
                if(classroom.getCapacity() >= classidegisecek.getEnrolledStudentsList().size()){
                    //admin.changeClassroom(classidegisecek,classroom);
                    //break;
                }
            }

            //System.out.println(classidegisecek.getCourseID() + " " + classidegisecek.getAssignedClassroom().getClassroomName());




        }
        catch(IOException e){

        }
    }
}

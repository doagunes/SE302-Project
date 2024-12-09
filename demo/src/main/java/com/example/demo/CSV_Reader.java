package com.example.demo;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSV_Reader {
    public ArrayList<Course> readCourses() throws IOException {
        ArrayList<Course> courses = new ArrayList<Course>();
        String filePath = "demo/src/main/resources/csv_files/Courses.csv";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            String data[] = line.split(";");
            courses.add(new Course(data[0], data[1], Integer.parseInt(data[2]), data[3], readStudents()));

        }
        return courses;
    }
    public ArrayList<String> readStudents () throws  IOException {
        ArrayList<String> students = new ArrayList<>();
        String filePath = "demo/src/main/resources/csv_files/Courses.csv";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            String studentsNames[] = line.split(";");
            for (int i = 4;i<studentsNames.length;i++) {
                students.add(studentsNames[i]);
            }
        }
        return students;

    }
}

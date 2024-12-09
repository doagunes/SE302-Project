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
        br.readLine(); // Stepping first line because there is no data at the first line
        while ((line = br.readLine()) != null) {
            String[] data = line.split(";");
            courses.add(new Course(data[0], data[1], Integer.parseInt(data[2]), data[3], readStudents(data)));

        }
        return courses;
    }
    public ArrayList<String> readStudents (String[] data) throws  IOException {
        ArrayList<String> students = new ArrayList<>();
        String filePath = "demo/src/main/resources/csv_files/Courses.csv";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        for (int i = 4; i < data.length; i++) {
            students.add(data[i]);
        }

        return students;
    }
    public ArrayList<Classroom> readClassrooms () throws IOException {
        ArrayList<Classroom> classrooms = new ArrayList<>();
        String filePath = "demo/src/main/resources/csv_files/ClassroomCapacity.csv";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        br.readLine(); // Stepping first line because there is no data at the first line
        while ((line = br.readLine()) != null) {
            String[] data = line.split(";");
            classrooms.add(new Classroom(data[0], Integer.parseInt(data[1])));

        }
        return classrooms;

    }

}

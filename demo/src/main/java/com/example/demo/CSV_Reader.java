package com.example.demo;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSV_Reader {
    public static ArrayList<Course> readCourses() throws IOException {
        ArrayList<Course> courses = new ArrayList<Course>();
        String filePath = "demo/src/main/resources/csv_files/Courses.csv";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        br.readLine(); // Stepping first line because there is no data at the first line
        while ((line = br.readLine()) != null) {
            String[] data = line.split(";");
            courses.add(new Course(data[0], data[1], Integer.parseInt(data[2]), data[3], readStudents(data)));

        }
        br.close();

        return courses;
    }
    public static ArrayList<String> readStudents (String[] data) throws  IOException {
        ArrayList<String> students = new ArrayList<>();
        String filePath = "demo/src/main/resources/csv_files/Courses.csv";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        for (int i = 4; i < data.length; i++) {
            students.add(data[i]);
        }
        br.close();
        return students;
    }
    public static ArrayList<Classroom> readClassrooms () throws IOException {
        ArrayList<Classroom> classrooms = new ArrayList<>();
        String filePath = "demo/src/main/resources/csv_files/ClassroomCapacity.csv";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        br.readLine(); // Stepping first line because there is no data at the first line
        while ((line = br.readLine()) != null) {
            String[] data = line.split(";");
            classrooms.add(new Classroom(data[0], Integer.parseInt(data[1])));

        }
        br.close();

        return classrooms;

    }
    public static ArrayList<String> readAllStudents () throws IOException {
        ArrayList<String> students = new ArrayList<>();
        String filePath = "demo/src/main/resources/csv_files/Courses.csv";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        br.readLine();
        while ((line = br.readLine()) != null) {
            String[] data = line.split(";");
            for (int i = 4;i< data.length;i++) {
                if (!students.contains(data[i])) {
                    students.add(data[i]);
                }
            }
        }


        br.close();
        return students;

    }

}

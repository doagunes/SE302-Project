package com.example.demo;

import java.util.HashMap;
import java.util.Map;

public class Student extends Person{
    private static Map<String, Student> studentsByName = new HashMap<>();

    public Student(String name) {
        super(name);
    }

    public static Student findStudentByName(String name) {
        // If student exists, return it
        if (studentsByName.containsKey(name)) {
            return studentsByName.get(name);
        }

        // Otherwise, create a new one and store it in the map
        Student student = new Student(name);
        studentsByName.put(name, student);
        return student;
    }

    @Override
    public Student add() {
        return null;
    }

    @Override
    public Student remove() {
        return null;
    }

    @Override
    public Student update(Object obj) {
        return null;
    }

}

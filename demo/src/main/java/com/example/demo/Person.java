package com.example.demo;

import java.util.ArrayList;

public abstract class Person {
    private String name;
    private ArrayList<Course> courses;

    public Person(String name) {
        this.name = name;
        this.courses = new ArrayList<>(); //TODO direkt sql'dan çekilcek kursalar
    }

    public String getName() {
        return name;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}

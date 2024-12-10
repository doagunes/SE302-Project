package com.example.demo;

import java.util.ArrayList;

public abstract class Person implements IGeneric {
    private String name;
    private int ID;
    private ArrayList<Course> courses;

    public Person(String name) {
        this.name = name;
        this.courses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}

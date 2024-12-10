package com.example.demo;

import java.util.HashMap;
import java.util.Map;

public class Lecturer extends Person {
    private static Map<String, Lecturer> lecturersByName = new HashMap<>();

    private Lecturer(String name) {
        super(name);
    }

    public static Lecturer findLecturerByName(String name) {
        if (lecturersByName.containsKey(name)) {
            return lecturersByName.get(name);
        } else {
            Lecturer lecturer = new Lecturer(name);
            lecturersByName.put(name, lecturer);
            return lecturer;
        }
    }

    @Override
    public Lecturer add() {
        return null;
    }

    @Override
    public Lecturer remove() {
        return null;
    }

    @Override
    public Lecturer update(Object obj) {
        return null;
    }
}

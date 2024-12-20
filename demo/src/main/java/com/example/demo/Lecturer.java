package com.example.demo;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lecturer extends Person {
    private static Map<String, Lecturer> lecturersByName = new HashMap<>();

    private Lecturer(String name) {
        super(name);
    }

    public static Lecturer findLecturerByName(String name) {
        if (lecturersByName.containsKey(name)) {
            //System.out.println("VAR OLAN HOCA DÖNDÜ    " + lecturersByName.get(name));
            return lecturersByName.get(name);
        } else {
            Lecturer lecturer = new Lecturer(name);
            lecturersByName.put(name, lecturer);
            //System.out.println("YENİ HOCA OLUŞTU   " + lecturer );
            return lecturer;
        }
    }

}

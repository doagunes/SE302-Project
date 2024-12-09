package com.example.demo;

import java.sql.*;

public class DatabaseConnection {

    private static final String url = "jdbc:sqlite:projectdb.db";
    private static Connection connection;

    public static Connection getConnection(){

        if(connection == null){ // singleton
            try {
                connection = DriverManager.getConnection(url); // connected to database
                System.out.println("Connected to database!");
            }catch (SQLException e){
                System.out.println("Connection error: " + e.getMessage());
            }
        }

        return connection;
    }

    public static void closeConnection(){

        if(connection != null){
            try{
                connection.close();
                System.out.println("Connection closed!");

            } catch (Exception e) {
                System.out.println("Connection closed error: " + e.getMessage());
            }
        }
    }
}

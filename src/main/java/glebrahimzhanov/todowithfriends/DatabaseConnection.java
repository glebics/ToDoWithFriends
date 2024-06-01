/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package glebrahimzhanov.todowithfriends;

/**
 *
 * @author glebrahimzanov
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/task_manager";
    private static final String USER = "task_user";
    private static final String PASSWORD = "123hard123pass";

    public static Connection getConnection() throws SQLException {
        System.out.println("Attempting to connect to database...");  // Отладочное сообщение
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connection established.");  // Отладочное сообщение
        return connection;
    }
}


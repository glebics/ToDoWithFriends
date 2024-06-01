package glebrahimzhanov.todowithfriends;

import java.sql.Connection;
import java.sql.SQLException;

public class ToDoWithFriends {

    public static void main(String[] args) {
        System.out.println("Starting application...");  // Отладочное сообщение
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

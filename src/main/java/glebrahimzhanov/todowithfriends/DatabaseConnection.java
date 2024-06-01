package glebrahimzhanov.todowithfriends;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/task_manager"; // Замените на ваше имя базы данных
    private static final String USER = "postgres"; // Имя суперпользователя
    private static final String PASSWORD = "new_password"; // Новый пароль суперпользователя

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

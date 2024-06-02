package glebrahimzhanov.todowithfriends;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
    public static void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Создание таблицы users, если она не существует
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                    + "id SERIAL PRIMARY KEY,"
                    + "username VARCHAR(255) NOT NULL UNIQUE,"
                    + "password VARCHAR(255) NOT NULL"
                    + ")";
            stmt.execute(createUsersTable);

            // Создание таблицы tasks, если она не существует
            String createTasksTable = "CREATE TABLE IF NOT EXISTS tasks ("
                    + "id SERIAL PRIMARY KEY,"
                    + "name VARCHAR(255) NOT NULL,"
                    + "description TEXT,"
                    + "user_id INTEGER NOT NULL,"
                    + "completed BOOLEAN NOT NULL DEFAULT FALSE,"
                    + "FOREIGN KEY (user_id) REFERENCES users(id)"
                    + ")";
            stmt.execute(createTasksTable);

            // Создание таблицы friends, если она не существует
            String createFriendsTable = "CREATE TABLE IF NOT EXISTS friends ("
                    + "user_id INTEGER NOT NULL,"
                    + "friend_id INTEGER NOT NULL,"
                    + "PRIMARY KEY (user_id, friend_id),"
                    + "FOREIGN KEY (user_id) REFERENCES users(id),"
                    + "FOREIGN KEY (friend_id) REFERENCES users(id)"
                    + ")";
            stmt.execute(createFriendsTable);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

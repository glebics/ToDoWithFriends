package glebrahimzhanov.todowithfriends;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    public static void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getConnection(); 
             Statement stmt = conn.createStatement()) {

            System.out.println("Connected to the database.");

            // Создание таблицы пользователей
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id SERIAL PRIMARY KEY, " +
                    "username VARCHAR(255) NOT NULL UNIQUE, " +
                    "password VARCHAR(255) NOT NULL)";
            stmt.execute(createUsersTable);

            System.out.println("Users table created or already exists.");

            // Создание таблицы задач
            String createTasksTable = "CREATE TABLE IF NOT EXISTS tasks (\n" +
                    "    id SERIAL PRIMARY KEY,\n" +
                    "    name VARCHAR(255) NOT NULL,\n" +
                    "    description TEXT,\n" +
                    "    completed BOOLEAN DEFAULT FALSE,\n" +
                    "    user_id INT REFERENCES users(id)\n" +
                    ");";
            stmt.execute(createTasksTable);

            System.out.println("Tasks table created or already exists.");

            // Создание таблицы друзей
            String createFriendsTable = "CREATE TABLE IF NOT EXISTS friends (\n" +
                    "    id SERIAL PRIMARY KEY,\n" +
                    "    user_id INT REFERENCES users(id),\n" +
                    "    friend_id INT REFERENCES users(id)\n" +
                    ");";
            stmt.execute(createFriendsTable);

            System.out.println("Friends table created or already exists.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

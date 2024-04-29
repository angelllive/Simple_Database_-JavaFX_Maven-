package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteInitializer {

    public static void initializeDatabase() {
        String url = "jdbc:sqlite:staff.db";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            // Создание таблицы Staff
            statement.execute("CREATE TABLE IF NOT EXISTS Staff ("
                    + "id TEXT PRIMARY KEY,"
                    + "lastName TEXT,"
                    + "firstName TEXT,"
                    + "mi TEXT,"
                    + "address TEXT,"
                    + "city TEXT,"
                    + "state TEXT,"
                    + "telephone TEXT,"
                    + "email TEXT)");

            System.out.println("Таблица Staff создана успешно.");

        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }
}

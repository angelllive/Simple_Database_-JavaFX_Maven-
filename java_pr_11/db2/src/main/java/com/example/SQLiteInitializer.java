package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteInitializer {

    private static final String URL = "jdbc:sqlite:students.db";

    public static void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement()) {

            // Создание таблицы Student
            statement.execute("CREATE TABLE IF NOT EXISTS Student (" +
                    "studentId TEXT, " +
                    "deptId TEXT, " +
                    "firstName TEXT, " +
                    "lastName TEXT, " +
                    "email TEXT)");

            System.out.println("Таблица Student создана успешно.");

        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    // Добавляем метод для удаления таблицы
    public static void dropTable() {
        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement()) {

            // Удаление таблицы Student
            statement.execute("DROP TABLE IF EXISTS Student");

            System.out.println("Таблица Student удалена успешно.");

        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы: " + e.getMessage());
        }
    }
}

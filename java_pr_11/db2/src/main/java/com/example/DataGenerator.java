package com.example;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class DataGenerator {

    public static final String URL = "jdbc:sqlite:students.db";

    public static void deleteExistingDatabase() {
        try {
            boolean deleted = new File("students.db").delete();
            if (!deleted) {
                System.err.println("Не удалось удалить существующий файл базы данных.");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при удалении файла базы данных: " + e.getMessage());
        }
    }

    public static void createDatabase() {
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement statement = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS Student (" +
                             "studentId TEXT, " +
                             "deptId TEXT, " +
                             "firstName TEXT, " +
                             "lastName TEXT, " +
                             "email TEXT)")) {
            statement.executeUpdate();
            System.out.println("Таблица Student создана успешно.");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    public static void generateRandomData() {
        Random random = new Random();
        String[] departments = { "AA", "BB", "CC", "DD", "EE" };
        String[] firstNames = { "Дмитрий", "Дастан", "Максим", "Ксения", "Амир", "Мансур" };
        String[] lastNames = { "Тороян", "Кожабеков", "Журба", "Семко", "Смагул", "Азатбек" };
        String[] emails = { "dmitriy@example.com", "dastan@example.com", "maksim@example.com", "kseniya@example.com",
                "amir@example.com", "mansur@example.com" };

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement insertStatement = connection.prepareStatement(
                     "INSERT INTO Student (studentId, deptId, firstName, lastName, email) VALUES (?, ?, ?, ?, ?)")) {

            connection.setAutoCommit(false); // Установка автоматической фиксации транзакции в режим "false"

            for (int i = 1; i <= 50; i++) {
                String studentId = "S" + i;
                String deptId = departments[random.nextInt(departments.length)];
                String firstName = firstNames[random.nextInt(firstNames.length)];
                String lastName = lastNames[random.nextInt(lastNames.length)];
                String email = emails[random.nextInt(emails.length)];

                insertStatement.setString(1, studentId);
                insertStatement.setString(2, deptId);
                insertStatement.setString(3, firstName);
                insertStatement.setString(4, lastName);
                insertStatement.setString(5, email);
                insertStatement.addBatch(); // Добавление запроса в пакет

                if (i % 10 == 0) {
                    insertStatement.executeBatch(); // Выполнение пакета каждые 10 записей
                    connection.commit(); // Фиксация транзакции
                }
            }

            insertStatement.executeBatch(); // Выполнение оставшихся запросов в пакете
            connection.commit(); // Фиксация оставшейся части транзакции

            System.out.println("Данные студентов сгенерированы и добавлены в базу данных.");

        } catch (SQLException e) {
            System.err.println("Ошибка при работе с базой данных: " + e.getMessage());
        }
    }
}

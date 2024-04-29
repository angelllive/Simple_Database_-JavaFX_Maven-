package com.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class App extends Application {

    private static final String URL = "jdbc:sqlite:students.db";

    @Override
    public void start(Stage primaryStage) throws Exception {
        SQLiteInitializer.initializeDatabase(); // Вызов метода для инициализации базы данных
        DataGenerator.generateRandomData(); // Генерация случайных данных

        VBox root = new VBox();

        CategoryAxis barXAxis = new CategoryAxis();
        NumberAxis barYAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(barXAxis, barYAxis);
        barChart.setTitle("Student Counts per Department");

        PieChart pieChart = new PieChart();
        pieChart.setTitle("Student Distribution by Department");

        // Fetch data from the database and populate both charts
        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement()) {
            String sql = "SELECT deptId, COUNT(*) AS studentCount " +
                    "FROM Student " +
                    "WHERE deptId IS NOT NULL " +
                    "GROUP BY deptId";
            ResultSet resultSet = statement.executeQuery(sql);

            XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String deptId = resultSet.getString("deptId");
                int studentCount = resultSet.getInt("studentCount");

                barSeries.getData().add(new XYChart.Data<>(deptId, studentCount));

                pieChartData.add(new PieChart.Data(deptId, studentCount));
            }

            barChart.getData().add(barSeries);

            pieChart.setData(pieChartData);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        root.getChildren().addAll(pieChart, barChart);

        primaryStage.setScene(new Scene(root, 1920, 1080));
        primaryStage.setTitle("Student Department Visualization");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

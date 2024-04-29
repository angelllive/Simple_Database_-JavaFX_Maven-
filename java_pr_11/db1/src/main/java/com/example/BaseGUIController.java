package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseGUIController {

    @FXML
    private TextField idField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField miField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField stateField;

    @FXML
    private TextField telephoneField;

    @FXML
    private TextField emailField;

    private Connection connection;

    public BaseGUIController() {
        String url = "jdbc:sqlite:staff.db";
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void viewRecord() {
        String id = idField.getText();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Staff WHERE id = ?");
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                lastNameField.setText(resultSet.getString("lastName"));
                firstNameField.setText(resultSet.getString("firstName"));
                miField.setText(resultSet.getString("mi"));
                addressField.setText(resultSet.getString("address"));
                cityField.setText(resultSet.getString("city"));
                stateField.setText(resultSet.getString("state"));
                telephoneField.setText(resultSet.getString("telephone"));
                emailField.setText(resultSet.getString("email"));
            } else {
                clearFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void insertRecord() {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Staff (id, lastName, firstName, mi, address, city, state, telephone, email) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, idField.getText());
            statement.setString(2, lastNameField.getText());
            statement.setString(3, firstNameField.getText());
            statement.setString(4, miField.getText());
            statement.setString(5, addressField.getText());
            statement.setString(6, cityField.getText());
            statement.setString(7, stateField.getText());
            statement.setString(8, telephoneField.getText());
            statement.setString(9, emailField.getText());

            statement.executeUpdate();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateRecord() {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Staff SET lastName=?, firstName=?, mi=?, address=?, city=?, state=?, telephone=?, email=? "
                            +
                            "WHERE id=?");
            statement.setString(1, lastNameField.getText());
            statement.setString(2, firstNameField.getText());
            statement.setString(3, miField.getText());
            statement.setString(4, addressField.getText());
            statement.setString(5, cityField.getText());
            statement.setString(6, stateField.getText());
            statement.setString(7, telephoneField.getText());
            statement.setString(8, emailField.getText());
            statement.setString(9, idField.getText());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clearFields() {
        idField.clear();
        lastNameField.clear();
        firstNameField.clear();
        miField.clear();
        addressField.clear();
        cityField.clear();
        stateField.clear();
        telephoneField.clear();
        emailField.clear();
    }
}

package glebrahimzhanov.todowithfriends.controller;

import dataaccessobject.UserDAOImpl;
import models.User;
import glebrahimzhanov.todowithfriends.MainApp;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginController extends VBox {
    private TextField usernameField;
    private PasswordField passwordField;
    private UserDAOImpl userDAO;

    public LoginController() {
        setSpacing(10);
        setStyle("-fx-padding: 20;");

        userDAO = new UserDAOImpl();

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(this::handleLoginButton);

        Button registerButton = new Button("Register");
        registerButton.setOnAction(this::handleRegisterButton);

        getChildren().addAll(usernameField, passwordField, loginButton, registerButton);
    }

    private void handleLoginButton(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println("Attempting to login with username: " + username);

        User user = userDAO.login(username, password);
        if (user != null) {
            System.out.println("Login successful for user: " + user.getUsername());
            MainApp.setCurrentUser(user);
            MainApp.showMainView();
        } else {
            System.out.println("Login failed for username: " + username);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password.");
            alert.showAndWait();
        }
    }

    private void handleRegisterButton(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println("Attempting to register with username: " + username);

        boolean success = userDAO.register(username, password);
        if (success) {
            System.out.println("Registration successful for username: " + username);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration Successful");
            alert.setHeaderText(null);
            alert.setContentText("Registration completed. You can now log in.");
            alert.showAndWait();
        } else {
            System.out.println("Registration failed for username: " + username);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Error");
            alert.setHeaderText(null);
            alert.setContentText("Username already exists.");
            alert.showAndWait();
        }
    }
}

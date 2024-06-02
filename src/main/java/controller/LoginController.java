package controller;

import dataaccessobject.UserDAOImpl;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import models.User;
import glebrahimzhanov.todowithfriends.MainApp;

public class LoginController extends VBox {
    private TextField usernameField;
    private PasswordField passwordField;
    private UserDAOImpl userDAO;

    public LoginController() {
        userDAO = new UserDAOImpl();
        
        setSpacing(10);
        setStyle("-fx-padding: 20;");

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

        User user = userDAO.login(username, password);
        if (user != null) {
            System.out.println("User found: " + user.getUsername());
            MainApp.setCurrentUser(user);
            MainApp.showMainView();
        } else {
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

        boolean success = userDAO.register(username, password);
        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration Successful");
            alert.setHeaderText(null);
            alert.setContentText("Registration completed. You can now log in.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Error");
            alert.setHeaderText(null);
            alert.setContentText("Username already exists.");
            alert.showAndWait();
        }
    }
}

package glebrahimzhanov.todowithfriends;

import controller.FriendTasksController;
import controller.LoginController;
import controller.MainController;
import models.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static Stage primaryStage;
    private static User currentUser;

    @Override
    public void start(Stage primaryStage) {
        MainApp.primaryStage = primaryStage;

        // Инициализация базы данных
        DatabaseSetup.initializeDatabase();

        showLoginView();
    }

    public static void showLoginView() {
        LoginController loginController = new LoginController();
        Scene scene = new Scene(loginController, 300, 200);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showMainView() {
        MainController mainController = new MainController(currentUser);
        Scene scene = new Scene(mainController, 800, 600);
        primaryStage.setTitle("ToDoWithFriends");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showFriendTasksView(User friend) {
        FriendTasksController friendTasksController = new FriendTasksController(friend);
        Scene scene = new Scene(friendTasksController, 800, 600);
        primaryStage.setTitle(friend.getUsername() + "'s Tasks");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showFriendsView() {
        MainController mainController = new MainController(currentUser);
        mainController.showFriendsPane(null);
        Scene scene = new Scene(mainController, 800, 600);
        primaryStage.setTitle("Friends");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static User getCurrentUser() {
        System.out.println("Getting current user: " + (currentUser != null ? currentUser.getUsername() : "null"));
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        System.out.println("Setting current user: " + (user != null ? user.getUsername() : "null"));
        currentUser = user;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

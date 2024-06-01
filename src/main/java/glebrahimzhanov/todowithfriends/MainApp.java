package glebrahimzhanov.todowithfriends;

import controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        MainApp.primaryStage = primaryStage;
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
        MainController mainController = new MainController();
        Scene scene = new Scene(mainController, 800, 600);
        primaryStage.setTitle("ToDoWithFriends");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

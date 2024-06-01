package glebrahimzhanov.todowithfriends;

import controller.FriendsController;
import controller.TasksController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Создаем меню
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(e -> System.exit(0));
        fileMenu.getItems().add(exitMenuItem);
        menuBar.getMenus().add(fileMenu);

        // Создаем вкладки
        TabPane tabPane = new TabPane();

        Tab tasksTab = new Tab("Tasks");
        TasksController tasksPane = new TasksController();
        tasksTab.setContent(tasksPane);
        tabPane.getTabs().add(tasksTab);

        Tab friendsTab = new Tab("Friends");
        FriendsController friendsPane = new FriendsController();
        friendsTab.setContent(friendsPane);
        tabPane.getTabs().add(friendsTab);

        // Настройка основного макета
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(tabPane);

        // Создаем сцену и показываем ее
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("ToDoWithFriends");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

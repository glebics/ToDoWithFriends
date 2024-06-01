package controller;

import glebrahimzhanov.todowithfriends.MainApp;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class TopMenuBar extends HBox {
    public TopMenuBar() {
        super(10);
        setStyle("-fx-padding: 10;");

        Button tasksButton = new Button("Tasks");
        tasksButton.setOnAction(this::showTasksView);

        Button friendsButton = new Button("Friends");
        friendsButton.setOnAction(this::showFriendsView);

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(this::handleLogoutButton);

        getChildren().addAll(tasksButton, friendsButton, logoutButton);
    }

    private void showTasksView(ActionEvent event) {
        TasksController tasksController = new TasksController();
        MainApp.getPrimaryStage().getScene().setRoot(tasksController);
    }

    private void showFriendsView(ActionEvent event) {
        FriendsController friendsController = new FriendsController();
        MainApp.getPrimaryStage().getScene().setRoot(friendsController);
    }

    private void handleLogoutButton(ActionEvent event) {
        MainApp.setCurrentUser(null);
        MainApp.showLoginView();
    }
}

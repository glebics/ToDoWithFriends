package controller;

import dataaccessobject.FriendDAO;
import dataaccessobject.UserDAOImpl;
import glebrahimzhanov.todowithfriends.MainApp;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.User;

import java.util.List;

public class FriendsController extends VBox {
    private VBox friendsContainer;
    private TextField friendUsernameField;

    public FriendsController() {
        setSpacing(10);
        setStyle("-fx-padding: 20;");

        friendUsernameField = new TextField();
        friendUsernameField.setPromptText("Enter friend's username");
        Button addFriendButton = new Button("Add Friend");
        addFriendButton.setOnAction(this::handleAddFriendButton);

        friendsContainer = new VBox(10);

        getChildren().addAll(createHeader(), friendUsernameField, addFriendButton, friendsContainer);

        loadFriends();
    }

    private Label createHeader() {
        Label header = new Label("Friends");
        header.setFont(javafx.scene.text.Font.font("Arial", 24));
        header.setStyle("-fx-text-fill: #333;");
        return header;
    }

    private void handleAddFriendButton(javafx.event.ActionEvent event) {
        String friendUsername = friendUsernameField.getText();
        User friend = new UserDAOImpl().getUserByUsername(friendUsername);
        if (friend != null) {
            FriendDAO.addFriend(MainApp.getCurrentUser().getId(), friend.getId());
            loadFriends();
        } else {
            // Show error message
            System.out.println("User not found");
        }
    }

    private void loadFriends() {
        friendsContainer.getChildren().clear();
        List<User> friends = FriendDAO.getFriends(MainApp.getCurrentUser().getId());
        for (User friend : friends) {
            friendsContainer.getChildren().add(createFriendPane(friend));
        }
    }

    private HBox createFriendPane(User friend) {
        HBox friendPane = new HBox(10);
        friendPane.setPadding(new Insets(10));
        friendPane.setStyle("-fx-border-color: black; -fx-border-radius: 10; -fx-background-color: #EEE; -fx-background-radius: 10;");
        friendPane.setPrefHeight(50);

        Label friendLabel = new Label(friend.getUsername());

        Button viewTasksButton = new Button("View Tasks");
        viewTasksButton.setOnAction(e -> MainApp.showFriendTasksView(friend));

        HBox.setHgrow(friendLabel, Priority.ALWAYS);
        friendPane.getChildren().addAll(friendLabel, viewTasksButton);
        return friendPane;
    }
}

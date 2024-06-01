package controller;

import dataaccessobject.FriendDAO;
import dataaccessobject.UserDAO;
import dataaccessobject.UserDAOImpl;
import glebrahimzhanov.todowithfriends.MainApp;
import models.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import java.util.List;

public class FriendsController extends AnchorPane {
    private VBox friendsContainer;
    private TextField friendUsernameField;
    private UserDAO userDAO;

    public FriendsController() {
        userDAO = new UserDAOImpl();
        TopMenuBar topMenuBar = new TopMenuBar();

        friendsContainer = new VBox(10);
        friendsContainer.setStyle("-fx-padding: 10;");

        friendUsernameField = new TextField();
        friendUsernameField.setPromptText("Enter friend's username");

        Button addFriendButton = new Button("Add Friend");
        addFriendButton.setOnAction(this::handleAddFriendButton);

        VBox content = new VBox(10, topMenuBar, friendUsernameField, addFriendButton, friendsContainer);
        content.setStyle("-fx-padding: 10;");
        this.getChildren().add(content);
        AnchorPane.setTopAnchor(content, 0.0);
        AnchorPane.setLeftAnchor(content, 0.0);
        AnchorPane.setRightAnchor(content, 0.0);
        AnchorPane.setBottomAnchor(content, 0.0);

        loadFriends();
    }

    private void handleAddFriendButton(ActionEvent event) {
        String friendUsername = friendUsernameField.getText();
        User friend = userDAO.getUserByUsername(friendUsername);
        if (friend != null) {
            FriendDAO.addFriend(MainApp.getCurrentUser().getId(), friend.getId());
            loadFriends();
        } else {
            // Show error message
            System.out.println("User not found");
        }
    }

    public void loadFriends() {
        friendsContainer.getChildren().clear();
        List<User> friends = FriendDAO.getFriends(MainApp.getCurrentUser().getId());
        for (User friend : friends) {
            friendsContainer.getChildren().add(createFriendPane(friend));
        }
    }

    private HBox createFriendPane(User friend) {
        HBox friendPane = new HBox(10);
        friendPane.setPadding(new Insets(10));
        friendPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));

        Label friendLabel = new Label(friend.getUsername());

        Button viewTasksButton = new Button("View Tasks");
        viewTasksButton.setOnAction(e -> MainApp.showFriendTasksView(friend));

        friendPane.getChildren().addAll(friendLabel, viewTasksButton);
        return friendPane;
    }
}

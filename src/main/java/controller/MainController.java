package glebrahimzhanov.todowithfriends.controller;

import dataaccessobject.FriendDAO;
import dataaccessobject.TaskDAO;
import dataaccessobject.UserDAOImpl;
import glebrahimzhanov.todowithfriends.MainApp;
import models.Task;
import models.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import java.util.List;

public class MainController extends AnchorPane {
    private VBox tasksPane;
    private VBox friendsPane;
    private VBox tasksContainer;
    private VBox friendsContainer;
    private TextField friendUsernameField;

    private UserDAOImpl userDAO;

    public MainController() {
        userDAO = new UserDAOImpl();

        HBox topMenu = new HBox(10);
        topMenu.setStyle("-fx-padding: 10;");

        Button tasksButton = new Button("Tasks");
        tasksButton.setOnAction(this::showTasksPane);

        Button friendsButton = new Button("Friends");
        friendsButton.setOnAction(this::showFriendsPane);

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(this::handleLogoutButton);

        topMenu.getChildren().addAll(tasksButton, friendsButton, logoutButton);

        tasksContainer = new VBox(10);
        tasksContainer.setStyle("-fx-padding: 10;");

        friendsContainer = new VBox(10);
        friendsContainer.setStyle("-fx-padding: 10;");

        tasksPane = new VBox(10, tasksContainer);
        tasksPane.setStyle("-fx-padding: 10;");
        tasksPane.setVisible(true);

        friendUsernameField = new TextField();
        friendUsernameField.setPromptText("Enter friend's username");
        Button addFriendButton = new Button("Add Friend");
        addFriendButton.setOnAction(this::handleAddFriendButton);

        friendsPane = new VBox(10, friendUsernameField, addFriendButton, friendsContainer);
        friendsPane.setStyle("-fx-padding: 10;");
        friendsPane.setVisible(false);

        VBox content = new VBox(10, topMenu, tasksPane, friendsPane);
        content.setStyle("-fx-padding: 10;");
        this.getChildren().add(content);
        AnchorPane.setTopAnchor(content, 0.0);
        AnchorPane.setLeftAnchor(content, 0.0);
        AnchorPane.setRightAnchor(content, 0.0);
        AnchorPane.setBottomAnchor(content, 0.0);

        loadTasks();
        loadFriends();
    }

    private void showTasksPane(ActionEvent event) {
        tasksPane.setVisible(true);
        friendsPane.setVisible(false);
    }

    private void showFriendsPane(ActionEvent event) {
        tasksPane.setVisible(false);
        friendsPane.setVisible(true);
    }

    private void handleLogoutButton(ActionEvent event) {
        MainApp.setCurrentUser(null);
        MainApp.showLoginView();
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

    private void loadTasks() {
        tasksContainer.getChildren().clear();
        List<Task> tasks = TaskDAO.getTasksByUserId(MainApp.getCurrentUser().getId());
        for (Task task : tasks) {
            tasksContainer.getChildren().add(createTaskPane(task));
        }
    }

    private void loadFriends() {
        friendsContainer.getChildren().clear();
        List<User> friends = FriendDAO.getFriends(MainApp.getCurrentUser().getId());
        for (User friend : friends) {
            friendsContainer.getChildren().add(createFriendPane(friend));
        }
    }

    private HBox createTaskPane(Task task) {
        HBox taskPane = new HBox(10);
        taskPane.setPadding(new Insets(10));
        taskPane.setStyle("-fx-border-color: black; -fx-border-radius: 5;");

        CheckBox completedCheckBox = new CheckBox();
        completedCheckBox.setSelected(task.isCompleted());
        completedCheckBox.setOnAction(e -> {
            task.setCompleted(completedCheckBox.isSelected());
            TaskDAO.updateTask(task);
            updateTaskStyle(taskPane, task);
        });

        Label taskLabel = new Label(task.getName() + ": " + task.getDescription());
        updateTaskStyle(taskPane, task);

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            TaskDAO.deleteTask(task.getId());
            loadTasks();
        });

        taskPane.getChildren().addAll(completedCheckBox, taskLabel, deleteButton);
        return taskPane;
    }

    private void updateTaskStyle(HBox taskPane, Task task) {
        if (task.isCompleted()) {
            taskPane.setStyle("-fx-background-color: lightgray; -fx-text-fill: gray; -fx-text-decoration: line-through;");
        } else {
            taskPane.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        }
    }

    private HBox createFriendPane(User friend) {
        HBox friendPane = new HBox(10);
        friendPane.setPadding(new Insets(10));
        friendPane.setStyle("-fx-border-color: black; -fx-border-radius: 5;");

        Label friendLabel = new Label(friend.getUsername());

        Button viewTasksButton = new Button("View Tasks");
        viewTasksButton.setOnAction(e -> MainApp.showFriendTasksView(friend));

        friendPane.getChildren().addAll(friendLabel, viewTasksButton);
        return friendPane;
    }
}

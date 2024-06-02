package controller;

import dataaccessobject.FriendDAO;
import dataaccessobject.TaskDAO;
import dataaccessobject.UserDAO;
import dataaccessobject.UserDAOImpl;
import glebrahimzhanov.todowithfriends.MainApp;
import models.Task;
import models.User;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class MainController extends AnchorPane implements TaskHandler {
    private VBox tasksPane;
    private VBox friendsPane;
    private VBox tasksContainer;
    private VBox friendsContainer;
    private TextField friendUsernameField;

    private UserDAO userDAO;

    public MainController(User currentUser) {
        userDAO = new UserDAOImpl();

        VBox sideMenu = new VBox(20);
        sideMenu.setStyle("-fx-background-color: linear-gradient(to bottom, #333, #444); -fx-padding: 20;");
        sideMenu.setPadding(new Insets(20));
        sideMenu.setPrefWidth(200);

        Button tasksButton = createSideMenuButton("Tasks");
        tasksButton.setOnAction(this::showTasksPane);

        Button friendsButton = createSideMenuButton("Friends");
        friendsButton.setOnAction(this::showFriendsPane);

        Button logoutButton = createSideMenuButton("Logout");
        logoutButton.setOnAction(this::handleLogoutButton);

        sideMenu.getChildren().addAll(tasksButton, friendsButton, logoutButton);
        VBox.setMargin(tasksButton, new Insets(10, 0, 0, 0));
        VBox.setMargin(friendsButton, new Insets(10, 0, 0, 0));
        VBox.setMargin(logoutButton, new Insets(10, 0, 0, 0));

        tasksContainer = new VBox(10);
        tasksContainer.setStyle("-fx-padding: 10;");

        Button createTaskButton = new Button("Create Task");
        createTaskButton.setStyle("-fx-background-color: linear-gradient(to bottom, #333, #444); -fx-text-fill: white; -fx-cursor: hand;");
        createTaskButton.setMaxWidth(Double.MAX_VALUE);
        createTaskButton.setOnAction(this::handleCreateTaskButton);

        tasksPane = new VBox(10, createHeader("My Tasks"), createTaskButton, tasksContainer);
        tasksPane.setStyle("-fx-padding: 20;");
        tasksPane.setVisible(true);
        VBox.setVgrow(tasksContainer, Priority.ALWAYS);

        friendUsernameField = new TextField();
        friendUsernameField.setPromptText("Enter friend's username");
        Button addFriendButton = new Button("Add Friend");
        addFriendButton.setOnAction(this::handleAddFriendButton);

        friendsContainer = new VBox(10);
        friendsPane = new VBox(10, createHeader("Friends"), friendUsernameField, addFriendButton, friendsContainer);
        friendsPane.setStyle("-fx-padding: 20;");
        friendsPane.setVisible(false);
        VBox.setVgrow(friendsContainer, Priority.ALWAYS);

        HBox mainContent = new HBox(10, sideMenu, tasksPane, friendsPane);
        mainContent.setStyle("-fx-padding: 10;");
        HBox.setHgrow(tasksPane, Priority.ALWAYS);
        HBox.setHgrow(friendsPane, Priority.ALWAYS);
        this.getChildren().add(mainContent);
        AnchorPane.setTopAnchor(mainContent, 0.0);
        AnchorPane.setLeftAnchor(mainContent, 0.0);
        AnchorPane.setRightAnchor(mainContent, 0.0);
        AnchorPane.setBottomAnchor(mainContent, 0.0);

        loadTasks();
        loadFriends();
    }

    private Button createSideMenuButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(160);
        button.setFont(Font.font("Arial", 16));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #555; -fx-cursor: hand;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-cursor: hand;"));
        return button;
    }

    private Label createHeader(String text) {
        Label header = new Label(text);
        header.setFont(Font.font("Arial", 24));
        header.setStyle("-fx-text-fill: #333;");
        return header;
    }

    public void showTasksPane(ActionEvent event) {
        tasksPane.setVisible(true);
        friendsPane.setVisible(false);
    }

    public void showFriendsPane(ActionEvent event) {
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

    private void handleCreateTaskButton(ActionEvent event) {
        CreateTaskDialog dialog = new CreateTaskDialog(this);
        dialog.show();
    }

    @Override
    public void loadTasks() {
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
        taskPane.setStyle("-fx-border-color: black; -fx-border-radius: 10; -fx-background-color: #EEE; -fx-background-radius: 10;");
        taskPane.setPrefHeight(50);
        taskPane.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(taskPane, Priority.ALWAYS);

        Label taskLabel = new Label(task.getName() + ": " + task.getDescription());
        updateTaskStyle(taskPane, task);

        CheckBox completedCheckBox = new CheckBox();
        completedCheckBox.setSelected(task.isCompleted());
        completedCheckBox.setOnAction(e -> {
            task.setCompleted(completedCheckBox.isSelected());
            TaskDAO.updateTask(task);
            updateTaskStyle(taskPane, task);
        });

        Button deleteButton = new Button("✖");
        deleteButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-cursor: hand;");
        deleteButton.setOnAction(e -> {
            TaskDAO.deleteTask(task.getId());
            loadTasks();
        });

        HBox.setHgrow(taskLabel, Priority.ALWAYS);
        taskPane.getChildren().addAll(taskLabel, completedCheckBox, deleteButton);
        HBox.setMargin(taskLabel, new Insets(0, 10, 0, 0));
        HBox.setMargin(completedCheckBox, new Insets(0, 10, 0, 0));
        HBox.setMargin(deleteButton, new Insets(0, 10, 0, 0));
        return taskPane;
    }

    private void updateTaskStyle(HBox taskPane, Task task) {
        if (task.isCompleted()) {
            taskPane.setStyle("-fx-background-color: lightgray; -fx-text-fill: gray; -fx-text-decoration: line-through; -fx-border-radius: 10; -fx-background-radius: 10;");
        } else {
            taskPane.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-radius: 10; -fx-background-radius: 10;");
        }
    }

    private HBox createFriendPane(User friend) {
        HBox friendPane = new HBox(10);
        friendPane.setPadding(new Insets(10));
        friendPane.setStyle("-fx-border-color: black; -fx-border-radius: 10; -fx-background-color: #EEE; -fx-background-radius: 10;");
        friendPane.setPrefHeight(50);
        friendPane.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(friendPane, Priority.ALWAYS);

        Label friendLabel = new Label(friend.getUsername());

        Button viewTasksButton = new Button("View Tasks");
        viewTasksButton.setOnAction(e -> MainApp.showFriendTasksView(friend));

        HBox.setHgrow(friendLabel, Priority.ALWAYS);
        friendPane.getChildren().addAll(friendLabel, viewTasksButton);
        return friendPane;
    }
}

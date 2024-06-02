package controller;

import dataaccessobject.TaskDAO;
import models.Task;
import models.User;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import glebrahimzhanov.todowithfriends.MainApp;

import java.util.List;

public class FriendTasksController extends VBox {
    private VBox tasksContainer;
    private User friend;

    public FriendTasksController(User friend) {
        this.friend = friend;
        setSpacing(10);
        setStyle("-fx-padding: 20;");

        Label header = new Label(friend.getUsername() + "'s Tasks");
        header.setFont(Font.font("Arial", 24));
        header.setTextFill(Color.BLACK);

        tasksContainer = new VBox(10);

        Button backButton = new Button("Back");
        backButton.setOnAction(this::handleBackButton);

        getChildren().addAll(backButton, header, tasksContainer);

        loadTasks();
    }

    private void handleBackButton(ActionEvent event) {
        MainApp.showMainView();
    }

    private void loadTasks() {
        tasksContainer.getChildren().clear();
        List<Task> tasks = TaskDAO.getTasksByUserId(friend.getId());
        for (Task task : tasks) {
            tasksContainer.getChildren().add(createTaskPane(task));
        }
    }

    private HBox createTaskPane(Task task) {
        HBox taskPane = new HBox(10);
        taskPane.setPadding(new Insets(10));
        taskPane.setStyle("-fx-border-color: black; -fx-border-radius: 5; -fx-background-color: #EEE;");
        taskPane.setPrefHeight(50);

        CheckBox completedCheckBox = new CheckBox();
        completedCheckBox.setSelected(task.isCompleted());
        completedCheckBox.setDisable(true);

        Label taskLabel = new Label(task.getName() + ": " + task.getDescription());
        updateTaskStyle(taskPane, task);

        taskPane.getChildren().addAll(completedCheckBox, taskLabel);
        return taskPane;
    }

    private void updateTaskStyle(HBox taskPane, Task task) {
        if (task.isCompleted()) {
            taskPane.setStyle("-fx-background-color: lightgray; -fx-text-fill: gray; -fx-text-decoration: line-through;");
        } else {
            taskPane.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        }
    }
}

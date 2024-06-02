package controller;

import dataaccessobject.TaskDAO;
import glebrahimzhanov.todowithfriends.MainApp;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Task;
import models.User;

import java.util.List;

public class TasksController extends VBox implements TaskHandler {
    private VBox tasksContainer;
    private User currentUser;

    public TasksController() {
        this(MainApp.getCurrentUser());
    }

    public TasksController(User user) {
        this.currentUser = user;
        if (currentUser == null) {
            System.out.println("Error: current user is null");
            return;
        }
        setSpacing(10);
        setStyle("-fx-padding: 20;");

        Button createTaskButton = new Button("Create Task");
        createTaskButton.setStyle("-fx-background-color: linear-gradient(to bottom, #333, #444); -fx-text-fill: white; -fx-cursor: hand;");
        createTaskButton.setPrefWidth(Double.MAX_VALUE);
        createTaskButton.setOnAction(this::handleCreateTaskButton);

        tasksContainer = new VBox(10);

        getChildren().addAll(createHeader(), createTaskButton, tasksContainer);

        loadTasks();
    }

    private Label createHeader() {
        Label header = new Label(currentUser.getUsername() + "'s Tasks");
        header.setFont(javafx.scene.text.Font.font("Arial", 24));
        header.setStyle("-fx-text-fill: #333;");
        return header;
    }

    private void handleCreateTaskButton(ActionEvent event) {
        CreateTaskDialog dialog = new CreateTaskDialog(this);
        dialog.show();
    }

    @Override
    public void loadTasks() {
        tasksContainer.getChildren().clear();
        List<Task> tasks = TaskDAO.getTasksByUserId(currentUser.getId());
        for (Task task : tasks) {
            tasksContainer.getChildren().add(createTaskPane(task));
        }
    }

    private HBox createTaskPane(Task task) {
        HBox taskPane = new HBox(10);
        taskPane.setPadding(new Insets(10));
        taskPane.setStyle("-fx-border-color: black; -fx-border-radius: 10; -fx-background-color: #EEE; -fx-background-radius: 10;");
        taskPane.setPrefHeight(50);
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
}

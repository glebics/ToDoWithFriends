package controller;

import dataaccessobject.TaskDAO;
import models.Task;
import glebrahimzhanov.todowithfriends.MainApp;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateTaskDialog extends Stage {
    private TasksController tasksController;

    public CreateTaskDialog(TasksController tasksController) {
        this.tasksController = tasksController;

        setTitle("Create Task");

        VBox vbox = new VBox(10);
        vbox.setPadding(new javafx.geometry.Insets(10));

        TextField taskNameField = new TextField();
        taskNameField.setPromptText("Task Name");

        TextArea taskDescriptionField = new TextArea();
        taskDescriptionField.setPromptText("Task Description");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String name = taskNameField.getText();
            String description = taskDescriptionField.getText();
            int userId = MainApp.getCurrentUser().getId(); // Используем ID текущего пользователя
            Task task = new Task(name, description, userId);
            TaskDAO.saveTask(task);
            tasksController.loadTasks();
            close();
        });

        vbox.getChildren().addAll(new Label("Task Name:"), taskNameField, new Label("Task Description:"), taskDescriptionField, saveButton);

        Scene scene = new Scene(vbox);
        setScene(scene);
    }
}

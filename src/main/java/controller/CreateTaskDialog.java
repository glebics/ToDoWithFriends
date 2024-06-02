package controller;

import dataaccessobject.TaskDAO;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Task;
import glebrahimzhanov.todowithfriends.MainApp;

public class CreateTaskDialog extends Stage {
    private TextField nameField;
    private TextArea descriptionField;
    private TaskHandler taskHandler;

    public CreateTaskDialog(TaskHandler taskHandler) {
        this.taskHandler = taskHandler;
        setTitle("Create Task");

        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20;");

        Label nameLabel = new Label("Task Name:");
        nameField = new TextField();

        Label descriptionLabel = new Label("Task Description:");
        descriptionField = new TextArea();
        descriptionField.setPromptText("Task Description");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(this::handleSaveButton);

        root.getChildren().addAll(nameLabel, nameField, descriptionLabel, descriptionField, saveButton);

        Scene scene = new Scene(root);
        setScene(scene);
        initModality(Modality.APPLICATION_MODAL);
    }

    private void handleSaveButton(ActionEvent event) {
        String name = nameField.getText();
        String description = descriptionField.getText();
        int userId = MainApp.getCurrentUser().getId();

        Task newTask = new Task(name, description, userId);
        TaskDAO.saveTask(newTask);

        taskHandler.loadTasks();
        close();
    }
}

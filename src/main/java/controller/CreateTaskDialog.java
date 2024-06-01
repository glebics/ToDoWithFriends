/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author glebrahimzanov
 */

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateTaskDialog extends Stage {
    public CreateTaskDialog() {
        setTitle("Create Task");

        VBox vbox = new VBox(10);
        vbox.setPadding(new javafx.geometry.Insets(10));

        TextField taskNameField = new TextField();
        taskNameField.setPromptText("Task Name");

        TextArea taskDescriptionField = new TextArea();
        taskDescriptionField.setPromptText("Task Description");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            // Код для сохранения задачи в базу данных
            close();
        });

        vbox.getChildren().addAll(new Label("Task Name:"), taskNameField, new Label("Task Description:"), taskDescriptionField, saveButton);

        Scene scene = new Scene(vbox);
        setScene(scene);
    }
}

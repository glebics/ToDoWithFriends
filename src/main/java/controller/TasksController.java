package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

public class TasksController extends AnchorPane {
    public TasksController() {
        Label tasksLabel = new Label("Tasks will be here");
        Button createTaskButton = new Button("Create Task");
        createTaskButton.setOnAction(this::showCreateTaskDialog);

        this.getChildren().addAll(tasksLabel, createTaskButton);
        AnchorPane.setTopAnchor(tasksLabel, 10.0);
        AnchorPane.setLeftAnchor(tasksLabel, 10.0);
        AnchorPane.setTopAnchor(createTaskButton, 40.0);
        AnchorPane.setLeftAnchor(createTaskButton, 10.0);
    }

    private void showCreateTaskDialog(ActionEvent event) {
        CreateTaskDialog dialog = new CreateTaskDialog();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }
}

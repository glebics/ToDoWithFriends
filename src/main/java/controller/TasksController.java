package controller;

import dataaccessobject.TaskDAO;
import models.Task;
import glebrahimzhanov.todowithfriends.MainApp;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import java.util.List;
import javafx.scene.layout.Pane;

public class TasksController extends AnchorPane {
    private VBox tasksContainer;

    public TasksController() {
        TopMenuBar topMenuBar = new TopMenuBar();

        tasksContainer = new VBox(10);
        tasksContainer.setStyle("-fx-padding: 10;");

        Button createTaskButton = new Button("Create Task");
        createTaskButton.setOnAction(this::showCreateTaskDialog);

        VBox content = new VBox(10, topMenuBar, createTaskButton, tasksContainer);
        content.setStyle("-fx-padding: 10;");
        this.getChildren().add(content);
        AnchorPane.setTopAnchor(content, 0.0);
        AnchorPane.setLeftAnchor(content, 0.0);
        AnchorPane.setRightAnchor(content, 0.0);
        AnchorPane.setBottomAnchor(content, 0.0);

        loadTasks();
    }

    private void showCreateTaskDialog(ActionEvent event) {
        CreateTaskDialog dialog = new CreateTaskDialog(this);
        dialog.showAndWait();
    }

    public void loadTasks() {
        tasksContainer.getChildren().clear();
        List<Task> tasks = TaskDAO.getTasksByUserId(MainApp.getCurrentUser().getId());
        for (Task task : tasks) {
            tasksContainer.getChildren().add(createTaskPane(task));
        }
    }

    private Pane createTaskPane(Task task) {
        HBox taskPane = new HBox(10);
        taskPane.setPadding(new Insets(10));
        taskPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderWidths.DEFAULT)));
        
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
}

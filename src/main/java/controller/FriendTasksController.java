package controller;

import dataaccessobject.TaskDAO;
import models.Task;
import models.User;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class FriendTasksController extends AnchorPane {
    private VBox tasksContainer;

    public FriendTasksController(User friend) {
        TopMenuBar topMenuBar = new TopMenuBar();

        tasksContainer = new VBox(10);
        tasksContainer.setStyle("-fx-padding: 10;");

        loadFriendTasks(friend);

        VBox content = new VBox(10, topMenuBar, tasksContainer);
        content.setStyle("-fx-padding: 10;");
        this.getChildren().add(content);
        AnchorPane.setTopAnchor(content, 0.0);
        AnchorPane.setLeftAnchor(content, 0.0);
        AnchorPane.setRightAnchor(content, 0.0);
        AnchorPane.setBottomAnchor(content, 0.0);
    }

    private void loadFriendTasks(User friend) {
        tasksContainer.getChildren().clear();
        List<Task> tasks = TaskDAO.getTasksByUserId(friend.getId());
        for (Task task : tasks) {
            Label taskLabel = new Label(task.getName() + ": " + task.getDescription());
            tasksContainer.getChildren().add(taskLabel);
        }
    }
}

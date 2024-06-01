/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author glebrahimzanov
 */
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class TasksController extends AnchorPane {
    public TasksController() {
        // Создание элементов интерфейса для задач
        Label tasksLabel = new Label("Tasks will be here");
        this.getChildren().add(tasksLabel);
        AnchorPane.setTopAnchor(tasksLabel, 10.0);
        AnchorPane.setLeftAnchor(tasksLabel, 10.0);

        // Добавление других элементов интерфейса для задач по мере необходимости
    }
}



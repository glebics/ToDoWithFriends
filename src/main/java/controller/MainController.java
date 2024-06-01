/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author glebrahimzanov
 */

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class MainController extends BorderPane {
    public MainController() {
        // Создаем меню (если нужно)
        // ...

        // Создаем вкладки
        TabPane tabPane = new TabPane();

        Tab tasksTab = new Tab("Tasks");
        TasksController tasksPane = new TasksController();
        tasksTab.setContent(tasksPane);
        tabPane.getTabs().add(tasksTab);

        Tab friendsTab = new Tab("Friends");
        FriendsController friendsPane = new FriendsController();
        friendsTab.setContent(friendsPane);
        tabPane.getTabs().add(friendsTab);

        // Настройка основного макета
        setTop(null); // Или ваше меню
        setCenter(tabPane);
    }
}


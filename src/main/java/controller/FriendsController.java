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

public class FriendsController extends AnchorPane {
    public FriendsController() {
        // Создание элементов интерфейса для друзей
        Label friendsLabel = new Label("Friends will be here");
        this.getChildren().add(friendsLabel);
        AnchorPane.setTopAnchor(friendsLabel, 10.0);
        AnchorPane.setLeftAnchor(friendsLabel, 10.0);

        // Добавление других элементов интерфейса для друзей по мере необходимости
    }
}


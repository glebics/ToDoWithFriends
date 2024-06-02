package controller;

import glebrahimzhanov.todowithfriends.MainApp;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LeftMenuBar extends VBox {
    public LeftMenuBar() {
        setSpacing(20);
        setPadding(new Insets(20));
        setStyle("-fx-background-color: linear-gradient(to bottom, #333, #444);");
        setPrefWidth(200); // Увеличенная ширина

        Button tasksButton = createMenuButton("Tasks");
        tasksButton.setOnAction(e -> MainApp.showMainView());

        Button friendsButton = createMenuButton("Friends");
        friendsButton.setOnAction(e -> MainApp.showFriendsView());

        Button logoutButton = createMenuButton("Logout");
        logoutButton.setOnAction(e -> MainApp.showLoginView());

        getChildren().addAll(tasksButton, friendsButton, logoutButton);
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", 16));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        button.setPrefWidth(160); // Увеличенная ширина кнопок
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #555; -fx-cursor: hand;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-cursor: hand;"));
        return button;
    }
}

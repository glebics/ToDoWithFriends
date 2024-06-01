package models;

public class Task {
    private int id;
    private String name;
    private String description;
    private boolean completed;
    private int userId;

    public Task(int id, String name, String description, boolean completed, int userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.completed = completed;
        this.userId = userId;
    }

    public Task(String name, String description, int userId) {
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.completed = false; // По умолчанию задача не выполнена
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public int getUserId() {
        return userId;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

package org.example.taskfe;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Task {

    private int id;
    private String title; // Matches the backend type
    private String description; // Matches the backend type
    private boolean completed; // Matches the backend type

    private transient SimpleStringProperty titleProperty;
    private transient SimpleStringProperty descriptionProperty;
    private transient BooleanProperty completedProperty;

    public Task() {
        this.titleProperty = new SimpleStringProperty();
        this.descriptionProperty = new SimpleStringProperty();
        this.completedProperty = new SimpleBooleanProperty();
    }

    public Task(int id, String title, String description, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;

        this.titleProperty = new SimpleStringProperty(title);
        this.descriptionProperty = new SimpleStringProperty(description);
        this.completedProperty = new SimpleBooleanProperty(completed);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
        if (this.titleProperty != null) {
            this.titleProperty.set(title);
        }
    }

    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
        if (this.descriptionProperty != null) {
            this.descriptionProperty.set(description);
        }
    }

    public boolean isCompleted() {
        return completed;
    }

    @JsonProperty("completed")
    public void setCompleted(boolean completed) {
        this.completed = completed;
        if (this.completedProperty != null) {
            this.completedProperty.set(completed);
        }
    }

    public SimpleStringProperty titleProperty() {
        if (titleProperty == null) {
            titleProperty = new SimpleStringProperty(title);
        }
        return titleProperty;
    }

    public SimpleStringProperty descriptionProperty() {
        if (descriptionProperty == null) {
            descriptionProperty = new SimpleStringProperty(description);
        }
        return descriptionProperty;
    }

    public BooleanProperty completedProperty() {
        if (completedProperty == null) {
            completedProperty = new SimpleBooleanProperty(completed);
        }
        return completedProperty;
    }

    public void setComplete(boolean completed) {
        this.completed = true;
    }
}

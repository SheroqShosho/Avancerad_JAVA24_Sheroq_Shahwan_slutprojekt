package org.example.taskfe;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HelloController {
    @FXML
    private TableColumn<Task, String> descriptionColum;

    @FXML
    private TextField descriptionField;

    @FXML
    private TableColumn<Task, Integer> idColumn;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, String> titleColumn;

    @FXML
    private TableColumn<Task, Boolean> completedColumn;

    @FXML
    private TextField titleField;


    private ObservableList<Task> taskList = FXCollections.observableArrayList();
    private static final String BASE_URL = "http://localhost:8080/api/tasks";

    @FXML
    void addButton(ActionEvent event) {
        try {
            boolean isCompleted = false;
            String title = titleField.getText();
            String description = descriptionField.getText();

            if (title != null && !title.isEmpty()) {
                Task task = new Task(0, title, description, isCompleted );

                ObjectMapper mapper = new ObjectMapper();
                mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
                String json = mapper.writeValueAsString(task);

                //String taskJson = String.format("{\"title\":\"%s\",\"description\":\"%s\",\"completed\":false}", title, description);
                HttpURLConnection connection = createConnection("POST", BASE_URL);
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(json.getBytes());
                    os.flush();
                }

                if (connection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                    refreshTable();
                    clearFields();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void deleteButton(ActionEvent event) {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            try {
                HttpURLConnection connection = createConnection("DELETE", BASE_URL + "/" + selectedTask.getId());
                if (connection.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT){
                    refreshTable();
                    clearFields();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void updateButton(ActionEvent event) {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            try {
                String updatedJson = String.format("{\"title\":\"%s\",\"description\":\"%s\",\"completed\":%b}",
                        titleField.getText(),
                        descriptionField.getText(),
                        selectedTask.isCompleted());
                HttpURLConnection connection = createConnection("PUT", BASE_URL + "/" + selectedTask.getId());
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(updatedJson.getBytes());
                    os.flush();

                }

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    refreshTable();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        descriptionColum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        completedColumn.setCellValueFactory(cellData ->{
            Task task = cellData.getValue();
            return new SimpleBooleanProperty(task.isCompleted());
        });

        completedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(completedColumn));

        completedColumn.setOnEditCommit(event -> {
            Task task = event.getRowValue();
            task.setComplete(event.getNewValue());
        });

        taskList.add(new Task(1, "Task 1", "Description 1", false));
        taskList.add(new Task(2, "Task 2", "Description 2", true));
        taskList.add(new Task(3, "Task 3", "Description 3", false));

        taskTable.setItems(taskList);

        taskTable.getSelectionModel().selectedItemProperty().addListener((observable, oldTask, newTask) -> {
            if (newTask != null) {
                titleField.setText(newTask.getTitle());
                descriptionField.setText(newTask.getDescription());
            }
        });

        taskTable.setEditable(true);
    }

    private HttpURLConnection createConnection(String method, String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        return connection;
    }


    private void refreshTable() {
        try {
            HttpURLConnection connection = createConnection("GET", BASE_URL);
            connection.setDoOutput(false);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                List<Task> tasks = new ObjectMapper().readValue(connection.getInputStream(), new TypeReference<List<Task>>() {});
                taskList.setAll(tasks);
                taskTable.setItems(taskList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        titleField.clear();
        descriptionField.clear();
    }

    @FXML
    private void onHelloButtonAction(ActionEvent event) {
        System.out.println("Hello button clicked");
    }
}

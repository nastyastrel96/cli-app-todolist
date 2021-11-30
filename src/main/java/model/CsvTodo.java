package model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvNumber;

import java.time.LocalDateTime;

public class CsvTodo {
    @CsvBindByName
    private TaskState state;

    @CsvBindByName
    private String description;

    @CsvDate(value = "yyyy-MM-dd'T'HH:mm:ss")
    @CsvBindByName
    private LocalDateTime creationDate;

    @CsvNumber(value = "#")
    @CsvBindByName
    private int serialNumber;

    public CsvTodo() {
    }

    public CsvTodo(TaskState state, String description, LocalDateTime creationDate, int serialNumber) {
        this.state = state;
        this.description = description;
        this.creationDate = creationDate;
        this.serialNumber = serialNumber;
    }

    public static CsvTodo fromToDoItem(ToDoItem toDoItem) {
        return new CsvTodo(toDoItem.state(), toDoItem.description(), toDoItem.creationDate(), toDoItem.serialNumber());
    }

    public TaskState getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "CsvTodo{" +
                "state=" + state +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", serialNumber=" + serialNumber +
                '}';
    }
}

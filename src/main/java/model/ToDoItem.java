package model;

import java.time.LocalDateTime;

public record ToDoItem(TaskState state, String description, LocalDateTime creationDate, int serialNumber) {
}

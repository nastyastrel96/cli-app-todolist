package render;

import model.TaskState;
import model.ToDoItem;

public final class ToDoItemFormatter implements Formatter<ToDoItem> {

    @Override
    public String format(ToDoItem content) {
        return content.serialNumber() + " " + formatTaskState(content.state()) + " " + content.description() + " " + content.creationDate();
    }

    private String formatTaskState(TaskState taskState) {
        return switch (taskState) {
            case DONE -> "✔";
            case UNDONE -> "✘";
        };
    }
}

package render;

import model.ToDoItem;

import java.util.List;
import java.util.stream.Collectors;

public final class ToDoItemsFormatter implements Formatter<List<ToDoItem>>{
    private final Formatter<ToDoItem> toDoItemFormatter;

    public ToDoItemsFormatter(Formatter<ToDoItem> toDoItemFormatter) {
        this.toDoItemFormatter = toDoItemFormatter;
    }

    @Override
    public String format(List<ToDoItem> content) {
        return content.stream().map(item -> toDoItemFormatter.format(item) + "\n").collect(Collectors.joining());
    }
}

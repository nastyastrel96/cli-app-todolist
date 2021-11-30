package commands;

import model.TaskState;
import model.ToDoItem;
import render.Formatter;
import storage.ToDoItemFileRepositoryFactory;
import storage.ToDoItemRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CommandAdd {
    private final ToDoItemFileRepositoryFactory toDoItemFileRepositoryFactory;
    private final Formatter<List<ToDoItem>> itemsFormatter;

    public CommandAdd(ToDoItemFileRepositoryFactory toDoItemFileRepositoryFactory, Formatter<List<ToDoItem>> itemsFormatter) {
        this.toDoItemFileRepositoryFactory = toDoItemFileRepositoryFactory;
        this.itemsFormatter = itemsFormatter;
    }

    public void add(Path path, String task) {
        ToDoItem item = new ToDoItem(TaskState.UNDONE, task, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), 0);
        if (Files.isReadable(path)) {
            ToDoItemRepository itemRepository = toDoItemFileRepositoryFactory.createFileRepository(path);
            itemRepository.save(item);
            System.out.println(itemsFormatter.format(itemRepository.findAll()));
        } else {
            System.out.println("The supplied file should exist and be readable");
        }
    }

}

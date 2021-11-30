package commands;

import model.ToDoItem;
import render.Formatter;
import storage.ToDoItemFileRepositoryFactory;
import storage.ToDoItemRepository;

import java.nio.file.Files;
import java.nio.file.Path;

public class CommandDone {
    private final ToDoItemFileRepositoryFactory toDoItemFileRepositoryFactory;
    private final Formatter<ToDoItem> itemFormatter;

    public CommandDone(ToDoItemFileRepositoryFactory toDoItemFileRepositoryFactory, Formatter<ToDoItem> itemFormatter) {
        this.toDoItemFileRepositoryFactory = toDoItemFileRepositoryFactory;
        this.itemFormatter = itemFormatter;
    }

    public void makeDone(Path path, int serialNumberOfItemToBeChanged) {
        if (Files.isReadable(path)) {
            ToDoItemRepository itemRepository = toDoItemFileRepositoryFactory.createFileRepository(path);
            System.out.println(itemFormatter.format(itemRepository.changeTodoStateToDone(serialNumberOfItemToBeChanged)));
        } else {
            System.out.println("The supplied file should exist and be readable");
        }
    }
}

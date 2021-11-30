package commands;

import model.ToDoItem;
import render.Formatter;
import storage.ToDoItemFileRepositoryFactory;
import storage.ToDoItemRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CommandList {
    private final ToDoItemFileRepositoryFactory toDoItemFileRepositoryFactory;
    private final Formatter<List<ToDoItem>> itemsFormatter;


    public CommandList(ToDoItemFileRepositoryFactory toDoItemFileRepositoryFactory, Formatter<List<ToDoItem>> itemsFormatter) {

        this.toDoItemFileRepositoryFactory = toDoItemFileRepositoryFactory;
        this.itemsFormatter = itemsFormatter;
    }


    public void showList(Path path, String wordToBeFound) {
        if (Files.isReadable(path)) {
            ToDoItemRepository itemRepository = toDoItemFileRepositoryFactory.createFileRepository(path);
            if (!(wordToBeFound == null)) {
                System.out.println(itemsFormatter.format(itemRepository.findSpecific(wordToBeFound)));
            } else System.out.println(itemsFormatter.format(itemRepository.findAll()));
        } else {
            System.out.println("The supplied file should exist and be readable");
        }
    }

}

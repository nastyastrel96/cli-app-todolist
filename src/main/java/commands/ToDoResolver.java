package commands;


import picocli.CommandLine;
import render.ToDoItemFormatter;
import render.ToDoItemsFormatter;
import storage.ToDoItemFileRepositoryFactory;

import java.nio.file.Path;

public class ToDoResolver {
    @CommandLine.Command(name = "todoResolver", subcommands = {ToDoResolverList.class, ToDoResolverAdd.class, ToDoResolverDone.class, CommandLine.HelpCommand.class})
    public static class ToDoResolverParent implements Runnable {

        @Override
        public void run() {

        }
    }

    @CommandLine.Command(name = "list", description = "List command shows list of todo")
    public static class ToDoResolverList implements Runnable {

        @CommandLine.Option(names = "-q", description = "Write the word in order to find necessary todo")
        private String wordToBeFound;

        @CommandLine.Parameters(index = "0", description = "Path to file with todo")
        private Path path;

        @Override
        public void run() {
            CommandList commandList = new CommandList(new ToDoItemFileRepositoryFactory(), new ToDoItemsFormatter(new ToDoItemFormatter()));
            commandList.showList(path, wordToBeFound);
        }
    }

    @CommandLine.Command(name = "add", description = "Add command add new todo in UNDONE state and shows all list of todo")
    public static class ToDoResolverAdd implements Runnable {
        @CommandLine.Parameters(index = "0", description = "Task to be done")
        private String taskToBeAdded;

        @CommandLine.Parameters(index = "1", description = "Path to file with todo")
        private Path path;

        @Override
        public void run() {
            CommandAdd commandAdd = new CommandAdd(new ToDoItemFileRepositoryFactory(), new ToDoItemsFormatter(new ToDoItemFormatter()));
            commandAdd.add(path, taskToBeAdded);
        }
    }

    @CommandLine.Command(name = "done", description = "Change task state from UNDONE to DONE (if it was UNDONE)")
    public static class ToDoResolverDone implements Runnable {

        @CommandLine.Parameters(index = "0", description = "Serial number of item you want to change")
        private int serialNumberOfItemToBeChanged;

        @CommandLine.Parameters(index = "1", description = "Path to file with todo")
        private Path path;

        @Override
        public void run() {
            CommandDone commandDone = new CommandDone(new ToDoItemFileRepositoryFactory(), new ToDoItemFormatter());
            commandDone.makeDone(path, serialNumberOfItemToBeChanged);
        }
    }
}

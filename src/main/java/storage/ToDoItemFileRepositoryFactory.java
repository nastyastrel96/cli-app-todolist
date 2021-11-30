package storage;

import java.nio.file.Path;

public class ToDoItemFileRepositoryFactory {

    public ToDoItemRepository createFileRepository(Path filePath) {
        return new ToDoItemFileRepository(filePath);
    }
}

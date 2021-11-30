package storage;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import model.CsvTodo;
import model.TaskState;
import model.ToDoItem;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ToDoItemFileRepository implements ToDoItemRepository {
    private final Path filePath;

    public ToDoItemFileRepository(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<ToDoItem> findAll() {
        List<ToDoItem> toDoItemList = new ArrayList<>();
        List<CsvTodo> csvTodoList = csvToBean();
        for (CsvTodo csvTodo : csvTodoList) {
            toDoItemList.add(new ToDoItem(csvTodo.getState(), csvTodo.getDescription(), csvTodo.getCreationDate(), csvTodo.getSerialNumber()));
        }
        return toDoItemList;
    }

    private List<CsvTodo> csvToBean() {
        List<CsvTodo> list = new ArrayList<>();
        try (java.io.Reader reader = Files.newBufferedReader(filePath)) {
            CsvToBean<CsvTodo> csvToBean = new CsvToBeanBuilder<CsvTodo>(reader)
                    .withType(CsvTodo.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            for (CsvTodo csvTodo : csvToBean) {
                list.add(new CsvTodo(csvTodo.getState(), csvTodo.getDescription(), csvTodo.getCreationDate(), csvTodo.getSerialNumber()));
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }


    @Override
    public void save(ToDoItem item) {
        writeToFile(item);
    }

    @Override
    public List<ToDoItem> findSpecific(String wordToBeFound) {
        List<ToDoItem> toDoItemList = findAll();
        return toDoItemList.stream().filter(item -> item.description().contains(wordToBeFound)).collect(Collectors.toList());
    }

    @Override
    public ToDoItem changeTodoStateToDone(int serialNumber) {
        List<CsvTodo> csvTodoList = csvToBean();
        ToDoItem item = null;
        for (CsvTodo csvTodo : csvTodoList) {
            if (csvTodo.getSerialNumber() == serialNumber) {
                csvTodo.setState(TaskState.DONE);
                item = new ToDoItem(csvTodo.getState(), csvTodo.getDescription(), csvTodo.getCreationDate(), csvTodo.getSerialNumber());
            }
        }
        writeUndoneToDone(csvTodoList);
        return item;
    }

    private void writeUndoneToDone(List<CsvTodo> list) {
        createWriterForCsvTodo(list);
    }

    private void createWriterForCsvTodo(List<CsvTodo> list) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(String.valueOf(filePath), false))) {
            StatefulBeanToCsv<CsvTodo> beanToCsv = new StatefulBeanToCsvBuilder<CsvTodo>(writer).build();
            beanToCsv.write(list);

        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException ex) {
            throw new RuntimeException("Could not write to .csv file " + filePath.toAbsolutePath());
        }
    }

    private void writeToFile(ToDoItem item) {
        List<ToDoItem> toDoItemList = findAll();
        List<CsvTodo> csvTodoList = new ArrayList<>();
        for (ToDoItem toDoItem : toDoItemList) {
            csvTodoList.add(CsvTodo.fromToDoItem(toDoItem));
        }
        CsvTodo csvTodoForAdding = CsvTodo.fromToDoItem(item);
        csvTodoForAdding.setSerialNumber(csvTodoList.size() + 1);
        csvTodoList.add(csvTodoForAdding);

        createWriterForCsvTodo(csvTodoList);
    }

}

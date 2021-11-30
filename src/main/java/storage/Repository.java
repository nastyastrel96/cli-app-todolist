package storage;

import java.util.List;

public interface Repository<T> {
    List<T> findAll();
    void save(T item);
    List<T> findSpecific(String wordToBeFound);
    T changeTodoStateToDone(int serialNumber);

}

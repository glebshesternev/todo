package ifmo.oop.todo.dao;



import ifmo.oop.todo.domain.Entity;

import java.util.List;



public interface Dao<T extends Entity> {

    void insert(T o);

    T get(long id);

    List<T> getAll();

    void update(T o);

    void remove(long id);
}

package ifmo.oop.todo.dao;



import ifmo.oop.todo.domain.Task;

import java.text.SimpleDateFormat;
import java.util.List;



public interface TaskDao extends Dao<Task> {

    SimpleDateFormat sdf = Task.sdf;

    List<Task> get(String namePattern);

    List<Task> get(String dueDateFrom, String dueDateTo);

    void remove(String namePattern);

    void remove(String dueDateFrom, String dueDateTo);
}

package ifmo.oop.todo.domain;



import java.util.Date;
import java.util.List;



public interface Model {

    void createTask(String name, Date dueDate);

    void updateDueDateById(long id, Date dueDate);

    void removeById(long id);

    void removeByName(String name);

    void removeFromDateToDate(Date from, Date to);

    Task getById(long id);

    List<Task> getByName(String name);

    List<Task> getFromDateToDate(Date from, Date to);

    List<Task> getAll();

}

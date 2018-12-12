package ifmo.oop.todo.domain;



import ifmo.oop.todo.dao.TaskDao;

import java.util.Date;
import java.util.List;



public class TaskManager implements Model {

    private final TaskDao tasks;


    public TaskManager(TaskDao o) {

        tasks = o;
    }


    @Override
    public void createTask(String name, Date dueDate) {
        Task task = Task.newBuiilder()
                .setName(name)
                .setDueDate(dueDate)
                .setCreateDate(new Date(System.currentTimeMillis()))
                .setEditDate(new Date(System.currentTimeMillis()))
                .build();
        tasks.insert(task);
    }


    @Override
    public void updateDueDateById(long id, Date dueDate) {

        tasks.update(Task.newBuiilder()
                .setId(id)
                .setDueDate(dueDate)
                .build());
    }


    @Override
    public void removeById(long id) {

        tasks.remove(id);
    }


    @Override
    public void removeByName(String name) {

        tasks.remove(name);
    }


    @Override
    public void removeFromDateToDate(Date from, Date to) {

        tasks.remove(tasks.sdf.format(from), tasks.sdf.format(to));
    }


    @Override
    public Task getById(long id) {

        return tasks.get(id);
    }


    @Override
    public List<Task> getByName(String name) {

        return tasks.get(name);
    }


    @Override
    public List<Task> getFromDateToDate(Date from, Date to) {

        return tasks.get(tasks.sdf.format(from), tasks.sdf.format(to));
    }


    public List<Task> getAll() {

        return tasks.getAll();
    }

}
















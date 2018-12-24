package ifmo.oop.todo.ui.cli.commands;



import ifmo.oop.todo.dao.TaskDao;
import ifmo.oop.todo.domain.Model;
import ifmo.oop.todo.ui.cli.Command;

import java.text.ParseException;



public class InsertCommand implements Command {

    private Model taskManager;


    public InsertCommand(Model taskManager) {

        this.taskManager = taskManager;

    }


    @Override
    public boolean execute(String... args) {

        if (taskManager == null) {
            System.out.println("Source mot found");
        } else if (args == null || args[1] == null) {
            System.out.println("Parametrs not found");
        } else {
            try {
                taskManager.createTask(args[0], TaskDao.sdf.parse(args[1]));
                System.out.println("Created.");
            } catch (ParseException ignored) {
            }
        }
        return true;
    }


    @Override
    public String getName() {

        return "createTask";
    }


    @Override
    public String getDescription() {

        return "[name] [dueDate]";
    }
}


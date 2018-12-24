package ifmo.oop.todo.ui.cli.commands;



import ifmo.oop.todo.dao.TaskDao;
import ifmo.oop.todo.domain.Model;
import ifmo.oop.todo.ui.cli.Command;

import java.text.ParseException;



public class RemoveCommand implements Command {

    private Model taskManager;


    public RemoveCommand(Model taskManager) {

        this.taskManager = taskManager;

    }


    @Override
    public boolean execute(String... args) {

        if (taskManager == null) {
            System.out.println("Source mot found");
        } else if (args == null) {
            System.out.println("Parametrs not found");
        } else if (args.length == 1) {
            System.out.println("Removed.");
            taskManager.removeByName(args[0]);
        } else if (args.length == 2) {
            System.out.println("Removed.");
            try {
                taskManager.removeFromDateToDate(TaskDao.sdf.parse(args[0]), TaskDao.sdf.parse(args[1]));
            } catch (ParseException ignored) {
            }
        } else {
            System.out.println("Parametrs not found");
        }
        return true;
    }


    @Override
    public String getName() {

        return "removeById";
    }


    @Override
    public String getDescription() {

        return "[name] | [dueDateFrom] [dueDateTo]";
    }
}

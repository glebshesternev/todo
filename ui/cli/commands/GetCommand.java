package ifmo.oop.todo.ui.cli.commands;



import ifmo.oop.todo.dao.TaskDao;
import ifmo.oop.todo.domain.Model;
import ifmo.oop.todo.domain.Task;
import ifmo.oop.todo.ui.cli.Command;

import java.text.ParseException;



public class GetCommand implements Command {

    private Model taskManager;


    public GetCommand(Model taskManager) {

        this.taskManager = taskManager;

    }


    @Override
    public boolean execute(String... args) {

        if (taskManager == null) {
            System.out.println("Source mot found");
        } else if (args == null) {
            System.out.println("Parametrs not found");
        } else if (args[0].equals("all")) {
            System.out.println("All tasks:");
            for (Task t : taskManager.getAll()) {
                System.out.println(t.toString());
            }
        } else if (args.length == 1) {
            System.out.println("\"" + args[0] + "\" " + "tasks:");
            for (Task t : taskManager.getByName(args[0])) {
                System.out.println(t.toString());
            }
        } else if (args.length == 2) {
            System.out.println("From " + args[0] + " to " + args[1] + ":");
            try {
                for (Task t : taskManager.getFromDateToDate(TaskDao.sdf.parse(args[0]), TaskDao.sdf.parse(args[1]))) {
                    System.out.println(t.toString());
                }
            } catch (ParseException ignored) {
            }
        } else {
            System.out.println("Parametrs not found");
        }
        return true;
    }


    @Override
    public String getName() {

        return "getByName";
    }


    @Override
    public String getDescription() {

        return "all | [name] | [dueDateFrom] [dueDateTo]";
    }
}

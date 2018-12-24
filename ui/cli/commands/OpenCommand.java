package ifmo.oop.todo.ui.cli.commands;



import ifmo.oop.todo.dao.sqlite.SQLiteTaskDao;
import ifmo.oop.todo.domain.Model;
import ifmo.oop.todo.domain.TaskManager;
import ifmo.oop.todo.ui.cli.Command;

import java.sql.SQLException;



public class OpenCommand implements Command {

    private Model taskManager;


    public OpenCommand(Model taskManager) {

        this.taskManager = taskManager;

    }


    @Override
    public boolean execute(String... args) {

        if (args == null) {
            System.out.println("Parametrs not found");
        } else if (args[0].equals("sqlite")) {
            try {
                taskManager = new TaskManager(new SQLiteTaskDao(args[1]));
            } catch (SQLException e) {
            }
            if (taskManager != null)
                System.out.println("Done.");
            else System.out.println("Error.");
        } else if (args[0].equals("xml")) {
            System.out.println("ne gotovo");
        } else {
            System.out.println("Parametrs not found");
        }
        return true;
    }


    @Override
    public String getName() {

        return "open";
    }


    @Override
    public String getDescription() {

        return "sqlite [filePath] | xml [filePath]";
    }
}
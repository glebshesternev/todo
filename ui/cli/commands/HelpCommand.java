package ifmo.oop.todo.ui.cli.commands;



import ifmo.oop.todo.domain.Model;
import ifmo.oop.todo.ui.cli.Command;

import java.util.Map;



public class HelpCommand implements Command {

    private Model taskManager;
    private Map<String, Command> commands;


    public HelpCommand(Model taskManager, Map<String, Command> commands) {

        this.taskManager = taskManager;
        this.commands = commands;

    }


    @Override
    public boolean execute(String... args) {

        if (args == null) {
            System.out.println("Avaliable commands:");
            for (Command command : commands.values()) {
                System.out.println(command.getName() + " " + command.getDescription());
            }
        } else {
            for (String str : args) {
                System.out.println("Help for command " + str + ":");
                Command command = commands.get(str);
                if (command == null) {
                    System.out.println("Command not found");
                } else {
                    System.out.println(command.getName() + " " + command.getDescription());
                }
            }
        }
        return true;
    }

    @Override
    public String getName() {

        return "help";
    }


    @Override
    public String getDescription() {

        return "[commands...]";
    }
}
package ifmo.oop.todo.ui.cli;



import ifmo.oop.todo.domain.Model;
import ifmo.oop.todo.ui.cli.commands.*;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;



public class CliApp {

    private Map<String, Command> commands;

    private String consoleEncoding;

    private Model taskManager;


    public CliApp(String consoleEncoding) {

        commands = new TreeMap<>();
        Command command = new HelpCommand(taskManager, commands);
        commands.put(command.getName(), command);
        command = new ExitCommand();
        commands.put(command.getName(), command);
        command = new InsertCommand(taskManager);
        commands.put(command.getName(), command);
        command = new OpenCommand(taskManager);
        commands.put(command.getName(), command);
        command = new RemoveCommand(taskManager);
        commands.put(command.getName(), command);
        command = new GetCommand(taskManager);
        commands.put(command.getName(), command);
        this.consoleEncoding = consoleEncoding;
    }


    public void execute() {

        boolean result = true;
        Scanner scanner = new Scanner(System.in, consoleEncoding);
        do {
            System.out.print("> ");
            String fullCommand = scanner.nextLine();
            ParsedCommand pc = new ParsedCommand(fullCommand);
            if (pc.command == null || "".equals(pc.command)) {
                continue;
            }
            Command command = commands.get(pc.command);
            if (command == null) {
                System.out.println("Command not found");
                continue;
            }
            result = command.execute(pc.args);
        } while (result);
    }


}
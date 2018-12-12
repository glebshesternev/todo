package ifmo.oop.todo.ui.cli;



import ifmo.oop.todo.dao.TaskDao;
import ifmo.oop.todo.dao.sqlite.SQLiteTaskDao;
import ifmo.oop.todo.domain.Model;
import ifmo.oop.todo.domain.Task;
import ifmo.oop.todo.domain.TaskManager;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;



public class CliApp {

    private static final String SOURCE_NOT_FOUND = "Source mot found";
    private static final String COMMAND_NOT_FOUND = "Command not found";
    private static final String PARAMERTS_NOT_FOUND = "Parametrs not found";
    private static final String MSG_DELIM = "===========================================================================";

    private Map<String, Command> commands;

    private String consoleEncoding;

    private Model taskManager;


    public CliApp(String consoleEncoding) {

        commands = new TreeMap<>();
        Command command = new HelpCommand();
        commands.put(command.getName(), command);
        command = new ExitCommand();
        commands.put(command.getName(), command);
        command = new InsertCommand();
        commands.put(command.getName(), command);
        command = new OpenCommand();
        commands.put(command.getName(), command);
        command = new RemoveCommand();
        commands.put(command.getName(), command);
        command = new GetCommand();
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
                System.out.println(COMMAND_NOT_FOUND);
                continue;
            }
            result = command.execute(pc.args);
        } while (result);
    }


    class HelpCommand implements Command {

        @Override
        public boolean execute(String... args) {

            if (args == null) {
                System.out.println("Avaliable commands:\n" + MSG_DELIM);
                for (Command command : commands.values()) {
                    System.out.println(command.getName() + " " + command.getDescription());
                }
                System.out.println(MSG_DELIM);
            } else {
                for (String str : args) {
                    System.out.println("Help for command " + str + ":");
                    Command command = commands.get(str);
                    if (command == null) {
                        System.out.println(COMMAND_NOT_FOUND);
                    } else {
                        System.out.println(command.getName() + " " + command.getDescription());
                    }
                    System.out.println(MSG_DELIM);
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



    class ExitCommand implements Command {

        @Override
        public boolean execute(String... args) {

            System.out.println("Finishing... done.");
            return false;
        }


        @Override
        public String getName() {

            return "exit";
        }


        @Override
        public String getDescription() {

            return "";
        }

    }



    class OpenCommand implements Command {

        @Override
        public boolean execute(String... args) {

            if (args == null) {
                System.out.println(PARAMERTS_NOT_FOUND);
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
                System.out.println(PARAMERTS_NOT_FOUND);
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



    class InsertCommand implements Command {

        @Override
        public boolean execute(String... args) {

            if (taskManager == null) {
                System.out.println(SOURCE_NOT_FOUND);
            } else if (args == null || args[1] == null) {
                System.out.println(PARAMERTS_NOT_FOUND);
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



    class GetCommand implements Command {

        @Override
        public boolean execute(String... args) {

            if (taskManager == null) {
                System.out.println(SOURCE_NOT_FOUND);
            } else if (args == null) {
                System.out.println(PARAMERTS_NOT_FOUND);
            } else if (args[0].equals("all")) {
                System.out.println("All tasks:");
                System.out.println(MSG_DELIM);
                for (Task t : taskManager.getAll()) {
                    System.out.println(t.toString());
                }
                System.out.println(MSG_DELIM);
            } else if (args.length == 1) {
                System.out.println("\"" + args[0] + "\" " + "tasks:");
                System.out.println(MSG_DELIM);
                for (Task t : taskManager.getByName(args[0])) {
                    System.out.println(t.toString());
                }
                System.out.println(MSG_DELIM);
            } else if (args.length == 2) {
                System.out.println("From " + args[0] + " to " + args[1] + ":");
                System.out.println(MSG_DELIM);
                try {
                    for (Task t : taskManager.getFromDateToDate(TaskDao.sdf.parse(args[0]), TaskDao.sdf.parse(args[1]))) {
                        System.out.println(t.toString());
                    }
                } catch (ParseException ignored) {
                }
                System.out.println(MSG_DELIM);
            } else {
                System.out.println(PARAMERTS_NOT_FOUND);
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



    class RemoveCommand implements Command {

        @Override
        public boolean execute(String... args) {

            if (taskManager == null) {
                System.out.println(SOURCE_NOT_FOUND);
            } else if (args == null) {
                System.out.println(PARAMERTS_NOT_FOUND);
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
                System.out.println(PARAMERTS_NOT_FOUND);
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


}
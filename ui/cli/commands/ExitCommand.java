package ifmo.oop.todo.ui.cli.commands;



import ifmo.oop.todo.ui.cli.Command;



public class ExitCommand implements Command {

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

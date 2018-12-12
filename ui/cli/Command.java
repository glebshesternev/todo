package ifmo.oop.todo.ui.cli;



public interface Command {

    boolean execute(String... args);

    String getName();

    String getDescription();
}

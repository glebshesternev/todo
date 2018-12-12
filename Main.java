package ifmo.oop.todo;



import ifmo.oop.todo.ui.cli.CliApp;



public class Main {


    public static void main(String[] args) {

        CliApp consoleApp = new CliApp("Cp1251");
        consoleApp.execute();
    }

}

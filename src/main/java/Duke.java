import commands.Command;
import exceptions.DukeException;
import storage.Storage;
import tasks.*;
import utils.Parser;
import views.UI;

import java.io.IOException;
import java.util.Scanner;

/**
 * Main driver class for Duke
 */

public class Duke{
    private static final TaskList tasks = new TaskList();
    private static final Scanner sc = new Scanner(System.in);
    private static UI ui = new UI();
    private static Storage storage = new Storage(tasks);


    public static void main(String[] args) throws IOException {
        storage.load();

        ui.printInitMessage();
        ui.printCommands();

        acceptCommands();
        storage.save();
        ui.printExitMessage();
    }

    /**
     * Takes in input from the user, and passes it to Parser to get the appropriate command
     * and execute it.
     */
    private static void acceptCommands(){
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = sc.nextLine();
                Command c = Parser.parse(fullCommand);
                c.execute(ui, tasks, storage);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.printErrorMessage(e.getMessage());
            }
        }
    }

}

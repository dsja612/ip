package commands;

import exceptions.DukeException;
import storage.Storage;
import tasks.TaskList;
import views.UI;

public class UnmarkCommand extends Command {
    private int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(UI ui, TaskList tasks, Storage storage) throws DukeException {
        try {
            tasks.markUndone(index);
            this.commandStatus = "Oof! I have marked this task as undone for you! \n" + tasks.get(index - 1);
            ui.printCommandOutput(this);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Please provide a valid index!");
        }
    }
}

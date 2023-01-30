package commands;

import exceptions.DukeException;

import storage.Storage;

import tasks.TaskList;
import tasks.ToDo;

import views.UI;

public class ToDoCommand extends Command {
    private String name;

    public ToDoCommand(String name) {
        this.name = name;
    }

    @Override
    public void execute(UI ui, TaskList tasks, Storage storage) throws DukeException {
        ToDo todo = new ToDo(name);
        tasks.addTask(todo);
        this.commandStatus = "Added To-do: " + todo + "\n"
                + "You now have " + tasks.size() + " task(s) in your list";
        ui.printCommandOutput(this);
    }
}
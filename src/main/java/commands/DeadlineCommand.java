package commands;

import exceptions.DukeException;

import storage.Storage;

import tasks.Deadline;
import tasks.TaskList;

import views.UI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DeadlineCommand extends Command {
    private String name;
    private LocalDateTime deadline;

    public DeadlineCommand(String name, String deadline) throws DukeException {
        this.name = name;
        try {
            this.deadline = LocalDateTime.parse(deadline, DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
        } catch (DateTimeParseException e) {
            throw new DukeException("Please enter a valid date format in \"dd/mm/yyyy!\"");
        }
    }

    @Override
    public void execute(UI ui, TaskList tasks, Storage storage) throws DukeException {
        Deadline deadline = new Deadline(this.name, this.deadline);
        tasks.addTask(deadline);
        this.commandStatus = "Added deadline: " + deadline + "\n"
                + "You now have " + tasks.size() + " task(s) in your list";
        ui.printCommandOutput(this);
    }
}

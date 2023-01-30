package storage;

import exceptions.DukeException;
import tasks.Deadline;
import tasks.Event;
import tasks.TaskList;
import tasks.ToDo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;

/**
 * Handles loading and saving of tasks to local storage.
 */
public class Storage {
    private final Path HOME_DIRECTORY = Path.of(System.getProperty("user.dir") + "/data");
    private File dataFile;
    private final TaskList tasks;

    public Storage(TaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * Loads tasks from a local file into a TaskList, creates one if one does not exist.
     * @throws IOException If fail to load/create a storage file
     */
    public void load() throws IOException {
        try {
            if (!Files.exists(HOME_DIRECTORY)) {
                Files.createDirectories(HOME_DIRECTORY);
            }

            this.dataFile = new File(this.HOME_DIRECTORY + "/duke.txt");
            boolean isCreated = true;
            if (!this.dataFile.exists()) {
                isCreated = this.dataFile.createNewFile();
            }
            if (!isCreated) {
                throw new DukeException("Failed to create storage file!");
            }
            Scanner sc = new Scanner(this.dataFile);
            while (sc.hasNextLine()) {
                String input = sc.nextLine();
                parseFile(input, tasks);
            }

        } catch (FileNotFoundException | DukeException e) {
            System.out.println(e);
        }
    }

    /**
     * Saves current TaskList into local file.
     * @throws IOException If fail to save tasks into file
     */
    public void save() throws IOException {
        try {
            FileWriter fileWriter = new FileWriter(this.dataFile);
            for (int i = 0; i < this.tasks.size(); i++) {
                String line = tasks.get(i).toSaveFormat();
                fileWriter.write(line + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Failed to save existing tasks...");
        }
    }

    /**
     * Parse input from file into tasks.
     * @param input Line from local file
     * @param tasks TaskList to store tasks
     * @throws DukeException If fail to create task from invalid input
     */
    public static void parseFile(String input, TaskList tasks) throws DukeException {
        String[] inputList = input.split(",");
        String taskType = inputList[0];
        String taskName = inputList[1];

        switch (taskType) {
            case "T": {
                tasks.addTask(new ToDo(taskName));
                break;
            }
            case "D": {
                String by = inputList[2];
                LocalDateTime deadline = LocalDateTime.parse(by, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                tasks.addTask(new Deadline(taskName, deadline));
                break;
            }
            case "E": {
                String from = inputList[2];
                String to = inputList[3];
                LocalDateTime startDate = LocalDateTime.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                LocalDateTime endDate = LocalDateTime.parse(to, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                tasks.addTask(new Event(taskName, startDate, endDate));
                break;
            }
            default: {
                throw new DukeException("Unable to parse this line: " + input);
            }
        }
    }
}

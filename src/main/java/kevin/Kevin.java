package kevin;

import java.util.Scanner;

import kevin.command.Command;
import kevin.parser.Parser;
import kevin.storage.Storage;
import kevin.task.Deadline;
import kevin.task.Event;
import kevin.task.Task;
import kevin.task.TaskList;
import kevin.task.Todo;
import kevin.ui.Ui;

/**
 * The main entry point of the Kevin task management application.
 */
public class Kevin {

    private final Ui ui;
    private final Parser parser;
    private final TaskList taskList;

    /**
     * Creates a Kevin instance with default dependencies and loads persisted tasks.
     */
    public Kevin() {
        ui = new Ui();
        parser = new Parser();

        Storage storage = new Storage();
        taskList = new TaskList(100, storage);
        taskList.load();
    }

    /**
     * Generates a response for the user's message (used by the JavaFX GUI).
     *
     * @param input The raw user input.
     * @return The message to display in the UI.
     */
    public String getResponse(String input) {
        if (input == null) {
            return ui.showError("Input cannot be null.");
        }

        try {
            Command command = parser.parse(input.trim());
            return handle(command);
        } catch (IllegalArgumentException e) {
            // Expected user errors (e.g., invalid index, missing description).
            return ui.showError(e.getMessage());
        } catch (Exception e) {
            // Unexpected errors; keep message generic so you don't leak internals.
            return ui.showError("Something went wrong. Please try again.");
        }
    }

    private String handle(Command command) {
        switch (command.getType()) {
            case HI:
                return ui.showWelcome();
            case BYE:
                return ui.showGoodbye();
            case LIST:
                return ui.showList(taskList.formatList());
            case TODO:
                return handleTodo(command);
            case DEADLINE:
                return handleDeadline(command);
            case EVENT:
                return handleEvent(command);
            case FIND:
                return handleFind(command);
            case MARK:
                return ui.showMarked(taskList.mark(command.getIndex()));
            case UNMARK:
                return ui.showUnmarked(taskList.unmark(command.getIndex()));
            case DELETE:
                return handleDelete(command);
            default:
                return ui.showError("I don't know what that means.");
        }
    }

    private String handleTodo(Command command) {
        Task task = new Todo(command.getDescription());
        taskList.add(task);
        return ui.showAdded(task, taskList.size());
    }

    private String handleDeadline(Command command) {
        Task task = new Deadline(command.getDescription(), command.getBy());
        taskList.add(task);
        return ui.showAdded(task, taskList.size());
    }

    private String handleEvent(Command command) {
        Task task = new Event(command.getDescription(), command.getFrom(), command.getTo());
        taskList.add(task);
        return ui.showAdded(task, taskList.size());
    }

    private String handleFind(Command command) {
        Task[] matches = taskList.find(command.getDescription());
        return ui.showFound(matches);
    }

    private String handleDelete(Command command) {
        Task removed = taskList.delete(command.getIndex());
        return ui.showDeleted(removed, taskList.size());
    }
}

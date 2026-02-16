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
     * Constructor
     */
    public Kevin() {
        ui = new Ui();
        parser = new Parser();

        Storage storage = new Storage();
        taskList = new TaskList(100, storage);
        taskList.load();
    }

    /**
     * Generates a response for the user's chat message.
     * This is used by the JavaFX GUI.
     */
    public String getResponse(String input) {
        try {
            Command c = parser.parse(input);

            switch (c.getType()) {
                case HI:
                    return ui.showWelcome();
                case BYE:
                    return ui.showGoodbye();
                case LIST:
                    return ui.showList(taskList.formatList());
                case TODO: {
                    Task t = new Todo(c.getDescription());
                    taskList.add(t);
                    return ui.showAdded(t, taskList.size());
                }

                case FIND: {
                    Task[] matches = taskList.find(c.getDescription());
                    return ui.showFound(matches);
                }

                case DEADLINE: {
                    Task t = new Deadline(c.getDescription(), c.getBy());
                    taskList.add(t);
                    return ui.showAdded(t, taskList.size());
                }

                case EVENT: {
                    Task t = new Event(c.getDescription(), c.getFrom(), c.getTo());
                    taskList.add(t);
                    return ui.showAdded(t, taskList.size());
                }

                case MARK: {
                    Task t = taskList.mark(c.getIndex()); // c.getIndex() is 1-based
                    return ui.showMarked(t);
                }

                case UNMARK: {
                    Task t = taskList.unmark(c.getIndex());
                    return ui.showUnmarked(t);
                }

                case DELETE:
                    Task removed = taskList.delete(c.getIndex());
                    return ui.showDeleted(removed, taskList.size());

                default:
                    return ui.showError("I don't know what that means.");
            }

        } catch (Exception e) {
            return ui.showError(e.getMessage());
        }
    }
}

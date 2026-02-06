package kevin.command;

import java.time.LocalDateTime;

import kevin.task.TaskList;
import kevin.ui.Ui;
import kevin.storage.Storage;
/**
 * Represents an executable user command in the application.
 */
public class Command {

    public enum Type {
        BYE,
        LIST,
        TODO,
        DEADLINE,
        EVENT,
        MARK,
        UNMARK,
        DELETE,
        FIND
    }

    private final Type type;

    // Optional fields depending on command
    private String description;
    private int index; // for mark / unmark / delete (1-based)
    private LocalDateTime by;     // deadline
    private LocalDateTime from;   // event
    private LocalDateTime to;     // event

    private Command(Type type) {
        this.type = type;
    }

    //Factory methods

    public static Command bye() {
        return new Command(Type.BYE);
    }

    public static Command find(String keyword) {
        Command c = new Command(Type.FIND);
        c.description = keyword;   // reuse description field to store keyword
        return c;
    }

    public static Command list() {
        return new Command(Type.LIST);
    }

    public static Command todo(String description) {
        Command c = new Command(Type.TODO);
        c.description = description;
        return c;
    }

    public static Command deadline(String description, LocalDateTime by) {
        Command c = new Command(Type.DEADLINE);
        c.description = description;
        c.by = by;
        return c;
    }

    public static Command event(String description, LocalDateTime from, LocalDateTime to) {
        Command c = new Command(Type.EVENT);
        c.description = description;
        c.from = from;
        c.to = to;
        return c;
    }

    public static Command mark(int index) {
        Command c = new Command(Type.MARK);
        c.index = index;
        return c;
    }

    public static Command unmark(int index) {
        Command c = new Command(Type.UNMARK);
        c.index = index;
        return c;
    }

    public static Command delete(int index) {
        Command c = new Command(Type.DELETE);
        c.index = index;
        return c;
    }

    //Getters

    public Type getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getIndex() {
        return index;
    }

    public LocalDateTime getBy() {
        return by;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }
}

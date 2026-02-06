package kevin.task;
/**
 * Represents a generic task with a description and completion status.
 */
public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Constructor
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markDone() {
        isDone = true;
    }

    public void markUndone() {
        isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    /**
     * Format to be saved into hard disk
     * Default: treat as Todo-like storage
     * Format: T | doneFlag | description
     */
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    public String getStatusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    @Override
    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + description;
    }
}

package kevin.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kevin.util.DateTimeUtil;

/**
 * Represents a task that must be completed by a specific date/time.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private LocalDateTime by;

    /**
     * Constructor
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the due date/time of this deadline.
     *
     * @return Due date/time of the deadline.
     */
    public LocalDateTime getBy() {
        return by;
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + by.format(DateTimeUtil.INPUT);
    }

    @Override
    public String toString() {
        return "[D]" + getStatusIcon() + " " + getDescription()
                + " (by: " + DateTimeUtil.format(by) + ")";
    }
}

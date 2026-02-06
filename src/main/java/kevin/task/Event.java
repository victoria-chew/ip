package kevin.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kevin.util.DateTimeUtil;
/**
 * Represents an event task that occurs within a specific time period.
 */
public class Event extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Constructor
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone() ? "1" : "0") + " | " + getDescription()
                + " | " + from.format(DateTimeUtil.INPUT)
                + " | " + to.format(DateTimeUtil.INPUT);
    }

    @Override
    public String toString() {
        return "[E]" + getStatusIcon() + " " + getDescription()
                + " (from: " + DateTimeUtil.format(from)
                + " to: " + DateTimeUtil.format(to) + ")";
    }
}

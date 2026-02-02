package kevin.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import kevin.util.DateTimeUtil;

public class Deadline extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

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

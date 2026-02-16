package kevin.parser;

import java.time.LocalDateTime;

import kevin.command.Command;
import kevin.util.DateTimeUtil;
/**
 * Parses user input strings into executable {@link Command} objects.
 */
public class Parser {
    /**
     * Parses the user input into a {@link Command}.
     *
     * @param input Full user input string.
     * @return The corresponding command object.
     * @throws IllegalArgumentException If the input does not match any supported command format.
     */
    public Command parse(String input) {
        assert input != null : "Input should not be null";
        input = input.trim();

        if (input.equals("bye")) {
            return Command.bye();
        }

        if (input.equals("hi")) {
            return Command.hi();
        }

        if (input.equals("list")) {
            return Command.list();
        }

        if (input.startsWith("mark")) {
            return Command.mark(parseIndex(input, "mark"));
        }

        if (input.equals("find") || input.startsWith("find ")) {
            String keyword = input.length() > 4 ? input.substring(4).trim() : "";
            if (keyword.isEmpty()) {
                throw new IllegalArgumentException("The keyword of a find command cannot be empty.");
            }
            return Command.find(keyword);
        }

        if (input.startsWith("unmark")) {
            return Command.unmark(parseIndex(input, "unmark"));
        }

        if (input.startsWith("delete")) {
            return Command.delete(parseIndex(input, "delete"));
        }

        if (input.equals("todo") || input.startsWith("todo ")) {
            String desc = input.length() > 4 ? input.substring(4).trim() : "";
            if (desc.isEmpty()) {
                throw new IllegalArgumentException("The description of a todo cannot be empty.");
            }
            return Command.todo(desc);
        }

        if (input.equals("deadline") || input.startsWith("deadline ")) {
            String rest = input.length() > 8 ? input.substring(8).trim() : "";

            if (rest.isEmpty()) {
                throw new IllegalArgumentException("The description of a deadline cannot be empty.");
            }

            if (!rest.contains(" /by ")) {
                throw new IllegalArgumentException("Deadline format: deadline <desc> /by <yyyy-MM-dd HHmm>");
            }

            String[] parts = rest.split(" /by ", 2);
            String desc = parts[0].trim();
            String byStr = parts[1].trim();

            if (desc.isEmpty() || byStr.isEmpty()) {
                throw new IllegalArgumentException("Deadline format: deadline <desc> /by <yyyy-MM-dd HHmm>");
            }

            LocalDateTime by = parseDateTime(byStr);
            return Command.deadline(desc, by);
        }

        if (input.equals("event") || input.startsWith("event ")) {
            String rest = input.length() > 5 ? input.substring(5).trim() : "";

            if (rest.isEmpty()) {
                throw new IllegalArgumentException("The description of an event cannot be empty.");
            }

            // event <desc> /from <dt> /to <dt>
            String[] partsFrom = rest.split(" /from ", 2);
            if (partsFrom.length < 2) {
                throw new IllegalArgumentException("Event format: event <desc> "
                        + "/from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
            }

            String desc = partsFrom[0].trim();

            String[] partsTo = partsFrom[1].split(" /to ", 2);
            if (partsTo.length < 2) {
                throw new IllegalArgumentException("Event format: event <desc> "
                        + "/from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
            }

            String fromStr = partsTo[0].trim();
            String toStr = partsTo[1].trim();

            if (desc.isEmpty() || fromStr.isEmpty() || toStr.isEmpty()) {
                throw new IllegalArgumentException("Event format: event <desc> "
                        + "/from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
            }

            LocalDateTime from = parseDateTime(fromStr);
            LocalDateTime to = parseDateTime(toStr);

            return Command.event(desc, from, to);
        }

        throw new IllegalArgumentException("I don't know what that means.");
    }

    // Parses the integer for mark/unmark/delete
    private int parseIndex(String input, String keyword) {
        String[] parts = input.split("\\s+");

        if (parts.length < 2) {
            throw new IllegalArgumentException("Please specify a task number. Example: " + keyword + " 2");
        }

        try {
            int n = Integer.parseInt(parts[1]);
            if (n <= 0) {
                throw new IllegalArgumentException("Task number must be >= 1.");
            }
            return n; // keep 1-based, TaskList expects 1-based in our solution
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Task number must be an integer. Example: " + keyword + " 2");
        }
    }

    // Parses date-time in the no-'T' format
    private LocalDateTime parseDateTime(String s) {
        try {
            return DateTimeUtil.parse(s); // expects yyyy-MM-dd HHmm
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date-time. Use yyyy-MM-dd HHmm (e.g. 2026-11-01 1600).");
        }
    }
}

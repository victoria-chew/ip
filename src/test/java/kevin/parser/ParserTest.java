package kevin.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kevin.command.Command;

public class ParserTest {

    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Parser();
    }

    // -------- helpers --------

    private void assertParses(String input) {
        Command cmd = parser.parse(input);
        assertNotNull(cmd, "Parser returned null Command for input: " + input);
    }

    private void assertParsesAndToStringContains(String input, String... expectedParts) {
        Command cmd = parser.parse(input);
        assertNotNull(cmd, "Parser returned null Command for input: " + input);

        String s = String.valueOf(cmd).toLowerCase(); // uses toString()
        for (String part : expectedParts) {
            assertTrue(s.contains(part.toLowerCase()),
                    "Expected Command.toString() to contain '" + part + "' but was: " + cmd);
        }
    }

    private void assertIae(String input, String expectedMessage) {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> parser.parse(input));
        assertEquals(expectedMessage, ex.getMessage());
    }

    // -------- basic commands --------

    @Test
    void parse_bye_ok() {
        assertParses("bye");
    }

    @Test
    void parse_list_ok() {
        assertParses("list");
    }

    @Test
    void parse_trimsWhitespace_ok() {
        assertParses("   list   ");
    }

    // -------- mark / unmark / delete --------

    @Test
    void parse_mark_ok() {
        // expects index 2 to be carried somewhere (often visible in toString)
        assertParses("mark 2");
    }

    @Test
    void parse_unmark_ok() {
        assertParses("unmark 3");
    }

    @Test
    void parse_delete_ok() {
        assertParses("delete 1");
    }

    @Test
    void parseMarkMissingIndexThrows() {
        assertIae("mark", "Please specify a task number. Example: mark 2");
    }

    @Test
    void parseMarkNonIntegerIndexThrows() {
        assertIae("mark abc", "Task number must be an integer. Example: mark 2");
    }

    @Test
    void parseMarkZeroIndexThrows() {
        assertIae("mark 0", "Task number must be >= 1.");
    }

    // -------- todo --------

    @Test
    void parse_todo_ok() {
        assertParses("todo read book");
    }

    @Test
    void parseTodoMissingDescriptionThrows() {
        assertIae("todo", "The description of a todo cannot be empty.");
    }

    // -------- deadline --------

    @Test
    void parse_deadline_ok() {
        assertParses("deadline finish report /by 2026-11-01 1600");
    }

    @Test
    void parseDeadlineMissingDescriptionThrows() {
        assertIae("deadline", "The description of a deadline cannot be empty.");
    }

    @Test
    void parseDeadlineMissingBySeparatorThrows() {
        assertIae("deadline finish report",
                "Deadline format: deadline <desc> /by <yyyy-MM-dd HHmm>");
    }

    @Test
    void parseDeadlineInvalidDateTimeThrows() {
        assertIae("deadline finish report /by not-a-date",
                "Invalid date-time. Use yyyy-MM-dd HHmm (e.g. 2026-11-01 1600).");
    }

    // -------- event --------

    @Test
    void parse_event_ok() {
        assertParses("event meeting /from 2026-11-01 1600 /to 2026-11-01 1800");
    }

    @Test
    void parseEventMissingDescriptionThrows() {
        assertIae("event", "The description of an event cannot be empty.");
    }

    @Test
    void parseEventMissingFromOrToThrows() {
        assertIae("event meeting",
                "Event format: event <desc> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
        assertIae("event meeting /from 2026-11-01 1600",
                "Event format: event <desc> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
    }

    @Test
    void parseEventInvalidDateTimeThrows() {
        assertIae("event meeting /from bad /to 2026-11-01 1800",
                "Invalid date-time. Use yyyy-MM-dd HHmm (e.g. 2026-11-01 1600).");
    }

    // -------- unknown --------

    @Test
    void parse_unknown_throws() {
        assertIae("wat",
                "I didn't learn this at doggy day care. Send me a HELP to view what I know!");
    }
}

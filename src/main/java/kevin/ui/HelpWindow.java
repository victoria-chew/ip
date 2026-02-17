package kevin.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Controller for the main GUI.
 */
public class HelpWindow {
    @FXML
    private TextArea helpTextArea;

    @FXML
    public void initialize() {
        helpTextArea.setText(buildHelpText());
    }

    /**
     * Main help text that is displayed in help window
     */
    private String buildHelpText() {
        return String.join("\n",
                "📌 Adding Tasks",
                "todo <description>",
                "  → Adds a simple task",
                "  Example: todo read book",
                "",
                "deadline <description> /by <yyyy-mm-dd hh:mm>",
                "  → Adds a task with a deadline",
                "  Example: deadline submit report /by 2026-02-20 1800",
                "",
                "event <description> /from <start> /to <end>",
                "  → Adds an event",
                "  Example: event meeting /from 2026-02-20 1400 /to 2026-02-20 1600",
                "",
                "📋 Viewing Tasks",
                "list",
                "  → Shows all tasks",
                "",
                "🔎 Finding Tasks",
                "find <keyword>",
                "  → Finds tasks containing the keyword",
                "",
                "✅ Managing Tasks",
                "mark <task number>",
                "  → Marks a task as completed",
                "",
                "unmark <task number>",
                "  → Marks a task as not completed",
                "",
                "delete <task number>",
                "  → Deletes a task",
                "",
                "🚪 Exiting",
                "bye",
                "  → Closes Kevin",
                "",
                "💡 Tip: Task numbers are 1-based (use list to see them)."
        );
    }
}


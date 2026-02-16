package kevin.ui;

import kevin.task.Task;
/**
 * Handles the Ui interaction with user
 */
public class Ui {

    /**
     * welcome message
     */
    public String showWelcome() {
        return "Hello! I'm Kevin. What can I do for you?";
    }


    /**
     * bye message
     */
    public String showGoodbye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * unknown error message
     */
    public String showError(String message) {
        return "OOPS! " + message;
    }

    /**
     * find task message
     */
    public String showFound(Task[] matches) {
        StringBuilder sb = new StringBuilder();

        sb.append("Here are the matching tasks in your list:\n");

        if (matches.length == 0) {
            sb.append(" (none)");
        } else {
            for (int i = 0; i < matches.length; i++) {
                sb.append(" ")
                        .append(i + 1)
                        .append(". ")
                        .append(matches[i])
                        .append("\n");
            }
        }

        return sb.toString().trim();
    }

    /**
     * added task message
     */
    public String showAdded(Task task, int size) {
        return "Got it. I've added this task:\n" + task + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * marked task message
     */
    public String showMarked(Task task) {
        return "Nice! I've marked this task as done:\n  " + task;
    }

    /**
     * unmarked task message
     */
    public String showUnmarked(Task task) {
        return "OK, I've marked this task as not done yet:\n  " + task;
    }

    /**
     * deleted task message
     */
    public String showDeleted(Task task, int size) {
        return "Noted. I've removed this task:\n"
                + "  " + task + "\n"
                + "Now you have " + size + " tasks in the list.";
    }

    public String showList(String listOutput) {
        return listOutput;
    }
}

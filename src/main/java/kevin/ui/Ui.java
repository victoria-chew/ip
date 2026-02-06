package kevin.ui;

import kevin.task.Task;
/**
 * Handles the Ui interaction with user
 */
public class Ui {

    /**
     * welcome message
     */
    public void showWelcome() {
        System.out.println("Hello! I'm Kevin");
        System.out.println("What can I do for you?");
    }

    /**
     * bye message
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * unknown error message
     */
    public void showError(String message) {
        System.out.println("OOPS! " + message);
    }

    /**
     * find task message
     */
    public void showFound(Task[] matches) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.length; i++) {
            System.out.println(" " + (i + 1) + "." + matches[i]);
        }
        if (matches.length == 0) {
            System.out.println(" (none)");
        }
    }

    /**
     * added task message
     */
    public void showAdded(Task task, int size) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    /**
     * marked task message
     */
    public void showMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
    }

    /**
     * unmarked task message
     */
    public void showUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
    }

    /**
     * deleted task message
     */
    public void showDeleted(Task task, int size) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    public void showList(String listOutput) {
        System.out.println(listOutput);
    }
}

import java.util.Scanner;

public class Kevin {

    //Error message
    private static void showError(String message) {
        System.out.println("OOPS! " + message);
    }

    public static void main(String[] args) {
        // Scanner to read user input from standard input
        Scanner scanner = new Scanner(System.in);

        // Instead of storng count and tasks
        Storage storage = new Storage();
        TaskList taskList = new TaskList(100, storage);
        taskList.load();

        // Initial greeting
        System.out.println("Hello! I'm Kevin");
        System.out.println("What can I do for you?");

        // Main input-processing loop
        while (true) {
            // Read and trim user input
            String input = scanner.nextLine().trim();

            //EXIT COMMAND
            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break; // terminate the loop and program
            }

            //LIST COMMAND (edited)
            if (input.equals("list")) {
                System.out.println(taskList.formatList());
                continue;
            }

            //MARK COMMAND (edited)
            else if (input.startsWith("mark")) {
                String[] parts = input.split("\\s+");

                if (parts.length < 2) {
                    showError("Please specify a task number. Example: mark 2");
                    continue;
                }

                try {
                    int taskNumber = Integer.parseInt(parts[1]);
                    Task t = taskList.mark(taskNumber);

                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(t);

                } catch (NumberFormatException e) {
                    showError("Task number must be an integer. Example: mark 2");
                } catch (IllegalArgumentException e) {
                    // TaskList throws this when out of range
                    showError(e.getMessage());
                }

                continue;
            }


            //UNMARK COMMAND (edited)
            else if (input.startsWith("unmark")) {
                String[] parts = input.split("\\s+");

                if (parts.length < 2) {
                    showError("Please specify a task number. Example: unmark 2");
                    continue;
                }

                try {
                    int taskNumber = Integer.parseInt(parts[1]);
                    Task t = taskList.unmark(taskNumber);

                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(t);

                } catch (NumberFormatException e) {
                    showError("Task number must be an integer. Example: unmark 2");
                } catch (IllegalArgumentException e) {
                    showError(e.getMessage());
                }

                continue;
            }

            //TODO COMMAND (edited)
            else if (input.equals("todo") || input.startsWith("todo ")) {
                // Extract description after "todo"
                String desc = input.length() > 4 ? input.substring(4).trim() : "";

                // Error: empty description
                if (desc.isEmpty()) {
                    showError("The description of a todo cannot be empty.");
                    continue;
                }

                // Create and store Todo task
                taskList.add(new Todo(desc));
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + taskList.get(taskList.size()));
                System.out.println("Now you have " + taskList.size() + " tasks in the list.");
                continue;
            }

            //DEADLINE COMMAND (edited)
            else if (input.equals("deadline") || input.startsWith("deadline ")) {
                String rest = input.length() > 8 ? input.substring(8).trim() : "";

                // Error: empty description
                if (rest.isEmpty()) {
                    showError("The description of a deadline cannot be empty.");
                    continue;
                }

                // Error: missing /by
                if (!rest.contains(" /by ")) {
                    showError("Deadline format: deadline <description> /by <time>");
                    continue;
                }

                // Split description and deadline time
                String[] parts = rest.split(" /by ", 2);
                String desc = parts[0].trim();
                String by = parts[1].trim();

                // Error: missing fields
                if (desc.isEmpty() || by.isEmpty()) {
                    showError("Deadline format: deadline <description> /by <time>");
                    continue;
                }

                // Create and store Deadline task
                taskList.add(new Deadline(desc, by));

                System.out.println("Got it. I've added this task:");
                System.out.println("  " + taskList.get(taskList.size()));
                System.out.println("Now you have " + taskList.size() + " tasks in the list.");
                continue;
            }

            //EVENT COMMAND (edited)
            else if (input.equals("event") || input.startsWith("event ")) {
                String rest = input.length() > 5 ? input.substring(5).trim() : "";

                // Error: empty description
                if (rest.isEmpty()) {
                    showError("The description of an event cannot be empty.");
                    continue;
                }

                // Error: missing /from or /to
                if (!rest.contains(" /from ") || !rest.contains(" /to ")) {
                    showError("Event format: event <description> /from <start> /to <end>");
                    continue;
                }

                // Extract description, start time, and end time
                String[] partsFrom = rest.split(" /from ", 2);
                String desc = partsFrom[0].trim();
                String[] partsTo = partsFrom[1].split(" /to ", 2);

                if (partsTo.length < 2) {
                    showError("Event format: event <description> /from <start> /to <end>");
                    continue;
                }

                String from = partsTo[0].trim();
                String to = partsTo[1].trim();

                // Error: missing any field
                if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                    showError("Event format: event <description> /from <start> /to <end>");
                    continue;
                }

                // Create and store Event task
                taskList.add(new Event(desc, from, to));
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + taskList.get(taskList.size()));
                System.out.println("Now you have " + taskList.size() + " tasks in the list.");
                continue;
            }

            //DELETE COMMAND (edited)
            else if (input.startsWith("delete")) {
                String[] parts = input.split("\\s+");

                if (parts.length < 2) {
                    showError("Please specify a task number. Example: delete 3");
                    continue;
                }

                try {
                    int taskNumber = Integer.parseInt(parts[1]);
                    Task removed = taskList.delete(taskNumber);

                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removed);
                    System.out.println("Now you have " + taskList.size() + " tasks in the list.");

                } catch (NumberFormatException e) {
                    showError("Task number must be an integer. Example: delete 3");
                } catch (IllegalArgumentException e) {
                    showError(e.getMessage());
                }

                continue;
            }

            //UNKNOWN COMMAND
            showError("I don't know what that means. Try: todo, deadline, event, list, mark, unmark, bye");
        }
    }
}

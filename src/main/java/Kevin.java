import java.util.Scanner;

public class Kevin {

    //Error message
    private static void showError(String message) {
        System.out.println("OOPS! " + message);
    }

    public static void main(String[] args) {
        // Scanner to read user input from standard input
        Scanner scanner = new Scanner(System.in);

        // Fixed-size array to store tasks (assumption: max 100 tasks)
        Task[] tasks = new Task[100];
        int count = 0; // number of tasks currently stored

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

            //LIST COMMAND
            if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < count; i++) {
                    // Display each task with a 1-based index
                    System.out.println((i + 1) + "." + tasks[i]);
                }
                continue;
            }

            //MARK COMMAND
            if (input.startsWith("mark")) {
                String[] parts = input.split("\\s+");

                // Error: missing task number
                if (parts.length < 2) {
                    showError("Please specify a task number. Example: mark 2");
                    continue;
                }

                try {
                    int index = Integer.parseInt(parts[1]) - 1;

                    // Error: index out of bounds
                    if (index < 0 || index >= count) {
                        showError("That task number is out of range.");
                        continue;
                    }

                    // Mark task as done
                    tasks[index].markDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks[index]);

                } catch (NumberFormatException e) {
                    // Error: non-integer task number
                    showError("Task number must be an integer. Example: mark 2");
                }
                continue;
            }

            //UNMARK COMMAND
            if (input.startsWith("unmark")) {
                String[] parts = input.split("\\s+");

                // Error: missing task number
                if (parts.length < 2) {
                    showError("Please specify a task number. Example: unmark 2");
                    continue;
                }

                try {
                    int index = Integer.parseInt(parts[1]) - 1;

                    // Error: index out of bounds
                    if (index < 0 || index >= count) {
                        showError("That task number is out of range.");
                        continue;
                    }

                    // Mark task as not done
                    tasks[index].markUndone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks[index]);

                } catch (NumberFormatException e) {
                    // Error: non-integer task number
                    showError("Task number must be an integer. Example: unmark 2");
                }
                continue;
            }

            //TODO COMMAND
            if (input.equals("todo") || input.startsWith("todo ")) {
                // Extract description after "todo"
                String desc = input.length() > 4 ? input.substring(4).trim() : "";

                // Error: empty description
                if (desc.isEmpty()) {
                    showError("The description of a todo cannot be empty.");
                    continue;
                }

                // Create and store Todo task
                tasks[count] = new Todo(desc);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[count]);
                count++;
                System.out.println("Now you have " + count + " tasks in the list.");
                continue;
            }

            //DEADLINE COMMAND
            if (input.equals("deadline") || input.startsWith("deadline ")) {
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
                tasks[count] = new Deadline(desc, by);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[count]);
                count++;
                System.out.println("Now you have " + count + " tasks in the list.");
                continue;
            }

            //EVENT COMMAND
            if (input.equals("event") || input.startsWith("event ")) {
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
                tasks[count] = new Event(desc, from, to);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[count]);
                count++;
                System.out.println("Now you have " + count + " tasks in the list.");
                continue;
            }

            //UNKNOWN COMMAND
            showError("I don't know what that means. Try: todo, deadline, event, list, mark, unmark, bye");
        }
    }
}

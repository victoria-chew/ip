import java.util.Scanner;

public class Kevin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Task[] tasks = new Task[100]; //list of tasks
        int count = 0;

        //hello
        System.out.println("Hello! I'm Kevin");
        System.out.println("What can I do for you?");

        //echo + list/add + mark + unmark
        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }
            else if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < count; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
            }
            else if (input.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(input.split(" ")[1]);
                int index = taskNumber - 1;

                tasks[index].markDone();

                System.out.println("Nice! I've marked this task as done:");
                System.out.println(tasks[index]);
            }
            else if (input.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.split(" ")[1]);
                int index = taskNumber - 1;

                tasks[index].markUndone();

                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(tasks[index]);
            }
            else if (input.startsWith("todo ")) {
                String desc = input.substring(5).trim();
                tasks[count] = new Todo(desc);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[count]);
                count++;
                System.out.println("Now you have " + count + " tasks in the list.");
            }
            else if (input.startsWith("deadline ")) {
                String rest = input.substring(9).trim(); // after "deadline"
                String[] parts = rest.split(" /by ", 2);
                String desc = parts[0].trim();
                String by = parts[1].trim();

                tasks[count] = new Deadline(desc, by);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[count]);
                count++;
                System.out.println("Now you have " + count + " tasks in the list.");
            }
            else if (input.startsWith("event ")) {
                String rest = input.substring(6).trim(); // after "event"
                String[] partsFrom = rest.split(" /from ", 2);
                String desc = partsFrom[0].trim();

                String[] partsTo = partsFrom[1].split(" /to ", 2);
                String from = partsTo[0].trim();
                String to = partsTo[1].trim();

                tasks[count] = new Event(desc, from, to);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[count]);
                count++;
                System.out.println("Now you have " + count + " tasks in the list.");
            }
            else {
                tasks[count] = new Task(input);
                count++;
                System.out.println("added: " + input);
            }
        }
    }
}


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
                for (int i = 0; i < count; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
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
            else {
                tasks[count] = new Task(input);
                count++;
                System.out.println("added: " + input);
            }
        }
    }
}


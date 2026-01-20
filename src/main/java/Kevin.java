import java.util.Scanner;

public class Kevin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] items = new String[100]; // list of things
        int count = 0;                    // number of items

        //hello
        System.out.println("Hello! I'm Kevin");
        System.out.println("What can I do for you?");

        //echo + list/add
        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }
            else if (input.equals("list")) {
                for (int i = 0; i < count; i++) {
                    System.out.println((i + 1) + ". " + items[i]);
                }
            }
            else {
                items[count] = input;
                count++;
                System.out.println("added: " + input);
            }
        }
    }
}
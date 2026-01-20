import java.util.Scanner;

public class Kevin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //hello
        System.out.println("Hello! I'm Kevin");
        System.out.println("What can I do for you?");

        //echo
        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            System.out.println(input);
        }
    }
}
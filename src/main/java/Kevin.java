import java.util.Scanner;

public class Kevin {

    private final Ui ui;
    private final Parser parser;
    private final TaskList taskList;

    public Kevin() {
        ui = new Ui();
        parser = new Parser();

        Storage storage = new Storage();
        taskList = new TaskList(100, storage);
        taskList.load();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        ui.showWelcome();

        while (true) {
            String input = scanner.nextLine();

            try {
                Command c = parser.parse(input);

                switch (c.getType()) {

                    case BYE:
                        ui.showGoodbye();
                        return; // exits run()

                    case LIST:
                        ui.showList(taskList.formatList());
                        break;

                    case TODO: {
                        Task t = new Todo(c.getDescription());
                        taskList.add(t);                 // TaskList autosaves
                        ui.showAdded(t, taskList.size());
                        break;
                    }

                    case DEADLINE: {
                        Task t = new Deadline(c.getDescription(), c.getBy());
                        taskList.add(t);
                        ui.showAdded(t, taskList.size());
                        break;
                    }

                    case EVENT: {
                        Task t = new Event(c.getDescription(), c.getFrom(), c.getTo());
                        taskList.add(t);
                        ui.showAdded(t, taskList.size());
                        break;
                    }

                    case MARK: {
                        Task t = taskList.mark(c.getIndex()); // c.getIndex() is 1-based
                        ui.showMarked(t);
                        break;
                    }

                    case UNMARK: {
                        Task t = taskList.unmark(c.getIndex());
                        ui.showUnmarked(t);
                        break;
                    }

                    case DELETE: {
                        Task removed = taskList.delete(c.getIndex());
                        ui.showDeleted(removed, taskList.size());
                        break;
                    }

                    default:
                        ui.showError("I don't know what that means.");
                }

            } catch (Exception e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Kevin().run();
    }
}

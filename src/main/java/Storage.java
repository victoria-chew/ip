import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {

    // Relative path
    private static final String FILE_PATH = "./data/kevin.txt";

    /**
     * Loads tasks from file into the given array.
     * Returns the number of tasks loaded.
     */

    public int load(Task[] tasks) {
        File file = new File(FILE_PATH);

        // Case 1: file (or folder) does not exist → start empty
        if (!file.exists()) {
            return 0;
        }

        int count = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = parseTask(line);
                if (task != null) {
                    tasks[count] = task;
                    count++;
                }
            }
        } catch (Exception e) {
            // Stretch goal (corrupted file): ignore bad lines safely
            System.out.println("Warning: Failed to load some saved tasks.");
        }

        return count;
    }

    /**
     * Saves the current task list to file.
     */
    public void save(Task[] tasks, int count) throws IOException {
        File file = new File(FILE_PATH);

        // Ensure ./data directory exists
        file.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(file)) {
            for (int i = 0; i < count; i++) {
                writer.write(tasks[i].toFileString() + "\n");
            }
        }
    }

    /**
     * Converts a line from the file into a Task object.
     */
    private Task parseTask(String line) {
        // Format examples:
        // T | 1 | read book
        // D | 0 | return book | Sunday
        // E | 1 | project meeting | Mon 2pm | 4pm

        String[] parts = line.split(" \\| ");

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;

        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                task = new Deadline(description, parts[3]);
                break;
            case "E":
                task = new Event(description, parts[3], parts[4]);
                break;
            default:
                return null; // corrupted line → skip safely
        }

        if (isDone) {
            task.markDone();
        }

        return task;
    }
}

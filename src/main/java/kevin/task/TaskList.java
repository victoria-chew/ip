package kevin.task;

import java.io.IOException;
import kevin.storage.Storage;

public class TaskList {
    private final Task[] tasks;
    private int count;
    private final Storage storage;

    public TaskList(int capacity, Storage storage) {
        this.tasks = new Task[capacity];
        this.count = 0;
        this.storage = storage;
    }

    // Load tasks at startup
    public void load() {
        count = storage.load(tasks);
    }

    public int size() {
        return count;
    }

    public Task get(int oneBasedIndex) {
        int idx = oneBasedIndex - 1;
        if (idx < 0 || idx >= count) {
            throw new IllegalArgumentException("That task number is out of range.");
        }
        return tasks[idx];
    }

    public void add(Task task) {
        if (count >= tasks.length) {
            throw new IllegalStateException("Task list is full.");
        }
        tasks[count] = task;
        count++;
        save();
    }

    public Task delete(int oneBasedIndex) {
        int idx = oneBasedIndex - 1;
        if (idx < 0 || idx >= count) {
            throw new IllegalArgumentException("That task number is out of range.");
        }

        Task removed = tasks[idx];

        for (int i = idx; i < count - 1; i++) {
            tasks[i] = tasks[i + 1];
        }
        tasks[count - 1] = null;
        count--;

        save();
        return removed;
    }

    public Task mark(int oneBasedIndex) {
        Task t = get(oneBasedIndex);
        t.markDone();
        save();
        return t;
    }

    public Task unmark(int oneBasedIndex) {
        Task t = get(oneBasedIndex);
        t.markUndone();
        save();
        return t;
    }

    public String formatList() {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n");
        for (int i = 0; i < count; i++) {
            sb.append(i + 1).append(".").append(tasks[i]).append("\n");
        }
        return sb.toString().trim(); // removes last newline
    }

    private void save() {
        try {
            storage.save(tasks, count);
        } catch (IOException e) {
            // Don't crash the app if saving fails
            // You can also choose to throw a custom exception instead.
            System.out.println("OOPS! Could not save tasks to file.");
        }
    }
}

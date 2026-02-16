package kevin.task;

import java.io.IOException;

import kevin.storage.Storage;
/**
 * Stores and manages a list of tasks in the application.
 * Supports adding, deleting, marking, and unmarking tasks.
 */
public class TaskList {
    private final Task[] tasks;
    private int count;
    private final Storage storage;

    /**
     * Creates a task list with a fixed capacity.
     */
    public TaskList(int capacity, Storage storage) {
        this.tasks = new Task[capacity];
        this.count = 0;
        this.storage = storage;
    }

    /**
     * Loads tasks from storage into this task list.
     */
    public void load() {
        count = storage.load(tasks);
    }

    /**
     * Returns the number of tasks currently in the list.
     *
     * @return Number of tasks in the list.
     */
    public int size() {
        return count;
    }

    /**
     * Returns the task at the given 1-based index.
     *
     * @param oneBasedIndex Index of the task (1-based).
     * @return The task at the specified index.
     * @throws IllegalArgumentException If the index is out of range.
     */
    public Task get(int oneBasedIndex) {
        int idx = oneBasedIndex - 1;

        assert idx == oneBasedIndex - 1 : "Index conversion failed";

        if (idx < 0 || idx >= count) {
            throw new IllegalArgumentException("That task number is out of range.");
        }
        return tasks[idx];
    }

    /**
     * Adds a task to the list and saves the updated list to storage.
     *
     * @param task Task to be added.
     * @throws IllegalStateException If the task list is full.
     */
    public void add(Task task) {
        assert count >= 0 && count <= tasks.length : "TaskList count out of bounds";
        assert task != null : "Cannot add null task";

        if (count >= tasks.length) {
            throw new IllegalStateException("Task list is full.");
        }
        tasks[count] = task;
        count++;

        assert count <= tasks.length : "TaskList overflow after add";

        save();
    }

    /**
     * Deletes the task at the given 1-based index and saves the updated list to storage.
     *
     * @param oneBasedIndex Index of the task to delete (1-based).
     * @return The deleted task.
     * @throws IllegalArgumentException If the index is out of range.
     */
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

    /**
     * Marks the task at the given 1-based index as done and saves the updated list to storage.
     *
     * @param oneBasedIndex Index of the task to mark as done (1-based).
     * @return The updated task.
     * @throws IllegalArgumentException If the index is out of range.
     */
    public Task mark(int oneBasedIndex) {
        Task t = get(oneBasedIndex);
        t.markDone();
        save();
        return t;
    }

    /**
     * Marks the task at the given 1-based index as not done and saves the updated list to storage.
     *
     * @param oneBasedIndex Index of the task to unmark (1-based).
     * @return The updated task.
     * @throws IllegalArgumentException If the index is out of range.
     */
    public Task unmark(int oneBasedIndex) {
        Task t = get(oneBasedIndex);
        t.markUndone();
        save();
        return t;
    }

    /**
     * Formats all tasks in the list into a numbered string for display to the user.
     *
     * @return Formatted string of tasks.
     */
    public String formatList() {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n");
        for (int i = 0; i < count; i++) {
            sb.append(i + 1).append(".").append(tasks[i]).append("\n");
        }
        return sb.toString().trim(); // removes last newline
    }

    /**
     * constructs the list of found tasks
     */
    public Task[] find(String keyword) {
        String needle = keyword.toLowerCase();
        Task[] temp = new Task[count];
        int m = 0;

        for (int i = 0; i < count; i++) {
            if (tasks[i].getDescription().toLowerCase().contains(needle)) {
                temp[m++] = tasks[i];
            }
        }

        Task[] matches = new Task[m];
        System.arraycopy(temp, 0, matches, 0, m);
        return matches;
    }

    private void save() {
        try {
            storage.save(tasks, count);
        } catch (IOException e) {
            //to not crash the app if saving fails
            System.out.println("OOPS! Could not save tasks to file.");
        }
    }
}

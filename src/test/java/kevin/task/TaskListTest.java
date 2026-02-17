package kevin.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import kevin.storage.Storage;

public class TaskListTest {

    /**
     * Test double: prevents TaskList from doing real file I/O during tests.
     * TaskList will still call save(), but it becomes a no-op.
     */
    static class NoOpStorage extends Storage {
        @Override
        public int load(Task[] tasks) {
            return 0; // start empty
        }

        @Override
        public void save(Task[] tasks, int count) throws IOException {
            // do nothing
        }
    }

    private TaskList makeTaskList() {
        return new TaskList(100, new NoOpStorage());
    }

    @Test
    public void add_increasesSize() {
        TaskList list = makeTaskList();

        assertEquals(0, list.size());
        list.add(new Todo("task one"));
        assertEquals(1, list.size());
    }

    @Test
    public void delete_removesCorrectTask() {
        TaskList list = makeTaskList();

        Task t1 = new Todo("task one");
        Task t2 = new Todo("task two");
        list.add(t1);
        list.add(t2);

        // delete is ONE-BASED: delete(2) removes the 2nd task
        Task removed = list.delete(2);

        assertSame(t2, removed);
        assertEquals(1, list.size());
        assertSame(t1, list.get(1)); // remaining first task
    }

    @Test
    public void mark_setsTaskDone() {
        TaskList list = makeTaskList();

        list.add(new Todo("task one"));

        Task marked = list.mark(1); // ONE-BASED
        // Usually Kevin tasks show [X] when done
        assertTrue(marked.toString().contains("[X]"));
    }

    @Test
    public void unmark_setsTaskNotDone() {
        TaskList list = makeTaskList();

        list.add(new Todo("task one"));

        list.mark(1);
        Task unmarked = list.unmark(1);
        // Usually Kevin tasks show [ ] when not done
        assertTrue(unmarked.toString().contains("[ ]"));
    }
}


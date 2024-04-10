package service;

import com.yandex.module4.model.Epic;
import com.yandex.module4.model.Status;
import com.yandex.module4.model.SubTask;
import com.yandex.module4.model.Task;
import com.yandex.module4.service.HistoryManager;
import com.yandex.module4.service.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private Task task;
    private Task task1;
    private Epic epic;
    private SubTask subTask;
    private HistoryManager inMemoryHistoryManager;

    @BeforeEach
    public void beforeEach() {
        inMemoryHistoryManager = Managers.getDefaultHistory();
        task = new Task("TASK", "howToDo TASK text", Status.NEW, 1);
        task1 = new Task("TASK11111", "howToDo TASK text111", Status.NEW, 2);
        epic = new Epic("EPIC", "howToDo EPIC text", Status.NEW, 3);
    }

    @Test
    public void getHistory3valueTest() {
        int result = 1;
        inMemoryHistoryManager.add(task);
        inMemoryHistoryManager.add(epic);
        subTask = new SubTask("SubTask", "howToDo SubTask text", epic, Status.NEW, 4);
        inMemoryHistoryManager.add(subTask);
        if (inMemoryHistoryManager.getHistory().get(0).equals(task)) {
            if (inMemoryHistoryManager.getHistory().get(1).equals(epic)) {
                if (inMemoryHistoryManager.getHistory().get(2).equals(subTask)) {
                    result = 0;
                }
            }
        }
        assertEquals(0, result, "Задачи не попадают в список истории просмотров корректно");
    }

    @Test
    public void getHistoryTest() {
        int result = 1;
        inMemoryHistoryManager.add(task);
        if (inMemoryHistoryManager.getHistory().get(0).equals(task)) {
            result = 0;
        }
        assertEquals(0, result, "Задачи не попадают в список истории просмотров корректно");
    }

    @Test
    public void oldVersionTaskInHistoryTest() {
        int result = 1;
        inMemoryHistoryManager.add(task);
        inMemoryHistoryManager.add(task1);
        if (inMemoryHistoryManager.getHistory().get(0).equals(task)) {
            result = 0;
        }
        assertEquals(0, result, "Копии просмотренных задач не сохраняются в истории");
    }

    @Test
    public void nullWritingInHistory() {
        inMemoryHistoryManager.add(null);
        assertEquals(0, inMemoryHistoryManager.getHistory().size(), "Null записывается в список историй");
    }
}
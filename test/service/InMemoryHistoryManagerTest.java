package service;

import com.yandex.module4.model.Epic;
import com.yandex.module4.model.Status;
import com.yandex.module4.model.SubTask;
import com.yandex.module4.model.Task;
import com.yandex.module4.service.HistoryManager;
import com.yandex.module4.service.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private Task task;
    private Task task1;
    private Task task2;
    private Epic epic;
    private SubTask subTask;
    private HistoryManager inMemoryHistoryManager;

    @BeforeEach
    public void beforeEach() {
        inMemoryHistoryManager = Managers.getDefaultHistory();
        task = new Task("TASK", "howToDo TASK text", Status.NEW, 1, Duration.ofMinutes(55L), LocalDateTime.MIN);
        task1 = new Task("TASK11111", "howToDo TASK text111", Status.NEW, 2, Duration.ofMinutes(55L), LocalDateTime.now());
        task2 = new Task("TASK222221", "howToDo TASK text2222", Status.NEW, 5, Duration.ofMinutes(55L), LocalDateTime.now());
        epic = new Epic("EPIC", "howToDo EPIC text", Status.NEW, 3);
    }

    @Test
    public void getHistory3valueTest() {
        inMemoryHistoryManager.add(task);
        inMemoryHistoryManager.add(epic);
        subTask = new SubTask("SubTask", "howToDo SubTask text", epic, Status.NEW, 4, Duration.ofMinutes(55L), LocalDateTime.of(1999,
                Month.NOVEMBER, 5, 11, 2));
        inMemoryHistoryManager.add(subTask);
        assertEquals(task, inMemoryHistoryManager.getHistory().get(0), "Task не попадают в список истории просмотров корректно");
        assertEquals(epic, inMemoryHistoryManager.getHistory().get(1), "Epic не попадают в список истории просмотров корректно");
        assertEquals(subTask, inMemoryHistoryManager.getHistory().get(2), "SubTask не попадают в список истории просмотров корректно");
    }

    @Test
    public void getHistoryTest() {
        inMemoryHistoryManager.add(task);
        assertEquals(task, inMemoryHistoryManager.getHistory().get(0), "Задачи не попадают в список истории просмотров корректно");
    }

    @Test
    public void oldVersionTaskInHistoryTest() {
        inMemoryHistoryManager.add(task);
        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.add(task);
        assertEquals(task, inMemoryHistoryManager.getHistory().get(1), "Копии просмотренных задач не сохраняются в истории");
    }

    @Test
    public void nullWritingInHistory() {
        inMemoryHistoryManager.add(null);
        assertEquals(0, inMemoryHistoryManager.getHistory().size(), "Null записывается в список историй");
    }

    @Test
    public void lastTaskInHistoryDelete() {
        inMemoryHistoryManager.add(task);
        inMemoryHistoryManager.remove(task.getId());
        assertEquals(0, inMemoryHistoryManager.getHistory().size(), "Задачи удаляются неправильно");
    }

    @Test
    public void firstTaskInHistoryDelete() {
        inMemoryHistoryManager.add(task);
        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.remove(task.getId());
        assertEquals(1, inMemoryHistoryManager.getHistory().size(), "Задачи удаляются неправильно");
    }

    @Test
    public void middleTaskInHistoryDelete() {
        inMemoryHistoryManager.add(task);
        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.remove(task1.getId());
        assertEquals(2, inMemoryHistoryManager.getHistory().size(), "Задачи удаляются неправильно");
    }
}
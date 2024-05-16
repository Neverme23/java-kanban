package service;

import com.yandex.module4.model.Epic;
import com.yandex.module4.model.Status;
import com.yandex.module4.model.SubTask;
import com.yandex.module4.model.Task;
import com.yandex.module4.service.InMemoryTaskManager;
import com.yandex.module4.service.Managers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private Task task;
    private Task task1;
    private Epic epic;
    private SubTask subTask;
    private SubTask subTask2;
    private InMemoryTaskManager inMemoryTaskManager;

    @BeforeEach
    public void beforeEach() {
        inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();
        task = new Task("TASK", "howToDo text", Status.NEW, Duration.ofMinutes(55L), LocalDateTime.of(1996, Month.JUNE, 1, 22, 14));
        task1 = new Task("TASK11111", "howToDo text111", Status.NEW, Duration.ofMinutes(55L), LocalDateTime.of(1994, Month.JUNE, 1, 22, 14));
        epic = new Epic("EPIC", "howToDo text");
    }

    @AfterEach
    public void afterEach() {
        inMemoryTaskManager.removeAllTask();
        inMemoryTaskManager.removeAllSubTask();
        inMemoryTaskManager.removeAllEpic();
    }

    @Test
    public void addTaskTest() {
        int result = 1;
        inMemoryTaskManager.addTask(task);
        if (inMemoryTaskManager.getTaskWithID(task.getId()).get().equals(task)) {
            result = 0;
        }
        assertEquals(0, result, "Задача не добавлена");
    }

    @Test
    public void addEpicTest() {
        int result = 1;
        inMemoryTaskManager.addEpic(epic);
        if (inMemoryTaskManager.getEpicWithID(epic.getId()).get().equals(epic)) {
            result = 0;
        }
        assertEquals(0, result, "Задача не добавлена");
    }

    @Test
    public void addSubTaskTest() {
        int result = 1;
        inMemoryTaskManager.addEpic(epic);
        subTask = new SubTask("SubTask", "howToDo text", epic, Status.NEW, Duration.ofMinutes(12L), LocalDateTime.of(1990, Month.JUNE, 1, 22, 14));
        inMemoryTaskManager.addSubTask(subTask);
        if (inMemoryTaskManager.getSubTaskWithID(subTask.getId()).get().equals(subTask)) {
            result = 0;
        }
        assertEquals(0, result, "Задача не добавлена");
    }

    @Test
    public void checkIDConflict() {
        int result = 0;
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addTask(task1);
        if (inMemoryTaskManager.getTaskWithID(task.getId()).equals(task1)) {
            result = 1;
        }
        assertEquals(0, result, "Задачи конфликтуют");
    }

    @Test
    public void checkTaskConstantTest() {
        inMemoryTaskManager.addTask(task);
        assertEquals(task.getName(), inMemoryTaskManager.getTaskWithID(task.getId()).get().getName(), "name не конфликтует");
        assertEquals(task.getHowToDo(), inMemoryTaskManager.getTaskWithID(task.getId()).get().getHowToDo(), "howToDo не конфликтует");
        assertEquals(task.getId(), inMemoryTaskManager.getTaskWithID(task.getId()).get().getId(), "id не конфликтует");
        assertEquals(task.getStatus(), inMemoryTaskManager.getTaskWithID(task.getId()).get().getStatus(), "status не конфликтует");
    }

    @Test
    public void tasksWriteInHistoryList() {
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addEpic(epic);
        subTask = new SubTask("SubTask", "howToDo text", epic, Status.NEW, Duration.ofMinutes(54L), LocalDateTime.of(1994, Month.MARCH, 1, 22, 14));
        inMemoryTaskManager.addSubTask(subTask);
        inMemoryTaskManager.getTaskWithID(task.getId());
        inMemoryTaskManager.getEpicWithID(epic.getId());
        inMemoryTaskManager.getSubTaskWithID(subTask.getId());
        assertEquals(3, inMemoryTaskManager.getHistory().size(), "Задачи записываются в историю");
    }

    @Test
    public void taskWriteInHistoryList() {
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.getTaskWithID(task.getId());
        assertEquals(1, inMemoryTaskManager.getHistory().size(), "Задачи записываются в историю");
    }

    @Test
    public void historyListIs0() {
        assertEquals(0, inMemoryTaskManager.getHistory().size(), "Задачи записываются в историю");
    }

    @Test
    public void epicStatusInProgressTest() {
        int result = 0;
        inMemoryTaskManager.addEpic(epic);
        subTask = new SubTask("SubTask", "howToDo SubTask text", epic, Status.NEW, Duration.ofMinutes(55L), LocalDateTime.of(1999,
                Month.NOVEMBER, 5, 11, 2));
        subTask2 = new SubTask("SubTask2", "howToDo SubTask text2", epic, Status.DONE, Duration.ofMinutes(55L), LocalDateTime.of(1992,
                Month.NOVEMBER, 5, 11, 2));
        inMemoryTaskManager.addSubTask(subTask);
        inMemoryTaskManager.addSubTask(subTask2);
        System.out.println(epic.getStatus());
        if (epic.getStatus().equals(Status.IN_PROGRESS)) {
            result = 1;
        }
        assertEquals(1, result, "Неправильно расчитывается статус");
    }

    @Test
    public void epicStatusDoneTest() {
        int result = 0;
        inMemoryTaskManager.addEpic(epic);
        subTask = new SubTask("SubTask", "howToDo SubTask text", epic, Status.DONE, Duration.ofMinutes(55L), LocalDateTime.of(1999,
                Month.NOVEMBER, 5, 11, 2));
        subTask2 = new SubTask("SubTask2", "howToDo SubTask text2", epic, Status.DONE, Duration.ofMinutes(55L), LocalDateTime.of(1992,
                Month.NOVEMBER, 5, 11, 2));
        inMemoryTaskManager.addSubTask(subTask);
        inMemoryTaskManager.addSubTask(subTask2);
        System.out.println(epic.getStatus());
        if (epic.getStatus().equals(Status.DONE)) {
            result = 1;
        }
        assertEquals(1, result, "Неправильно расчитывается статус");
    }

    @Test
    public void epicStatusNewTest() {
        int result = 0;
        inMemoryTaskManager.addEpic(epic);
        subTask = new SubTask("SubTask", "howToDo SubTask text", epic, Status.NEW, Duration.ofMinutes(55L), LocalDateTime.of(1999,
                Month.NOVEMBER, 5, 11, 2));
        inMemoryTaskManager.addSubTask(subTask);
        if (epic.getStatus().equals(Status.NEW)) {
            result = 1;
        }
        assertEquals(1, result, "Неправильно расчитывается статус");
    }

    @Test
    public void epicStatusInProgressTest2() {
        int result = 0;
        inMemoryTaskManager.addEpic(epic);
        subTask = new SubTask("SubTask", "howToDo SubTask text", epic, Status.IN_PROGRESS, Duration.ofMinutes(55L), LocalDateTime.of(1999,
                Month.NOVEMBER, 5, 11, 2));
        inMemoryTaskManager.addSubTask(subTask);
        if (epic.getStatus().equals(Status.IN_PROGRESS)) {
            result = 1;
        }
        assertEquals(1, result, "Неправильно расчитывается статус");
    }

    @Test
    public void epicInSubTaskTest() {
        int result = 0;
        inMemoryTaskManager.addEpic(epic);
        subTask = new SubTask("SubTask", "howToDo SubTask text", epic, Status.IN_PROGRESS, Duration.ofMinutes(55L), LocalDateTime.of(1999,
                Month.NOVEMBER, 5, 11, 2));
        inMemoryTaskManager.addSubTask(subTask);
        if (inMemoryTaskManager.getEpicWithID(subTask.getEpic()).get() == epic) {
            result = 1;
        }
        assertEquals(1, result, "SubTask бех эпика");
    }

    @Test
    public void timeDurationTest() {
        int result = 0;
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addTask(task1);
        if (!(inMemoryTaskManager.taskTimeFilter(task, task1))) {
            result = 1;
        }
        assertEquals(1, result, "Фильтр пересечения по времени работает неправильно");
    }
}
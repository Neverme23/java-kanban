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

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private  Task task;
    private  Task task1;
    private  Epic epic;
    private  SubTask subTask;
    private InMemoryTaskManager inMemoryTaskManager;
    @BeforeEach
    public void beforeEach(){
        inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();
        task = new Task("TASK", "howToDo text", Status.NEW);
        task1 = new Task("TASK11111", "howToDo text111", Status.NEW);
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
        if (inMemoryTaskManager.getTaskWithID(task.getId()).equals(task)) {
            result = 0;
        }
        assertEquals(0, result, "Задача не добавлена");
    }
    @Test
    public void addEpicTest() {
        int result = 1;
        inMemoryTaskManager.addEpic(epic);
        if (inMemoryTaskManager.getEpicWithID(epic.getId()).equals(epic)) {
            result = 0;
        }
        assertEquals(0, result, "Задача не добавлена");
    }
    @Test
    public void addSubTaskTest() {
        int result = 1;
        inMemoryTaskManager.addEpic(epic);
        subTask = new SubTask("SubTask", "howToDo text", epic, Status.NEW);
        inMemoryTaskManager.addSubTask(subTask);
        if (inMemoryTaskManager.getSubTaskWithID(subTask.getId()).equals(subTask)) {
            result = 0;
        }
        assertEquals(0, result, "Задача не добавлена");
    }
    @Test
    public void checkIDConflict() {
        int result = 0;
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addTask(task1);
        if(inMemoryTaskManager.getTaskWithID(task.getId()).equals(task1)){
            result = 1;
        }
        assertEquals(0, result, "Задачи конфликтуют");
    }
    @Test
    public void checkTaskConstantTest() {
        int result = 1;
        inMemoryTaskManager.addTask(task);
        if(inMemoryTaskManager.getTaskWithID(task.getId()).getName().equals(task.getName())) {
            if(inMemoryTaskManager.getTaskWithID(task.getId()).getHowToDo().equals(task.getHowToDo())) {
                if(inMemoryTaskManager.getTaskWithID(task.getId()).getId() == task.getId()) {
                    if(inMemoryTaskManager.getTaskWithID(task.getId()).getStatus() == task.getStatus()) {
                        result = 0;
                    }
                }
            }
        }
        assertEquals(0, result, "Задачи не меняются");
    }
    @Test
    public void tasksWriteInHistoryList() {
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addEpic(epic);
        subTask = new SubTask("SubTask", "howToDo text", epic, Status.NEW);
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
    public void historyListIs0(){
        assertEquals(0, inMemoryTaskManager.getHistory().size(), "Задачи записываются в историю");
    }
}
package com.yandex.module4.service;

import com.yandex.module4.model.Epic;
import com.yandex.module4.model.Status;
import com.yandex.module4.model.SubTask;
import com.yandex.module4.model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private Task task;
    private  Task task1;
    private Epic epic;
    private  SubTask subTask;
    private  InMemoryTaskManager inMemoryTaskManager;

    @BeforeEach
    public void beforeEach(){
        inMemoryTaskManager = (InMemoryTaskManager)Managers.getDefault();
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
    public void getHistory3valueTest(){
        int result = 1;
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.addEpic(epic);
        subTask = new SubTask("Text", "howToDo text", epic, Status.NEW);
        inMemoryTaskManager.addSubTask(subTask);
        inMemoryTaskManager.getTaskWithID(task.getId());
        inMemoryTaskManager.getEpicWithID(epic.getId());
        inMemoryTaskManager.getSubTaskWithID(subTask.getId());
        if(inMemoryTaskManager.getHistory().get(0).equals(task)) {
            if(inMemoryTaskManager.getHistory().get(1).equals(epic)) {
                if(inMemoryTaskManager.getHistory().get(2).equals(subTask)) {
                    result = 0;
                }
            }
        }
        assertEquals(0, result, "Задачи не попадают в список истории просмотров корректно");
    }
    @Test
    public void getHistoryTest(){
        int result = 1;
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.getTaskWithID(task.getId());
        if(inMemoryTaskManager.getHistory().get(0).equals(task)) {
            result = 0;
        }
        assertEquals(0, result, "Задачи не попадают в список истории просмотров корректно");
    }

    @Test
    public void OldVersionTaskInHistoryTest() {
        int result = 1;
        inMemoryTaskManager.addTask(task);
        inMemoryTaskManager.getTaskWithID(task.getId());
        inMemoryTaskManager.removeAllTask();
        inMemoryTaskManager.addTask(task1);
        if (inMemoryTaskManager.getHistory().get(0).equals(task)) {
            result = 0;
        }
        assertEquals(0, result, "Копии просмотренных задач не сохраняются в истории");
    }
    @Test
    public void OldVersionSubTaskInHistoryTest() {
        int result = 1;
        inMemoryTaskManager.addEpic(epic);
        subTask = new SubTask("Text", "howToDo text", epic, Status.NEW);
        inMemoryTaskManager.addSubTask(subTask);
        inMemoryTaskManager.getSubTaskWithID(subTask.getId());
        SubTask subTask1 = new SubTask("Text1111", "howToDo text11111", epic, Status.NEW);
        inMemoryTaskManager.changeSubTask(subTask1);
        if (inMemoryTaskManager.getHistory().get(0).equals(subTask)) {
            result = 0;
        }
        assertEquals(0, result, "Копии просмотренных задач не сохраняются в истории");
    }
    @Test
    public void HistoryEreseFirstTaskAfter10Tasks() {
        int i = 0;
        inMemoryTaskManager.addTask(task);
        while (i < 11) {
            inMemoryTaskManager.getTaskWithID(task.getId());
            i++;
        }
        assertEquals(10,inMemoryTaskManager.getHistory().size(), "История обновляется после 10 просмотров");
    }

}
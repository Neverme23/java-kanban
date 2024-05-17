package com.yandex.module4.service;

import com.yandex.module4.model.Epic;
import com.yandex.module4.model.SubTask;
import com.yandex.module4.model.Task;
import java.util.List;
import java.util.Optional;


public interface TaskManager {
    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubTask(SubTask subTask);

    List<Task> getTasks();

    List<SubTask> getSubTasks();

    List<Epic> getEpics();

    void changeTask(Task task);

    void changeSubTask(SubTask subTask);

    void changeEpic(Epic epic);

    void removeAllTask();

    void removeAllSubTask();

    Optional<Task> getTaskWithID(int id);

    Optional<SubTask> getSubTaskWithID(int id);

    Optional<Epic> getEpicWithID(int id);

    void removeTaskWithID(int id);

    void removeSubTaskWithID(int id);

    void removeEpicWithID(int id);

    List<SubTask> getEpicTasks(int id);

    int getCountID();

    List<Task> getHistory();

    void removeAllEpic();

}

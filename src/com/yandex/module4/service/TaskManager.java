package com.yandex.module4.service;

import com.yandex.module4.model.Epic;
import com.yandex.module4.model.SubTask;
import com.yandex.module4.model.Task;

import java.util.ArrayList;

public interface TaskManager {
    void addTask(Task task);
    void addEpic(Epic epic);
    void addSubTask(SubTask subTask);
    ArrayList<Task> getTasks();
    ArrayList<SubTask> getSubTasks();
    ArrayList<Epic> getEpics();
    void changeTask(Task task);
    void changeSubTask(SubTask subTask);
    void changeEpic(Epic epic);
    void removeAllTask();
    void removeAllSubTask();
    Task getTaskWithID(int id);
    SubTask getSubTaskWithID(int id);
    Epic getEpicWithID(int id);
    void removeTaskWithID(int id);
    void removeSubTaskWithID(int id);
    void removeEpicWithID(int id);
    ArrayList<SubTask> getEpicTasks(int id);
    int getCountID();
    ArrayList <Task> getHistory();
    void removeAllEpic();

}

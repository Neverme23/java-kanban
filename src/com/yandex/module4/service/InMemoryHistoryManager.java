package com.yandex.module4.service;

import com.yandex.module4.model.Epic;
import com.yandex.module4.model.SubTask;
import com.yandex.module4.model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private ArrayList<Task> historyTasks;

    InMemoryHistoryManager(){
        historyTasks = new ArrayList<>();
    }
    @Override
    public void add(Task task) {
        Task copyTask = new Task(task);
        historyTasks.add(copyTask);
        checkHistory();
    }
    public void add(Epic epic) {
        Epic copyEpic = new Epic(epic);
        historyTasks.add(copyEpic);
        checkHistory();
    }
    public void add(SubTask subTask) {
        SubTask copySubTask = new SubTask(subTask);
        historyTasks.add(copySubTask);
        checkHistory();
    }
    @Override
    public ArrayList<Task> getHistory() {
        return historyTasks;
    }
    public void checkHistory() {
        if (historyTasks.size() > 10) {
            historyTasks.remove(0);
            historyTasks.trimToSize();
        }
    }
}


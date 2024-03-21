package com.yandex.module4.service;

import com.yandex.module4.model.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> historyTasks;
    private final static int MAX_VALUE = 10;


    InMemoryHistoryManager(){
        historyTasks = new LinkedList<>();
    }
    @Override
    public void add(Task task) {
        if (task != null) {
            historyTasks.add(task);
            checkHistory();
        }
    }

    @Override
    public List<Task> getHistory() {
        return List.copyOf(historyTasks);
    }
    public void checkHistory() {
        if (historyTasks.size() > MAX_VALUE) {
            historyTasks.remove(0);
        }
    }
}


package com.yandex.module4.service;

import com.yandex.module4.model.Task;

import java.util.List;

public interface HistoryManager {

    void add(Task task);

    List<Task> getHistory();

    void removeNode(Node<Task> node);

    void removeAllEpic();

    void removeAllTask();

    void removeAllSubTask();

    void remove(int id);
}

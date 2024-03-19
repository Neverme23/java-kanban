package com.yandex.module4.service;

import com.yandex.module4.model.Task;

import java.util.ArrayList;

public interface HistoryManager {
    void add (Task task);
    ArrayList <Task> getHistory();
}

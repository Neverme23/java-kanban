package com.yandex.module4.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> tasks;

    public Epic(String name, String howToDo) {
        super(name, howToDo);
        tasks = new ArrayList<>();
    }
    public Epic(Epic epic) {
        super(epic);
        tasks = new ArrayList<>();
    }
    public Epic(String name, String howToDo,Status status, int id) {
        super(name, howToDo, status, id);
        tasks = new ArrayList<>();
    }
    public List <Integer> getTasks () {
        return tasks;
    }
    public void addTask (SubTask subTask) {
        tasks.add(subTask.getId());
    }
}

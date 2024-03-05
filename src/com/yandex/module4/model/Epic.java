package com.yandex.module4.model;

import java.util.ArrayList;
public class Epic extends Task {
    private final ArrayList <Integer> tasks;

    public Epic(String name, String howToDo) {
        super(name, howToDo);
        tasks = new ArrayList<>();
    }

    public ArrayList <Integer> getTasks () {
        return tasks;
    }
    public void addTask (SubTask subTask) {
        tasks.add(subTask.getId());
    }
}

package com.yandex.module4.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> tasks;


    private LocalDateTime endTime;

    public Epic(String name, String howToDo) {
        super(name, howToDo, Duration.ZERO, null);
        tasks = new ArrayList<>();
    }

    public Epic(String name, String howToDo, Status status, int id) {
        super(name, howToDo, status, id, Duration.ZERO, null);
        tasks = new ArrayList<>();
    }
    public Epic(String name, String howToDo, Status status, int id, LocalDateTime startTime, Duration duration) {
        super(name, howToDo, status, id, duration, startTime);
        tasks = new ArrayList<>();
    }
    public Epic(String name, String howToDo, Status status, LocalDateTime startTime, Duration duration) {
        super(name, howToDo, status, duration, startTime);
        tasks = new ArrayList<>();
    }

    public Epic(String name, String howToDo, Status status, int id,Duration duration, LocalDateTime startTime, LocalDateTime endTime) {
        super(name, howToDo, status, id, duration, startTime);
        this.endTime = endTime;
        tasks = new ArrayList<>();
    }

    public List<Integer> getTasks() {
        return tasks;
    }

    public void addTask(SubTask subTask) {
        tasks.add(subTask.getId());
    }
    @Override
    public LocalDateTime getEndTime() {
        return this.endTime;
    }
    public void setEndTime(LocalDateTime localDateTime) {
        this.endTime = localDateTime;
    }
}

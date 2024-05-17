package com.yandex.module4.model;


import java.time.Duration;
import java.time.LocalDateTime;

public class Task {
    private String name;
    private String howToDo;
    private int id;
    private Status status;
    private Duration duration;
    private LocalDateTime startTime;

    public Task(String name, String howToDo, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.howToDo = howToDo;
        this.duration = duration;
        this.startTime = startTime;
        this.status = Status.NEW;
    }

    public Task(Task task) {
        this.name = task.name;
        this.howToDo = task.howToDo;
        this.status = task.status;
        this.id = task.id;
        this.duration = task.duration;
        this.startTime = task.startTime;
    }

    public Task(String name, String howToDo, Status status,Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.howToDo = howToDo;
        this.startTime = startTime;
        this.status = status;
        this.duration = duration;
    }

    public Task(String name, String howToDo, Status status, int id, Duration duration, LocalDateTime startTime) {
        this(name, howToDo,status,duration, startTime);
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHowToDo() {
        return howToDo;
    }

    public void setHowToDo(String howToDo) {
        this.howToDo = howToDo;
    }

    public int getId() {
        int a = id;
        return a;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Task t = (Task) obj;
        if (this.id == t.id) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 29 * id;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

}

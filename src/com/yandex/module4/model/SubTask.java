package com.yandex.module4.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {
    private final int epic;

    public SubTask(String name, String howToDo, Epic epic, Duration duration, LocalDateTime startTime) {
        super(name, howToDo, duration, startTime);
        this.epic = epic.getId();
    }

    public SubTask(String name, String howToDo, Epic epic, Status status, Duration duration, LocalDateTime startTime) {
        super(name, howToDo, status,duration, startTime);
        this.epic = epic.getId();
    }

    public SubTask(String name, String howToDo, Epic epic, Status status, int id, Duration duration, LocalDateTime startTime) {
        super(name, howToDo, status, id, duration, startTime);
        this.epic = epic.getId();
    }

    public int getEpic() {
        return epic;
    }
}

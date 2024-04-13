package com.yandex.module4.model;

public class SubTask extends Task {
    private final int epic;

    public SubTask(String name, String howToDo, Epic epic) {
        super(name, howToDo);
        this.epic = epic.getId();
    }

    public SubTask(String name, String howToDo, Epic epic, Status status) {
        super(name, howToDo, status);
        this.epic = epic.getId();
    }

    public SubTask(String name, String howToDo, Epic epic, Status status, int id) {
        super(name, howToDo, status, id);
        this.epic = epic.getId();
    }


    public int getEpic() {
        return epic;
    }
}

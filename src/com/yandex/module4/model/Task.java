package com.yandex.module4.model;

public class Task {
    private String name;
    private String howToDo;
    private int id;
    private Status status;

    public Task (String name, String howToDo) {
        this.name = name;
        this.howToDo = howToDo;
        this.status = Status.NEW;
    }
    public Task (String name, String howToDo, Status status) {
        this.name = name;
        this.howToDo = howToDo;
        this.status =status;
    }
    public Task (String name, String howToDo, Status status, int id) {
        this(name, howToDo, status);
        this.id = id;
    }


    public String getName () {
        return name;
    }
    public void setName (String name) {
        this.name = name;
    }
    public String getHowToDo () {
        return howToDo;
    }
    public void setHowToDo (String howToDo) {
        this.howToDo = howToDo;
    }
    public int getId () {
        int a = id;
        return a;
    }
    public void setId (int id) {
        this.id = id;
    }
    public Status getStatus () {
        return status;
    }
    public void setStatus (Status status) {
        this.status = status;
    }
    @Override
    public boolean equals (Object obj) {
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
    public int hashCode () {
        return 29*id;
    }


}

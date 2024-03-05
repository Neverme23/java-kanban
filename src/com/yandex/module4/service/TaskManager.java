package com.yandex.module4.service;

import com.yandex.module4.model.Epic;
import com.yandex.module4.model.SubTask;
import com.yandex.module4.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int countID = 1;
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, SubTask> subTasks;
    private HashMap<Integer, Epic> epics;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.epics = new HashMap<>();
    }

    public void addTask (Task t) {
        t.setId(countID);
        tasks.put(t.getId(), t);
        countID++;
    }

    public Task getTask (int countID) {
        return tasks.get(countID);
    }

    public void addEpic (Epic t) {
        t.setId(countID);
        epics.put(t.getId(), t);
        countID++;
    }
    public Epic getEpic (int countID) {
        return epics.get(countID);
    }
    public void addSubTask(SubTask st) {
        st.setId(countID);
        subTasks.put(st.getId(), st);
        epics.get(st.getEpic()).addTask(st);
        changeStatus(st, st.getStatus());
        countID++;
    }
    public SubTask getSubTask(int countID) {
        return subTasks.get(countID);
    }
    public int getCountID() {
        return countID;
    }
    public ArrayList <Task> printTask () {
        ArrayList <Task> task = new ArrayList<>();
        for (Task t:tasks.values()) {
            task.add(t);
        }
        return task;
        }
    public ArrayList <SubTask> printSubTask () {
        ArrayList <SubTask> subTask = new ArrayList<>();
        for (SubTask t:subTasks.values()) {
            subTask.add(t);
        }
        return subTask;
    }
    public ArrayList <Epic> printEpic () {
        ArrayList <Epic> epic = new ArrayList<>();
        for (Epic t:epics.values()) {
            epic.add(t);
        }
        return epic;
    }
    public void changeTask(Task t) {
        tasks.put(t.getId(), t);
    }
    public void changeSubTask(SubTask t) {
        subTasks.put(t.getId(),t);
        changeStatus(t, t.getStatus());
    }
    public void changeEpic(Epic t) {
        epics.put(t.getId(), t);
    }
    public void removeAllTask () {
        tasks.clear();
    }
    public void removeAllSubTask () {
        for (SubTask st:subTasks.values()) {
            epics.get(st.getEpic()).getTasks().clear();
        }
        subTasks.clear();

    }
    public void removeAllEpic () {
        for (Epic e:epics.values()) {
            e.getTasks().clear();
        }
        subTasks.clear();
        epics.clear();
    }
    public Task getTaskWithID (int id) {
        return tasks.get(id);
    }
    public SubTask getSubTaskWithID (int id) {
        return subTasks.get(id);
    }
    public Epic getEpicWithID (int id) {
        return epics.get(id);
    }
    public void removeTaskWithID (int id) {
        tasks.remove(id);
    }
    public void removeSubTaskWithID (int id) {
        epics.get(subTasks.get(id).getEpic()).getTasks().remove((Integer) id);
        subTasks.remove(id);
    }
    public void removeEpicWithID (int id) {
        epics.get(id).getTasks().clear();
        epics.remove(id);
    }
    public ArrayList <SubTask> getEpicTasks (int id) {
        ArrayList <SubTask> st = new ArrayList<>();
        for (int i :epics.get(id).getTasks()) {
            st.add(subTasks.get(i));
        }
        return st;
    }
    private void changeStatus(SubTask st, Status status) {
        st.setStatus(status);
        if (status == Status.IN_PROGRESS) {
            epics.get(st.getEpic()).setStatus(status);
        }
        for (int i : epics.get(st.getEpic()).getTasks()) {
            if (subTasks.get(i).getStatus() != Status.DONE) {
                return;
            } else {
                epics.get(st.getEpic()).setStatus(Status.DONE);
            }
        }
    }
}





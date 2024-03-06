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

    public void addTask(Task task) {
        task.setId(countID);
        tasks.put(task.getId(), task);
        countID++;
    }

    public void addEpic(Epic epic) {
        epic.setId(countID);
        epics.put(epic.getId(), epic);
        countID++;
    }

    public void addSubTask(SubTask subTask) {
        subTask.setId(countID);
        subTasks.put(subTask.getId(), subTask);
        epics.get(subTask.getEpic()).addTask(subTask);
        changeStatus(epics.get(subTask.getEpic()));
        countID++;
    }

    public int getCountID() {
        return countID;
    }

    public ArrayList<Task> printTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<SubTask> printSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    public ArrayList<Epic> printEpics() {
        return new ArrayList<>(epics.values());
    }

    public void changeTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void changeSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        changeStatus(epics.get(subTask.getEpic()));
    }

    public void changeEpic(Epic epic) {
        Epic oldEpic = epics.get(epic.getId());
        oldEpic.setName(epic.getName());
        oldEpic.setHowToDo(epic.getHowToDo());
    }

    public void removeAllTask() {
        tasks.clear();
    }

    public void removeAllSubTask() {
        for (Epic epic : epics.values()) {
            epic.getTasks().clear();
            changeStatus(epic);
        }
        subTasks.clear();
    }

    public void removeAllEpic() {
        subTasks.clear();
        epics.clear();
    }

    public Task getTaskWithID(int id) {
        return tasks.get(id);
    }

    public SubTask getSubTaskWithID(int id) {
        return subTasks.get(id);
    }

    public Epic getEpicWithID(int id) {
        return epics.get(id);
    }

    public void removeTaskWithID(int id) {
        tasks.remove(id);
    }

    public void removeSubTaskWithID(int id) {
        Epic epic = epics.get(subTasks.get(id).getEpic());
        epics.get(subTasks.get(id).getEpic()).getTasks().remove((Integer) id);
        subTasks.remove(id);
        changeStatus(epic);
    }

    public void removeEpicWithID(int id) {
        for (int i :epics.get(id).getTasks()) {
            subTasks.remove(i);
        }
        epics.remove(id);
    }

    public ArrayList<SubTask> getEpicTasks(int id) {
        ArrayList<SubTask> st = new ArrayList<>();
        for (int i : epics.get(id).getTasks()) {
            st.add(subTasks.get(i));
        }
        return st;
    }

    private void changeStatus(Epic epic) {
        int doneCount = 0;
        int inProgressCount = 0;
        for (Integer i : epic.getTasks()) {
            if (subTasks.get(i).getStatus() == Status.DONE) {
                doneCount++;
            } else if (subTasks.get(i).getStatus() == Status.IN_PROGRESS) {
                inProgressCount++;
            }
        }
        if (doneCount == epic.getTasks().size()) {
            epic.setStatus(Status.DONE);
        } else if (inProgressCount > 0) {
            epic.setStatus(Status.IN_PROGRESS);
        } else {
            epic.setStatus(Status.NEW);
        }
    }
}





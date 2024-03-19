package com.yandex.module4.service;

import com.yandex.module4.model.Epic;
import com.yandex.module4.model.Status;
import com.yandex.module4.model.SubTask;
import com.yandex.module4.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private int countID = 1;
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, SubTask> subTasks;
    private HashMap<Integer, Epic> epics;
    private InMemoryHistoryManager inMemoryHistoryManager;

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.epics = new HashMap<>();
        inMemoryHistoryManager = (InMemoryHistoryManager)Managers.getDefaultHistory();
    }

    @Override
    public void addTask(Task task) {
        task.setId(countID);
        tasks.put(task.getId(), task);
        countID++;
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(countID);
        epics.put(epic.getId(), epic);
        countID++;
    }

    @Override
    public void addSubTask(SubTask subTask) {
        subTask.setId(countID);
        subTasks.put(subTask.getId(), subTask);
        final Epic epic = epics.get(subTask.getEpic());
        epic.addTask(subTask);
        changeStatus(epic);
        countID++;
    }

    @Override
    public int getCountID() {
        return countID;
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void changeTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void changeSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        changeStatus(epics.get(subTask.getEpic()));
    }

    @Override
    public void changeEpic(Epic epic) {
        Epic oldEpic = epics.get(epic.getId());
        oldEpic.setName(epic.getName());
        oldEpic.setHowToDo(epic.getHowToDo());
    }

    @Override
    public void removeAllTask() {
        tasks.clear();
    }

    @Override
    public void removeAllSubTask() {
        for (Epic epic : epics.values()) {
            epic.getTasks().clear();
            changeStatus(epic);
        }
        subTasks.clear();
    }

    @Override
    public void removeAllEpic() {
        subTasks.clear();
        epics.clear();
    }

    @Override
    public Task getTaskWithID(int id) {
        inMemoryHistoryManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public SubTask getSubTaskWithID(int id) {
        inMemoryHistoryManager.add(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public Epic getEpicWithID(int id) {
        inMemoryHistoryManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public void removeTaskWithID(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeSubTaskWithID(int id) {
        final SubTask subTask = subTasks.remove(id);
        final Epic epic = epics.get(subTask.getEpic());
        epic.getTasks().remove((Integer) id);
        changeStatus(epic);
    }

    @Override
    public void removeEpicWithID(int id) {
        for (int i : epics.get(id).getTasks()) {
            subTasks.remove(i);
        }
        epics.remove(id);
    }

    @Override
    public ArrayList<SubTask> getEpicTasks(int id) {
        ArrayList<SubTask> st = new ArrayList<>();
        for (int i : epics.get(id).getTasks()) {
            st.add(subTasks.get(i));
        }
        return st;
    }

    private void changeStatus(Epic epic) {
        if (epic.getTasks().size() == 0) {
            epic.setStatus(Status.NEW);
            return;
        }
        int count = 0;
        for (Integer i : epic.getTasks()) {
            final Status status = subTasks.get(i).getStatus();
            if (status == Status.DONE) {
                count++;
            } else if (status == Status.IN_PROGRESS) {
                epic.setStatus(Status.IN_PROGRESS);
                return;
            }
        }
        if (count == epic.getTasks().size()) {
            epic.setStatus(Status.DONE);
        } else if (count > 0) {
            epic.setStatus(Status.IN_PROGRESS);
        } else {
            epic.setStatus(Status.NEW);
        }
    }
    @Override
    public ArrayList<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }
}





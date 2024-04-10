package com.yandex.module4.service;

import com.yandex.module4.model.Epic;
import com.yandex.module4.model.Status;
import com.yandex.module4.model.SubTask;
import com.yandex.module4.model.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int countID = 1;
    private final Map<Integer, Task> tasks;
    private final Map<Integer, SubTask> subTasks;
    private final Map<Integer, Epic> epics;
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.epics = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
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
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public List<Epic> getEpics() {
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
        historyManager.removeAllTask();
    }

    @Override
    public void removeAllSubTask() {
        for (Epic epic : epics.values()) {
            epic.getTasks().clear();
            changeStatus(epic);
        }
        subTasks.clear();
        historyManager.removeAllSubTask();
    }

    @Override
    public void removeAllEpic() {
        subTasks.clear();
        epics.clear();
        historyManager.removeAllEpic();
    }

    @Override
    public Task getTaskWithID(int id) {
        final Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public SubTask getSubTaskWithID(int id) {
        final SubTask subTask = subTasks.get(id);
        historyManager.add(subTask);
        return subTask;
    }

    @Override
    public Epic getEpicWithID(int id) {
        final Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public void removeTaskWithID(int id) {
        historyManager.remove(id);
        tasks.remove(id);
    }

    @Override
    public void removeSubTaskWithID(int id) {
        historyManager.remove(id);
        final SubTask subTask = subTasks.remove(id);
        final Epic epic = epics.get(subTask.getEpic());
        epic.getTasks().remove((Integer) id);
        changeStatus(epic);
    }

    @Override
    public void removeEpicWithID(int id) {
        for (int i : epics.get(id).getTasks()) {
            subTasks.remove(i);
            historyManager.remove(i);
        }
        epics.remove(id);
        historyManager.remove(id);
    }

    @Override
    public List<SubTask> getEpicTasks(int id) {
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
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}





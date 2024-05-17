package com.yandex.module4.service;

import com.yandex.module4.model.Epic;
import com.yandex.module4.model.Status;
import com.yandex.module4.model.SubTask;
import com.yandex.module4.model.Task;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int countID = 1;
    private final Map<Integer, Task> tasks;
    private final Map<Integer, SubTask> subTasks;
    private final Map<Integer, Epic> epics;
    private final Set<Task> sortedTask;
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.sortedTask = new TreeSet<>((o1, o2) -> {
            if (o1.getStartTime().isBefore(o2.getStartTime())) {
                return 1;
            } else if (o1.getStartTime().isAfter(o2.getStartTime())) {
                return -1;
            } else {
                return 0;
            }
        });
        historyManager = Managers.getDefaultHistory();
    }

    @Override
    public void addTask(Task task) {
        boolean result = sortedTask.stream().anyMatch(task1 -> taskTimeFilter(task, task1));
        if (!result) {
            task.setId(countID);
            tasks.put(task.getId(), task);
            countID++;
            sortingTask();
        }
    }

    @Override
    public void addEpic(Epic epic) {
        boolean result = sortedTask.stream().anyMatch(task1 -> taskTimeFilter(epic, task1));
        if (!result) {
            epic.setId(countID);
            epics.put(epic.getId(), epic);
            countID++;
            sortingTask();
        }
    }

    @Override
    public void addSubTask(SubTask subTask) {
        boolean result = sortedTask.stream().anyMatch(task1 -> taskTimeFilter(subTask, task1));
        if (!(result)) {
            subTask.setId(countID);
            subTasks.put(subTask.getId(), subTask);
            final Epic epic = epics.get(subTask.getEpic());
            epic.addTask(subTask);
            changeStatus(epic);
            countID++;
            sortingTask();
        }
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
        boolean result = sortedTask.stream().anyMatch(task1 -> taskTimeFilter(task, task1));
        if (!result) {
            tasks.put(task.getId(), task);
            sortingTask();
        }
    }

    @Override
    public void changeSubTask(SubTask subTask) {
        boolean result = sortedTask.stream().anyMatch(task1 -> taskTimeFilter(subTask, task1));
        if (!result) {
            subTasks.put(subTask.getId(), subTask);
            Epic epic = epics.get(subTask.getEpic());
            changeStatus(epic);
            changeEpicDuration(epic);
            changeEpicStartTime(epic);
            changeEpicEndTime(epic);
            sortingTask();
        }
    }

    @Override
    public void changeEpic(Epic epic) {
        boolean result = sortedTask.stream().anyMatch(task1 -> taskTimeFilter(epic, task1));
        if (!result) {
            Epic oldEpic = epics.get(epic.getId());
            oldEpic.setName(epic.getName());
            oldEpic.setHowToDo(epic.getHowToDo());
            sortingTask();
        }
    }

    @Override
    public void removeAllTask() {
        tasks.clear();
        historyManager.removeAllTask();
        sortingTask();
    }

    @Override
    public void removeAllSubTask() {
        epics.values().stream().forEach(epic -> {
            epic.getTasks().clear();
            changeStatus(epic);
            changeEpicDuration(epic);
            changeEpicStartTime(epic);
            changeEpicEndTime(epic);
        });
        subTasks.clear();
        historyManager.removeAllSubTask();
        sortingTask();
    }

    @Override
    public void removeAllEpic() {
        subTasks.clear();
        epics.clear();
        historyManager.removeAllSubTask();
        historyManager.removeAllEpic();
        sortingTask();
    }

    @Override
    public Optional<Task> getTaskWithID(int id) {
        Optional<Task> optional = Optional.ofNullable(tasks.get(id));
        optional.ifPresent(historyManager::add);
        return optional;
    }

    @Override
    public Optional<SubTask> getSubTaskWithID(int id) {
        Optional<SubTask> optional = Optional.ofNullable(subTasks.get(id));
        optional.ifPresent(historyManager::add);
        return optional;
    }

    @Override
    public Optional<Epic> getEpicWithID(int id) {
        Optional<Epic> optional = Optional.ofNullable(epics.get(id));
        optional.ifPresent(historyManager::add);
        return optional;
    }

    @Override
    public void removeTaskWithID(int id) {
        historyManager.remove(id);
        tasks.remove(id);
        sortingTask();
    }

    @Override
    public void removeSubTaskWithID(int id) {
        historyManager.remove(id);
        final SubTask subTask = subTasks.remove(id);
        final Epic epic = epics.get(subTask.getEpic());
        epic.getTasks().remove((Integer) id);
        changeStatus(epic);
        changeEpicDuration(epic);
        changeEpicStartTime(epic);
        changeEpicEndTime(epic);
        sortingTask();
    }

    @Override
    public void removeEpicWithID(int id) {
        epics.get(id).getTasks().stream().forEach(subTaskID -> {
            subTasks.remove(subTaskID);
            historyManager.remove(subTaskID);
        });
        epics.remove(id);
        historyManager.remove(id);
        sortingTask();
    }

    @Override
    public List<SubTask> getEpicTasks(int id) {
        ArrayList<SubTask> st = new ArrayList<>();
        epics.get(id).getTasks().stream().forEach(subTaskID ->
                st.add(subTasks.get(subTaskID))
        );
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

    public void changeEpicDuration(Epic epic) {
        epic.setDuration(Duration.ZERO);
        epic.getTasks().stream().forEach(subTaskID -> {
            SubTask st = subTasks.get(subTaskID);
            epic.setDuration(epic.getDuration().plus(st.getDuration()));
        });
    }

    public void changeEpicStartTime(Epic epic) {
        epic.setStartTime(LocalDateTime.MAX);
        epic.getTasks().stream().forEach(subTaskID -> {
            SubTask st = subTasks.get(subTaskID);
            if (epic.getStartTime().isAfter(st.getStartTime())) {
                epic.setStartTime(st.getStartTime());
            }
        });
    }

    public void changeEpicEndTime(Epic epic) {
        epic.setEndTime(LocalDateTime.MIN);
        epic.getTasks().stream().forEach(subTaskID -> {
            SubTask st = subTasks.get(subTaskID);
            if (epic.getEndTime().isBefore(st.getEndTime())) {
                epic.setEndTime(st.getEndTime());
            }
        });
    }

    public void sortingTask() {
        sortedTask.clear();
        tasks.values().stream()
                .filter(task ->
                        task.getStartTime() != null
                )
                .forEach(sortedTask::add);
        epics.values().stream()
                .filter(epic -> epic.getStartTime() != null)
                .forEach(sortedTask::add);
        subTasks.values().stream()
                .filter(subTask -> subTask.getStartTime() != null)
                .forEach(sortedTask::add);
    }

    public Set<Task> getPrioritizedTasks() {
        return sortedTask;
    }

    public boolean taskTimeFilter(Task t1, Task t2) {
        boolean result = true;
        if (t1 == null || t2 == null) {
            return false;
        }
        if (t1.getStartTime() == null || t2.getStartTime() == null) {
            return false;
        }
        if (t1.getStartTime().isBefore(t2.getStartTime())) {
            if (t1.getStartTime().plus(t1.getDuration()).isBefore(t2.getStartTime())) {
                result = false;
            }
        } else if (t2.getStartTime().isBefore(t1.getStartTime())) {
            if (t2.getStartTime().plus(t2.getDuration()).isBefore(t1.getStartTime())) {
                result = false;
            }
        }
        return result;
    }
}





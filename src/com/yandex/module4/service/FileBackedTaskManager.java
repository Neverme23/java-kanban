package com.yandex.module4.service;

import com.yandex.module4.model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private Path path;

    public FileBackedTaskManager(Path path) {
        this.path = path;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file.toPath());
        if (Files.exists(fileBackedTaskManager.path)) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileBackedTaskManager.path.toFile(), StandardCharsets.UTF_8))) {
                br.readLine();
                while (br.ready()) {
                    fileBackedTaskManager.fromString(br.readLine());
                }
            } catch (IOException e) {
                throw new ManagerLoadException("Ошибка чтения файла");
            }
        }
        return fileBackedTaskManager;
    }

    public void save() throws ManagerSaveException {
        try (FileWriter fw = new FileWriter(path.toFile(), StandardCharsets.UTF_8, false)) {
            int howMuchTasksWrite = 1;
            if (path.toFile().length() < 1) {
                fw.write("class,name,howToDo,ID,status\n");
            }
            while (howMuchTasksWrite < getCountID()) {
                if (getTaskWithID(howMuchTasksWrite).isPresent()) {
                    fw.write(toStrings(getTaskWithID(howMuchTasksWrite).get()) + "\n");
                } else if (getEpicWithID(howMuchTasksWrite).isPresent()) {
                    fw.write(toStrings(getEpicWithID(howMuchTasksWrite).get()) + "\n");
                } else if (getSubTaskWithID(howMuchTasksWrite).isPresent()) {
                    fw.write(toStrings(getSubTaskWithID(howMuchTasksWrite).get()) + "\n");
                }
                howMuchTasksWrite++;
            }
        } catch (FileNotFoundException e) {
            throw new ManagerSaveException("Файл не найден");

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка доступа к файлу");
        }

    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        save();
    }


    @Override
    public void changeTask(Task task) {
        super.changeTask(task);
        save();
    }

    @Override
    public void changeSubTask(SubTask subTask) {
        super.changeSubTask(subTask);
        save();
    }

    @Override
    public void changeEpic(Epic epic) {
        super.changeEpic(epic);
        save();
    }

    @Override
    public void removeAllTask() {
        super.removeAllTask();
        save();
    }

    @Override
    public void removeAllSubTask() {
        super.removeAllSubTask();
        save();
    }

    @Override
    public void removeAllEpic() {
        super.removeAllEpic();
        save();
    }

    @Override
    public void removeTaskWithID(int id) {
        super.removeTaskWithID(id);
        save();
    }

    @Override
    public void removeSubTaskWithID(int id) {
        super.removeSubTaskWithID(id);
        save();
    }

    @Override
    public void removeEpicWithID(int id) {
        super.removeEpicWithID(id);
        save();
    }

    public String toStrings(Task task) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        if (task.getClass() == Task.class) {
            return TypeOfTasks.Task + "," + task.getName() + "," + task.getHowToDo() + "," + task.getId() + ","
                    + task.getStatus() + "," + task.getDuration().toMinutes() + "," + task.getStartTime().format(formatter);
        } else if (task.getClass() == SubTask.class) {
            SubTask st = (SubTask) task;
            return TypeOfTasks.SubTask + "," + st.getName() + "," + st.getHowToDo() + "," + st.getId() + ","
                    + st.getStatus() + "," + st.getEpic() + "," + st.getDuration().toMinutes() + "," + st.getStartTime().format(formatter);
        } else {
            Epic e = (Epic) task;
            return TypeOfTasks.Epic + "," + e.getName() + "," + e.getHowToDo() + "," + e.getId() + "," + e.getStatus()
                    + "," + e.getTasks().toString() + "," + e.getDuration().toMinutes() + "," + e.getStartTime().format(formatter) + "," + e.getEndTime().format(formatter);
        }
    }


    public Task fromString(String task) {
        String[] textTask = task.split(",");
        Status status;
        Task taskFromString;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime timeFromString;
        Duration durationFromString;
        if (textTask[4].equals("NEW")) {
            status = Status.NEW;
        } else if (textTask[4].equals("DONE")) {
            status = Status.DONE;
        } else {
            status = Status.IN_PROGRESS;
        }
        if (textTask[0].equals("Task")) {
            timeFromString = LocalDateTime.parse(textTask[6], formatter);
            durationFromString = Duration.ofMinutes(Long.parseLong(textTask[5]));
            taskFromString = new Task(textTask[1], textTask[2], status, Integer.parseInt(textTask[3]), durationFromString, timeFromString);
            super.addTask(taskFromString);
        } else if (textTask[0].equals("SubTask") && getEpicWithID(Integer.parseInt(textTask[5])).isPresent()) {
            timeFromString = LocalDateTime.parse(textTask[7], formatter);
            durationFromString = Duration.ofMinutes(Long.parseLong(textTask[6]));
            SubTask subTask = new SubTask(textTask[1], textTask[2], getEpicWithID(Integer.parseInt(textTask[5])).get(), status,
                    Integer.parseInt(textTask[3]), durationFromString, timeFromString);
            super.addSubTask(subTask);
            getEpicWithID(Integer.parseInt(textTask[5])).get().addTask(subTask);
            taskFromString = subTask;
        } else {
            timeFromString = LocalDateTime.parse(textTask[7], formatter);
            durationFromString = Duration.ofMinutes(Long.parseLong(textTask[6]));
            LocalDateTime timeOfEndEpic = LocalDateTime.parse(textTask[8], formatter);
            Epic epic = new Epic(textTask[1], textTask[2], status, Integer.parseInt(textTask[3]), durationFromString, timeFromString, timeOfEndEpic);
            super.addEpic(epic);
            taskFromString = epic;
        }
        return taskFromString;
    }
}

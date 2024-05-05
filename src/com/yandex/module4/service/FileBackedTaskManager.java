package com.yandex.module4.service;

import com.yandex.module4.model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
                if (getTaskWithID(howMuchTasksWrite) != null) {
                    fw.write(toStrings(getTaskWithID(howMuchTasksWrite)) + "\n");
                } else if (getEpicWithID(howMuchTasksWrite) != null) {
                    fw.write(toStrings(getEpicWithID(howMuchTasksWrite)) + "\n");
                } else {
                    fw.write(toStrings(getSubTaskWithID(howMuchTasksWrite)) + "\n");
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
        if (task.getClass() == Task.class) {
            return TypeOfTasks.Task + "," + task.getName() + "," + task.getHowToDo() + "," + task.getId() + ","
                    + task.getStatus();
        } else if (task.getClass() == SubTask.class) {
            SubTask st = (SubTask) task;
            return TypeOfTasks.SubTask + "," + st.getName() + "," + st.getHowToDo() + "," + st.getId() + ","
                    + st.getStatus() + "," + st.getEpic();
        } else {
            Epic e = (Epic) task;
            return TypeOfTasks.Epic + "," + e.getName() + "," + e.getHowToDo() + "," + e.getId() + "," + e.getStatus()
                    + "," + e.getTasks().toString();
        }
    }


    public Task fromString(String task) {
        String[] textTask = task.split(",");
        Status status;
        Task taskFromString;
        if (textTask[4].equals("NEW")) {
            status = Status.NEW;
        } else if (textTask[4].equals("DONE")) {
            status = Status.DONE;
        } else {
            status = Status.IN_PROGRESS;
        }
        if (textTask[0].equals("Task")) {
            taskFromString = new Task(textTask[1], textTask[2], status, Integer.parseInt(textTask[3]));
            super.addTask(taskFromString);
        } else if (textTask[0].equals("SubTask")) {
            SubTask subTask = new SubTask(textTask[1], textTask[2], getEpicWithID(Integer.parseInt(textTask[5])),
                    status, Integer.parseInt(textTask[3]));
            super.addSubTask(subTask);
            getEpicWithID(Integer.parseInt(textTask[5])).addTask(subTask);
            taskFromString = subTask;
        } else {
            Epic epic = new Epic(textTask[1], textTask[2], status, Integer.parseInt(textTask[3]));
            super.addEpic(epic);
            taskFromString = epic;
        }
        return taskFromString;
    }

    public static void main(String[] args) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(Paths.get("Tasks.txt"));
        Epic e1 = new Epic("Работать", "Работать работу");
        fileBackedTaskManager.addEpic(e1);
        SubTask st1 = new SubTask("Идти на работу", "На транспорте", e1);
        fileBackedTaskManager.addSubTask(st1);
        SubTask st2 = new SubTask("Идти с работы", "Пешком", e1);
        fileBackedTaskManager.addSubTask(st2);
        Task task = new Task("Выходной", "спать");
        fileBackedTaskManager.addTask(task);
        System.out.println(fileBackedTaskManager.getCountID());
        FileBackedTaskManager fileBackedTaskManager2 = Managers.getFileBackerTaskManager();
        System.out.println(fileBackedTaskManager2.getCountID());
        System.out.println(fileBackedTaskManager.getSubTasks());
        System.out.println(fileBackedTaskManager2.getSubTasks());
        System.out.println(fileBackedTaskManager2.getTasks());
        System.out.println(fileBackedTaskManager.getTasks());
        System.out.println(fileBackedTaskManager.getEpics());
        System.out.println(fileBackedTaskManager.getEpics());

    }
}

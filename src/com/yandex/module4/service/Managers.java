package com.yandex.module4.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Managers {
    private Managers() {
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTaskManager getFileBackerTaskManager() {
        Path path = Paths.get("Tasks.txt");
        if(Files.exists(path)) {
            return FileBackedTaskManager.loadFromFile(path.toFile());
        } else {
            return new FileBackedTaskManager(path);
        }
    }
}


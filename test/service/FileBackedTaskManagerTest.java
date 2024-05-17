package service;

import com.yandex.module4.model.Epic;
import com.yandex.module4.model.Status;
import com.yandex.module4.model.Task;
import com.yandex.module4.service.FileBackedTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class FileBackedTaskManagerTest {

    private Task task;
    private Task task1;
    private Epic epic;
    private FileBackedTaskManager fileBackedTaskManager;

    @BeforeEach
    public void beforeEach() {
        fileBackedTaskManager = new FileBackedTaskManager(Paths.get("Tasks.txt"));
        task = new Task("TASK", "howToDo text", Status.NEW, Duration.ofMinutes(55L), LocalDateTime.now());
        task1 = new Task("TASK11111", "howToDo text111", Status.NEW, Duration.ofMinutes(55L),
                LocalDateTime.of(1999, Month.JUNE, 5, 1, 14));
        epic = new Epic("EPIC", "howToDo text", Status.NEW, 5, Duration.ZERO,
                LocalDateTime.of(1992, Month.NOVEMBER, 4, 2, 12),
                LocalDateTime.of(1993, Month.NOVEMBER, 4, 2, 12));
    }

    @Test
    public void addTaskTest() {
        int result = 1;
        fileBackedTaskManager.addTask(task);
        if (fileBackedTaskManager.getTaskWithID(task.getId()).get().equals(task)) {
            result = 0;
        }
        assertEquals(0, result, "Задача не добавлена");
    }

    @Test
    public void load0TaskTest() {
        int result = 1;
        File file;
        FileBackedTaskManager fileBackedTaskManager2;
        try {
            file = File.createTempFile("Testing", ".txt", null);
            fileBackedTaskManager2 = FileBackedTaskManager.loadFromFile(file);
            file.delete();
            if (fileBackedTaskManager2.getCountID() == 1) {
                result = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(0, result, "Загружается не пустой файл");
    }

    @Test
    public void load3TasksTest() {
        int result = 1;
        File file;
        FileBackedTaskManager fileBackedTaskManager;
        try {
            file = File.createTempFile("Testing", ".txt", null);
            fileBackedTaskManager = new FileBackedTaskManager(file.toPath());
            fileBackedTaskManager.addTask(task);
            fileBackedTaskManager.addTask(task1);
            fileBackedTaskManager.addEpic(epic);
            FileBackedTaskManager fileBackedTaskManager1 = FileBackedTaskManager.loadFromFile(file);
            file.delete();
            if (fileBackedTaskManager1.getCountID() == 4) {
                result = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(0, result, "Загружается неправильно");
    }
}

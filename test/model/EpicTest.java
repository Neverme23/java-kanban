package model;

import com.yandex.module4.model.Epic;
import com.yandex.module4.model.Status;
import com.yandex.module4.model.SubTask;
import com.yandex.module4.model.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    private static Epic epic;
    private static Epic epic1;

    @BeforeAll
    public static void beforeAll() {
        epic = new Epic("Text", "howToDo text", Status.NEW, 5);
        epic1 = new Epic("Text1", "howToDo text1", Status.NEW, 5);
    }

    @Test
    public void taskEqualsEpicWithIDTest() {
        int result = 1;
        if (epic1.equals(epic)) {
            result = 0;
        }
        assertEquals(0, result, "Задачи не совпадают по ID");
    }

    @Test
    public void addEpicInEpicSubTaskTest() {
        int result = 1;
        try {
            Task task1 = epic;
            epic.addTask((SubTask) task1);
        } catch (ClassCastException e) {
            result = 0;
        }
        assertEquals(0, result, "Эпик получилось добавить в эпик");
    }
}
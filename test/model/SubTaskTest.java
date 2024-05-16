package model;

import com.yandex.module4.model.Epic;
import com.yandex.module4.model.Status;
import com.yandex.module4.model.SubTask;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    @Test
    public void taskEqualsSubTaskWithIDTest() {
        Epic epic = new Epic("Text", "howToDo text");
        SubTask subTask = new SubTask("Text", "howToDo text", epic, Status.NEW, 5, Duration.ofMinutes(20L), LocalDateTime.now());
        SubTask subTask1 = new SubTask("Text1", "howToDo text1", epic, Status.NEW, 5, Duration.ofMinutes(20L), LocalDateTime.MIN);
        int result = 1;
        if (subTask.equals(subTask1)) {
            result = 0;
        }
        assertEquals(0, result, "Задачи не совпадают по ID");
    }
}
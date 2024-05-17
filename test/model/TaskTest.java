package model;

import static org.junit.jupiter.api.Assertions.*;
import com.yandex.module4.model.Status;
import com.yandex.module4.model.Task;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDateTime;

class TaskTest {

    @Test
    public void taskEqualsTaskWithIDTest() {
        Task task = new Task("Text", "howToDo text", Status.NEW, 5, Duration.ofMinutes(25L), LocalDateTime.now());
        Task task1 = new Task("Text1", "howToDo text1", Status.NEW, 5, Duration.ofMinutes(55L), LocalDateTime.MIN);
        int result = 1;
        if (task1.equals(task)) {
            result = 0;
        }
        assertEquals(0, result, "Задачи не совпадают по ID");
    }
}
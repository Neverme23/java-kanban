package service;

import com.yandex.module4.service.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    public void managersReturnTypeInMemoryTaskManagerTest() {
        int result = 1;
        TaskManager taskManager = Managers.getDefault();
        if (taskManager.getClass() == InMemoryTaskManager.class) {
            InMemoryTaskManager inMemoryTaskManager = (InMemoryTaskManager) taskManager;
            if (inMemoryTaskManager.getCountID() == 1 && inMemoryTaskManager.getTasks() != null) {
                if (inMemoryTaskManager.getEpics() != null && inMemoryTaskManager.getSubTasks() != null) {
                    result = 0;
                }
            }
        }
        assertEquals(0, result, "Менеджер не инициализирует InMemoryTaskManager");
    }

    @Test
    public void managersReturnTypeInMemoryHistoryManagerTest() {
        int result = 1;
        HistoryManager historyManager = Managers.getDefaultHistory();
        if (historyManager.getClass() == InMemoryHistoryManager.class) {
            InMemoryHistoryManager inMemoryHistoryManager = (InMemoryHistoryManager) historyManager;
            if (inMemoryHistoryManager.getHistory() != null) {
                result = 0;
            }
        }
        assertEquals(0, result, "Менеджер не инициализирует InMemoryHistoryManager");
    }
}
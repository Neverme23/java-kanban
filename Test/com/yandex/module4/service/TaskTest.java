package com.yandex.module4.service;

import static org.junit.jupiter.api.Assertions.*;

import com.yandex.module4.model.Status;
import com.yandex.module4.model.Task;
import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    public void taskEqualsTaskWithIDTest() {
        Task task = new Task("Text", "howToDo text", Status.NEW, 5);
        Task task1 = new Task("Text1", "howToDo text1", Status.NEW, 5);
        int result = 1;
        if(task1.equals(task)){
            result = 0;
        }
        assertEquals(0, result, "Задачи не совпадают по ID");
    }
  }
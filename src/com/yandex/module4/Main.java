package com.yandex.module4;

import com.yandex.module4.model.*;
import com.yandex.module4.service.*;

public class Main {

    public static void main(String[] args) {
        TaskManager tm = new TaskManager();
        Task t1 = new Task("Гялуть", "Идти гулять");
        Task t2 = new Task("Спать", "Идти спать");
        Epic e1 = new Epic("Работать", "Работать работу");
        Epic e2 = new Epic("Покупки", "Идти в магазин");
        tm.addTask(t1);
        tm.addTask(t2);
        System.out.println(t1.getId());
        tm.addEpic(e1);
        tm.addEpic(e2);
        System.out.println(e1.getId());
        SubTask st1 = new SubTask("Идти на работу", "На транспорте", e1 );
        SubTask st2 = new SubTask("Идти с работы", "Пешком", e1 );
        SubTask st3 = new SubTask("Купить продукты", "Взять деньги", e2 );
        tm.addSubTask(st1);
        tm.addSubTask(st2);
        tm.addSubTask(st3);
        System.out.println(tm.getTasks());
        System.out.println(tm.getEpics());
        System.out.println(tm.getSubTasks());
        Task t3 = new Task("Стоять","Стоять на улице", Status.IN_PROGRESS, 2);
        tm.changeTask(t3);
        System.out.println(tm.getTasks());
        System.out.println(t3.getStatus());
        System.out.println(tm.getTasks());
        System.out.println(tm.getTaskWithID(2).getName());
        SubTask st4 = new SubTask("Купить воды", "Взять в дар", e2, Status.NEW, 7);
        SubTask st5 = new SubTask("Купить сахар", "в магазине", e2, Status.DONE, 7);
        tm.changeSubTask(st4);
        tm.addSubTask(st5);
        System.out.println(st4.getStatus());
        System.out.println(e2.getStatus());
        System.out.println(tm.getEpicTasks(3));
        tm.removeTaskWithID(2);
        System.out.println(tm.getTasks());
        tm.removeAllTask();
        System.out.println(tm.getTasks());
        tm.removeSubTaskWithID(6);
        System.out.println(tm.getSubTasks());
        tm.removeAllEpic();
        System.out.println("-----");
        tm.getSubTasks();
        System.out.println("-----");
       }
}

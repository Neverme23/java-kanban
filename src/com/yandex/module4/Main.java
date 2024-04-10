package com.yandex.module4;

import com.yandex.module4.model.*;
import com.yandex.module4.service.*;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager tm = new InMemoryTaskManager();
        Epic e1 = new Epic("Работать", "Работать работу");
        Epic e2 = new Epic("Покупки", "Идти в магазин");
        tm.addEpic(e1);
        tm.addEpic(e2);
        SubTask st1 = new SubTask("Идти на работу", "На транспорте", e1 );
        SubTask st2 = new SubTask("Идти с работы", "Пешком", e1 );
        SubTask st3 = new SubTask("работать", "Активно работать работу", e1 );
        tm.addSubTask(st1);
        tm.addSubTask(st2);
        tm.addSubTask(st3);
        System.out.println(tm.getEpicWithID(2));
        System.out.println(tm.getEpicWithID(1));
        System.out.println(tm.getSubTaskWithID(5));
        System.out.println(tm.getSubTaskWithID(4));
        System.out.println(tm.getSubTaskWithID(3));
        System.out.println(Arrays.toString(tm.getHistory().toArray()));
        System.out.println(tm.getEpicWithID(2));
        System.out.println(Arrays.toString(tm.getHistory().toArray()));
        tm.removeEpicWithID(2);
        tm.removeEpicWithID(1);
        System.out.println("______________");
        System.out.println(Arrays.toString(tm.getHistory().toArray()));
       }
}

package com.yandex.module4.service;



final public class Managers {
    TaskManager taskManager;
    HistoryManager historyManager;

    public Managers() {
        this.taskManager = new InMemoryTaskManager();
        this.historyManager = new InMemoryHistoryManager();
    }
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }

}

public class Main {

    public static void main(String[] args) {
        TaskManager tm = new TaskManager();
        Task t1 = new Task("Гялуть", "Идти гулять");
        Task t2 = new Task("Спать", "Идти спать");
        Epic e1 = new Epic("Работать", "Работать работу");
        Epic e2 = new Epic("Покупки", "Идти в магазин");
        SubTask st1 = new SubTask("Идти на работу", "На транспорте", e1 );
        SubTask st2 = new SubTask("Идти с работы", "Пешком", e1 );
        SubTask st3 = new SubTask("Купить продукты", "Взять деньги", e2 );
        tm.setTask(t1);
        tm.setTask(t2);
        tm.setEpic(e1);
        tm.setEpic(e2);
        tm.setSubTask(st1);
        tm.setSubTask(st2);
        tm.setSubTask(st3);
        System.out.println(tm.printTask());
        System.out.println(tm.printEpic());
        System.out.println(tm.printSubTask());
        Task t3 = new Task("Стоять","Стоять на улице", Status.IN_PROGRESS);
        tm.changeTask(t3, 2);
        System.out.println(tm.printTask());
        System.out.println(t3.getStatus());
        SubTask st4 = new SubTask("Купить воды", "Взять в дар", e2, Status.DONE);
        tm.changeSubTask(st4, 7);
        System.out.println(st4.getStatus());
        System.out.println(e2.getStatus());
        System.out.println(tm.getEpicTasks(3));
        tm.removeTaskWithID(2);
        System.out.println(tm.printTask());
        tm.removeAllTask();
        System.out.println(tm.printTask());
        tm.removeSubTaskWithID(6);
        System.out.println(tm.printSubTask());



    }
}

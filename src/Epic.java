import java.util.ArrayList;
public class Epic extends Task {
    private ArrayList <SubTask> task;

    Epic(String name, String howToDo) {
        super(name, howToDo);
        task = new ArrayList<>();
    }

    public ArrayList getTask () {
        return task;
    }
    public void setTask (SubTask subTask) {
        task.add(subTask);
    }
}

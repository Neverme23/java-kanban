
public class SubTask extends Task {
    private Epic epic;

    SubTask (String name,String howToDo, Epic epic) {
        super(name, howToDo);
        this.epic = epic;
    }

    SubTask (String name,String howToDo, Epic epic, Status status) {
        super(name, howToDo, status);
        this.epic = epic;

    }

    public Epic getEpic () {
        return epic;
    }
}

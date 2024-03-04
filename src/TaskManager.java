import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    public static int countID = 1;
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, SubTask> subTasks;
    private HashMap<Integer, Epic> epic;

    TaskManager() {
        this.tasks = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.epic = new HashMap<>();
    }

    public void setTask (Task t) {
        tasks.put(t.getId(), t);
    }

    public Task getTask (int countID) {
        return tasks.get(countID);
    }

    public void setEpic (Epic t) {
        epic.put(t.getId(), t);
    }
    public Epic getEpic (int countID) {
        return epic.get(countID);
    }
    public void setSubTask(SubTask st) {
        subTasks.put(st.getId(), st);
        st.getEpic().getTask().add(st);
    }
    public SubTask getSubTask(int countID) {
        return subTasks.get(countID);
    }
    public static int getCountID() {
        return countID;
    }
    public String printTask () {
        String result = "";
        for (Task t: tasks.values()) {
            result += t.getName()+ " ";
            }
        return result;
        }
    public String printSubTask () {
        String result = "";
        for (SubTask st: subTasks.values()) {
            result += st.getName() + " ";
        }
        return result;
    }
    public String printEpic () {
        String result = "";
        for (Epic e: epic.values()) {
            result += e.getName() + " ";
        }
        return result;
    }
    public void changeTask(Task t, int hisID) {
        removeTaskWithID(hisID);
        tasks.put(hisID, t);
    }
    public void changeSubTask(SubTask t, int hisID) {
        t.setId(hisID);
        t.getEpic().getTask().remove(subTasks.get(hisID));
        t.getEpic().getTask().add(t);
        subTasks.put(hisID, t);
        changeStatus(t, t.getStatus());
    }
    public void changeEpic(Epic t, int hisID) {
        epic.put(hisID, t);
    }
    public void removeAllTask () {
        tasks.clear();
    }
    public void removeAllSubTask () {
        subTasks.clear();
    }
    public void removeAllEpic () {
        subTasks.clear();
        epic.clear();
    }
    public Task getTaskWithID (int id) {
        return tasks.get(id);
    }
    public SubTask getSubTaskWithID (int id) {
        return subTasks.get(id);
    }
    public Epic getEpicWithID (int id) {
        return epic.get(id);
    }
    public void removeTaskWithID (int id) {
        tasks.remove(id);
    }
    public void removeSubTaskWithID (int id) {
        subTasks.remove(id);

    }
    public void removeEpicWithID (int id) {
        epic.remove(id);
    }
    public String getEpicTasks (int id) {
        String result = "";
        ArrayList <SubTask> st = epic.get(id).getTask();
        for (SubTask st1 :st) {
            result += st1.getName() + " ";
        }
        return result;
    }
    private void changeStatus(SubTask st, Status status) {
        st.setStatus(status);
        Epic e1 = st.getEpic();
        if (status == Status.IN_PROGRESS) {
            e1.setStatus(status);
        }
        ArrayList<SubTask> ar1 = e1.getTask();
        for (SubTask st1 : ar1) {
            if (st1.getStatus() != Status.DONE) {
                return;
            } else {
                e1.setStatus(Status.DONE);
            }
        }
    }
}





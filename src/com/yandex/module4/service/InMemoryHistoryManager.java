package com.yandex.module4.service;

import com.yandex.module4.model.Epic;
import com.yandex.module4.model.SubTask;
import com.yandex.module4.model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private Node<Task> first = null;
    private Node<Task> last = null;

    private final Map<Integer, Node<Task>> historyNode;


    InMemoryHistoryManager() {
        historyNode = new HashMap<>();
    }

    public void linkLast(Task task) {
        int startCount = 0;
        Node<Task> node = new Node<>(task, this.last);
        if (last != null) {
            last.setNextNode(node);
        }
        last = node;
        if (historyNode.size() == startCount) {
            first = node;
        }
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        linkLast(task);
        if (historyNode.containsKey(task.getId())) {
            remove(task.getId());
        }
        historyNode.put(task.getId(), last);
    }


    @Override
    public List<Task> getHistory() {
        ArrayList<Task> result = new ArrayList<>();
        if (last == null || first == null) {
            return result;
        }
        int count = 0;
        Node<Task> node = first;
        Task task = node.getData();
        result.add(task);
        last.getData();
        while (result.get(count) != last.getData()) {
            if ((node = node.getNext()) == null ) {
                break;
            }
                task = node.getData();
                result.add(task);
                count++;
        }
        return result;
    }

    @Override
    public void removeNode(Node<Task> node) {
        Node<Task> prev = node.getPrev();
        Node<Task> next = node.getNext();
        if (prev != null) {
            prev.setNextNode(node.getNext());
        }

        if (next != null) {
            next.setPrevNode(node.getPrev());
        }
        historyNode.remove(node.getData().getId());
    }

    @Override
    public void remove(int id) {
        Node<Task> node = historyNode.get(id);
        if (node == first) {
            first = node.getNext();
        }
        removeNode(node);
    }

    @Override
    public void removeAllEpic() {
        Node<Task> nodeForRemove = null;
        boolean isComplite = true;
        OUTER:
        while (isComplite) {
            isComplite = false;
            INNER:
            for (Node<Task> node : historyNode.values()) {
                if (node.getData().getClass() == Epic.class) {
                    nodeForRemove = node;
                    isComplite = true;
                    break INNER;
                }
            }
            if (nodeForRemove != null) {
                removeNode(nodeForRemove);
            }
        }
        if (historyNode.isEmpty()) {
            first = null;
        }
    }

    @Override
    public void removeAllTask() {
        Node<Task> nodeForRemove = null;
        boolean isComplite = true;
        while (isComplite) {
            isComplite = false;
            for (Node<Task> node : historyNode.values()) {
                if (node.getData().getClass() == Task.class) {
                    nodeForRemove = node;
                    isComplite = true;
                }
            }
            if (nodeForRemove != null) {
                removeNode(nodeForRemove);
            }
        }
        if (historyNode.isEmpty()) {
            first = null;
        }
    }

    @Override
    public void removeAllSubTask() {
        Node<Task> nodeForRemove = null;
        boolean isComplite = true;
        while (isComplite) {
            isComplite = false;
            for (Node<Task> node : historyNode.values()) {
                if (node.getData().getClass() == SubTask.class) {
                    nodeForRemove = node;
                    isComplite = true;
                }
            }
            if (nodeForRemove != null) {
                removeNode(nodeForRemove);
            }
        }
        if (historyNode.isEmpty()) {
            first = null;
        }
    }
}





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
        Node<Task> node = new Node<>(task, last);
        if (last == null) {
            first = node;
            last = node;
        } else {
            last.setNextNode(node);
            last = node;
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
        Node<Task> node = first;
        while (node != null) {
            result.add(node.getData());
            node = node.getNext();
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
            if (node == first) {
                first = node.getNext();
            } else {
                if (node == last) {
                    last = node.getPrev();
                }
                next.setPrevNode(node.getPrev());
            }
        }
        historyNode.remove(node.getData().getId());
    }

    @Override
    public void remove(int id) {
        removeNode(historyNode.get(id));
    }

    @Override
    public void removeAllEpic() {
        Node<Task> nodeForRemove = first;
        while (nodeForRemove != null) {
            if (nodeForRemove.getData().getClass() == Epic.class) {
                removeNode(nodeForRemove);
            }
            nodeForRemove = nodeForRemove.getNext();
        }
        if (historyNode.isEmpty()) {
            first = null;
        }
    }

    @Override
    public void removeAllTask() {
        Node<Task> nodeForRemove = first;
        while (nodeForRemove != null) {
            if (nodeForRemove.getData().getClass() == Task.class) {
                removeNode(nodeForRemove);
            }
            nodeForRemove = nodeForRemove.getNext();
        }
        if (historyNode.isEmpty()) {
            first = null;
        }
    }

    @Override
    public void removeAllSubTask() {
        Node<Task> nodeForRemove = first;
        while (nodeForRemove != null) {
            if (nodeForRemove.getData().getClass() == Task.class) {
                removeNode(nodeForRemove);
            }
            nodeForRemove = nodeForRemove.getNext();
        }
        if (historyNode.isEmpty()) {
            first = null;
        }
    }
}





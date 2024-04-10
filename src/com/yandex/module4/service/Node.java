package com.yandex.module4.service;

public class Node <T> {
    private T data;
    private Node<T> next;
    private Node<T> prev;

    public Node(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    public Node(T data, Node<T> prev) {
        this.data = data;
        this.next = null;
        this.prev = prev;
    }

    public void setNextNode(Node<T> next) {
        this.next = next;
    }

    public void setPrevNode(Node<T> prev) {
        this.prev = prev;
    }

    public Node<T> getNext() {
        return next;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public T getData() {
        return data;
    }
}

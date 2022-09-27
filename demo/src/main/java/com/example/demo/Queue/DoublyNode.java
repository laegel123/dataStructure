package com.example.demo.Queue;

public class DoublyNode<E> {
    E data;
    DoublyNode<E> prev;
    DoublyNode<E> next;

    DoublyNode(E data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

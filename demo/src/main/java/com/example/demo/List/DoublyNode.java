package com.example.demo.List;

public class DoublyNode<E> {

    E data;
    DoublyNode<E> next;
    DoublyNode<E> prev;

    DoublyNode(E data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}

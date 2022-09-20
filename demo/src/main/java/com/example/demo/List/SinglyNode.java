package com.example.demo.List;

public class SinglyNode<E> {
    E data;
    SinglyNode<E> next;

    SinglyNode(E data) {
        this.data = data;
        this.next = null;
    }
}

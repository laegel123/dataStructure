package com.example.demo.Queue;

public class Node<E> {
    E data;
    Node<E> next;

    Node(E value) {
        this.data = value;
        this.next = null;
    }

}

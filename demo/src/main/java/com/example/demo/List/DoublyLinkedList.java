package com.example.demo.List;

import com.example.demo.List.Interface.List;

public class DoublyLinkedList<E> implements List<E> {

    private DoublyNode<E> head;
    private DoublyNode<E> tail;
    private int size;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    private DoublyNode<E> searchNode(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (index > size / 2) {
            DoublyNode<E> node = tail;

            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }

            return node;
        } else {
            DoublyNode<E> node = head;

            for (int i = 0; i < index; i++) {
                node = node.next;
            }

            return node;
        }
    }

    @Override
    public boolean add(E value) {
        addLast(value);
        return true;
    }

    @Override
    public void add(int index, E value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (size == 0) {
            addFirst(value);
            return;
        }

        if (size == index) {
            addLast(value);
            return;
        }

        DoublyNode<E> newNode = new DoublyNode<>(value);
        DoublyNode<E> prevNode = searchNode(index - 1);
        DoublyNode<E> nextNode = prevNode.next;

        prevNode.next = null;
        newNode.prev = null;

        prevNode.next = newNode;

        newNode.prev = prevNode;
        newNode.next = nextNode;

        newNode.prev = newNode;

        size++;
    }

    public void addFirst(E value) {
        DoublyNode<E> newNode = new DoublyNode<>(value);

        newNode.next = head;

        if (head != null) {
            head.prev = newNode;
        }

        head = newNode;
        size++;

        if (head.next == null) {
            tail = head;
        }
    }

    public void addLast(E value) {
        DoublyNode<E> newNode = new DoublyNode<>(value);

        if (size == 0) {
            addFirst(value);
            return;
        }

        newNode.prev = tail;
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public boolean remove(Object value) {
        return false;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public void set(int index, E value) {

    }

    @Override
    public boolean contains(Object value) {
        return false;
    }

    @Override
    public int indexOf(Object value) {
        return 0;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {

    }
}

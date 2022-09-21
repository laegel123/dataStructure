package com.example.demo.List;

import com.example.demo.List.Interface.List;
import org.w3c.dom.Node;

import java.util.NoSuchElementException;

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

        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            E returnElement = head.data;
            remove();
            return returnElement;
        }

        DoublyNode<E> prevNode = searchNode(index - 1);
        DoublyNode<E> removedNode = prevNode.next;
        DoublyNode<E> nextNode = removedNode.next;

        E returnElement = removedNode.data;

        prevNode.next = null;
        removedNode.prev = null;
        removedNode.next = null;
        removedNode.data = null;

        if (nextNode != null) {
            nextNode.prev = null;

            nextNode.prev = prevNode;
            prevNode.next = nextNode;
        } else {
            tail = prevNode;
        }

        size--;

        return returnElement;
    }

    @Override
    public boolean remove(Object value) {
        DoublyNode<E> prevNode = head;
        DoublyNode<E> node = head;

        for (; node != null; node = node.next) {
            if (value.equals(node.data)) {
                break;
            }

            prevNode = node;
        }

        if (node == null) {
            return false;
        }

        if (node.equals(head)) {
            remove();

            return true;
        } else {
            DoublyNode<E> nextNode = node.next;

            prevNode.next = null;
            node.next = null;
            node.prev = null;
            node.data = null;

            if (nextNode != null) {
                nextNode.prev = null;

                nextNode.prev = prevNode;
                prevNode.next = nextNode;
            } else {
                tail = prevNode;
            }

            size--;

            return true;
        }
    }

    public E remove() {
        DoublyNode<E> headNode = head;

        if (headNode == null) {
            throw new NoSuchElementException();
        }

        E returnElement = headNode.data;
        DoublyNode<E> nextNode = headNode.next;

        headNode.data = null;
        headNode.next = null;

        if (nextNode != null) {
            nextNode.prev = null;
        }

        head = nextNode;
        size--;

        if (size == 0) {
            tail = null;
        }

        return returnElement;
    }

    @Override
    public E get(int index) {
        return searchNode(index).data;
    }

    @Override
    public void set(int index, E value) {
        DoublyNode<E> replaceNode = searchNode(index);

        replaceNode.data = null;
        replaceNode.data = value;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value) >= 0;
    }

    @Override
    public int indexOf(Object value) {
        int index = 0;

        for (DoublyNode<E> node = head; node != null; node = node.next) {
            if (value.equals(node.data)) {
                return index;
            }
            index++;
        }

        return -1;
    }

    /**
     * 거꾸로 indexOf 동작시킨다.
     * @param value
     * @return
     */
    public int lastIndexOf(Object value) {
        int index = size;

        for (DoublyNode<E> node = tail; node != null; node = node.prev) {
            index--;

            if (value.equals(node.data)) {
                return index;
            }
        }

        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (DoublyNode<E> node = head; node != null; ) {
            DoublyNode<E> nextNode = node.next;
            node.data = null;
            node.next = null;
            node.prev = null;
            node = nextNode;
        }

        head = tail = null;
        size = 0;
    }
}

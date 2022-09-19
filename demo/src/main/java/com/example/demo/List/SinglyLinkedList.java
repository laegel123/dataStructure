package com.example.demo.List;

import com.example.demo.List.Interface.List;

import java.util.NoSuchElementException;

public class SinglyLinkedList<E> implements List<E> {

    private Node<E> head; // 노드 첫 부분.
    private Node<E> tail; // 노드 마지막 부분.
    private int size; // 요소 개수.

    public SinglyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * index번째 노드를 찾는다.
     * @param index
     * @return
     */
    private Node<E> search(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> searchNode = head;

        for (int i = 0; i < index; i++) {
            searchNode = searchNode.next;
        }

        return searchNode;
    }

    @Override
    public boolean add(E value) {
        addLast(value);

        return false;
    }

    @Override
    public void add(int index, E value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            addFirst(value);

            return;
        }

        if (index == size) {
            addLast(value);

            return;
        }

        Node<E> newNode = new Node<>(value);
        Node<E> prevNode = search(index - 1);

        newNode.next = prevNode.next;
        prevNode.next = null;
        prevNode.next = newNode;
        size++;
    }

    public void addFirst(E value) {
        Node<E> newNode = new Node<>(value);
        newNode.next = head;
        head = newNode;
        size++;

        if (head.next == null) {
            tail = head;
        }
    }

    public void addLast(E value) {
        Node<E> newNode = new Node<>(value);

        if (size == 0) {
            addFirst(value);

            return;
        }

        tail.next = newNode;
        tail = newNode;
        size++;
    }

    /**
     * 가장 앞의 노드를 삭제한다.
     * @return
     */
    public E remove() {
        Node<E> headNode = head;

        if (headNode == null) {
            throw new NoSuchElementException();
        }

        E returnElement = headNode.data;

        Node<E> nextNode = headNode.next;

        head.next = null;
        head.data = null;

        head = nextNode;
        size--;

        if (size == 0) {
            tail = null;
        }
        return returnElement;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            return remove();
        }

        Node<E> prevNode = search(index - 1);
        Node<E> removeNode = prevNode.next;
        Node<E> nextNode = removeNode.next;

        E returnElement = removeNode.data;

        prevNode.next = nextNode;

        // removeNode를 삭제처리한다. (gc처리에 도움을 주기 위해서)
        removeNode.next = null;
        removeNode.data = null;

        if (prevNode.next == null) {
            tail = prevNode;
        }

        size--;

        return returnElement;
    }

    @Override
    public boolean remove(Object value) {

        Node<E> prevNode = head;
        Node<E> removeNode = head;
        boolean hasValue = false;

        for (; removeNode != null; removeNode = removeNode.next) {
            if (value.equals(removeNode)) {
                hasValue = true;

                break;
            }

            prevNode = removeNode;
        }

        if (removeNode == null) {
            return false;
        }

        if (removeNode.equals(head)) {
            remove();

            return true;
        } else {
            prevNode.next = removeNode.next;

            if (prevNode.next == null) {
                tail = prevNode;
            }

            removeNode.next = null;
            removeNode.data = null;
            size--;

            return true;
        }
    }

    @Override
    public E get(int index) {
        return search(index).data;
    }

    @Override
    public void set(int index, E value) {
        Node<E> modifiedNode = search(index);

        modifiedNode.data = null;
        modifiedNode.data = value;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value) >= 0;
    }

    @Override
    public int indexOf(Object value) {
        int index = 0;

        for (Node<E> x = head; x != null; x = x.next) {
            if (value.equals(x)) {
                return index;
            }
            index++;
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
        for (Node<E> x = head; x != null; ) {
            Node<E> nextNode = x.next;
            x.data = null;
            x.next = null;
            x = nextNode;
        }

        head = tail = null;
        size = 0;

    }
}

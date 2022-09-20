package com.example.demo.List;

import com.example.demo.List.Interface.List;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<E> implements List<E>, Cloneable {

    private SinglyNode<E> head; // 노드 첫 부분.
    private SinglyNode<E> tail; // 노드 마지막 부분.
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
    private SinglyNode<E> search(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        SinglyNode<E> searchNode = head;

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

        SinglyNode<E> newNode = new SinglyNode<>(value);
        SinglyNode<E> prevNode = search(index - 1);

        newNode.next = prevNode.next;
        prevNode.next = null;
        prevNode.next = newNode;
        size++;
    }

    public void addFirst(E value) {
        SinglyNode<E> newNode = new SinglyNode<>(value);
        newNode.next = head;
        head = newNode;
        size++;

        if (head.next == null) {
            tail = head;
        }
    }

    public void addLast(E value) {
        SinglyNode<E> newNode = new SinglyNode<>(value);

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
        SinglyNode<E> headNode = head;

        if (headNode == null) {
            throw new NoSuchElementException();
        }

        E returnElement = headNode.data;

        SinglyNode<E> nextNode = headNode.next;

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

        SinglyNode<E> prevNode = search(index - 1);
        SinglyNode<E> removeNode = prevNode.next;
        SinglyNode<E> nextNode = removeNode.next;

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

        SinglyNode<E> prevNode = head;
        SinglyNode<E> removeNode = head;
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
        SinglyNode<E> modifiedNode = search(index);

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

        for (SinglyNode<E> x = head; x != null; x = x.next) {
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
        for (SinglyNode<E> x = head; x != null; ) {
            SinglyNode<E> nextNode = x.next;
            x.data = null;
            x.next = null;
            x = nextNode;
        }

        head = tail = null;
        size = 0;

    }

    public Object clone() throws CloneNotSupportedException {

        @SuppressWarnings("unchecked")
        SinglyLinkedList<? super E> clone = (SinglyLinkedList<? super E>) super.clone();

        clone.head = null;
        clone.tail = null;
        clone.size = 0;

        for (SinglyNode<E> x = head; x != null; x = x.next) {
            clone.addLast(x.data);
        }

        return clone;
    }

    public Object[] toArray() {
        Object[] array = new Object[size];
        int idx = 0;

        for (SinglyNode<E> x = head; x != null; x = x.next) {
            array[idx++] = (E) x.data;
        }

        return array;
    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            // Array.newInstance(컴포넌트 타입, 생성할 크기)
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }

        int i = 0;
        Object[] result = a;

        for (SinglyNode<E> x = head; x != null; x = x.next) {
            result[i++] = x.data;
        }

        return a;
    }

    public void sort() {
        sort(null);
    }

    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);

        int i = 0;
        for (SinglyNode<E> x = head; x != null; x = x.next, i++) {
            x.data = (E) a[i];
        }
    }































}

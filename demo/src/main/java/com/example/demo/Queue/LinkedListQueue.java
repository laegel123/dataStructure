package com.example.demo.Queue;

import com.example.demo.Queue.Interface.Queue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class LinkedListQueue<E> implements Queue<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public LinkedListQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public boolean offer(E value) {
        Node<E> newNode = new Node<>(value);

        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }

        tail = newNode;
        size++;

        return true;
    }

    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }

        E returnElement = head.data;
        Node<E> nextNode = head.next;

        head.data = null;
        head.next = null;

        head = nextNode;
        size--;

        return returnElement;
    }

    @Override
    public E peek() {
        if (size == 0) {
            return null;
        }

        return head.data;
    }

    public E element() {
        E returnElement = peek();

        if (returnElement == null) {
            throw new NoSuchElementException();
        }

        return returnElement;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object value) {
        for (Node<E> node = head; node != null; node = node.next) {
            if (value.equals(node.data)) {
                return true;
            }
        }

        return false;
    }

    public void clear() {
        for (Node<E> node = head; node != null; node = node.next) {
            Node<E> next = node.next;

            node.data = null;
            node.next = null;
            node = next;
        }

        size = 0;
        head = tail = null;
    }

    public Object[] toArray() {
        Object[] array = new Object[size];
        int idx = 0;
        for (Node<E> x = head; x != null; x = x.next) {
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
        // 얕은 복사를 위한 s 배열
        Object[] result = a;
        for (Node<E> x = head; x != null; x = x.next) {
            result[i++] = x.data;
        }
        return a;
    }

    @Override
    public Object clone() {

        // super.clone() 은 CloneNotSupportedException 예외를 처리해주어야 한다.
        try {
            @SuppressWarnings("unchecked")
            LinkedListQueue<E> clone = (LinkedListQueue<E>) super.clone();	// 새로운 큐 객체 생성
            clone.head = null;
            clone.tail = null;
            clone.size = 0;

            // 내부까지 복사되는 것이 아니기 때문에 내부 데이터들을 모두 복사해준다.
            for(Node<E> x = head; x != null; x = x.next) {
                clone.offer(x.data);
            }
            return clone;

        } catch (CloneNotSupportedException e) {
            throw new Error(e);	// 예외처리는 여러분들이 자유롭게 구성하면 된다.
        }
    }

    public void sort() {
        /**
         *  Comparator를 넘겨주지 않는 경우 해당 객체의 Comparable에 구현된
         *  정렬 방식을 사용한다.
         *  만약 구현되어있지 않으면 cannot be cast to class java.lang.Comparable
         *  에러가 발생한다.
         *  만약 구현되어있을 경우 null로 파라미터를 넘기면
         *  Arrays.sort()가 객체의 compareTo 메소드에 정의된 방식대로 정렬한다.
         */
        sort(null);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void sort(Comparator<? super E> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);

        int i = 0;
        // 정렬 된 a 배열을 큐로 복사한다.
        for (Node<E> x = head; x != null; x = x.next, i++) {
            x.data = (E) a[i];
        }
    }
}

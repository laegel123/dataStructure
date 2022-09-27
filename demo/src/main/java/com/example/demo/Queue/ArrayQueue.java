package com.example.demo.Queue;

import com.example.demo.List.ArrayList;
import com.example.demo.Queue.Interface.Queue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ArrayQueue<E> implements Queue<E>, Cloneable {

    private static final int DEFAULT_CAPACITY = 64;

    private Object[] array;
    private int size;

    private int front; // 시작 요소의 전 인덱스를 가리키는 변수.
    private int rear; // 마지막 요소의 인덱스를 가리키는 변수.

    public ArrayQueue() {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.front = 0;
        this.rear = 0;
    }

    public ArrayQueue(int capacity) {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.front = 0;
        this.rear = 0;
    }

    /**
     * 용적을 resizing 해주는 메서드이다.
     */
    public void resize(int newCapacity) {
        int arrayCapacity = array.length;
        Object[] newArray = new Object[newCapacity];

        for (int i = 1, j = front + 1; i <= size; i++, j++) {
            newArray[i] = array[j % arrayCapacity];
        }

        this.array = null;
        this.array = newArray;

        front = 0;
        rear = size;
    }

    @Override
    public boolean offer(E e) {
        if ((rear + 1) % array.length == front) {
            resize(array.length * 2);
        }

        rear = (rear + 1) % array.length;

        array[rear] = e;
        size++;

        return true;
    }

    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }

        front = (front + 1) % array.length; // front 를 한 칸 옮긴다.

        @SuppressWarnings("unchecked")
        E item = (E) array[front]; // 반환할 데이터를 임시 저장한다.

        array[front] = null;  // 해당 front 데이터를 삭제한다.
        size--;

        // 용적이 최소 크기(64) 보다 크고 요소 개수가 1/4 미만일 경우
        if (array.length > DEFAULT_CAPACITY && size < (array.length / 4)) {
            // 아무리 작아도 최소용적 미만으로 줄이지는 않도록 한다.
            resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
        }

        return item;
    }

    public E remove() {
        E item = poll();

        if (item == null) {
            throw new NoSuchElementException();
        }

        return item;
    }

    public E element() {
        E item = peek();

        if (item == null) {
            throw new NoSuchElementException();
        }

        return item;
    }

    @Override
    public E peek() {

        if (size == 0) {
            return null;
        }

        @SuppressWarnings("unchecked")
        E item = (E) array[(front + 1) % array.length];

        return item;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object value) {
        int start = (front + 1) % array.length;

        /**
         *  i : 요소 개수만큼만 반복한다.
         *  idx : 원소 위치로, 매 회 (idx + 1) % array.length; 의 위치로 갱신
         */
        for (int i = 0, idx = start; i < size; i++, idx = (idx + 1) % array.length) {
            if (array[idx].equals(value)) {
                return true;
            }
        }

        return false;
    }

    public void clear() {
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }

        front = rear = size = 0;
    }

    public Object[] toArray() {
        return toArray(new Object[size]);
    }


    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {

        final T[] res;
        // 들어오는 배열의 길이가 큐의 요소 개수보다 작은경우
        if(a.length < size) {
            /*
             * front가 rear보다 앞에 있을 경우 (또는 요소가 없을 경우 f==r)
             *  ______________________
             *  |  |  |  |  |  |  |  |
             *  ˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉ
             *    	f        r
             */
            if(front <= rear) {
                return (T[]) Arrays.copyOfRange(array, front + 1, rear + 1, a.getClass());
            }

            /*
             * front가 rear보다 뒤에 있을 경우
             *  ______________________
             *  |  |  |  |  |  |  |  |
             *  ˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉ
             *    	r        f
             */

            res = (T[]) Arrays.copyOfRange(array, 0, size, a.getClass());
            int rearlength = array.length - 1 - front;	// 뒷 부분의 요소 개수

            // 뒷 부분 복사
            if(rearlength > 0) {
                System.arraycopy(array, front + 1, res, 0, rearlength);
            }
            // 앞 부분 복사
            System.arraycopy(array, 0, res, rearlength, rear + 1);

            return res;
        }


        /*
         * front가 rear보다 앞에 있을 경우 (또는 요소가 없을 경우 f==r)
         *  ______________________
         *  |  |  |  |  |  |  |  |
         *  ˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉ
         *    	f        r
         */
        if(front <= rear) {
            System.arraycopy(array, front + 1, a, 0, size);
        }


        /*
         * front가 rear보다 뒤에 있을 경우
         *  ______________________
         *  |  |  |  |  |  |  |  |
         *  ˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉˉ
         *    	r        f
         */
        else {

            int rearlength = array.length - 1 - front;	// 뒷 부분의 요소 개수

            // 뒷 부분 복사
            if(rearlength > 0) {
                System.arraycopy(array, front + 1, a, 0, rearlength);
            }
            // 앞 부분 복사
            System.arraycopy(array, 0, a, rearlength, rear + 1);
        }

        return a;
    }

    @Override
    public Object clone() {

        // super.clone() 은 CloneNotSupportedException 예외를 처리해주어야 한다.
        try {

            @SuppressWarnings("unchecked")
            ArrayQueue<E> clone = (ArrayQueue<E>) super.clone();// 새로운 큐 객체 생성

            // 새로운 큐의 배열도 생성해주어야 함 (내부 객체는 깊은 복사가 되지 않기 때문)
            clone.array = Arrays.copyOf(array, array.length);
            return clone;
        }
        catch(CloneNotSupportedException e) {
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

    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> c) {

        // null 접근 방지를 위해 toArray로 요소만 있는 배열을 얻어 이를 정렬한뒤 덮어씌운다.
        Object[] res = toArray();

        Arrays.sort((E[]) res, 0, size, c);

        clear();
        /*
         *  정렬된 원소를 다시 array에 0부터 차례대로 채운다.
         *  이 때 front = 0 인덱스는 비워야 하므로 사실상 1번째 인덱스부터 채워야 한다.
         *
         */
        System.arraycopy(res, 0, array, 1, res.length);	// res 배열을 array에 복사

        this.rear = this.size = res.length;

    }
}

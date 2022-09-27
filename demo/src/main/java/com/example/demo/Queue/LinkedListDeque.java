package com.example.demo.Queue;

import com.example.demo.Queue.Interface.Queue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class LinkedListDeque<E> implements Queue<E>, Cloneable {

    private DoublyNode<E> head;
    private DoublyNode<E> tail;
    private int size;

    public LinkedListDeque() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public boolean offer(E value) {
        return offerLast(value);
    }

    public boolean offerLast(E value) {

        // 데이터가 없을 경우 offerFirst()로 인자를 넘겨줌
        if (size == 0) {
            return offerFirst(value);
        }

        DoublyNode<E> newNode = new DoublyNode<>(value);

        tail.next = newNode;	// tail이 가리키는 노드의 다음 노드를 새 노드를 가리키도록 연결
        newNode.prev = tail;	// 새 노드가 가리키는 이전노드 또한 tail이 가리키는 노드로 연결
        tail = newNode;		// tail이 가리키는 노드를 새 노드로 가리키도록 변경
        size++;

        return true;
    }

    public boolean offerFirst(E value) {
        DoublyNode<E> newNode = new DoublyNode<>(value);
        newNode.next = head;

        if (head != null) {
            head.prev = newNode;
        }

        /**
         * head가 null이 아닐 경우에만 기존 head노드의 prev 변수가
         * 새 노드를 가리키도록 한다.
         * 이유는 기존 head노드가 없는 경우(null)는 데이터가
         * 아무 것도 없던 상태였으므로 head.prev를 하면 잘못된 참조가 된다.
         */
        if (head != null) {
            head.prev = newNode;
        }

        head = newNode;	// head가 가리키는 노드가 새 노드를 가리키도록 한다.
        size++;

        /**
         * 다음에 가리킬 노드가 없는 경우(=데이터가 새 노드밖에 없는 경우 = 이전의 head가 null인경우)
         * 데이터가 한 개(새 노드)밖에 없으므로 새 노드는 처음 시작노드이자
         * 마지막 노드다. 즉 tail = head 다.
         */
        if (head.next == null) {
            tail = head;
        }
        return true;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    public E pollFirst() {
        if (size == 0) {
            return null;
        }

        E element = head.data;	// 반환하기 위한 데이터

        DoublyNode<E> nextNode = head.next;	// head의 다음노드

        // head가 가리키는 모든 데이터들 삭제
        head.data = null;
        head.next = null;

        // 삭제하기전 데이터가 두 개 이상 있을 경우(head와 tail이 같지 않은 경우)
        if (nextNode != null) {
            nextNode.prev = null;
        }
        head = null;
        head = nextNode;	// head가 가리키는 노드를 삭제한 노드의 다음 노드로 갱신
        size--;

        /**
         * 삭제된 요소가 덱의 유일한 요소였을 경우
         * 그 요소는 head 이자 tail이었으므로
         * 삭제되면서 tail도 가리킬 요소가 없기 때문에
         * size가 0일경우 tail도 null로 변환
         */
        if(size == 0) {
            tail = null;
        }

        return element;
    }

    public E pollLast() {
        if (size == 0) {
            return null;
        }

        E element = tail.data;	// 반환하기 위한 데이터

        DoublyNode<E> prevNode = tail.prev;

        // tail이 가리키는 노드의 데이터와 링크 삭제
        tail.data = null;
        tail.prev = null;

        // 삭제하기전 데이터가 두 개 이상 있을 경우(head와 tail이 같지 않을 경우)
        if (prevNode != null) {
            prevNode.next = null;
        }

        tail = null;
        tail = prevNode;
        size--;

        /**
         * 삭제된 요소가 덱의 유일한 요소였을 경우
         * 그 요소는 head 이자 tail이었으므로
         * 삭제되면서 head도 가리킬 요소가 없기 때문에
         * size가 0일경우 head도 null로 변환
         */
        if(size == 0) {
            head = null;
        }
        return element;
    }

    public E removeLast() {
        E element = pollLast();

        if(element == null) {
            throw new NoSuchElementException();
        }
        return element;
    }

    public E remove() {
        return removeFirst();
    }

    public E removeFirst() {
        E element = poll();

        if(element == null) {
            throw new NoSuchElementException();
        }
        return element;
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    public E peekFirst() {
        // 요소가 없을 경우 null 반환
        if(size == 0) {
            return null;
        }

        return head.data;
    }

    public E peekLast() {
        // 요소가 없을 경우 null 반환
        if(size == 0) {
            return null;
        }

        return tail.data;
    }

    public E element() {
        return getFirst();
    }

    public E getFirst() {
        E item = peek();

        // 앞의 원소 null 이라면(size = 0) 예외를 던진다.
        if(item == null) {
            throw new NoSuchElementException();
        }
        return item;
    }

    public E getLast() {
        E item = peekLast();

        // 앞의 원소 null 이라면(size = 0) 예외를 던진다.
        if(item == null) {
            throw new NoSuchElementException();
        }
        return item;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object value) {

        /**
         * head 데이터부터 x가 null이 될 때까지 value랑 x의 데이터(x.data)랑
         * 같은지를 비교하고 같을 경우 true를 반환한다.
         */
        for(DoublyNode<E> x = head; x != null; x = x.next) {
            if(value.equals(x.data)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        for (DoublyNode<E> x = head; x != null;) {
            DoublyNode<E> next = x.next;

            x.data = null;
            x.next = null;
            x.prev = null;
            x = next;
        }
        size = 0;
        head = tail = null;
    }

    public Object[] toArray() {
        Object[] array = new Object[size];
        int idx = 0;
        for (DoublyNode<E> x = head; x != null; x = x.next) {
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
        for (DoublyNode<E> x = head; x != null; x = x.next) {
            result[i++] = x.data;
        }
        return a;
    }

    @Override
    public Object clone() {

        // super.clone() 은 CloneNotSupportedException 예외를 처리해주어야 한다.
        try {
            @SuppressWarnings("unchecked")
            LinkedListDeque<E> clone = (LinkedListDeque<E>) super.clone();	// 새로운 덱 객체 생성
            clone.head = null;
            clone.tail = null;
            clone.size = 0;

            // 내부까지 복사되는 것이 아니기 때문에 내부 데이터들을 모두 복사해준다.
            for(DoublyNode<E> x = head; x != null; x = x.next) {
                clone.offerLast(x.data);
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
        for (DoublyNode<E> x = head; x != null; x = x.next, i++) {
            x.data = (E) a[i];
        }
    }
}

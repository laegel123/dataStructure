package com.example.demo.List;

import com.example.demo.List.Interface.List;

import java.util.Arrays;

public class ArrayList<E> implements List<E>, Cloneable {

    private static final int DEFAULT_CAPACITY = 10; // 최소 용적 크기.
    private static final Object[] EMPTY_ARRAY = {}; // 빈 배열.

    private int size; // 요소 개수.
    Object[] array; // 요소를 담을 배열.

    public ArrayList() {
        this.size = 0;
        this.array = EMPTY_ARRAY;
    }

    public ArrayList(int capacity) {
        this.size = 0;
        this.array = new Object[capacity];
    }

    public void resize() {
        int array_capacity = array.length;
        // array 가 빈 배열이라면, 초기화
        if (Arrays.equals(array, EMPTY_ARRAY)) {
            array = new Object[DEFAULT_CAPACITY];

            return;
        }

        // 용량이 꽉 찰 경우, 지금 배열 크기 * 2
        if (size == array_capacity) {
            int new_capacity = array_capacity * 2;
            array = Arrays.copyOf(array, new_capacity);

            return;
        }

        // 용량의 절반 미만일 경우
        if (size < array_capacity / 2) {
            int new_capacity = array_capacity / 2;
            array = Arrays.copyOf(array, Math.max(new_capacity, DEFAULT_CAPACITY));

            return;
        }
    }



    @Override
    public boolean add(E value) {
        addLast(value);

        return true;
    }

    @Override
    public void add(int index, E value) {
        // 영역 벗어났는지 검사. (빈 공간을 허용하지 않기 때문에 index>size 일 때 오류가 나게 한다.)
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (index == array.length) {
            addLast(value);

        } else {
            // 용량 다 차있다면 재할당.
            if (size == array.length) {
                resize();
            }

            for (int i = size; i > index; i--) {
                array[i] = array[i - 1];
            }

            array[index] = value;
            size++;
        }
    }

    /**
     *  리스트 마지막에 요소를 추가한다.
     * @param value
     */
    public void addLast(E value) {
        if (size == array.length) {
            resize();
        }

        array[size] = value;
        size++;
    }

    /**
     * 리스트 첫 번째에 요소를 추가한다.
     * @param value
     */
    public void addFirst(E value) {
        add(0, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public E remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        E returnElement = (E) array[index];
        array[index] = null;

        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
            array[i + 1] = null; // gc의 데이터 메모리 수거를 도와주기 위해 명시적으로 요소를 null로 처리한다.
        }

        size--;
        resize();

        return returnElement;
    }

    @Override
    public boolean remove(Object value) {
        int index = indexOf(value);

        if (index == -1) {
            return false;
        }

        remove(index);

        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        // @SuppressWarnings("unchecked") 하는 이유는 타입 안전성에 대한 경고를 받을 필요가 없기 때문이다. (항상 E 타입으로 캐스팅하기 때문에 반환할 수 없는 타입이 올 가능성은 없다.)
        // 허용 범위 벗어나면 예외를 발생시킨다.
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        // Object 타입에서 E 타입으로 캐스팅 후 반환한다.
        return (E) array[index];
    }

    @Override
    public void set(int index, E value) {

        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else {
            array[index] = value;
        }

    }

    @Override
    public boolean contains(Object value) {
        if (indexOf(value) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int indexOf(Object value) {
        int i = 0;

        for (i = 0; i < size; i++) {
            // 객체끼지 비교하므로 .euqals() 로 비교한다.
            if (array[i].equals(value)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 마지막 인덱스부터 동일한 요소가 있는지 찾는다.
     * @param value
     * @return
     */
    public int lastIndexOf(Object value) {
        for (int i = size - 1; i >= 0; i--) {
            if (array[i].equals(value)) {
                return i;
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
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }

        size = 0;
        resize();

    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        // 새로운 객체를 생성한다.
        ArrayList<?> cloneList = (ArrayList<?>) super.clone();

        // 새로운 객체의 배열을 생성한다.
        cloneList.array = new Object[size];

        // 배열 값을 복사한다.
        System.arraycopy(array, 0, cloneList.array, 0, size);

        return cloneList;
    }

    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(array, size, a.getClass());
        }

        // a 배열의 크기가 size보다 클 경우, size 만큼만 복사하고 a 배열의 나머지 요소는 유지한다.
        System.arraycopy(array, 0, a, 0, size);
        return a;
    }
}

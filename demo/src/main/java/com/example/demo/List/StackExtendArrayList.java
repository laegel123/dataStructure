package com.example.demo.List;

import com.example.demo.List.Interface.StackInterface;

import java.util.EmptyStackException;

public class StackExtendArrayList<E> extends ArrayList<E> implements StackInterface<E> {

    public StackExtendArrayList() {
        super();
    }

    public StackExtendArrayList(int capacity) {
        super(capacity);
    }


    @Override
    public E push(E item) {
        addLast(item);

        return item;
    }

    @Override
    public E pop() {
        int length = size();
        E returnElement = remove(length - 1);

        return returnElement;
    }

    @Override
    public E peek() {
        int length = size();

        if (length == 0) {
            throw new EmptyStackException();
        }

        E returnElement = get(length - 1);

        return returnElement;
    }

    @Override
    public int search(Object value) {
        int idx = lastIndexOf(value);

        if (idx >= 0) {
            return size() - idx;
        }

        return -1;
    }

    @Override
    public boolean empty() {
        return size() == 0;
    }

}

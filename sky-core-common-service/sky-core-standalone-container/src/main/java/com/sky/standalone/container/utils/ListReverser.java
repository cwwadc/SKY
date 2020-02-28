package com.sky.standalone.container.utils;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListReverser<T> implements Iterable<T> {

    private ListIterator<T> listIterator;

    public ListReverser(List<T> wrappedList) {
        this.listIterator = wrappedList.listIterator(wrappedList.size());
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {

            public boolean hasNext() {
                return listIterator.hasPrevious();
            }

            public T next() {
                return listIterator.previous();
            }

            public void remove() {
                listIterator.remove();
            }

        };
    }
}
package com.epam.trainee;

import java.util.*;

public class WideList<T> implements List<T> {

    private static final int INITIAL_CAPACITY = 16;
    private static final double FILLING_TRIGGER_PERCENT = 0.7;

    private final double fillingPercent;

    private int capacity;
    private int length;
    private Object[] elements;

    public WideList(Collection<T> fromCollection) {
        this.capacity = newCapacity(fromCollection.size());
        fillingPercent = FILLING_TRIGGER_PERCENT;
        elements = new Object[capacity];

        fromCollection.toArray(elements);
        length = fromCollection.size();
    }

    public WideList(int initialCapacity) {
        this(initialCapacity, FILLING_TRIGGER_PERCENT);
    }

    public WideList(int initialCapacity, double fillingTriggerPercent) {
        this.capacity = initialCapacity;
        this.fillingPercent = fillingTriggerPercent;
        this.length = 0;
    }

    private static int newCapacity(int size) {
        return (int) (size * (1 + FILLING_TRIGGER_PERCENT) + 1);
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return elements.length == 0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, length);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        copyRange(elements,0,length,a,0,a.length);
        return a;
    }

    private <T1> void copyRange(T1[] fromArray, int first1, int last1,
                                T1[] toArray, int first2, int last2) {

        boolean isCorrect = checkIndexBounds(fromArray, first1) &&
                checkIndexBounds(fromArray, last1-1) &&
                checkIndexBounds(toArray, first2) &&
                checkIndexBounds(toArray, last2-1);

        if (isCorrect) {
            copyArrayElements(fromArray, first1, last1, toArray, first2, last2);
        }
    }

    private <T1> boolean checkIndexBounds(T1[] array, int index) {
        return index >= 0 && index < array.length;
    }

    private <T1> void copyArrayElements(T1[] fromArray, int first1, int last1,
                                        T1[] toArray, int first2, int last2){

        while (first1 < last1 && first2 < last2) {
            toArray[first2++] = fromArray[first1++];
        }
    }

    @Override
    public boolean add(T t) {
        ensureCapacity();
        elements[length++] = t;
        return true;
    }

    private void ensureCapacity() {
        if (length / capacity >= fillingPercent) {
            increaseCapacity();
        }
    }

    private void increaseCapacity() {
        Object[] elementsToCopy = elements;
        int elementsCount = elementsToCopy.length;
        capacity = newCapacity(elementsCount);
        elements = new Object[capacity];
        copyRange(elementsToCopy, 0, elementsCount, elements, 0, capacity);
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    private int indexOfElement(Object t) {
        for(int i = 0; i < length; i++) {
            if (elements[i].equals(t)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        // no implementation required
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) {
        checkIndexBounds(elements, index);
        return (T) elements[index];
    }

    @Override
    public T set(int index, T element) {
        checkIndexBounds(elements, index);
        return null;
    }

    @Override
    public void add(int index, T element) {

    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }
}

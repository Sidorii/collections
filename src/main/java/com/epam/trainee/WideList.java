package com.epam.trainee;

import java.util.*;

public class WideList<T> implements List<T> {

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_PERMITTED_FILLING = 0.7f;
    static final float EXTENSION_PERCENT = 1.4f;

    private final float permittedFilling;
    private int capacity;
    private int elementsCount;
    protected Object[] elements;


    public WideList() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_PERMITTED_FILLING);
    }

    public WideList(int initialCapacity) {
        this(initialCapacity, DEFAULT_PERMITTED_FILLING);
    }

    public WideList(int initialCapacity, float fillingTriggerPercent) {
        this.capacity = initialCapacity;
        this.permittedFilling = fillingTriggerPercent;
        this.elements = new Object[capacity];
        this.elementsCount = 0;
    }

    public WideList(Collection<T> fromCollection) {
        this.elementsCount = fromCollection.size();
        ensureCapacity(elementsCount);
        this.elements = new Object[capacity];
        fromCollection.toArray(elements);

        this.permittedFilling = DEFAULT_PERMITTED_FILLING;
    }

    private void ensureCapacity(int minSize) {

        double filledPercent = (double) minSize / capacity;
        if (filledPercent >= permittedFilling) {
            growCapacity(minSize);
            redefineElements();
        }
    }

    private void growCapacity(int minSize) {
        int newCapacity = (int) (minSize * EXTENSION_PERCENT + 1);
        this.capacity = newCapacity > capacity ? newCapacity : capacity;
    }

    private void redefineElements() {
        elements = Arrays.copyOf(elements, capacity);
    }



    @Override
    public int size() {
        return elementsCount;
    }

    @Override
    public boolean isEmpty() {
        return elementsCount == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new ElementsIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, elementsCount);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(T1[] a) {
        return (T1[]) Arrays.copyOf(elements, elementsCount, a.getClass());
    }

    private <T1> boolean isIndexInBounds(int index) {
        return index >= 0 && index < capacity;
    }


    @Override
    public boolean add(T t) {
        add(elementsCount, t);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Unsupported remove operation in this type of collection");
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
        return addAll(elementsCount, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        try {
            int requiredCapacity = index + c.size();
            ensureCapacity(requiredCapacity);
            c.forEach(this::add);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Unsupported remove operation in this type of collection");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Unsupported remove operation in this type of collection");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Unsupported remove operation in this type of collection");
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) {
        if (isIndexInBounds(index)) {
            return (T) elements[index];
        }
        return null;
    }

    @Override
    public T set(int index, T element) {
        T el = get(index);
        add(index, element);
        return el;
    }

    @Override
    public void add(int index, T element) {
        if(!isIndexInBounds(index)) {
            ensureCapacity(index);
        }
        elementsCount++;
        elements[index] = element;
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException("Unsupported remove operation in this type of collection");
    }

    @Override
    public int indexOf(Object o) {
        return findFirstOrLastIndexOf(o, true);
    }

    @Override
    public int lastIndexOf(Object o) {
        return findFirstOrLastIndexOf(o, false);
    }

    private int findFirstOrLastIndexOf(Object o, boolean isFirst) {
        ElementsIterator it = new ElementsIterator(isFirst);

        while (it.hasNext()) {
            Object el = it.next();
            if (el != null && el.equals(o)) {
                return it.getCurrentIndex();
            }
        }
        return -1;
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


    protected class ElementsIterator implements Iterator<T> {

        private int currentIndex;
        private int toIndex;
        private boolean isGrowing;


        protected ElementsIterator() {
            this(true);
        }

        protected ElementsIterator(boolean isGrowing) {
            this.isGrowing = isGrowing;

            if (isGrowing) {
                currentIndex = 0;
                toIndex = elementsCount;
            } else {
                currentIndex = elementsCount - 1;
                toIndex = 0;
            }
        }

        int getCurrentIndex() {
            return currentIndex;
        }

        @Override
        public boolean hasNext() {
            return (isGrowing && currentIndex < toIndex) || (!isGrowing && currentIndex >= toIndex);
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Next element not found. Index is: " + currentIndex);
            }

            T element = get(currentIndex);
            movePointer();
            return element;
        }

        private void movePointer() {
            currentIndex += isGrowing ? 1 : -1;
        }
    }
}

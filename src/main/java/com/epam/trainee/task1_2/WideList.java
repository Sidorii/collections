package com.epam.trainee.task1_2;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class WideList<T> implements List<T> {

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_PERMITTED_FILLING = 0.9f;
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
        growCapacity(elementsCount);
        this.elements = new Object[capacity];
        this.permittedFilling = DEFAULT_PERMITTED_FILLING;
        fromCollection.toArray(elements);
    }

    private void ensureCapacity(int minSize) {
        double filledPercent = ((double) minSize) / capacity;
        if (filledPercent >= permittedFilling) {
            growCapacity(minSize);
            redefineElements();
        }
    }

    private void growCapacity(int minSize) {
        int newCapacity = (int) (minSize * EXTENSION_PERCENT + 1);
        this.capacity = (newCapacity > capacity) ? newCapacity : capacity;
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

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new ElementsIterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, elementsCount);
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(@NotNull T1[] a) {
        return (T1[]) Arrays.copyOf(elements, elementsCount, a.getClass());
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Unsupported remove operation in this type of collection");
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        return addAll(elementsCount, c);
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends T> c) {
        checkAddRange(index);

        try {
            int requiredCapacity = elementsCount + c.size();
            ensureCapacity(requiredCapacity);

            if (index < elementsCount) {
                System.arraycopy(elements, index,
                        elements, index + c.size(), elementsCount - index);
            }
            addElements(index, c);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void addElements(int index, Collection<? extends T> c) {
        for (T el : c) {
            add(index++, el);
        }
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException("Unsupported remove operation in this type of collection");
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
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
        checkAddRange(index);
        T el = get(index);
        putElement(index, element);
        return el;
    }

    private void putElement(int index, T el) {
        if (index == elementsCount) {
            add(el);
        } else {
            ensureCapacity(index);
            elements[index] = el;
        }
    }

    @Override
    public boolean add(T t) {
        add(elementsCount, t);
        return true;
    }

    @Override
    public void add(int index, T element) {
        checkAddRange(index);
        ensureCapacity(elementsCount + 1);

        if (index < elementsCount) {
            System.arraycopy(elements, index,
                    elements, index + 1, elementsCount - index);
        }
        elements[index] = element;
        elementsCount++;
    }

    private void checkAddRange(int index) {
        if (index < 0 || index > elementsCount) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
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

    @NotNull
    @Override
    public ListIterator<T> listIterator() {
        return new ElementsListIterator(0);
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator(int index) {
        return new ElementsListIterator(index);
    }

    @NotNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        int sublistLen = toIndex - fromIndex;
        WideList<T> sublist = new WideList<>(sublistLen);
        for (int i = fromIndex; i < toIndex; i++) {
            sublist.add(get(i));
        }
        return sublist;
    }

    private boolean isIndexInBounds(int index) {
        return index >= 0 && index < capacity;
    }


    @Override
    public String toString() {
        return Arrays.deepToString(elements);
    }

    class ElementsIterator implements Iterator<T> {

        private boolean isForwardDirection;
        private ElementsListIterator listIterator;

        ElementsIterator() {
            this(true);
        }

        ElementsIterator(boolean isForwardDirection) {
            this.isForwardDirection = isForwardDirection;
            if (isForwardDirection) {
                listIterator = new ElementsListIterator(0);
            } else {
                listIterator = new ElementsListIterator(elementsCount - 1);
            }
        }

        @Override
        public boolean hasNext() {
            return isForwardDirection ? listIterator.hasNext() : listIterator.hasPrevious();
        }

        @Override
        public T next() {
            return isForwardDirection ? listIterator.next() : listIterator.previous();
        }

        private int getCurrentIndex() {
            return isForwardDirection ? listIterator.previousIndex() : listIterator.nextIndex();
        }
    }


    private class ElementsListIterator implements ListIterator<T> {
        private int point;
        private int head;
        private int tail;
        private boolean isForwardDirection;

        ElementsListIterator(int point) {
            if (!isIndexInBounds(point)) {
                throw new IllegalArgumentException("Can't create iterator " +
                        "from position that out of bounds");
            }
            this.point = point;
            head = 0;
            tail = elementsCount;
        }

        @Override
        public boolean hasNext() {
            return point < tail;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T el = get(point);
                pointForward();
                return el;
            }
            throw new NoSuchElementException("No next element found");
        }

        @Override
        public boolean hasPrevious() {
            return point >= head;
        }

        @Override
        public T previous() {
            if (hasPrevious()) {
                T el = get(point);
                pointBack();
                return el;
            }
            throw new NoSuchElementException("No previous element found");
        }

        @Override
        public int nextIndex() {
            int index = point + 1;
            return isIndexInBounds(index) ? index : -1;
        }

        @Override
        public int previousIndex() {
            int index = point - 1;
            return isIndexInBounds(index) ? index : -1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }

        @Override
        public void set(T t) {
            WideList.this.set(point, t);
        }

        @Override
        public void add(T t) {
            if (isForwardDirection) {
                pointForward();
            } else {
                pointBack();
            }
            WideList.this.add(point, t);
        }

        private void pointForward() {
            isForwardDirection = true;
            point++;
        }

        private void pointBack() {
            isForwardDirection = false;
            point--;
        }
    }
}

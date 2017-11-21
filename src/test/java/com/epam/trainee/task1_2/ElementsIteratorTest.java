package com.epam.trainee.task1_2;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ElementsIteratorTest {

    private WideList.ElementsIterator elementsIterator;
    private WideList<Integer> wideList;

    @Before
    public void setUp() {
        wideList = new WideList<>();
        wideList.add(10);
        wideList.add(20);
        wideList.add(15);
    }

    @Test
    public void growingIteratorTest() {
        elementsIterator = wideList.new ElementsIterator();

        assertTrue(elementsIterator.hasNext());
        assertEquals(10, elementsIterator.next());
        assertTrue(elementsIterator.hasNext());
        assertEquals(20, elementsIterator.next());
        assertTrue(elementsIterator.hasNext());
        assertEquals(15, elementsIterator.next());
        assertFalse(elementsIterator.hasNext());
    }

    @Test
    public void fallingIteratorTest() {
        elementsIterator = wideList.new ElementsIterator(false);

        assertTrue(elementsIterator.hasNext());
        assertEquals(15, elementsIterator.next());
        assertTrue(elementsIterator.hasNext());
        assertEquals(20, elementsIterator.next());
        assertTrue(elementsIterator.hasNext());
        assertEquals(10, elementsIterator.next());
        assertFalse(elementsIterator.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void testOutOfBounds() {
        elementsIterator = new WideList<>().new ElementsIterator();
        elementsIterator.next();
    }
}
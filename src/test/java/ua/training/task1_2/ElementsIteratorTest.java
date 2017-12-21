package ua.training.task1_2;

import org.junit.Before;
import org.junit.Test;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ElementsIteratorTest {

    private ListIterator elementsIterator;
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
        elementsIterator = wideList.listIterator();

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
        elementsIterator = wideList.new ElementsListIterator(wideList.size()-1);

        assertTrue(elementsIterator.hasPrevious());
        assertEquals(15, elementsIterator.previous());
        assertTrue(elementsIterator.hasPrevious());
        assertEquals(20, elementsIterator.previous());
        assertTrue(elementsIterator.hasPrevious());
        assertEquals(10, elementsIterator.previous());
        assertFalse(elementsIterator.hasPrevious());
    }

    @Test(expected = NoSuchElementException.class)
    public void testOutOfBounds() {
        elementsIterator = new WideList<>().new ElementsListIterator(0);
        elementsIterator.next();
    }
}
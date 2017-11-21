package com.epam.trainee;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static com.epam.trainee.WideList.*;

public class WideListTest {

    private WideList<Integer> wideList;
    private ArrayList<Integer> testContent;

    @Before
    public void setUp() {
        wideList = new WideList<>();

        testContent = new ArrayList<Integer>() {
            {
                add(0);
                add(1);
            }
        };
    }

    @Test
    public void testSize() {
        assertEquals(0, wideList.size());

        wideList = new WideList<>(testContent);
        assertEquals(2, wideList.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(wideList.isEmpty());

        wideList = new WideList<>(testContent);
        assertFalse(wideList.isEmpty());
    }

    @Test
    public void testToArray() {
        wideList = new WideList<>(testContent);
        Object[] result = wideList.toArray();

        assertArrayEquals(testContent.toArray(), wideList.toArray());
    }

    @Test
    public void testToArrayWithArg() {
        wideList = new WideList<>(testContent);

        Integer[] result = wideList.toArray(new Integer[2]);
        Object[] expected = testContent.toArray();

        assertArrayEquals(expected, result);
    }

    @Test
    public void testCapacityExtension() {

        int toRange = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_PERMITTED_FILLING) + 1;
        int oldCapacity = wideList.elements.length; //represent current collection capacity

        for (int i = 0; i < toRange; i++) {
            wideList.add(i);
        }
        assertEquals(toRange, wideList.size());

        int newCapacity = (int) (oldCapacity*EXTENSION_PERCENT); //now capacity must be increased
        int expectedCapacity = (int) (oldCapacity * EXTENSION_PERCENT);

        assertEquals(expectedCapacity, newCapacity);
    }

    @Test
    public void testCapacityNotExtensionsBeforeTime() {
        int maxSizeBeforeExtension = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_PERMITTED_FILLING);

        int oldCapacity = wideList.elements.length;

        for (int i = 0; i < maxSizeBeforeExtension; i++) {
            wideList.add(i);
        }

        int newCapacity = wideList.elements.length;
        assertEquals(oldCapacity, newCapacity);
    }

    @Test
    public void testSimpleAdd() {
        wideList.add(1);
        assertTrue(wideList.size() == 1);
        assertEquals(1,wideList.elements[0]);
    }

    @Test
    public void testContains() {
        Integer val = 1;
        wideList.add(val);

        assertTrue(wideList.contains(val));
        assertFalse(wideList.contains(null));
    }

    @Test
    public void testAddAll() {

        for(int i = 0; i < 50; i++) {
            testContent.add((int) (Math.random() * 100));
        }
        wideList.add(10);
        wideList.addAll(1,testContent);
    }
}

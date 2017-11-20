package com.epam.trainee;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CollectionUtilsTest {

    private List<Integer> inputs;
    private Map<Integer, Long> expectedResult;

    @Before
    public void setUp() {
        inputs = new ArrayList<Integer>() {
            {
                add(10);
                add(12);
                add(-2);
                add(0);
                add(120);
                add(12);
                add(0);
                add(2);
                add(12);

            }
        };

        expectedResult = new HashMap<Integer,Long>() {
            {
                put(10, 1L);
                put(12, 3L);
                put(-2, 1L);
                put(0, 2L);
                put(120, 1L);
                put(2, 1L);
            }
        };
    }

    @Test
    public void testCommonExecution() {

        Map<Integer, Long> elementsCount = CollectionUtils.collectionElementsCount(inputs);

        for (Map.Entry<Integer, Long> elementEntry : elementsCount.entrySet()) {
            Long expectedCount = expectedResult.get(elementEntry.getKey());

            assertEquals(expectedCount, elementEntry.getValue());
        }
    }

    @Test
    public void testInputHasNull() {
        inputs = new ArrayList<Integer>() {
            {
                add(null);
                add(null);
                add(null);
            }
        };
        Map<Integer, Long> elementsCount = CollectionUtils.collectionElementsCount(inputs);

        assertTrue(elementsCount.size() == 0);
    }

    @Test
    public void testWithString() {
        List<String> strInputs = new ArrayList<String>() {
            {
                add("one");
                add("two");
                add("three");
                add("two");
            }
        };

        Map<String, Long> elementsCount = CollectionUtils.collectionElementsCount(strInputs);

        assertEquals(1, (long) elementsCount.get("one"));
        assertEquals(2, (long) elementsCount.get("two"));
        assertEquals(1, (long) elementsCount.get("three"));
    }
}
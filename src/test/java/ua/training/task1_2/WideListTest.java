package ua.training.task1_2;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class WideListTest {

    private static final int MIN_COUNT_FOR_CAPACITY_EXTENSION = 15;


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
        wideList.addAll(testContent);
        assertEquals(2, wideList.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(wideList.isEmpty());
        wideList.addAll(testContent);
        assertFalse(wideList.isEmpty());
    }

    @Test
    public void testToArray() {
        wideList.addAll(testContent);
        Object[] result = wideList.toArray();
        assertArrayEquals(testContent.toArray(), result);
    }

    @Test
    public void testToArrayWithArg() {
        wideList.addAll(testContent);
        Integer[] result = wideList.toArray(new Integer[testContent.size()]);
        Object[] expected = testContent.toArray();
        assertArrayEquals(expected, result);
    }

    @Test
    public void testCapacityExtension() {

        int elementsCount = MIN_COUNT_FOR_CAPACITY_EXTENSION;
        int oldCapacity = wideList.elements.length; //represent current collection capacity

        for (int i = 0; i < elementsCount; i++) {
            wideList.add(i);
        }
        assertEquals(elementsCount, wideList.size());

        int newCapacity = wideList.elements.length; //now capacity must be increased
        int expectedCapacity = (int) (MIN_COUNT_FOR_CAPACITY_EXTENSION * WideList.EXTENSION_PERCENT) + 1;

        assertEquals(expectedCapacity, newCapacity);
    }

    @Test
    public void testCapacityNotExtensionsBeforeTime() {
        int maxSizeBeforeExtension = MIN_COUNT_FOR_CAPACITY_EXTENSION - 1;
        int oldCapacity = wideList.elements.length; //represent current collection capacity

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
        assertEquals(1, wideList.elements[0]);
    }

    @Test
    public void testContains() {
        Integer val = 1;
        wideList.add(val);

        assertTrue(wideList.contains(val));
        assertFalse(wideList.contains(null));
    }

    @Test
    public void testContainsAll() {
        wideList.addAll(testContent);

        assertTrue(wideList.containsAll(testContent));
        testContent.add(10);
        assertFalse(wideList.containsAll(testContent));
    }

    @Test
    public void testAddAll() {
        testContent = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            testContent.add((int) (Math.random() * 100));
        }
        wideList.addAll(0, testContent);
        assertEquals(50, wideList.size());
    }

    @Test
    public void testAddAllWithIndex() {
        testContent = new ArrayList<>();
        wideList.add(1);
        wideList.add(50);
        wideList.add(51);
        for (int i = 2; i < 50; i++) {
            testContent.add(i);
        }
        wideList.addAll(1, testContent);
        assertEquals(51, wideList.size());

        testContent = new ArrayList<>();
        for (int i = 1; i < 52; i++) {
            testContent.add(i);
        }

        assertArrayEquals(testContent.toArray(), wideList.subList(0, wideList.size()).toArray());
    }

    @Test
    public void testGetElement() {
        wideList.add(10);
        wideList.add(15);

        int el10 = wideList.get(0);
        int el15 = wideList.get(1);
        assertEquals(10, el10);
        assertEquals(15, el15);
    }

    @Test
    public void testGetWithIndexOutOfBounds() {
        assertNull(wideList.get(-1));
        assertNull(wideList.get(100500));
    }

    @Test
    public void testSimpleSetsElement() {
        wideList.set(0, 14);
        assertEquals(Integer.valueOf(14), wideList.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetWrongPosition() {
        wideList.set(1, 10);
    }

    @Test
    public void testSetAsReplaceElement() {
        wideList.add(10);
        Integer el10 = wideList.set(0, 15);
        assertEquals(Integer.valueOf(10), el10);
        assertEquals(Integer.valueOf(15), wideList.get(0));
        assertEquals(1, wideList.size());
    }

    @Test
    public void tesSetWithCapacityExtension() {
        for (int i = 0; i < MIN_COUNT_FOR_CAPACITY_EXTENSION; i++) {
            wideList.set(i, i);
        }
        assertTrue(wideList.elements.length > 16);
    }

    @Test
    public void tesAdd() {
        wideList.add(100500);
        assertEquals(100500, (long) wideList.get(0));
        assertEquals(1, wideList.size());
    }

    @Test
    public void testAddNull() {
        wideList.add(null);
        assertNull(wideList.get(0));
    }

    @Test
    public void testAddWithCapacityExtension() {
        for (int i = 0; i < MIN_COUNT_FOR_CAPACITY_EXTENSION; i++) {
            wideList.add(i);
        }
        assertTrue(wideList.elements.length > 16);
    }

    @Test
    public void testAddWithIndex() {
        wideList.add(0, 0);
        wideList.add(1, 1);

        assertEquals(0, (long) wideList.get(0));
        assertEquals(1, (long) wideList.get(1));
    }

    @Test
    public void testAddInsideList() {
        wideList.add(0, 0);
        wideList.add(1, 1);
        wideList.add(2, 3); //mismatch is here
        wideList.add(3, 4);

        wideList.add(2, 2);
        Integer resArr[] = {0, 1, 2, 3, 4};
        assertArrayEquals(resArr, wideList.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddWothWrongIndex() {
        wideList.add(-1, 10);
    }

    @Test
    public void testIndexOf() {
        wideList.add(1);
        wideList.add(2);
        wideList.add(3);
        wideList.add(4);
        wideList.add(5);
        wideList.add(null);
        int indexOf1 = wideList.indexOf(1);
        int indexOf2 = wideList.indexOf(2);
        int indexOf3 = wideList.indexOf(3);
        int indexOf4 = wideList.indexOf(4);
        int indexOf5 = wideList.indexOf(5);
        int indexOfNull = wideList.indexOf(null);
        int indexOfNotFound = wideList.indexOf(100500);

        assertTrue(indexOf1 == 0);
        assertTrue(indexOf2 == 1);
        assertTrue(indexOf3 == 2);
        assertTrue(indexOf4 == 3);
        assertTrue(indexOf5 == 4);
        assertTrue(indexOfNull == 5);
        assertTrue(indexOfNotFound == -1);
    }

    @Test
    public void testLastIndexOf() {
        wideList.add(1);
        wideList.add(2);
        wideList.add(1);
        wideList.add(3);
        wideList.add(null);
        int lastIndOf1 = wideList.lastIndexOf(1);
        int lastIndOf2 = wideList.lastIndexOf(2);
        int lastIndOf3 = wideList.lastIndexOf(3);
        int lastIndOfNull = wideList.lastIndexOf(null);
        int lastIndOfNotFound = wideList.lastIndexOf(100500);

        assertTrue(lastIndOf1 == 2);
        assertTrue(lastIndOf2 == 1);
        assertTrue(lastIndOf3 == 3);
        assertTrue(lastIndOfNull == 4);
        assertTrue(lastIndOfNotFound == -1);
    }


    /*Optional tests for comparing productivity between WideList and ArrayList collections
    * Skipped by default, remove @Ignore annotation for tests running*/
    @Test
    @Ignore
    public void addProductivityTest() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        WideList<Integer> wideList = new WideList<>();

        long time1 = System.nanoTime();
        for (int i = 0; i < 500_000; i++) {
            arrayList.add(i);
        }
        long time2 = System.nanoTime();
        long arrayListTimeResult = time2 - time1;

        time1 = System.nanoTime();
        for (int i = 0; i < 500_000; i++) {
            wideList.add(i);
        }
        time2 = System.nanoTime();
        long wideListResult = time2 - time1;

        printProductivityResult(arrayListTimeResult, wideListResult);
    }

    @Test
    @Ignore
    public void getProductivityTest() {

        ArrayList<Integer> inputData = generateInputData(500_000);

        WideList<Integer> wideList = new WideList<>(inputData);
        ArrayList<Integer> arrayList = new ArrayList<>(inputData);

        long time1 = System.nanoTime();
        for (int i = 0; i < 500_000; i++) {
            arrayList.get(i);
        }
        long time2 = System.nanoTime();

        long arrayListTimeResult = time2 - time1;

        time1 = System.nanoTime();
        for (int i = 0; i < 500_000; i++) {
            wideList.get(i);
        }
        time2 = System.nanoTime();

        long wideListResult = time2 - time1;

        printProductivityResult(arrayListTimeResult, wideListResult);
    }


    @Test
    @Ignore
    public void insertAllProductivityTest() {
        ArrayList<Integer> inputData = generateInputData(500_000);
        WideList<Integer> wideList = new WideList<>(inputData);
        ArrayList<Integer> arrayList = new ArrayList<>(inputData);

        wideList.add(1);
        wideList.add(2);
        wideList.add(3);
        wideList.add(4);
        wideList.add(5);

        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);

        long time1 = System.nanoTime();
        arrayList.addAll(inputData);
        long time2 = System.nanoTime();

        long arrayListTimeResult = time2 - time1;

        time1 = System.nanoTime();
        wideList.addAll(inputData);
        time2 = System.nanoTime();

        long wideListResult = time2 - time1;

        printProductivityResult(arrayListTimeResult, wideListResult);
    }

    @Test
    @Ignore
    public void setByIndexProductivityTest() {
        ArrayList<Integer> contentData = generateInputData(500_000);
        WideList<Integer> wideList = new WideList<>(contentData);
        ArrayList<Integer> arrayList = new ArrayList<>(contentData);

        long time1 = System.nanoTime();
        for (int i = 0; i < 500_000; i++) {
            int index = (int) (Math.random() * 499_000);
            arrayList.set(index, i);
        }
        long time2 = System.nanoTime();

        long arrayListTimeResult = time2 - time1;

        time1 = System.nanoTime();
        for (int i = 0; i < 500_000; i++) {
            int index = (int) (Math.random() * 499_000);
            wideList.set(index, i);
        }
        time2 = System.nanoTime();

        long wideListResult = time2 - time1;

        printProductivityResult(arrayListTimeResult, wideListResult);
    }

    private ArrayList<Integer> generateInputData(int inputsNumber) {
        ArrayList<Integer> inputData = new ArrayList<>();
        for (int i = 0; i < inputsNumber; i++) {
            inputData.add(i);
        }
        return inputData;
    }

    private void printProductivityResult(long arrayListTimeResult, long wideListResult) {
        long differ = arrayListTimeResult - wideListResult;
        System.out.printf("ArrayList: %d, WideList: %d. [Difference: %d (%s sec)]%n",
                arrayListTimeResult, wideListResult, differ, differ / Math.pow(10, 9));
    }
}

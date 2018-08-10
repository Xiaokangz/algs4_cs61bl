import static org.junit.Assert.*;

public class FixedSizeListTest {

    @org.junit.Test
    public void testContainAndAdd() {
        FixedSizeList f = new FixedSizeList(10);
        assertFalse(f.contains(0));
        f.add(0);
        assertTrue(f.contains(0));
    }

    @org.junit.Test
    public void testSize() {
        FixedSizeList f = new FixedSizeList(10);
        assertEquals(f.size(), 0);
        for (int i = 0; i < 5; i++) {
            f.add(i);
        }
        assertEquals(f.size(), 5);
    }

    @org.junit.Test
    public void testIsEmpyt() {
       FixedSizeList f = new FixedSizeList(10);
       assertTrue(f.isEmpty());
       f.add(0);
       assertFalse(f.isEmpty());
    }

    @org.junit.Test
    public void testRemove() {
        FixedSizeList f = new FixedSizeList(10);
        assertFalse(f.contains(0));
        f.add(0);
        assertTrue(f.contains(0));
        f.remove(0);
        assertFalse(f.contains(0));
        f.add(0);
        f.add(0);
        f.remove(0);
        assertTrue(f.contains(0));
        f.remove(0);
        assertFalse(f.contains(0));
    }

    @org.junit.Test
    public void testGet() {
        FixedSizeList f = new FixedSizeList(10);
        for (int i = 0; i < 5; i++) {
            f.add(i);
        }
        for (int i = 0; i < 5; i++) {
            assertEquals(f.get(i), i);
        }
    }

    @org.junit.Test
    public void testAdd() {
        FixedSizeList f = new FixedSizeList(10);
        f.add(0, 1);
        f.add(1,4);
        f.add(1, 2);
        f.add(0, 0);
        f.add(3, 3);
        for (int i = 0; i < 5; i++) {
            assertEquals(f.get(i), i);
        }
    }

    @org.junit.Test
    public void TestRemoveIndex() {
        FixedSizeList f = new FixedSizeList(10);
        for (int i = 0; i < 5; i++) {
            f.add(i);
        }
        f.add(5, 5);
        f.removeIndex(2);
        assertEquals(f.get(0), 0);
        assertEquals(f.get(1), 1);
        assertEquals(f.get(2), 3);
        assertEquals(f.get(3), 4);
        assertEquals(f.get(4), 5);
    }

}
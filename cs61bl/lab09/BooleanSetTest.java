import static org.junit.Assert.*;

public class BooleanSetTest {

    @org.junit.Test
    public void testConsAndContain() {
        BooleanSet b = new BooleanSet(10);
        for (int i = 0; i < 10; i += 1) {
            assertFalse(b.contains(i));
        }
        try {
            b.contains(10);
            assertFalse(true);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @org.junit.Test
    public void testAddAndRemove() {
        BooleanSet b = new BooleanSet(10);
        assertFalse(b.contains(0));
        b.add(0);
        assertTrue(b.contains(0));
        b.remove(0);
        assertFalse(b.contains(0));
        try {
            b.add(10);
            assertFalse(true);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        try {
            b.remove(10);
            assertFalse((true));
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @org.junit.Test
    public void testIsEmpty() {
        BooleanSet b = new BooleanSet(10);
        assertTrue(b.isEmpty());
        b.add(0);
        assertFalse(b.isEmpty());
    }

    @org.junit.Test
    public void testSize() {
        BooleanSet b = new BooleanSet(10);
        assertEquals(b.size(), 0);
        for (int i = 0; i < 5; i += 1) {
            b.add(i);
        }
        assertEquals(b.size(), 5);
    }
}
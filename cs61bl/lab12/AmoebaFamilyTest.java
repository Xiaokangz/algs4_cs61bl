import static org.junit.Assert.*;

public class AmoebaFamilyTest {

    @org.junit.Test
    public void testHeight() {
        AmoebaFamily family = new AmoebaFamily("Amos McCoy");
        family.addChild("Amos McCoy", "mom/dad");
        family.addChild("Amos McCoy", "auntie");
        family.addChild("mom/dad", "me");
        family.addChild("mom/dad", "Fred");
        family.addChild("mom/dad", "Wilma");
        family.addChild("me", "Mike");
        family.addChild("me", "Homer");
        family.addChild("me", "Marge");
        family.addChild("Mike", "Bart");
        family.addChild("Mike", "Lisa");
        family.addChild("Marge", "Bill");
        family.addChild("Marge", "Hilary");
        assertEquals(4, family.height());
    }

    @org.junit.Test
    public void testHeight2() {
        AmoebaFamily family = new AmoebaFamily("Single");
        assertEquals(0, family.height());
    }

}
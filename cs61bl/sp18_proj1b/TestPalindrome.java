import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    } //Uncomment this class once you've created your Palindrome class.

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("aABcBAa"));
        assertFalse(palindrome.isPalindrome("palindrome"));
        assertFalse(palindrome.isPalindrome("aABcbAa"));
    }

    @Test
    public void testIsPalindrome2() {
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("abcdebab", cc));
        assertTrue(palindrome.isPalindrome("abccb", cc));
        assertTrue(palindrome.isPalindrome("", cc));
        assertTrue(palindrome.isPalindrome("a", cc));
        assertFalse(palindrome.isPalindrome("abcba", cc));
        assertFalse(palindrome.isPalindrome("tbarcdbd", cc));
    }
}
import java.util.LinkedList;

public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> wordLLD = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i += 1) {
            wordLLD.addLast(word.charAt(i));
        }
        return wordLLD;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> d = wordToDeque(word);
        int halfLen = d.size() / 2;
        for (int i = 0; i < halfLen; i += 1) {
            Character first = d.removeFirst();
            Character last = d.removeLast();
            if (!first.equals(last)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> d = wordToDeque(word);
        int halfLen = d.size() / 2;
        for (int i = 0; i < halfLen; i += 1) {
            Character first = d.removeFirst();
            Character last = d.removeLast();
            if (!cc.equalChars(first, last)) {
                return false;
            }
        }
        return true;
    }
}

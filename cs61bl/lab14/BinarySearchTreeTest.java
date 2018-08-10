import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class BinarySearchTreeTest {

    @org.junit.Test
    public void treeFormatTest() {
        BinarySearchTree<String> x = new BinarySearchTree();
        x.add("C");
        x.add("A");
        x.add("E");
        x.add("B");
        x.add("D");
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(outContent));
        BinaryTree.print(x, "x");
        System.setOut(oldOut);
        assertEquals(outContent.toString().trim(),
                "x in preorder\nC A B E D \nx in inorder\nA B C D E \n\n".trim());
        ArrayList<String> pre = new ArrayList<>();
        ArrayList<String> in = new ArrayList<>();
        pre.add("C");
        pre.add("A");
        pre.add("B");
        pre.add("E");
        pre.add("D");
        in.add("A");
        in.add("B");
        in.add("C");
        in.add("D");
        in.add("E");
        BinarySearchTree<String> y = new BinarySearchTree(pre, in);
        final ByteArrayOutputStream outContent2 = new ByteArrayOutputStream();
        PrintStream oldOut2 = System.out;
        System.setOut(new PrintStream(outContent2));
        BinaryTree.print(y, "y");
        System.setOut(oldOut2);
        assertEquals(outContent2.toString().trim(),
                "y in preorder\nC A B E D \ny in inorder\nA B C D E \n\n".trim());
    }

}
import static org.junit.Assert.*;

public class BinaryTreeTest {

    @org.junit.Test
    public void testHeight() {
        BinaryTree b1 = new BinaryTree(new BinaryTree.TreeNode("a", new BinaryTree.TreeNode("b"), new BinaryTree.TreeNode("c")));
        assertEquals(2, b1.height());
        BinaryTree b2 = new BinaryTree(new BinaryTree.TreeNode("a", new BinaryTree.TreeNode("b", new BinaryTree.TreeNode("d",
                new BinaryTree.TreeNode("e"), new BinaryTree.TreeNode("f")), null), new BinaryTree.TreeNode("c")));
        assertEquals(4, b2.height());
    }

    @org.junit.Test
    public void testBalanced() {
        BinaryTree b1 = new BinaryTree(new BinaryTree.TreeNode("a", new BinaryTree.TreeNode("b"), new BinaryTree.TreeNode("c")));
        assertTrue(b1.isCompletelyBalanced());
        BinaryTree b2 = new BinaryTree(new BinaryTree.TreeNode("a", new BinaryTree.TreeNode("b", new BinaryTree.TreeNode("d",
                new BinaryTree.TreeNode("e"), new BinaryTree.TreeNode("f")), null), new BinaryTree.TreeNode("c")));
        assertFalse(b2.isCompletelyBalanced());
        BinaryTree b3 = new BinaryTree(new BinaryTree.TreeNode('a', new BinaryTree.TreeNode("a", new BinaryTree.TreeNode("b"), new BinaryTree.TreeNode("c")),
                new BinaryTree.TreeNode("a", new BinaryTree.TreeNode("b"), new BinaryTree.TreeNode("c"))));
        assertTrue(b3.isCompletelyBalanced());
    }
}
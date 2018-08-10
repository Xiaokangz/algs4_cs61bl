import java.util.*;

/**
 * A BinaryTree is a tree with nodes that have up to two children.
 */
public class BinaryTree {

    /**
     * root is the root of this BinaryTree
     */
    private TreeNode root;

    /**
     * The BinaryTree constructor
     */
    public BinaryTree() {
        root = null;
    }

    public BinaryTree(TreeNode t) {
        root = t;
    }

    public TreeNode getRoot() {
        return root;
    }

    /**
     * Print the values in the tree in preorder: root value first, then values
     * in the left subtree (in preorder), then values in the right subtree
     * (in preorder).
     */
    public void printPreorder() {
        if (root == null) {
            System.out.println("(empty tree)");
        } else {
            root.printPreorder();
            System.out.println();
        }
    }

    /**
     * Print the values in the tree in inorder: values in the left subtree
     * first (in inorder), then the root value, then values in the first
     * subtree (in inorder).
     */
    public void printInorder() {
        if (root == null) {
            System.out.println("(empty tree)");
        } else {
            root.printInorder();
            System.out.println();
        }
    }

    /**
     * Fills this BinaryTree with values a, b, and c.
     * DO NOT MODIFY THIS METHOD.
     */
    public void fillSampleTree1() {
        root = new TreeNode("a", new TreeNode("b"), new TreeNode("c"));
    }

    /**
     * Fills this BinaryTree with values a, b, and c, d, e, f.
     * DO NOT MODIFY THIS METHOD.
     */
    public void fillSampleTree2() {
        root = new TreeNode("a", new TreeNode("b", new TreeNode("d",
            new TreeNode("e"), new TreeNode("f")), null), new TreeNode("c"));
    }

    /**
     * Fills this BinaryTree with the values a, b, c, d, e, f in the way that the spec pictures.
     */
    public void fillSampleTree3() {
        //YOUR CODE HERE.
        root = new TreeNode("a", new TreeNode("b"),
                new TreeNode("c", new TreeNode("d", new TreeNode("e"), new TreeNode("f")), null));
    }

    /**                                                                          
     * Fills this BinaryTree with the same leaf TreeNode.                        
     * DO NOT MODIFY THIS METHOD.                                                
     */ 
    public void fillSampleTree4() {
        TreeNode leafNode = new TreeNode("c");                                   
        root = new TreeNode("a", new TreeNode("b", leafNode, leafNode), new TreeNode("d", leafNode, leafNode));
    }
    /**
     * Like the Amoeba class, returns the height of the deepest node.
     **/
    public int height() {
        //YOUR CODE HERE
        if (root != null) {
            return root.height();
        }
        return 0;
    }

    public boolean isCompletelyBalanced() {
        //YOUR CODE HERE
        if (root != null) {
            return root.isCompletelyBalanced();
        }
        return true;
    }

    /**
     * Creates two BinaryTrees and prints them out in inorder
     */
    public static void main(String[] args) {
        BinaryTree t;
        t = new BinaryTree();
        print(t, "the empty tree");
        t.fillSampleTree1();
        print(t, "sample tree 1");
        t.fillSampleTree2();
        print(t, "sample tree 2");
        t.fillSampleTree3();
        print(t, "sample tree 3");
        t.print();
        t.fillSampleTree4();
        System.out.println(t.check());
        fibTree(5).print();
        exprTree("((a+(5*(a+b)))+(6*5))").print();
        t = exprTree("((a+(5*(9+1)))+(6*5))");
        t.optimize();
        t.print();
    }

    /**
     * Prints out the contents of a BinaryTree with a description in both
     * preorder and inorder
     * @param t           the BinaryTree to print out
     * @param description a String describing the BinaryTree t
     */
    private static void print(BinaryTree t, String description) {
        System.out.println(description + " in preorder");
        t.printPreorder();
        System.out.println(description + " in inorder");
        t.printInorder();
        System.out.println();
    }

    /**
     * A TreeNode is a Node this BinaryTree
     * Note: this class is public in this lab for testing purposes.
     * However, in professional settings as well as the rest of
     * your labs and projects, we recommend that you keep your
     * classes private.
     */
    public static class TreeNode {

        /**
         * item is the item that is contained in this TreeNode
         * left is the left child of this TreeNode
         * right is the right child of this TreeNode
         */
        public Object item;
        public TreeNode left;
        public TreeNode right;

        /**
         * A TreeNode constructor that creates a node with obj as its item
         * @param  obj the item to be contained in this TreeNode
         */
        TreeNode(Object obj) {
            item = obj;
            left = null;
            right = null;
        }

        /**
         * A TreeNode constructor that creates a node with obj as its item and
         * left and right as its children
         * @param  obj   the item to be contained in this TreeNode
         * @param  left  the left child of this TreeNode
         * @param  right the right child of this TreeNode
         */
        TreeNode(Object obj, TreeNode left, TreeNode right) {
            item = obj;
            this.left = left;
            this.right = right;
        }

        public Object getItem() {
            return item;
        }

        public TreeNode getLeft() {
            return left;
        }

        public TreeNode getRight() {
            return right;
        }

        /**
         * Prints this TreeNode and the subtree rooted at it in preorder
         */
        private void printPreorder() {
            System.out.print(item + " ");
            if (left != null) {
                left.printPreorder();
            }
            if (right != null) {
                right.printPreorder();
            }
        }

        /**
         * Prints this TreeNode and the subtree rooted at it in inorder
         */
        private void printInorder() {
            if (left != null) {
                left.printInorder();
            }
            System.out.print(item + " ");
            if (right != null) {
                right.printInorder();
            }
        }

        //Add more recursive methods here!

        public int height() {
            if (left == null && right == null) {
                return 1;
            } else if (left == null) {
                return 1 + right.height();
            } else if (right == null) {
                return 1 + left.height();
            } else {
                return 1 + Math.max(left.height(), right.height());
            }
        }

        public boolean isCompletelyBalanced() {
            if (left == null && right == null) {
                return true;
            } else if (left == null || right == null) {
                return false;
            } else {
                if (left.height() == right.height() && left.isCompletelyBalanced() && right.isCompletelyBalanced()) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        //Copy into TreeNode class
        private static final String indent1 = "    ";

        private void print(int indent) {
            // TODO your code here
            if (right != null) {
                right.print(indent + 1);
            }
            println (item, indent);
            // TODO your code here
            if (left != null) {
                left.print(indent + 1);
            }
        }

        private static void println(Object obj, int indent) {
            for (int k=0; k<indent; k++) {
                System.out.print(indent1);
            }
            System.out.println(obj);
        }

        public static TreeNode fibTree(int n) {
            if (n == 0 || n == 1) {
                return new TreeNode(n);
            }
            TreeNode fibleft = fibTree(n - 1);
            TreeNode fibright = fibTree(n - 2);
            return new TreeNode((int)fibleft.item + (int)fibright.item, fibleft, fibright);
        }

        public void optimize() {
            String str = (String)item;
            if (left != null && right != null) {
                left.optimize();
                right.optimize();
                String lstr = (String)left.item;
                String rstr = (String)right.item;
                if (validateInt(lstr) && validateInt(rstr)) {
                    int l = Integer.parseInt(lstr);
                    int r = Integer.parseInt(rstr);
                    int result;
                    //System.out.println(str);
                    if (str.equals("+")) {
                        result = l + r;
                    } else if (str.equals("-")) {
                        result = l - r;
                    } else if (str.equals("*")) {
                        result = l * r;
                        //System.out.println("* " + result);
                    } else {
                        result = l / r;
                    }
                    item = Integer.toString(result);
                    left = null;
                    right = null;
                }
            }
        }
    }

    //Copy into BinaryTree class
    public void print() {
        if (root != null) {
            root.print(0);
        }
    }

    public boolean check() {
        alreadySeen = new ArrayList();
        try {
            isOK(root);
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    // Contains nodes already seen in the traversal.
    private ArrayList alreadySeen;

    private void isOK(TreeNode t) throws IllegalStateException {
        if (t == null) {
            return;
        }
        if (alreadySeen.contains(t)) {
            throw new IllegalStateException("");
        }
        alreadySeen.add(t);
        isOK(t.left);
        isOK(t.right);
    }

    public static BinaryTree fibTree(int n) {
        BinaryTree result = new BinaryTree(TreeNode.fibTree(n));
        return result;
    }

    public static BinaryTree exprTree(String s) {
        BinaryTree result = new BinaryTree();
        result.root = result.exprTreeHelper(s);
        return result;
    }

    // Return the tree corresponding to the given arithmetic expression.
    // The expression is legal, fully parenthesized, contains no blanks,
    // and involves only the operations + and *.
    private TreeNode exprTreeHelper(String expr) {
        if (expr.charAt(0) != '(') {
            return new TreeNode(expr); // you fill this in
        } else {
            // expr is a parenthesized expression.
            // Strip off the beginning and ending parentheses,
            // find the main operator (an occurrence of + or * not nested
            // in parentheses, and construct the two subtrees.
            int nesting = 0;
            int opPos = 0;
            for (int k = 1; k < expr.length() - 1; k++) {
                // you supply the missing code
                if (expr.charAt(k) == '(') {
                    nesting++;
                } else if (expr.charAt(k) == ')') {
                    nesting--;
                }
                if (nesting == 0) {
                    opPos = k + 1;
                    break;
                }
            }
            String opnd1 = expr.substring(1, opPos);
            String opnd2 = expr.substring(opPos + 1, expr.length() - 1);
            String op = expr.substring(opPos, opPos + 1);
            System.out.println("expression = " + expr);
            System.out.println("operand 1  = " + opnd1);
            System.out.println("operator   = " + op);
            System.out.println("operand 2  = " + opnd2);
            System.out.println();
            return new TreeNode(op, exprTreeHelper(opnd1), exprTreeHelper(opnd2)); // you fill this in
        }
    }

    public void optimize() {
        if (root != null) {
            root.optimize();
        }

    }

    public static boolean validateInt(String s) {
        if (s.isEmpty()) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1){
                    return false;
                } else {
                    continue;
                }
            }
            char num = s.charAt(i);
            if (num > '9' || num < '0') {
                return false;
            }
        }
        //System.out.println("yes");
        return true;
    }

}

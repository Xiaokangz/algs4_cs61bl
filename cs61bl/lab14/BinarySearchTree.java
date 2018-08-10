import jdk.nashorn.api.tree.Tree;

import java.util.ArrayList;

/** A class implementing a BST.
  * @author CS 61BL Staff.
  */
public class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> {

	/** Constructs an empty BST. */
	public BinarySearchTree() {
		//YOUR CODE HERE
		super();
	}

	/** BST Constructor.
         *  @param root node to use for constructing new BST.
         */
	public BinarySearchTree(TreeNode root) {
		//YOUR CODE HERE
		super(root);
	}

	public BinarySearchTree(ArrayList<T> pre, ArrayList<T> in) {
		super(pre, in);
	}
	
	/** Method for checking if BST has a given key.
         *  @param  key to search for
         *  @return true if this BST contains key
         *          false if this BST does not contain key
         */
	public boolean contains(T key) {
		//YOUR CODE HERE
		TreeNode temp = root;
		while(temp != null) {
			if (((Comparable<T>) temp.item).compareTo(key) == 0) {
				return true;
			} else if (temp.item.compareTo(key) < 0) {
				temp = temp.right;
			} else {
				temp = temp.left;
			}
		}
		return false;
	}
	
	/** Adds a node for KEY iff it isn't in the BST already. 
         *  @param key to be added
         */
	public void add(T key) {
		//YOUR CODE HERE
		TreeNode prev = null;
		TreeNode temp = root;
		boolean isright = false;
		while (temp != null) {
			if (((Comparable<T>) temp.item).compareTo(key) == 0) {
				return;
			} else if (((Comparable<T>) temp.item).compareTo(key) < 0) {
				prev = temp;
				temp = temp.right;
				isright = true;
			} else {
				prev = temp;
				temp = temp.left;
				isright = false;
			}
		}
		if (prev == null) {
			root = new TreeNode(key);
		} else if (isright) {
			prev.right = new TreeNode(key);
		} else {
			prev.left = new TreeNode(key);
		}
	}
	
	/** Deletes a node from the BST. 
         *  @param  key to be deleted
         *  @return key that was deleted
         */
	public T delete(T key) {
		TreeNode parent = null;
		TreeNode curr = root;
		TreeNode delNode = null;
		TreeNode replacement = null;
		boolean rightSide = false;
		
		while (curr != null && !curr.item.equals(key)) {
			if (((Comparable<T>) curr.item).compareTo(key) > 0) {
				parent = curr;
				curr = curr.left;
				rightSide = false;
			} else {
				parent = curr;
				curr = curr.right;
				rightSide = true;
			}
		}
		delNode = curr;
		if (curr == null) {
			return null;
		}
		
		if (delNode.right == null) {
			if (root == delNode) {
				root = root.left;
			} else {
				if (rightSide) {
					parent.right = delNode.left;
				} else {
					parent.left = delNode.left;
				}
			}
		} else {
			curr = delNode.right;
			replacement = curr.left;
			if (replacement == null) {
				replacement = curr;
			} else {
				while (replacement.left != null) {
					curr = replacement;
					replacement = replacement.left;
				}
				curr.left = replacement.right;
				replacement.right = delNode.right;
			}
			replacement.left = delNode.left;
			if (root == delNode) {
				root = replacement;
			} else {
				if (rightSide) {
					parent.right = replacement;
				} else {
					parent.left = replacement;
				}
			}
		}
		return delNode.item;
	}
}

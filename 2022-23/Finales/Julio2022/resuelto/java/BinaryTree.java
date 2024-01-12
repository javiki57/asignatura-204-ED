/* ------------------------------------------------------------------------------
   -- Student's name:
   -- Student's group:
   -- Identity number (DNI if Spanish/passport if Erasmus):
   --
   -- Data Structures. Grado en Informática. UMA.
   -------------------------------------------------------------------------------
*/

package exercises;
import dataStructures.tuple.Tuple2;

import java.util.LinkedList;
import java.util.List;

public class BinaryTree<T extends Comparable<? super T>> {

    private static class Node<E> {
        E value;
        Node<E> left;
        Node<E> right;

        public Node(E value, Node<E> left, Node<E> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public Node(E value) {
            this(value, null, null);
        }
    }

    private Node<T> root;

    public BinaryTree() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void insertBST(T value) {
        root = insertBSTRec(root, value);
    }

    private Node<T> insertBSTRec(Node<T> node, T elem) {
        if (node == null) {
            node = new Node<>(elem);
        } else if (elem.compareTo(node.value) < 0)
            node.left = insertBSTRec(node.left, elem);
        else if (elem.compareTo(node.value) > 0)
            node.right = insertBSTRec(node.right, elem);
        else
            node.value = elem;
        return node;
    }

    /**
     * Returns representation of tree as a String.
     */
    @Override
    public String toString() {
        String className = getClass().getSimpleName();
        return className + "(" + toStringRec(this.root) + ")";
    }

    private static String toStringRec(Node<?> node) {
        return node == null ? "null" : "Node<" + toStringRec(node.left) + ","
                + node.value + "," + toStringRec(node.right) + ">";
    }

// -- ESCRIBE TU SOLUCIÓN DEBAJO ----------------------------------------------
// -- WRITE YOUR SOLUTION BELOW  ----------------------------------------------
// EXERCISE 2
    public int subTreesInRange(T min, T max) {
        Tuple2<Integer, Boolean> res = subTreesInRange(root,min, max);
        return res._1();
    }

    public Tuple2<Integer,Boolean> subTreesInRange(Node<T> node, T min, T max) {
        if (node == null)
            return new Tuple2<>(0,true);
        else {
            Tuple2<Integer,Boolean> res1 = subTreesInRange(node.left, min, max);
            Tuple2<Integer,Boolean> res2 = subTreesInRange(node.right, min, max);
            return (min.compareTo(node.value) <= 0 &&
                    node.value.compareTo(max) <= 0 &&
                    res1._2() && res2._2()) ? new Tuple2<>(1+res1._1()+res2._1(), true):
                    new Tuple2<>(res1._1()+res2._1(), false);
        }
    }
}

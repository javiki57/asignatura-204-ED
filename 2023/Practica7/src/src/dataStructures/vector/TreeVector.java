/******************************************************************************
 * Student's name:
 * Student's group:
 * Data Structures. Grado en Informática. UMA.
******************************************************************************/

package dataStructures.vector;

import dataStructures.list.List;

public class TreeVector<T> {

    private int size;
    private Tree<T> root;

    private interface Tree<E> {
        E get(int index);

        void set(int index, E x);

        List<E> toList();
    }

    private static class Leaf<E> implements Tree<E> {
        private E value;

        public Leaf(E x) {
            value = x;
        }

        @Override
        public E get(int i) {
        	//to do
            return null;
        }

        @Override
        public void set(int i, E x) {
        	//to do
        }

        @Override
        public List<E> toList() {
        	//to do
            return null;
        }
    }

    private static class Node<E> implements Tree<E> {
        private Tree<E> left;
        private Tree<E> right;

        public Node(Tree<E> l, Tree<E> r) {
            left = l;
            right = r;
        }

        @Override
        public E get(int i) {
        	//to do
            return null;
        }

        @Override
        public void set(int i, E x) {
        	//to do
        }

        @Override
        public List<E> toList() {
        	//to do
            return null;
        }
    }

    public TreeVector(int n, T value) {
    	//to do
    }

    public int size() {
    	//to do
        return 0;
    }

    public T get(int i) {
    	//to do
        return null;
    }

    public void set(int i, T x) {
    	//to do
    }

    public List<T> toList() {
    	//to do
        return null;
    }

    protected static <E> List<E> intercalate(List<E> xs, List<E> ys) {
    	//to do
        return null;
    }

    static protected boolean isPowerOfTwo(int n) {
    	//to do
        return false;
    }

    public static <E> TreeVector<E> fromList(List<E> l) {
    	//to do
        return null;
    }
}

/*
 * Estructuras de Datos. 2º Curso. ETSI Informática. UMA
 * PRACTICA 7. Vectores
 * APELLIDOS, NOMBRE:
 */

package dataStructures.vector;

import dataStructures.list.List;

public class TreeVector<T> {

    private final int size;
    private final Tree<T> root;

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
            // TODO
            return null;
        }

        @Override
        public void set(int i, E x) {
            // TODO
        }

        @Override
        public List<E> toList() {
            // TODO
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
            // TODO
            return null;
        }

        @Override
        public void set(int i, E x) {
            // TODO
        }

        @Override
        public List<E> toList() {
            // TODO
            return null;
        }
    }

    public TreeVector(int n, T value) {
        // TODO
    }

    public int size() {
        // TODO
        return 0;
    }

    public T get(int i) {
        // TODO
        return null;
    }

    public void set(int i, T x) {
        // TODO
    }

    public List<T> toList() {
        // TODO
        return null;
    }

    protected static <E> List<E> intercalate(List<E> xs, List<E> ys) {
        // TODO
        return null;
    }

    static protected boolean isPowerOfTwo(int n) {
        // TODO
        return false;
    }

    public static <E> TreeVector<E> fromList(List<E> l) {
        // TODO
        return null;
    }
}

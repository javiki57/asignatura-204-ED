package dataStructures.bag;

import java.util.Iterator;
import java.util.StringJoiner;

public class SortedLinkedBag<T extends Comparable<? super T>> implements Bag<T> {

    private static class Node<E> {
        private E elem;
        private int count;
        private Node<E> next;

        public Node(E x, Node<E> nextNode) {
            elem = x;
            count = 1;
            next = nextNode;
        }
    }

    /**
     * <strong>Representation invariant:</strong>
     * <p>
     * 1. the linked sequence referenced by {@code first} must be sorted by {@code elem}, with no duplicates
     * <p>
     * 2. {@code count} in every {@code Node} must be positive
     * <p>
     * 3. {@code size} must be equal to the sum of all {@code count} in the linked sequence
     * referenced by {@code first}
     * <p>
     * <strong>Example:</strong>
     * <pre>
     *    first -> ('a', 5) -> ('d', 1) -> ('t', 3) -> ('z', 2)
     *    size = 11
     * </pre>
     */
    private Node<T> first;
    private int size;

    public SortedLinkedBag() {
        // TODO
    }

    @Override
    public boolean isEmpty() {
        // TODO
        return false;
    }

    @Override
    public int size() {
        // TODO
        return 0;
    }

    @Override
    public void insert(T x) {
        // TODO
    }

    @Override
    public int occurrences(T x) {
        // TODO
        return 0;
    }

    @Override
    public void delete(T x) {
        // TODO
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" ", "Bag(", ")");
        for (Node<T> current = first; current != null; current = current.next) {
            sj.add(String.format("(%s, %s)", current.elem, current.count));
        }
        return sj.toString();
    }

    @Override
    public Iterator<T> iterator() {
        // TODO
        return null;
    }
}

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
        first = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {

        return size == 0;
    }

    @Override
    public int size() {

        return size;
    }

    @Override
    public void insert(T x) {

        if(first == null){
           first = new Node<>(x,null);
        }else{

            Node<T> current = first;
            Node<T> previous = null;

            while(current != null && x.compareTo(current.elem)>0) {

                previous = current;
                current = current.next;
            }

            if(current != null && x.equals(current.elem)){
                current.count++;

            }else{
                Node<T> nuevo = new Node<>(x, current);

                if(previous == null) {
                    first = nuevo;

                }else{
                    previous.next = nuevo;
                }
                size ++;
            }
        }
    }

    @Override
    public int occurrences(T x) {
        Node<T> current = first;

        while(current != null && x.compareTo(current.elem)>0){
            current = current.next;
        }

        if (current != null && x.equals(current.elem)) {
            return current.count;
        }

        return 0;

    }

    @Override
    public void delete(T x) {
        Node<T> current = first;
        Node<T> previous = null;

        while(current != null && x.compareTo(current.elem)>0){
            previous = current;
            current = current.next;
        }

        if(current != null && x.equals(current.elem)){

            if(occurrences(current.elem)>1){
                current.count--;

            }else{

                if(previous == null){
                    first = current.next;

                }else{
                    previous.next=current.next;
                }
                size--;
            }
        }
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

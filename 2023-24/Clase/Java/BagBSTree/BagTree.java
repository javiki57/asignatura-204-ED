package dataStructures.bag;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class BagTree<T extends Comparable<? super T>> implements Bag<T>{
    private static class Node<K> {
        K element;
        Integer occurrences;
        Node<K> left;
        Node<K> right;

        public Node(K k, Integer v) {
            element = k;
            occurrences = v;
            left = null;
            right = null;
        }
    }

    Node<T> root;
    public BagTree(){
        root = null;
    }
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int occurrences(T item) {
        return occurrencesR(root,item);
    }

    private int occurrencesR(Node<T> root, T item){
        if(root != null){

            if(root.element.compareTo(item) == 0){
                return root.occurrences;

            }else if(root.element.compareTo(item) < 0){
               return occurrencesR(root.left, item);

            }else{
               return occurrencesR(root.right, item);
            }
        }

        return 0;
    }

    
    @Override
    public void insert(T item) {
        root = insertR(root, item);
    }

    private Node<T> insertR(Node<T> root, T item){

        if(root != null){
            if(root.element.compareTo(item) == 0){
                root.occurrences++;

            } else if (root.element.compareTo(item) < 0) {
                root.left = insertR(root.left, item);

            } else {
                root.right = insertR(root.right, item);
            }

        }else{
            root = new Node<>(item, 1);
        }

        return root;
    }

    
    @Override
    public void delete(T item) {
        root = deleteR(root, item);
    }

    private Node<T> deleteR(Node<T> root, T item){
        if(root != null){

            if(root.element.compareTo(item) == 0){
                if(root.occurrences > 1 ) root.occurrences--;
                else{
                    if(root.left == null){
                        root = root.right;

                    }else if(root.right == null){
                        root = root.left;

                    }else{
                        split(root.right,root);
                    }
                }

            }else if (root.element.compareTo(item) < 0){
                root.left = deleteR(root.left,item);

            } else {
                root.right = deleteR(root.right,item);

            }
        }
        return root;
    }

    private static <T extends Comparable<? super T>>
    Node<T> split(Node<T> node, Node<T> temp) {
        if (node.left == null) {
            // min node found, so copy min key and value
            temp.element = node.element;
            temp.occurrences = node.occurrences;
            return node.right; // remove node
        } else {
            // remove min from left subtree
            node.left = split(node.left, temp);
            return node;
        }
    }


    public String toString(){
        //TODO
    	return null;
    }

    public Iterable<T> inOrder(){
        return new BagTreeInOrdIterator();
    }

    private class BagTreeInOrdIterator implements Iterator<T>{

        public boolean hasNext(){

        }

        @Override
        public T next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }

        }
    }

}

/******************************************************************************
 * Student's name:
 * Student's group:
 * Data Structures. Grado en Informática. UMA.
******************************************************************************/

package dataStructures.vector;

import dataStructures.list.LinkedList;
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

            return value;
        }

        @Override
        public void set(int i, E x) {
        	this.value = x;
        }

        @Override
        public List<E> toList() {
        	List<E> lista = new LinkedList<E>();
            lista.append(value);
            return lista;
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

            if( i % 2 == 0){
                return left.get(i/2);

            }else{
                return right.get(i/2);
            }
        }


        @Override
        public void set(int i, E x) {
            if( i % 2 == 0){
                left.set(i/2,x);

            }else{
                right.set(i/2,x);
            }
        }

        @Override
        public List<E> toList() {

            return intercalate(left.toList(), right.toList());
        }
    }

    public TreeVector(int n, T value) {

        if(n < 0){
            throw new VectorException("Error, vector negativo.");
        }

        size = (int) Math.pow(2,n);
        root = arbol(value, size);

    }

    private Tree<T> arbol(T value, int n){
        if(n>0){
            return  new Node<>(arbol(value, n-1),arbol(value, n-1));

        }else{
            return new Leaf<>(value);
        }
    }

    public int size() {

        return size;
    }

    public T get(int i) {
        if(i < 0 || i >= size){
            throw new VectorException("Error, el parámetro i está fuera del rango");
        }

        return root.get(i);
    }

    public void set(int i, T x) {
    	if(i < 0 || i >= size){
            throw new VectorException("Error, el parámetro i está fuera del rango");
        }

        root.set(i,x);
    }

    public List<T> toList() {

        return root.toList();
    }

    protected static <E> List<E> intercalate(List<E> xs, List<E> ys) {
        int i = 0;
        List<E> lista = new LinkedList<>();

        while(i<xs.size() || i<ys.size()){
            lista.append(xs.get(i));
            lista.append(ys.get(i));
            i++;
        }

        return lista;
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

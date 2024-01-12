/*
 * Estructuras de Datos. 2º Curso. ETSI Informática. UMA
 * PRACTICA 7. Vectores
 * APELLIDOS, NOMBRE:
 */

package dataStructures.vector;

import dataStructures.list.ArrayList;
import dataStructures.list.List;

import java.util.Iterator;

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

            return value;
        }

        @Override
        public void set(int i, E x) {
            value = x;
        }

        @Override
        public List<E> toList() {
            List<E> lista = new ArrayList<>();
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
            if(i % 2 == 0){
                return left.get(i/2);
            }else{
                return right.get(i/2);
            }
        }

        @Override
        public void set(int i, E x) {
            if(i % 2 == 0){
                left.set(i/2, x);
            }else{
                right.set(i/2,x);
            }
        }

        @Override
        public List<E> toList() {

            return intercalate(left.toList(),right.toList());
        }
    }

    public TreeVector(int n, T value) {

        if(n<0) throw new VectorException("Exponente negativo");

        size = (int) Math.pow(2,n);
        root = tree (n, value);
    }

    private Tree<T> tree(int n, T value){
        if(n == 0){
            return new Leaf<>(value);

        }else{
            Tree<T> left = tree(n-1, value);
            Tree<T> right = tree(n-1, value);
            return new Node<>(left,right);

        }
    }

    public int size() {

        return size;
    }

    public T get(int i) {
        if(i >= size || i < 0) throw new VectorException("Error, posición del vector errónea.");


        return root.get(i);
    }

    public void set(int i, T x) {
        if(i >= size || i < 0) throw new VectorException("Error, posición del vector errónea.");
        else{
            root.set(i,x);
        }
    }

    public List<T> toList() {
        List<T> lista = root.toList();

        return lista;
    }

    protected static <E> List<E> intercalate(List<E> xs, List<E> ys) {
        List<E> lista = new ArrayList<>();
        int i = 0;

        Iterator<E> x = xs.iterator();
        Iterator<E> y = ys.iterator();
        while(x.hasNext() && y.hasNext()){
            lista.append(x.next());
            lista.append(y.next());
        }

        return lista;
    }

    static protected boolean isPowerOfTwo(int n) {
        int exp = 0;
        while(Math.pow(2,exp) != n && Math.pow(2, exp) <=n){
            exp++;
        }

        return Math.pow(2, exp) > n;
    }

    //Firulo este último método.
    public static <E> TreeVector<E> fromList(List<E> l) {
        if(!isPowerOfTwo(l.size())){
            throw new VectorException("La longitud de la lista no es potencia de 2");
        }

        return new TreeVector<>(l.size(), l.get(0));
    }


}

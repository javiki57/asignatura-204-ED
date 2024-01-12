/******************************************************************************
 * Student's name:
 * Student's group:
 * Data Structures. Grado en Informática. UMA.
******************************************************************************/

package dataStructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SparseVector<T> implements Iterable<T> {

    protected interface Tree<T> {

        T get(int sz, int i);

        Tree<T> set(int sz, int i, T x);
    }

    // Unif Implementation

    protected static class Unif<T> implements Tree<T> {

        private T elem;

        public Unif(T e) {
            elem = e;
        }

        @Override
        public T get(int sz, int i){
            //TO DO
            return null;
        }

        @Override
        public Tree<T> set(int sz, int i, T x) {
                //TO DO
                return null;
        }

        @Override
        public String toString() {
            return "Unif(" + elem + ")";
        }
    }

    // Node Implementation

    protected static class Node<T> implements Tree<T> {

        private Tree<T> left, right;

        public Node(Tree<T> l, Tree<T> r) {
            left = l;
            right = r;
        }

        @Override
        public T get(int sz, int i) {
            //TO DO
            return null;

        }

        @Override
        public Tree<T> set(int sz, int i, T x) {
            //TO DO
            return null;
        }
        protected Tree<T> simplify() {
            //TO DO
            return null;
        }

        @Override
        public String toString() {return "Node(" + left + ", " + right + ")";}
    }

    // SparseVector Implementation

    private int size;
    private Tree<T> root;

    public SparseVector(int n, T elem) {
        //TO DO

    }

    public int size() {
        //TO DO
        return 0;
    }

    public T get(int i) {
        //TO DO
        return null;
    }

    public void set(int i, T x) {
        //TO DO


    }





//Creo que de aqui para abajo esta fuera de la plantilla pero no estoy seguro asi que lo comento

    @Override
    public Iterator<T> iterator() {return new IteratorSparceVector();}

    //Creamos nueva clase Iterador para el SparceVector
    //que implemente Iterator<T>
    public class IteratorSparceVector implements Iterator<T>{

        //Creamos el indice i y lo incializamos a 0 que es la primera posicion
        int i;
        public IteratorSparceVector(){i=0;}

        //Metodo que va comprobando que habra un elemento en la proxima posicion
        //esto sera True siempre que el indice no supere el size del vector
        @Override
        public boolean hasNext() {
            return i<size;
        }

        //Cada vez que se llame a next iterara una posicion del vector
        @Override
        public T next(){
          if(!hasNext()){
              throw new NoSuchElementException();
          }
          T a = root.get(size,i);
          i++;
          return a;
        }
    }
    @Override
    public String toString() {
        return "SparseVector(" + size + "," + root + ")";
    }
}

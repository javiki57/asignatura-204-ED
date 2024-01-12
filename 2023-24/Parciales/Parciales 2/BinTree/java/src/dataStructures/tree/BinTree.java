/* *
 * Estructuras de Datos.
 * 2.º Grado en Ingeniería Informática, del Software y Computadores. UMA.
 *
 * Práctica evaluable 7. Enero 2021.
 *
 * Apellidos, Nombre:
 * Grupo:
 */

package dataStructures.tree;

import dataStructures.list.List;
import dataStructures.list.LinkedList;

public class BinTree<T extends Comparable<? super T>> {

    private static class Tree<E> {
        private E elem;
        private Tree<E> left;
        private Tree<E> right;

        public Tree(E e, Tree<E> l, Tree<E> r) {
            elem = e;
            left = l;
            right = r;
        }
    }

    private Tree<T> root;

    public BinTree() {
        root = null;
    }

    public BinTree(T x) {
        root = new Tree<>(x, null, null);
    }

    public BinTree(T x, BinTree<T> l, BinTree<T> r) {
        root = new Tree<>(x, l.root, r.root);
    }

    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns representation of tree as a String.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + toStringRec(this.root) + ")";
    }

    private static String toStringRec(Tree<?> tree) {
        return tree == null ? "null" : "Node<" + toStringRec(tree.left) + ","
                + tree.elem + "," + toStringRec(tree.right)
                + ">";
    }

    /**
     * Returns a String with the representation of tree in DOT (graphviz).
     */
    public String toDot(String treeName) {
        final StringBuffer sb = new StringBuffer();
        sb.append(String.format("digraph %s {\n", treeName));
        sb.append("node [fontname=\"Arial\", fontcolor=red, shape=circle, style=filled, color=\"#66B268\", fillcolor=\"#AFF4AF\" ];\n");
        sb.append("edge [color = \"#0070BF\"];\n");
        toDotRec(root, sb);
        sb.append("}");
        return sb.toString();
    }

    private void toDotRec(Tree<T> current, StringBuffer sb) {
        if (current != null) {
            final int currentId = System.identityHashCode(current);
            sb.append(String.format("%d [label=\"%s\"];\n", currentId, current.elem));
            if (!isLeaf(current)) {
                processChild(current.left, sb, currentId);
                processChild(current.right, sb, currentId);
            }
        }
    }

    private static <T extends Comparable<? super T>> boolean isLeaf(Tree<T> current) {
        return current != null && current.left == null && current.right == null;
    }

    private void processChild(Tree<T> child, StringBuffer sb, int parentId) {
        if (child == null) {
            sb.append(String.format("l%d [style=invis];\n", parentId));
            sb.append(String.format("%d -> l%d;\n", parentId, parentId));
        } else {
            sb.append(String.format("%d -> %d;\n", parentId, System.identityHashCode(child)));
            toDotRec(child, sb);
        }
    }

    // Ejercicio 1

    public T maximum() {

        if(root == null){
            throw new BinTreeException("Error, el arbol está vacío");

        }

        return maximumRec(root);
    }

    private T maximumRec(Tree<T> node){

        T sol = node.elem;
        T max;

        if(node.left != null){
            max = maximumRec(node.left);

            if(max.compareTo(sol) > 0){
                sol = max;
            }
        }

        if(node.right != null){
            max = maximumRec(node.right);

            if(max.compareTo(sol) > 0){
                sol = max;
            }
        }

        return sol;
    }

    // Ejercicio 2

     public int numBranches() {
        if(root == null){
            return 0;

        }else if (isLeaf(root)){
            return 0;

        }else{
            return numBranchesRec(root);
        }
    }

    private int numBranchesRec(Tree<T> node){

        int rama = 0;

        if(isLeaf(node)) {
            return 1;
        }

        if(node.right != null){
           rama += numBranchesRec(node.right);

        }

        if(node.left != null){
            rama += numBranchesRec(node.left);

        }

        return rama;
    }


    // Ejercicio 3

    public List<T> atLevel(int i) {


        return atLevelRec(root, i);
    }

    private List<T> atLevelRec(Tree<T> node, int i){

        List<T> lista = new LinkedList<>();

        if(node != null){
            if(i == 0){
                lista.append(node.elem);
            }
            List<T> ll = new LinkedList<>();
            List<T> lr = new LinkedList<>();

            if(node.left != null){
                ll = atLevelRec(node.left, i-1);
            }

            if(node.right != null){
                lr = atLevelRec(node.right,i-1);
            }

            for(T t : ll){
                lista.append(t);
            }

            for(T t : lr){
                lista.append(t);
            }

        }

        return lista;

    }



    // Ejercicio 4

    public void rotateLeftAt(T x) {

        root = rotateLeftAtRec(root, x);
    }

    private Tree<T> rotateLeftAtRec(Tree<T> node, T x){

       if(node == null){
           return null;
       }

       int cmp = x.compareTo(node.elem);

       if(cmp < 0){
           node.left = rotateLeftAtRec(node.left, x);

       }else if (cmp > 0){
           node.right = rotateLeftAtRec(node.right, x);

       }else{

           if(node.right != null){
               Tree<T> nuevo = node.right;
               node.right = nuevo.left;
               nuevo.left = node;

               return nuevo;

           }else{
               System.out.println("No se puede realizar la rotación izquierda en el nodo con valor " + x);
           }

       }

        return node;
    }

    // Ejercicio 5

    public void decorate(T x) {

        root = decorateRec(root, x);
    }
    private Tree<T> decorateRec(Tree<T> node , T x) {
        if (node == null) {
            return new Tree<>(x, null, null);
        }
        if (isLeaf(node)) {
            return new Tree<T>(node.elem, new Tree<>(x, null, null), new Tree<>(x, null, null));
        }

        node.left = decorateRec(node.left, x);
        node.right = decorateRec(node.right, x);

        return node;
    }
}

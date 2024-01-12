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


    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + toStringRec(this.root) + ")";
    }

    private static String toStringRec(Tree<?> tree) {
        return tree == null ? "null" : "Node<" + toStringRec(tree.left) + ","
                + tree.elem + "," + toStringRec(tree.right)
                + ">";
    }


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
        if(this.root == null) throw new BinTreeException("No hay elementos");

        return maximo(root);
    }

    private T maximo(Tree<T> t){
        T sol = (T) t.elem;
        T max;

        if(t.left != null){
            max = maximo(t.left);
            if(max.compareTo(sol) > 0 ) sol = max;
        }

        if(t.right != null){
            max = maximo(t.right);
            if(max.compareTo(sol) > 0) sol = max;
        }
       return sol;

    }


    // Ejercicio 2

    public int numBranches() {

        if(root == null) return 0;
        if(isLeaf(root)) return 0;

        return numBranchesRec(root);
    }

    private int numBranchesRec(Tree<T> t){
        int cont = 0;

        if(isLeaf(t)) return 1;
        if(t.right != null) cont += numBranchesRec(t.right);
        if(t.left != null)  cont += numBranchesRec(t.left);

        return cont;
    }


    // Ejercicio 3

    public List<T> atLevel(int i) {

        return atLevelRec(root,i);
    }

    private List<T> atLevelRec(Tree<T> t, int i){

        List<T> lista = new LinkedList<>();

        if(t != null){
            if(i==0) lista.append(t.elem);
            List<T> l1 = new LinkedList<>();
            List<T> l2 = new LinkedList<>();
            if(t.left != null) l1 = atLevelRec(t.left, i-1);
            if(t.right != null) l2 = atLevelRec(t.right,i-1);
            for (T j : l1) lista.append(j);
            for (T j : l2) lista.append(j);
        }

        return lista;
    }


    // Ejercicio 4

    public void rotateLeftAt(T x) {
        // TODO


    }

    // Ejercicio 5

    public void decorate(T x) {
        // TODO

    }

}
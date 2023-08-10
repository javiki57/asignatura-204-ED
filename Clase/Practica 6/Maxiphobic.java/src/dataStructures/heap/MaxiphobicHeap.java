/*
 * Estructuras de Datos. 2º Curso. ETSI Informática. UMA
 * PRACTICA 6. Montículos maxifóbicos en Java
 * APELLIDOS, NOMBRE:
 */

package dataStructures.heap;

// Solo tienes que completar el método merge.

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Heap implemented using maxiphobic heap-ordered binary trees.
 *
 * @param <T> Type of elements in heap.
 */
public class MaxiphobicHeap<T extends Comparable<? super T>> implements Heap<T> {

    private static class Tree<E> {
        private final E elem;
        private final int size;
        private final Tree<E> left;
        private final Tree<E> right;

        public Tree(E x, Tree<E> l, Tree<E> r) {
            elem = x;
            left = l;
            right = r;
            size = 1 + size(l) + size(r);
        }
    }

    private Tree<T> root;

    /**
     * Creates an empty Maxiphobic Heap.
     * <p>
     * Time complexity: O(1)
     */
    public MaxiphobicHeap() {
        root = null;
    }

    private static int size(Tree<?> heap) {
        return heap == null ? 0 : heap.size;
    }

    private static boolean isLeaf(Tree<?> current) {
        return current != null && current.left == null && current.right == null;
    }

    /*
        Al mezclar tenemos tres montículos: el ganador y los hijos del perdedor.
        Se cuelga a la izquierda el más pesado de los tres y a la derecha la mezcla
        de los más ligeros:

                               mínimo
                              /      \
                           más       mezcla de los
                         pesado       más ligeros

       Política de desempate:

       Si hay empate para elegir el montículo más pesado:

        1. elegir preferentemente al hijo izquierdo del perdedor
        2. si no, elegir preferentemente al hijo derecho del perdedor
        3. si no, elegir al ganador
    */

    private static <T extends Comparable<? super T>> Tree<T> merge(Tree<T> h1, Tree<T> h2) {
        // TODO
        Tree<T> heapDeveloper = null;

        if(h1 == null){
            return h2;

        }else if(h2 == null){
            return h1;
        }

        if(h1.elem.compareTo(h2.elem) <= 0){
            ArrayList<Tree<T>> arrayHeap = ganadorYPerdedores(h1.left,h2.right,h2);
            heapDeveloper = new Tree<>(h1.elem, arrayHeap.get(0), merge(arrayHeap.get(1), arrayHeap.get(2)));
        }else{
            ArrayList<Tree<T>> arrayHeap = ganadorYPerdedores(h2.left,h1.right,h1);
            heapDeveloper = new Tree<>(h2.elem, arrayHeap.get(0), merge(arrayHeap.get(1), arrayHeap.get(2)));
        }

        return heapDeveloper;
    }

    public static <T extends Comparable<? super T>> ArrayList<Tree<T>> ganadorYPerdedores(Tree<T> h1, Tree<T> h2, Tree<T> h3){
        ArrayList<Tree<T>> ListaDevolver = new ArrayList<>();
        ListaDevolver.add(h1);
        ListaDevolver.add(h2);
        ListaDevolver.add(h3);
        ListaDevolver.sort(new Comparator<Tree<T>>() {
            @Override
            public int compare(Tree<T> o1, Tree<T> o2) {
                if(o2 == null){
                    return -1;
                }else if (o1 == null){
                    return 1;
                }else if (o2.size >= o1.size){
                    return 1;
                }else{
                    return -1;
                }
            }
        });

        return ListaDevolver;

    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(1)
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(1)
     */
    @Override
    public int size() {
        return root == null ? 0 : root.size;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(1)
     *
     * @throws <code>EmptyHeapException</code> if heap stores no element.
     */
    @Override
    public T minElem() {
        if (isEmpty())
            throw new EmptyHeapException("minElem on empty heap");
        else
            return root.elem;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(log n)
     *
     * @throws <code>EmptyHeapException</code> if heap stores no element.
     */
    @Override
    public void delMin() {
        if (isEmpty())
            throw new EmptyHeapException("delMin on empty heap");
        else
            root = merge(root.left, root.right);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(log n)
     */
    @Override
    public void insert(T value) {
        Tree<T> newHeap = new Tree<>(value, null, null);
        root = merge(root, newHeap);
    }

    public void clear() {
        root = null;
    }

    /**
     * Returns representation of tree as a String.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + toStringRec(this.root) + ")";
    }

    /**
     * Returns a String with the representation of tree in DOT (graphviz).
     */
    public String toDot(String treeName) {
        final StringBuffer sb = new StringBuffer();
        sb.append(String.format("digraph \"%s\" {\n", treeName));
        sb.append("node [fontname=\"Arial\", fontcolor=red, shape=circle, style=filled, color=\"#66B268\", fillcolor=\"#AFF4AF\" ];\n");
        sb.append("edge [color = \"#0070BF\"];\n");
        toDotRec(root, sb);
        sb.append("}");
        return sb.toString();
    }

    private static String toStringRec(Tree<?> tree) {
        return tree == null ? "null" : "Node<" + toStringRec(tree.left) + ","
                + tree.elem + "," + toStringRec(tree.right)
                + ">";
    }

    private static void toDotRec(Tree<?> current, StringBuffer sb) {
        if (current != null) {
            final int currentId = System.identityHashCode(current);
            sb.append(String.format("%d [label=\"%s\"];\n", currentId, current.elem));
            if (!isLeaf(current)) {
                processChild(current.left, sb, currentId);
                processChild(current.right, sb, currentId);
            }
        }
    }

    private static void processChild(Tree<?> child, StringBuffer sb, int parentId) {
        if (child == null) {
            sb.append(String.format("l%d [style=invis];\n", parentId));
            sb.append(String.format("%d -> l%d;\n", parentId, parentId));
        } else {
            sb.append(String.format("%d -> %d;\n", parentId, System.identityHashCode(child)));
            toDotRec(child, sb);
        }
    }
}

/*
  Estructuras de Datos
  Grado en Ingeniería Informática, del Software y de Computadores
  Tema 4. Árboles
  Pablo López
*/


import dataStructures.list.LinkedList;
import dataStructures.list.List;

public class BinTree<T> {

    private static class Tree<E> {
        private final E elem;
        private final Tree<E> left;
        private final Tree<E> right;

        public Tree(E e, Tree<E> l, Tree<E> r) {
            elem = e;
            left = l;
            right = r;
        }
    }

    private final Tree<T> root;

    public BinTree() {
        root = null;
    }

    public BinTree(T x) {
        root = new Tree<>(x, null, null);
    }

    public BinTree(T x, BinTree<T> l, BinTree<T> r) {
        root = new Tree<>(x, l.root, r.root);
    }

    private static boolean isLeaf(Tree<?> current) {
        return current != null && current.left == null && current.right == null;
    }

    public int weight() {

        return weight(root);
    }
    private int weight(Tree<T> arbol){

        if(arbol == null){
            return 0;
        }else{
            int leftWeight = 1 + weight(arbol.left);
            int rightWeight = 1 + weight(arbol.right);
            return leftWeight + rightWeight + 1;
        }

    }

    public int height() {

        return height(root);
    }

    private int height(Tree<T> arbol){
        if(arbol == null){
            return 0;
        }else{
            int leftHeight = height(arbol.left);
            int rightHeight = height(arbol.right);
            return Math.max(leftHeight, rightHeight) + 1;

        }
    }

    public List<T> border() {
        List<T> lista = new LinkedList<>();
        border(root, lista);
        return lista;
    }

    private void border(Tree<T> arbol, List<T> lista){
        if(arbol != null){

            if(isLeaf(arbol)){
                lista.append(arbol.elem);

            }else{
                border(arbol.right,lista);
                border(arbol.left,lista);
            }

        }

    }

    public boolean isElem(T x) {

        return isElem(x,root);
    }

    private boolean isElem (T x, Tree<T> node){
        if (node == null) {
            return false;
        } else if (node.elem.equals(x)) {
            return true;
        } else {
            return isElem(x,node.right) || isElem(x, node.left);
        }
    }

    public List<T> atLevel(int i) {
        List<T> lista = new LinkedList<>();
        int treeHeight = height();

        if (i <= treeHeight) {
            atLevel(root, i,0, lista);
        }
        return lista;
    }

    private void atLevel(Tree<T> node, int targetLevel, int currentLevel, List<T> levelElements) {
        if (node != null) {

            if (currentLevel == targetLevel) {
                levelElements.append(node.elem);
                return;
            }

            atLevel(node.left, targetLevel, currentLevel + 1, levelElements);
            atLevel(node.right, targetLevel, currentLevel + 1, levelElements);
        }

    }

    public List<T> inOrder() {
        List<T> lista = new LinkedList<>();
        inOrder(root,lista);
        return lista;
    }

    private void inOrder(Tree<T> node, List<T> lista){
        if(node != null){
            inOrder(node.left,lista);
            lista.append(node.elem);
            inOrder(node.right,lista);
        }
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
        sb.append(String.format("digraph \"%s\" {\n", treeName));
        sb.append("node [fontname=\"Arial\", fontcolor=red, shape=circle, style=filled, color=\"#66B268\", fillcolor=\"#AFF4AF\" ];\n");
        sb.append("edge [color = \"#0070BF\"];\n");
        toDotRec(root, sb);
        sb.append("}");
        return sb.toString();
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

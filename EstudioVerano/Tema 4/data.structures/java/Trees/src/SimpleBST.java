/*
  Estructuras de Datos
  Grado en Ingeniería Informática, del Software y de Computadores
  Tema 4. Árboles
  Pablo López
*/

public class SimpleBST<K extends Comparable<? super K>, V> {

    private static class Tree<C, D> {
        C key;
        D value;
        Tree<C, D> left;
        Tree<C, D> right;

        public Tree(C key, D value, Tree<C, D> left, Tree<C, D> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    private Tree<K, V> root;

    public SimpleBST() {
        root = null;
    }

    public void insert(K k, V v) {
        root = insertRec(root, k, v);
    }

    private Tree<K,V> insertRec(Tree<K,V> node, K k, V v){

        if(node == null){
            return new Tree<>(k,v,null,null);
        }

        if(k.compareTo(node.key) < 0){
               node.left = insertRec(node.left, k ,v);

        }else if(k.compareTo(node.key) > 0){
               node.right = insertRec(node.right, k, v);

        }else{
               node.value = v;
        }

        return node;
    }

    public V search(K k) {

        return searchRec(root, k);
    }

    private V searchRec (Tree<K,V> node, K k){

        if(node == null){
            return null;
        }

        if(k.compareTo(node.key) == 0){

            return node.value;

        }else if(k.compareTo(node.key) < 0){

            return searchRec(node.left, k);

        }else{

            return searchRec(node.right, k);
        }

    }

    public void delete(K k) {
        root = deleteRec(root, k);
    }

    private Tree<K,V> deleteRec(Tree<K,V> node, K k){
        if(node == null){
            return null;
        }

        if(k.compareTo(node.key) < 0){
            node.left = deleteRec(node.left, k);
        }else if(k.compareTo(node.key) > 0){
            node.right = deleteRec(node.right, k);
        }else{
            if(node.left == null){
                return node.right;
            }else if (node.right == null){
                return node.left;
            }else{

                K key = findKey(node.right);
                V valor = search(k);

                node.key = key;
                node.value = valor;
                node.right = deleteRec(node.right,key);

            }
        }
        return node;
    }

    private K findKey(Tree<K,V> node){
        while(node.left != null){
            node = node.left;
        }
        return node.key;
    }


    /**
     * Returns representation of tree as a String.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + toStringRec(root) + ")";
    }

    private static <K,V> String toStringRec(Tree<K,V> tree) {
        if (tree == null) {
            return "";
        }
        else {
            String left = toStringRec(tree.left);
            left += String.format("(%s, %s) ", tree.key, tree.value);
            return left + toStringRec(tree.right);
        }
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

    private static void toDotRec(Tree<?,?> current, StringBuffer sb) {
        if (current != null) {
            final int currentId = System.identityHashCode(current);
            sb.append(String.format("%d [label=\"%s\"];\n", currentId, current.key));
            if (!isLeaf(current)) {
                processChild(current.left, sb, currentId);
                processChild(current.right, sb, currentId);
            }
        }
    }

    private static boolean isLeaf(Tree<?,?> current) {
        return current != null && current.right == null && current.left == null;
    }

    private static void processChild(Tree<?,?> child, StringBuffer sb, int parentId) {
        if (child == null) {
            sb.append(String.format("l%d [style=invis];\n", parentId));
            sb.append(String.format("%d -> l%d;\n", parentId, parentId));
        } else {
            sb.append(String.format("%d -> %d;\n", parentId, System.identityHashCode(child)));
            toDotRec(child, sb);
        }
    }
}

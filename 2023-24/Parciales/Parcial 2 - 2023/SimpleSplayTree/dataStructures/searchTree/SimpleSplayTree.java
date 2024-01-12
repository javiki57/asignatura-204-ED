/* -------------------------------------------------------------------------------
   -- Estructura de Datos. Grado en Informática/ Software / Computadores. UMA.
   -- Práctica evaluable 2023-2024
   -- Puntuación máxima: 1.5 puntos
   -------------------------------------------------------------------------------
*/

package dataStructures.searchTree;

import dataStructures.list.*;
import dataStructures.tuple.Tuple2;

import java.util.Iterator;


/**
 * SimpleSplayTree es un arbol binario de busqueda no-balanceado.
 * Las claves (key) definen una relacion de orden ({@link Comparable}).
 *
 * @param <K> Tipo de las claves (key).
 * @param <V> Tipo de los valores (value).
 */
public class SimpleSplayTree<K extends Comparable<? super K>, V> implements Iterable<Tuple2<K, V>> {
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;

        public Node(K k, V v) {
            key = k;
            value = v;
            left = null;
            right = null;
        }
    }

    private Node<K, V> root;
    private int size;

    /**
     * (0,05 puntos)
     * Crea un SimpleSplayTree vacio.
     */
    public SimpleSplayTree() {
        root = null;
        size = 0;
    }

    /**
     * (0,05 puntos)
     * Determina si el arbol esta vacio
     * @return true si el arbol está vacio
     */
    public boolean isEmpty() {

        return size == 0;
    }

    /**
     * (0,05 puntos)
     *
     * @return numero de elementos almacenados en el arbol
     */
    public int size() {

        return size;
    }


    /**
     * (0,25 puntos)
     * Rota un nodo a la izquierda. El nodo derecho pasa a ser la raiz
     * Solo se aplica si el nodo y su hijo derecho no son nulos.
     * En otro caso no modifica el nodo
     *
     * @param node el nodo a rotar
     * @param <K>
     * @param <V>
     * @return el nuevo nodo raiz
     */
    private static <K extends Comparable<? super K>, V> Node<K, V> rotateLeft(Node<K, V> node) {
        if(node != null && node.right != null){

            Node<K,V> nodo = node.right;
            node.right = nodo.left;
            nodo.left = node;
            return nodo;
        }

        return node;
    }

    /**
     * (0,25 puntos)
     * Rota un nodo a la derecha. El nodo izquierdo pasa a ser la raiz
     * Solo se aplica si el nodo y su rama izquierda no son nulas.
     * En otro caso no modifica el nodo
     *
     * @param node el nodo a rotar
     * @param <K>
     * @param <V>
     * @return el nuevo nodo raiz
     */
    private static <K extends Comparable<? super K>, V> Node<K, V> rotateRight(Node<K, V> node) {

        if(node != null && node.left != null){

            Node<K,V> nodo = node.left;
            node.left = nodo.right;
            nodo.right = node;

            return nodo;
        }
        return node;
    }

    /**
     * (0,25 puntos)
     * Inserta el par (k,v) en el arbol si la clave no esta previamente.
     * En otro caso, actualiza el valor asociado a la clave k.
     * Tras la insercion/actualizacion el nodo con clave k y valor v
     * se convierte en la raiz del arbol.
     *
     * @param k la clave a insertar
     * @param v el valor asociado a la clave
     */
    public void insert(K k, V v) {
        root = insertRec(root, k, v);
    }    

    private Node<K,V> insertRec(Node<K, V> node, K k, V v){
        if(node == null){
            size++;
            return new Node<>(k,v);
        }

        if(node.key.compareTo(k)==0){
            node.value = v;

        }else if(node.key.compareTo(k) > 0){
            node.left = insertRec(node.left,k,v);
            node = rotateRight(node);

        }else{
            node.right = insertRec(node.right,k,v);
            node = rotateLeft(node);
        }
        return node;
    }
    /**
     * (0,25 puntos)
     * Busca la clave k en el arbol.
     * Si está, devuelve el valor <V> asociado a k
     * y el arbol resultante tiene en la raiz el nodo con clave k.
     * En otro caso, devuelve null y arbol resultante tiene en la raíz
     * el último nodo que se ha visitado durante la busqueda.
     *
     * @param k la clave a buscar
     * @return el valor asociado a k o null si k no esta en el arbol
     */
    public V search(K k) {
        Tuple2<Node<K,V>,V> tuple = searchRec(root,k);
        root = tuple._1();
    	return tuple._2();
    }

    private Tuple2<Node<K,V>,V> searchRec(Node<K,V> node, K k){
        V v = null;
        if(node == null){
            return new Tuple2<>(node,v);
        }

        if(node.key.compareTo(k) == 0){
            return new Tuple2<>(node,node.value);

        }else if(node.key.compareTo(k) > 0){
            Tuple2<Node<K,V>,V> t = searchRec(node.left,k);
            node.left = t._1();
            node = rotateRight(node);
            v = t._2();
        }else{
            Tuple2<Node<K,V>,V> t = searchRec(node.right, k);
            node.right = t._1();
            node = rotateLeft(node);
            v = t._2();
        }

        return new Tuple2<>(node,v);
    }

    /**
     * (0,1 puntos)
     * Determina si la clave k esta en el arbol.
     * El arbol resultante se modifica con la misma estrategia que
     * en el metodo search.
     *
     * @param k la clave a buscar
     * @return true si k esta en el arbol
     */
    public boolean isElem(K k) {
        return search(k) != null;
    }


    /**
     * (0,25 puntos)
     * Recorre in-orden el arbol y devuelve el resultado en una lista.
     * El arbol no se modifica al recorrerlo.
     *
     * @param node la raiz del arbol a recorrer en in-orden.
     * @return lista de tuplas de pares (k,v)
     */

    public static <K, V> List<Tuple2<K, V>> inOrderRec(Node<K, V> node) {
        List<Tuple2<K, V>> result = new ArrayList<>();
        inOrderTraversal(node, result);
        return result;
    }

    private static <K, V> void inOrderTraversal(Node<K, V> node, List<Tuple2<K, V>> result) {
        if (node != null) {
            // Recorre el subárbol izquierdo
            inOrderTraversal(node.left, result);

            // Agrega el nodo actual a la lista
            result.append(new Tuple2<>(node.key, node.value));

            // Recorre el subárbol derecho
            inOrderTraversal(node.right, result);
        }
    }


    /***** NO MODIFICAR *****/
	public List<Tuple2<K, V>> inOrder() {
        return inOrderRec(root);
    }
    
    public Iterator<Tuple2<K, V>> iterator() {
        return inOrder().iterator();
    }

    @Override
    public String toString() {
        String className = getClass().getSimpleName();
        return className + "(" + toStringRec(this.root) + ")";
    }

    private static String toStringRec(Node<?, ?> tree) {
        return tree == null ? "null" : "Node<" + toStringRec(tree.left) + ","
                + tree.key + "," + tree.value + "," + toStringRec(tree.right) + ">";
    }

}


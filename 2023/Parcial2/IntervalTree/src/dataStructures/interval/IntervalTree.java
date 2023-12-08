package dataStructures.interval;

import dataStructures.list.LinkedList;
import dataStructures.list.List;

import java.util.ArrayList;

public class IntervalTree {

    private static class Node {
        Interval interval;
        int max;
        Node lt, rt;

        /* (1.25 ptos) Constructor de Node
         */
        public Node(Interval interval, Node lt, Node rt) {
            //TODO

        }

        public Node(Interval interval) {
            this(interval, null, null);
        }
    }

    /* Atributos de IntervalTree */
    Node root;  //raíz del árbol
    int size;   //número de intervalos almacenados

    /* (0.75 pto) Constructor del árbol de intervalo vacío
     */
    public IntervalTree() {
        //TODO
    }

    /* (0.5 ptos) Devuelve true si el árbol de intervalo está vacío
     */
    public boolean isEmpty() {
        //TODO
        return true;
    }

    /* (0.5 ptos) Devuelve el número de intervalos almacenados en el árbol de intervalo
     */
    public int size() {
        //TODO

        return 0;
    }


    /* (2 ptos) Inserta el intervalo x en el árbol de intervalos.
        El árbol de intrevalo resultante debe mantener la propiedad de orden.
        Es similar a un BST: los nodos del árbol están están ordenados
        por el valor inferior del intervalo que almacenan.
        Si existe un nodo cuyo intervalo tiene el mismo valor inferior que x,
        actualiza el límite superior del intervalo almacenado en el árbol.
     */
    public void insert(Interval x) {
        //TODO
    }

    private Node insertRec(Node node, int inf, int sup, Interval x) {
        //TODO
        return node;
    }


    /* (0.5 ptos) Devuelve true si se satisface la condición 1:
       Condición C1: el nodo n no es nulo y su atributo max
                     es mayor o igual que el límite inferior del intervalo x
     */
    private boolean condicionC1(Node n, Interval x) {

        //TODO
        return false;
    }

    /* (0.5 ptos) Devuelve true si se satisface la condición 2
       Condición C2: el hijo derecho de n no es nulo y el límite inferior de n
                    es menor o igual que el límite superior del intervalo x
     */
    private boolean condicionC2(Node n, Interval x) {
        //TODO
        return false;
    }


    /* (2 ptos) Devuelve un intervalo del árbol que solapa con x.
      Debe ser una implementación eficiente que aproveche la propiedad de orden.
      Si no solapa con ningún intervalo del árbol devuelve null.
      En las transparencias 13-16 hay ejemplos de como debe explorarse el árbol.
     */
    public Interval searchOverlappingInterval(Interval x) {
        //TODO
        return null;
    }

    /* (2 ptos) Devuelve una lista con todos los intervalos del árbol que solapan con x.
       Debe ser una implementación eficiente que aproveche la propiedad de orden.
       Si no solapa con ninguno devuelve una lista vacía.
       En la transparencia 17 hay un ejemplo.
     */
    public List<Interval> allOverlappingIntervals(Interval x){
        //TODO
        return null;
    }

    /* ---------- No modificar esta parte ---------- */
    @Override
    public String toString() {
        return toStringRec(root);
    }

    private String toStringRec(Node root) {
        if(root!=null){
            return toStringRec(root.lt) + root.interval.toString()+"( "+ root.max +" )"+ toStringRec(root.rt);
        }
        return "";
    }
}

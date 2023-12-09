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
            this.interval = interval;
            this.lt = lt;
            this.rt= rt;
            this.max = maxi(interval, lt, rt);

        }

        private int maxi(Interval interval, Node lt, Node rt ){

            int max = interval.high;

            if (lt != null && lt.max > max) {
                max = lt.max;
            }

            if (rt != null && rt.max > max) {
                max = rt.max;
            }

            return max;
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
        root = null;
        size = 0;
    }

    /* (0.5 ptos) Devuelve true si el árbol de intervalo está vacío
     */
    public boolean isEmpty() {

        return root == null;
    }

    /* (0.5 ptos) Devuelve el número de intervalos almacenados en el árbol de intervalo
     */
    public int size() {

        return size;
    }


    /* (2 ptos) Inserta el intervalo x en el árbol de intervalos.
        El árbol de intervalo resultante debe mantener la propiedad de orden.
        Es similar a un BST: los nodos del árbol están ordenados
        por el valor inferior del intervalo que almacenan.
        Si existe un nodo cuyo intervalo tiene el mismo valor inferior que x,
        actualiza el límite superior del intervalo almacenado en el árbol.
     */
    public void insert(Interval x) {
        root = insertRec(root, x);
    }

    private Node insertRec(Node node, Interval x) {
        if(node == null){
            size++;
            return new Node(x, null, null);
        }

        int comp = node.interval.compareTo(x);

        if(comp < 0){
            node.rt = insertRec(node.rt, x);

        }else if(comp > 0){
            node.lt = insertRec(node.lt, x);

        }else{
            node.interval.high = Math.max(node.interval.high, x.high);
        }

        node.max = Math.max(node.interval.high, Math.max(getMax(node.lt), getMax(node.rt)));

        return node;
    }

    private int getMax(Node node) {
        return (node == null) ? Integer.MIN_VALUE : node.max;
    }

    /* (0.5 ptos) Devuelve true si se satisface la condición 1:
       Condición C1: el nodo n no es nulo y su atributo max
                     es mayor o igual que el límite inferior del intervalo x
     */
    private boolean condicionC1(Node n, Interval x) {

        return n != null && n.max >= x.low;
    }

    /* (0.5 ptos) Devuelve true si se satisface la condición 2
       Condición C2: el hijo derecho de n no es nulo y el límite inferior de n
                    es menor o igual que el límite superior del intervalo x
     */
    private boolean condicionC2(Node n, Interval x) {

        return n != null && n.interval.low <= x.high;
    }


    /* (2 ptos) Devuelve un intervalo del árbol que solapa con x.
      Debe ser una implementación eficiente que aproveche la propiedad de orden.
      Si no solapa con ningún intervalo del árbol devuelve null.
      En las transparencias 13-16 hay ejemplos de como debe explorarse el árbol.
     */
    public Interval searchOverlappingInterval(Interval x) {

        return searchOverlappingIntervalRec(root, x);
    }

    private Interval searchOverlappingIntervalRec(Node node,Interval n){

        if(!condicionC1(node,n)){
            return null;

        }else{

            if(node.interval.overlap(n)){
                return node.interval;

            }else{

                if(condicionC2(node,n)){
                    return searchOverlappingIntervalRec(node.rt,n);

                }else{
                    return searchOverlappingIntervalRec(node.lt,n);
                }
            }
        }
    }

    /* (2 ptos) Devuelve una lista con todos los intervalos del árbol que solapan con x.
       Debe ser una implementación eficiente que aproveche la propiedad de orden.
       Si no solapa con ninguno devuelve una lista vacía.
       En la transparencia 17 hay un ejemplo.
     */
    public List<Interval> allOverlappingIntervals(Interval x){
        List<Interval> result = new LinkedList<>();
        allOverlappingIntervals(root, x, result);
        return result;

    }

    private void allOverlappingIntervals(Node node, Interval x, List<Interval> result) {
        if (node == null) {
            return; // Condición de parada si el nodo es nulo
        }

        // Condición de parada si no cumple con la condición C1
        if (!condicionC1(node, x)) {
            return;
        }

        // Verificar si solapa y agregar a la lista de resultados si solapa
        if (node.interval.overlap(x)) {
            result.append(node.interval);
        }

        // Explorar subárbol izquierdo y derecho recursivamente
        allOverlappingIntervals(node.lt, x, result);
        allOverlappingIntervals(node.rt, x, result);
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

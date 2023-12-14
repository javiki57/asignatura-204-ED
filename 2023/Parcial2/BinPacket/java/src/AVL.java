/**
 * Student's name:
 *
 * Student's group:
 */

import dataStructures.list.List;
import dataStructures.list.LinkedList;
import dataStructures.list.ListException;

import java.util.Iterator;


class Bin {
    private int remainingCapacity; // capacity left for this bin
    private List<Integer> weights; // weights of objects included in this bin

    public Bin(int initialCapacity) {

        if(initialCapacity < 0){
            throw new RuntimeException("Error, la capacidad inicial es nula");
        }

        this.remainingCapacity = initialCapacity;
        weights = new LinkedList<>();
    }

    // returns capacity left for this bin
    public int remainingCapacity() {
        int c = 0;

        for(Integer i : weights){
            c+= i;
        }

        return remainingCapacity-c;
    }

    // adds a new object to this bin
    public void addObject(int weight) {

        if(this.remainingCapacity < weight ){
            throw new ListException("Error, no cabe en el cubo");

        }

        this.remainingCapacity -= weight;
        weights.append(weight);

    }

    // returns an iterable through weights of objects included in this bin
    public Iterable<Integer> objects() {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;
    }

    public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        sb.append(remainingCapacity);
        sb.append(", ");
        sb.append(weights.toString());
        sb.append(")");
        return sb.toString();
    }
}

// Class for representing an AVL tree of bins
public class AVL {
    static private class Node {
        Bin bin; // Bin stored in this node
        int height; // height of this node in AVL tree
        int maxRemainingCapacity; // max capacity left among all bins in tree rooted at this node
        Node left, right; // left and right children of this node in AVL tree

        // recomputes height of this node
        void setHeight() {
            height = 1 + Math.max(left != null ? left.height : 0, right != null ? right.height : 0);
        }

        // recomputes max capacity among bins in tree rooted at this node
        void setMaxRemainingCapacity() {
            maxRemainingCapacity = Math.max(bin.remainingCapacity(), Math.max(left != null ? left.maxRemainingCapacity : 0, right != null ? right.maxRemainingCapacity : 0));        }

        // left-rotates this node. Returns root of resulting rotated tree
        Node rotateLeft() {

            Node nuevo = this.right;
            this.right = nuevo.left;
            nuevo.left = this;

            this.setHeight();
            this.setMaxRemainingCapacity();
            nuevo.setHeight();
            nuevo.setMaxRemainingCapacity();

            return nuevo;
        }
    }

    private static int height(Node node) {

        return node != null ? node.height : 0;
    }

    private static int maxRemainingCapacity(Node node) {

        return node.maxRemainingCapacity;
    }

    private Node root; // root of AVL tree

    public AVL() {
        this.root = null;
    }

    // adds a new bin at the end of right spine.
    private void addNewBin(Bin bin) {
        root = addnewbinrec(root,bin);

    }

    private Node addnewbinrec(Node node, Bin bin){
        if(node == null){
            Node n = new Node();
            n.bin = bin;
            n.setHeight();
            n.setMaxRemainingCapacity();
            return n;
        }

        if(bin.remainingCapacity() > maxRemainingCapacity(node)){
            node.right = addnewbinrec(node.right,bin);

            if(height(node.right) - height(node.left) > 1){

                if(bin.remainingCapacity() > maxRemainingCapacity(node.right)){
                    node = node.rotateLeft();
                }
            }

        }else{
            node.left = addnewbinrec(node.left,bin);

        }
        node.setHeight();
        node.setMaxRemainingCapacity();
        return node;

    }

    // adds an object to first suitable bin. Adds
    // a new bin if object cannot be inserted in any existing bin
    public void addFirst(int initialCapacity, int weight) {
        // todo
    }

    public void addAll(int initialCapacity, int[] weights) {
        // todo
    }

    public List<Bin> toList() {
        // todo
        return null;
    }

    public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        stringBuild(sb, root);
        sb.append(")");
        return sb.toString();
    }

    private static void stringBuild(StringBuilder sb, Node node) {
        if(node==null)
            sb.append("null");
        else {
            sb.append(node.getClass().getSimpleName());
            sb.append("(");
            sb.append(node.bin);
            sb.append(", ");
            sb.append(node.height);
            sb.append(", ");
            sb.append(node.maxRemainingCapacity);
            sb.append(", ");
            stringBuild(sb, node.left);
            sb.append(", ");
            stringBuild(sb, node.right);
            sb.append(")");
        }
    }
}

class LinearBinPacking {
    public static List<Bin> linearBinPacking(int initialCapacity, List<Integer> weights) {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;
    }
	
	public static Iterable<Integer> allWeights(Iterable<Bin> bins) {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;		
	}
}
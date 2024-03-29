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
        this.remainingCapacity = initialCapacity;
        weights = new LinkedList<>();
    }

    // returns capacity left for this bin
    public int remainingCapacity() {

        return this.remainingCapacity;
    }

    // adds a new object to this bin
    public void addObject(int weight) {
        if(weight > remainingCapacity()) throw new ListException("Error, el objeto no cabe en el cubo.");
        weights.append(weight);
        this.remainingCapacity -= weight;
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
            this.height = 1 + Math.max(left.height, right.height);
        }

        // recomputes max capacity among bins in tree rooted at this node
        void setMaxRemainingCapacity() {
            int max = Math.max(left.maxRemainingCapacity,right.maxRemainingCapacity);

            this.maxRemainingCapacity = Math.max(bin.remainingCapacity(),max);
        }

        // left-rotates this node. Returns root of resulting rotated tree
        Node rotateLeft() {

            Node rotated = right;
            this.right = rotated.left;
            rotated.left = this;

            this.setMaxRemainingCapacity();
            this.setHeight();

            rotated.setMaxRemainingCapacity();
            rotated.setHeight();

            return rotated;
        }
    }

    private static int height(Node node) {

        return node.height;
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
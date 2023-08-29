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
        weights = new LinkedList<>();
        remainingCapacity = initialCapacity;
    }

    // returns capacity left for this bin
    public int remainingCapacity() {
        int sum = 0;

        for(Integer i : weights){
            sum+=i;
        }

        return remainingCapacity - sum;
    }

    // adds a new object to this bin
    public void addObject(int weight) {
        if(weight > remainingCapacity()){
            throw new ListException("Error, el objeto no entra en el cubo.");
        }

        weights.append(weight);
        remainingCapacity-=weight;

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

            maxRemainingCapacity = Math.max(bin.remainingCapacity(), Math.max(left != null ? left.maxRemainingCapacity : 0, right != null ? right.maxRemainingCapacity : 0));

        }

        // left-rotates this node. Returns root of resulting rotated tree
        Node rotateLeft() {

            Node nuevo = this.right;
            this.right = nuevo.left;
            nuevo.left = this;

            this.setHeight();
            this.setMaxRemainingCapacity();
            nuevo.setMaxRemainingCapacity();
            nuevo.setHeight();

            return nuevo;
        }
    }

    private static int height(Node node) {

        return node != null ? node.height : 0;
    }

    private static int maxRemainingCapacity(Node node) {
        int max = node.bin.remainingCapacity();

        int leftMax = node.left != null ? node.left.maxRemainingCapacity : 0;
        int rightMax = node.right != null ? node.right.maxRemainingCapacity : 0;

        return Math.max(max, Math.max(leftMax, rightMax));
    }

    private Node root; // root of AVL tree

    public AVL() {
        this.root = null;
    }

    // adds a new bin at the end of right spine.
    private void addNewBin(Bin bin) {
       root = addnewbin(root,bin);
    }

    private Node addnewbin(Node root, Bin bin){
        if(root == null){
            Node n = new Node();
            n.bin = bin;
            n.setHeight();
            n.setMaxRemainingCapacity();
            return n;
        }

        if(bin.remainingCapacity() > maxRemainingCapacity(root)){
            root.right = addnewbin(root.right, bin);

            if(height(root.right) - height(root.left) > 1){
                if(bin.remainingCapacity() > maxRemainingCapacity(root.right)){
                    root = root.rotateLeft();
                }
            }

        }else{
            root.left = addnewbin(root.left, bin);
        }

        root.setHeight();
        root.setMaxRemainingCapacity();

        return root;

    }

    // adds an object to first suitable bin. Adds
    // a new bin if object cannot be inserted in any existing bin
    public void addFirst(int initialCapacity, int weight) {
        Bin nbin = new Bin(initialCapacity);
        nbin.addObject(weight);

        if(root == null || weight > maxRemainingCapacity(root)){
            addNewBin(nbin);
        }else if(root.left != null && root.left.maxRemainingCapacity >= weight){
            root.left.bin.addObject(weight);
            root.left.setHeight();
            root.left.setMaxRemainingCapacity();
        } else if (root.bin.remainingCapacity() >= weight) {
            root.bin.addObject(weight);
            root.setHeight();
            root.setMaxRemainingCapacity();
        } else {
            root.right.bin.addObject(weight);
            root.right.setHeight();
            root.right.setMaxRemainingCapacity();
        }
    }

    public void addAll(int initialCapacity, int[] weights) {
        for(int weight : weights){
            addFirst(initialCapacity,weight);
        }
    }

    public List<Bin> toList() {
        List<Bin> binList = new LinkedList<>();
        toListRecursive(root, binList);
        return binList;
    }

    private void toListRecursive(Node node, List<Bin> binList) {
        if (node == null) {
            return;
        }

        toListRecursive(node.left, binList);
        binList.append(node.bin);
        toListRecursive(node.right, binList);
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
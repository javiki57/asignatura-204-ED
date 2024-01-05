package demos.searchTree;

import dataStructures.searchTree.SimpleSplayTree;
import dataStructures.tuple.Tuple2;

public class SimpleSplayTreeDemo {

    public static void main(String args[]) {
        SimpleSplayTree<Integer,Integer> t = new SimpleSplayTree<>();
        System.out.println("Size of empty tree: "+t.size());
        int xs[] = new int[] { 8,5,7,1,9,6 };

        for(Integer x : xs)
            t.insert(x, 10*x);
        System.out.println("Size of tree after insertions: "+t.size());
        System.out.println("Tree: "+t);
        /**
         * SimpleSplayTree(Node<Node<null,1,10,Node<null,5,50,null>>,6,60,Node<Node<null,7,70,Node<null,8,80,null>>,9,90,null>>)
        */
        Integer value = t.search(7);
        System.out.println("Key 7 value? "+value);
        System.out.println("Tree after search 7: "+t);
        /**
         * SimpleSplayTree(Node<Node<Node<null,1,10,Node<null,5,50,null>>,6,60,null>,7,70,Node<Node<null,8,80,null>,9,90,null>>)
         */
        System.out.println("is key 2 an element? "+t.isElem(2));
        System.out.println("Tree after isElement(2): "+t);
        /**
         * SimpleSplayTree(Node<Node<null,1,10,null>,5,50,Node<Node<null,6,60,null>,7,70,Node<Node<null,8,80,null>,9,90,null>>>)
         */
        System.out.println(t.inOrder());
        /**
         * ArrayList(Tuple2(1,10),Tuple2(5,50),Tuple2(6,60),Tuple2(7,70),Tuple2(8,80),Tuple2(9,90))
         */
        for (Tuple2<Integer,Integer> p : t)
            System.out.println(p);
        /**
         * Tuple2(1,10)
         * Tuple2(5,50)
         * Tuple2(6,60)
         * Tuple2(7,70)
         * Tuple2(8,80)
         * Tuple2(9,90)
         */
    }
}

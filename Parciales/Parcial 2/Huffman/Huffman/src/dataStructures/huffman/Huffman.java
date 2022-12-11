package dataStructures.huffman;
/**
 * Huffman trees and codes.
 *
 * Data Structures, Grado en Informatica. UMA.
 *
 *
 * Student's name:
 * Student's group:
 */

import dataStructures.dictionary.AVLDictionary;
import dataStructures.dictionary.Dictionary;
import dataStructures.list.LinkedList;
import dataStructures.list.List;
import dataStructures.priorityQueue.BinaryHeapPriorityQueue;
import dataStructures.priorityQueue.PriorityQueue;
import dataStructures.tuple.Tuple2;

public class Huffman {

    // Exercise 1  
    public static Dictionary<Character, Integer> weights(String s) {
    	Dictionary<Character, Integer> dic = new AVLDictionary<>();
        for(Character c : s.toCharArray()){
            if(dic.isDefinedAt(c)){
                dic.insert(c,dic.valueOf(c) +1);
            }else{
                dic.insert(c,1);
            }
        }

        if(dic.size() == 1){
            throw new HuffmanException("Se necesita que el mensaje tenga 2 caracteres distintos");
        }

        return dic;
    }

    // Exercise 2.a 
    public static PriorityQueue<WLeafTree<Character>> huffmanLeaves(String s) {
    	PriorityQueue<WLeafTree<Character>> pq = new BinaryHeapPriorityQueue<>();
        for( Tuple2<Character,Integer> t : weights(s).keysValues()){
            WLeafTree<Character> tree = new WLeafTree<>(t._1(),t._2());
            pq.enqueue(tree);
        }

        return pq;
    }

    // Exercise 2.b  
    public static WLeafTree<Character> huffmanTree(String s) {
        PriorityQueue<WLeafTree<Character>> pq = huffmanLeaves(s);
        WLeafTree<Character> wlt,tree1,tree2,tree3;

        tree1 = pq.first();
        pq.dequeue();
        tree2 = pq.first();
        pq.dequeue();

        wlt = new WLeafTree<>(tree1,tree2);

        while(!pq.isEmpty()){
            wlt = new WLeafTree<>(wlt,pq.first());
            pq.dequeue();
        }

    	return wlt;
    }


    // Exercise 3.a 
    public static Dictionary<Character, List<Integer>> joinDics(Dictionary<Character, List<Integer>> d1, Dictionary<Character, List<Integer>> d2) {
        Dictionary<Character,List<Integer>> dic = d1;
        for (Tuple2<Character,List<Integer>> t1 : d2.keysValues()) {
            dic.insert(t1._1(),t1._2());

        }
    	return dic;
    }

    // Exercise 3.b  
    public static Dictionary<Character, List<Integer>> prefixWith(int i, Dictionary<Character, List<Integer>> d) {
        Dictionary<Character, List<Integer>> dic = new AVLDictionary<>();

        for(Tuple2<Character,List<Integer>> t1 : d.keysValues()){
            List<Integer> lista = new LinkedList<>();
            lista.append(i);
            for(Integer a : t1._2()){
                lista.append(a);
            }
            dic.insert(t1._1(),lista);
        }

    	return dic;
    }

    // Exercise 3.c  
    public static Dictionary<Character, List<Integer>> huffmanCode(WLeafTree<Character> ht) {

        if(ht.isLeaf()){
            Dictionary<Character, List<Integer>> dic = new AVLDictionary<>();
            dic.insert(ht.elem(),new LinkedList<>());
            return dic;
        }else{
            return joinDics(
                    prefixWith(0,huffmanCode(ht.leftChild())),
                    prefixWith(1,huffmanCode(ht.rightChild()))
            );

        }

    }

    // Exercise 4  
    public static List<Integer> encode(String s, Dictionary<Character, List<Integer>> hc) {
        List<Integer> lista = new LinkedList<>();
        List<Integer> aux;

        for(char c : s.toCharArray()){
            aux = hc.valueOf(c);

            for(int i : aux){
                lista.append(i);
            }
        }

    	return lista;
    }

    // Exercise 5 
    public static String decode(List<Integer> bits, WLeafTree<Character> ht) {
        StringBuilder result = new StringBuilder();
        WLeafTree<Character> aux = ht;

        for(int i : bits){

            if(i == 0){
                aux = aux.leftChild();

            }else{

                aux = aux.rightChild();
            }

            if(aux.isLeaf()){
                result.append(aux.elem());

                aux = ht;
            }
        }
    	return result.toString();
    }
}

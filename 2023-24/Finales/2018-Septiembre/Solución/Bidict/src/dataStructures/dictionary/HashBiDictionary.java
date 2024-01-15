package dataStructures.dictionary;
import dataStructures.list.List;

import dataStructures.list.ArrayList;
import dataStructures.set.AVLSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

import java.util.Iterator;

/**
 * Estructuras de Datos. Grados en Informatica. UMA.
 * Examen de septiembre de 2018.
 *
 * Apellidos, Nombre:
 * Titulacion, Grupo:
 */
public class HashBiDictionary<K,V extends Comparable<? super V>> implements BiDictionary<K,V>{
	private Dictionary<K,V> bKeys;
	private Dictionary<V,K> bValues;
	
	public HashBiDictionary() {
		bKeys = new HashDictionary<>();
		bValues = new HashDictionary<>();
	}
	
	public boolean isEmpty() {

		return bKeys.isEmpty() && bValues.isEmpty();
	}
	
	public int size() {

		return bKeys.size();
	}
	
	public void insert(K k, V v) {

		if(bKeys.isDefinedAt(k)){
			bKeys.delete(k);
			bValues.delete(bKeys.valueOf(k));
			insert(k,v);

		}else if(bValues.isDefinedAt(v)){
			bKeys.delete(bValues.valueOf(v));
			bValues.delete(v);
			insert(k,v);
		}else{
			bKeys.insert(k,v);
			bValues.insert(v,k);
		}
	}
	
	public V valueOf(K k) {

		return bKeys.valueOf(k);
	}
	
	public K keyOf(V v) {

		return bValues.valueOf(v);
	}
	
	public boolean isDefinedKeyAt(K k) {
		return bKeys.isDefinedAt(k);
	}
	
	public boolean isDefinedValueAt(V v) {
		return bValues.isDefinedAt(v);
	}
	
	public void deleteByKey(K k) {

		if(isDefinedKeyAt(k)){
			bKeys.delete(k);
			bValues.delete(valueOf(k));

		}else{
			throw new RuntimeException("Error");
		}
	}
	
	public void deleteByValue(V v) {

		if(isDefinedValueAt(v)){
			bKeys.delete(keyOf(v));
			bValues.delete(v);

		}else{
			throw new RuntimeException("Error");
		}
	}
	
	public Iterable<K> keys() {
		return bKeys.keys();
	}
	
	public Iterable<V> values() {
		return bValues.keys();
	}
	
	public Iterable<Tuple2<K, V>> keysValues() {
		return bKeys.keysValues();
	}
	
		
	public static <K,V extends Comparable<? super V>> BiDictionary<K, V> toBiDictionary(Dictionary<K,V> dict) {

		if(!inyectivo(dict)){
			throw new RuntimeException("Error, el diccionario no es inyectivo.");
		}

		BiDictionary<K,V> b = new HashBiDictionary();
		for(Tuple2<K,V> t : dict.keysValues()){
			b.insert(t._1(),t._2());
		}

		return b;
	}

	private static <K,V extends Comparable<? super V>> boolean inyectivo(Dictionary<K, V> dict){

		boolean es = true;

		Iterator<V> it = dict.values().iterator();
		Set<V> s = new AVLSet<>();
		V v;

		while(it.hasNext() && es){
			v = it.next();
			if(s.isElem(v)){
				es = false;
			}
			s.insert(v);
		}

		return es;
	}
	
	public <W extends Comparable<? super W>> BiDictionary<K, W> compose(BiDictionary<V,W> bdic) {
		BiDictionary<K, W> solucion = new HashBiDictionary<>();

		for(Tuple2<K, V> tupla : bKeys.keysValues()) {

			if(bdic.isDefinedKeyAt(tupla._2())) {

				solucion.insert(tupla._1(), bdic.valueOf(tupla._2()));
			}
		}
		return solucion;
	}
		
	public static <K extends Comparable<? super K>> boolean isPermutation(BiDictionary<K,K> bd) {

		Set<K> conj1 = new AVLSet<>();
		Set<K> conj2 = new AVLSet<>();
		boolean permutacion = true;

		for(Tuple2<K, K> tupla : bd.keysValues()) {
			conj1.insert(tupla._1());
			conj2.insert(tupla._2());
		}

		Iterator<K> it = conj1.iterator();
		while(it.hasNext() && permutacion) {
			if(!conj2.isElem(it.next())) {
				permutacion = false;
			}
		}

		return permutacion;

	}
	
	// Solo alumnos con evaluación por examen final.
    // =====================================
	
	public static <K extends Comparable<? super K>> List<K> orbitOf(K k, BiDictionary<K,K> bd) {
		// TODO
		return null;
	}
	
	public static <K extends Comparable<? super K>> List<List<K>> cyclesOf(BiDictionary<K,K> bd) {
		// TODO
		return null;
	}

    // =====================================
	
	
	@Override
	public String toString() {
		return "HashBiDictionary [bKeys=" + bKeys + ", bValues=" + bValues + "]";
	}
	
	
}

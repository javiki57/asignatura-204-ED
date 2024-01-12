package dataStructures.dictionary;
import dataStructures.list.List;

import dataStructures.list.ArrayList;
import dataStructures.set.AVLSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

/**
 * Estructuras de Datos. Grados en Informatica. UMA.
 * Examen de septiembre de 2018.
 *
 * Apellidos, Nombre:
 * Titulacion, Grupo:
 */
public class HashBiDictionary<K,V> implements BiDictionary<K,V>{
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

		if(bKeys.isDefinedAt(k)){
			return bKeys.valueOf(k);
		}

		return null;
	}
	
	public K keyOf(V v) {

		if(bValues.isDefinedAt(v)){
			return bValues.valueOf(v);
		}

		return null;
	}
	
	public boolean isDefinedKeyAt(K k) {
		return bKeys.isDefinedAt(k);
	}
	
	public boolean isDefinedValueAt(V v) {
		return bValues.isDefinedAt(v);
	}
	
	public void deleteByKey(K k) {

		if(isDefinedKeyAt(k)){
			bValues.delete(bKeys.valueOf(k));
			bKeys.delete(k);
		}
	}
	
	public void deleteByValue(V v) {

		if(isDefinedValueAt(v)){
			bKeys.delete(bValues.valueOf(v));
			bValues.delete(v);
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

		if(!esInyectivo(dict)){
			throw new IllegalArgumentException("El diccionario no es inyectivo.");
		}

		//TODO

		return null;
	}

	private static <K,V extends Comparable<? super V>> boolean esInyectivo(Dictionary<K, V> dict){
		//TODO

		return true;
	}
	
	public <W> BiDictionary<K, W> compose(BiDictionary<V,W> bdic) {
		// TODO
		return null;
	}
		
	public static <K extends Comparable<? super K>> boolean isPermutation(BiDictionary<K,K> bd) {
		// TODO
		return false;
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

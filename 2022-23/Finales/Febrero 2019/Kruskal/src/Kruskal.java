/**----------------------------------------------
 * -- Estructuras de Datos.  2018/19
 * -- 2º Curso del Grado en Ingeniería [Informática | del Software | de Computadores].
 * -- Escuela Técnica Superior de Ingeniería en Informática. UMA
 * --
 * -- Examen 4 de febrero de 2019
 * --
 * -- ALUMNO/NAME:
 * -- GRADO/STUDIES:
 * -- NÚM. MÁQUINA/MACHINE NUMBER:
 * --
 * ----------------------------------------------
 */

import dataStructures.graph.WeightedGraph;
import dataStructures.graph.WeightedGraph.WeightedEdge;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.priorityQueue.PriorityQueue;
import dataStructures.priorityQueue.LinkedPriorityQueue;
import dataStructures.set.Set;
import dataStructures.set.HashSet;

public class Kruskal {
	public static <V,W> Set<WeightedEdge<V,W>> kruskal(WeightedGraph<V,W> g) {

		Set<WeightedEdge<V,W>> set = new HashSet<>();
		PriorityQueue<WeightedEdge<V,W>> pq = new LinkedPriorityQueue<>();
		Dictionary<V,V> dic = new HashDictionary<>();

		for(WeightedEdge<V,W> edge : g.edges()){
			pq.enqueue(edge);
		}

		//Asociamos un vértice a uno mismo
		for(V v : g.vertices()){
			dic.insert(v, v);
		}


		while(!pq.isEmpty()){
			WeightedEdge<V,W> edge = pq.first();
			pq.dequeue();
			V a = representante(edge.source(),dic);
			V b = representante(edge.destination(),dic);

			if(!a.equals(b)){
				dic.insert(representante(edge.destination(),dic), edge.source());
				set.insert(edge);
			}

		}

		return set;
	}

	private static <V> V representante(V src, Dictionary<V,V> dic ){

		if(src.equals(dic.valueOf(src))){

			return src;
		}else{
			return representante(dic.valueOf(src),dic);
		}

	}

	// Sólo para evaluación continua / only for part time students
	public static <V,W> Set<Set<WeightedEdge<V,W>>> kruskals(WeightedGraph<V,W> g) {

		// COMPLETAR
		
		return null;
	}
}

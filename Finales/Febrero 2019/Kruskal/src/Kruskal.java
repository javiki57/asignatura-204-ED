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
			if(!representante(edge.source(), edge.destination(), dic)){
				dic.insert(edge.destination(), edge.source());

				set.insert(edge);
			}

		}

		return set;
	}

	private static <V> boolean representante(V src,V dst, Dictionary<V,V> dic ){

		V aux1 = src;
		V aux2 = dst;
		int i =0;
		boolean si =false;

		while(i< dic.size()) {
			if (!aux1.equals(dic.valueOf(aux1))) {
				aux1 = dic.valueOf(aux1);
			}

			if (!aux2.equals(dic.valueOf(aux2))) {
				aux2 = dic.valueOf(aux2);
			}

			if (dic.valueOf(aux1).equals(dic.valueOf(aux2))){
				return si= true;
			}
			i++;
		}
		return si;
	}

	// Sólo para evaluación continua / only for part time students
	public static <V,W> Set<Set<WeightedEdge<V,W>>> kruskals(WeightedGraph<V,W> g) {

		// COMPLETAR
		
		return null;
	}
}

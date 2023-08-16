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
import dataStructures.priorityQueue.LinkedPriorityQueue;
import dataStructures.set.Set;
import dataStructures.set.HashSet;

import java.util.PriorityQueue;
import java.util.Queue;

public class Kruskal {
	public static <V,W> Set<WeightedEdge<V,W>> kruskal(WeightedGraph<V,W> g) {

		Dictionary<V,V> conexiones = new HashDictionary<>();

		for(V v : g.vertices()){
			conexiones.insert(v,v);
		}

		Queue<WeightedEdge<V,W>> cola = new PriorityQueue<>();

		for(WeightedEdge<V,W> aristas : g.edges()){
			cola.add(aristas);
		}

		Set<WeightedEdge<V,W>> T = new HashSet<>();

		while(!cola.isEmpty()){
			WeightedEdge<V,W> arista = cola.remove();
			if(representante(conexiones, arista.source()) != representante(conexiones, arista.destination())){
				T.insert(arista);
				conexiones.insert(representante(conexiones,arista.destination()), representante(conexiones,arista.source()));
			}
		}
		
		return T;
	}

	private static <V> V representante(Dictionary<V,V> dic, V v) {
		V rep = dic.valueOf(v);

		if(!rep.equals(v)){
			rep = representante(dic,rep);
			dic.insert(v,rep);
		}

		return rep;
	}


	// Sólo para evaluación continua / only for part time students
	public static <V,W> Set<Set<WeightedEdge<V,W>>> kruskals(WeightedGraph<V,W> g) {

		// COMPLETAR
		
		return null;
	}
}

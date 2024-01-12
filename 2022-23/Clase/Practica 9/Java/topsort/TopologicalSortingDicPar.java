/**
 * Estructuras de Datos
 * Práctica 9 - Orden topológico en digrafos (sin clonar el grafo)
 */

package topsort;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.graph.DiGraph;
import dataStructures.list.LinkedList;
import dataStructures.list.List;
import dataStructures.set.Set;

public class TopologicalSortingDicPar<V> {

    private final List<Set<V>> topSort = null;
    private boolean hasCycle;

    public TopologicalSortingDicPar(DiGraph<V> graph) {
        // TODO
    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public List<Set<V>> order() {
        return hasCycle ? null : topSort;
    }
}

/**
 * Student's name:
 * Student's group:
 *
 * Data Structures. Grado en Informática. UMA.
 */

package dataStructures.graph;

import dataStructures.list.*;
import dataStructures.set.Set;

import java.util.Iterator;

public class EulerianCycle<V> {
    private List<V> eCycle;

    @SuppressWarnings("unchecked")
    public EulerianCycle(Graph<V> g) {
        Graph<V> graph = (Graph<V>) g.clone();
        eCycle = eulerianCycle(graph);
    }

    public boolean isEulerian() {
        return eCycle != null;
    }

    public List<V> eulerianCycle() {
        return eCycle;
    }

    // J.1
    private static <V> boolean isEulerian(Graph<V> g) {

        Set<V> vertices = g.vertices();
        boolean es = true;

        for(V v : vertices){

            es = g.degree(v) % 2 == 0;
        }

        return es;
    }

    // J.2
    private static <V> void remove(Graph<V> g, V v, V u) {

        g.deleteEdge(v,u);

        for(V vert : g.vertices()){
            if(g.degree(vert) == 0) g.deleteVertex(vert);
        }

    }

    // J.3
    private static <V> List<V> extractCycle(Graph<V> g, V v0) {

        List<V> ciclo = new ArrayList<V>();
        ciclo.append(v0);

        Set<V> suc = g.successors(v0);
        Iterator<V> it = suc.iterator();
        V u = it.next();
        ciclo.append(u);
        remove(g, v0, u);

        while (u != ciclo.get(0) && !g.isEmpty() && !g.successors(u).isEmpty()) {
            suc = g.successors(u);
            it = suc.iterator();
            v0 = u;
            u = it.next();
            ciclo.append(u);
            remove(g, v0, u);
        }

        return ciclo;
    }

    // J.4
    private static <V> void connectCycles(List<V> xs, List<V> ys) {
    		// TO DO
    }

    // J.5
    private static <V> V vertexInCommon(Graph<V> g, List<V> cycle) {
    		// TO DO
    		return null;
    }

    // J.6
    private static <V> List<V> eulerianCycle(Graph<V> g) {
    		// TO DO
        return null;
    }
}






package dataStructures.graph;

import dataStructures.list.LinkedList;
import dataStructures.list.List;
import dataStructures.set.HashSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

public class FordFulkerson<V> {
    private WeightedDiGraph<V,Integer> g; // Initial graph
    private List<WDiEdge<V,Integer>> sol; // List of edges representing maximal flow graph
    private V src; 			  // Source
    private V dst; 		  	  // Sink

    /**
     * Constructors and methods
     */

    public static <V> int maxFlowPath(List<WDiEdge<V,Integer>> path) {

        int max = Integer.MAX_VALUE;

        for(WDiEdge<V,Integer> edge : path){
            if(edge.getWeight() < max) max = edge.getWeight();
        }

        return max;
    }

    public static <V> List<WDiEdge<V,Integer>> updateEdge(V x, V y, Integer p, List<WDiEdge<V,Integer>> edges) {
        List<WDiEdge<V,Integer>> l = new LinkedList<>();

        for(WDiEdge<V,Integer> edge : edges){

            if(edge.getSrc().equals(x) && edge.getDst().equals(y)) {
                WDiEdge<V, Integer> e = new WDiEdge<>(x, edge.getWeight() + p, y);

                if (e.getWeight() != 0) l.append(e);


            }else{
                l.append(edge);
            }
        }
        if(!contains(l,x,y)) l.append(new WDiEdge<>(x,p,y));

        edges = l;

        return edges;
    }

    private static <V> boolean contains(List<WDiEdge<V,Integer>> l, V x, V y){

        for(WDiEdge<V,Integer> edge : l){
            if(edge.getSrc().equals(x) && edge.getDst().equals(y)) return true;
        }

        return false;
    }

    public static <V> List<WDiEdge<V,Integer>> updateEdges(List<WDiEdge<V,Integer>> path, Integer p, List<WDiEdge<V,Integer>> edges) {

        for(WDiEdge<V,Integer> ep : path){

            updateEdge(ep.getSrc(),ep.getDst(),p,edges);
        }
        return edges;
    }

    public static <V> List<WDiEdge<V,Integer>> addFlow(V x, V y, Integer p, List<WDiEdge<V,Integer>> sol) {

        List<WDiEdge<V,Integer>> l = new LinkedList<>();

        for( WDiEdge<V,Integer> edge : sol){

            if(edge.getSrc().equals(x) && edge.getDst().equals(y)){
                l.append( new WDiEdge<>(x,edge.getWeight() + p, y));

            }

            if(edge.getSrc().equals(y) && edge.getDst().equals(x)){

                if(edge.getWeight() < p){
                    l.append(new WDiEdge<>(x,p - edge.getWeight(),y));
                }

                if(edge.getWeight() > p){
                    l.append(new WDiEdge<>(y,p - edge.getWeight(), x));

                }
            }

            if(!contains(l,x,y)){
                l.append(new WDiEdge<>(x,p,y));
            }
        }

        sol = l;

        return sol;
    }

    public static <V> List<WDiEdge<V,Integer>> addFlows(List<WDiEdge<V,Integer>> path, Integer p, List<WDiEdge<V,Integer>> sol) {

        for(WDiEdge<V,Integer> edge : path){

            addFlow(edge.getSrc(),edge.getDst(),p,sol);
        }

        return sol;
    }

    public FordFulkerson(WeightedDiGraph<V,Integer> g, V src, V dst) {

        WeightedDiGraph<V,Integer> wdg = (WeightedDiGraph<V, Integer>) g.clone();
        List<WDiEdge<V, Integer>> path = new WeightedBreadthFirstTraversal<>(wdg,src).pathTo(dst);
        List<WDiEdge<V,Integer>> sol = new LinkedList<>();

        while(!path.isEmpty()){
            int maxflow = maxFlowPath(path);

            List<WDiEdge<V,Integer>> edges = wdg.wDiEdges();
            updateEdges(path,-maxflow,edges);
            List<WDiEdge<V,Integer>> inverse = new LinkedList<>();

            for(WDiEdge<V, Integer> edge : edges){
                inverse.append(new WDiEdge<>(edge.getDst(),edge.getWeight(),edge.getSrc()));
            }

            updateEdges(path,maxflow,edges);
            wdg = new WeightedDictionaryDiGraph<>(wdg.vertices(),edges);
            addFlows(path,maxflow,sol);
            path = new WeightedBreadthFirstTraversal<>(wdg,src).pathTo(dst);

        }

    }

    public int maxFlow() {
        // TO DO
        return 0;
    }

    public int maxFlowMinCut(Set<V> set) {
        // TO DO
        return 0;
    }

    /**
     * Provided auxiliary methods
     */
    public List<WDiEdge<V, Integer>> getSolution() {
        return sol;
    }

    /**********************************************************************************
     * A partir de aquí SOLO para estudiantes a tiempo parcial sin evaluación continua.
     * ONLY for part time students.
     * ********************************************************************************/

    public static <V> boolean localEquilibrium(WeightedDiGraph<V,Integer> g, V src, V dst) {
        // TO DO
        return false;
    }
    public static <V,W> Tuple2<List<V>,List<V>> sourcesAndSinks(WeightedDiGraph<V,W> g) {
        // TO DO
        return null;
    }

    public static <V> void unifySourceAndSink(WeightedDiGraph<V,Integer> g, V newSrc, V newDst) {
        // TO DO
    }
}

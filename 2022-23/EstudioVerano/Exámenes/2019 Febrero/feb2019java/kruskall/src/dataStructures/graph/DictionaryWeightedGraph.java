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

package dataStructures.graph;

import java.util.Iterator;
import java.util.Objects;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;

import dataStructures.set.Set;
import dataStructures.set.HashSet;
import dataStructures.tuple.Tuple2;

public class DictionaryWeightedGraph<V, W extends Comparable<? super W>> implements WeightedGraph<V, W> {

    static class WE<V1, W1 extends Comparable<? super W1>> implements WeightedEdge<V1, W1> {

		V1 src, dst;
        W1 wght;

        WE(V1 s, V1 d, W1 w) {
            src = s;
            dst = d;
            wght = w;
        }

        public V1 source() {
            return src;
        }

        public V1 destination() {
            return dst;
        }

        public W1 weight() {
            return wght;
        }

        public String toString() {
            return "WE(" + src + "," + dst + "," + wght + ")";
        }

		public int hashCode() {

			return Objects.hash(src, dst, wght);
		}

		public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            WE<?, ?> we = (WE<?, ?>) obj;
            return Objects.equals(src, we.src) && Objects.equals(dst, we.dst) && Objects.equals(wght, we.wght);
		}
		
		public int compareTo(WeightedEdge<V1, W1> o) {

			return wght.compareTo(o.weight());
		}
    }

    /**
     * Each vertex is associated to a dictionary containing associations
     * from each successor to its weight
     */
    protected Dictionary<V, Dictionary<V, W>> graph;

    public DictionaryWeightedGraph() {
        graph = new HashDictionary<>();
    }


    public void addVertex(V v) {

        if(!graph.isDefinedAt(v)){
            graph.insert(v, new HashDictionary<>());
        }
    }


    public void addEdge(V src, V dst, W w) {

        if(!graph.isDefinedAt(src) || !graph.isDefinedAt(dst)){
            throw new GraphException("Error, los vértices pertenecen al grafo.");
        }

        Dictionary<V, W> dsrc = graph.valueOf(src);
        dsrc.insert(dst,w);
        graph.insert(src, dsrc);

        Dictionary<V,W> ddst = graph.valueOf(dst);
        ddst.insert(src,w);
        graph.insert(dst,ddst);

    }

    public Set<Tuple2<V, W>> successors(V v) {

        if(!graph.isDefinedAt(v)){
            throw new GraphException("El vértice no pertenece al grafo");
        }

    	Set<Tuple2<V,W>> suc = new HashSet<>();
        Dictionary<V, W> vDictionary = graph.valueOf(v);

        for(V src : vDictionary.keys()){
            W weight = vDictionary.valueOf(src);
            suc.insert(new Tuple2<>(src,weight));

        }

        return suc;
    }


    public Set<WeightedEdge<V, W>> edges() {
        
    	Set<WeightedEdge<V, W>> s = new HashSet<>();

        for(V src : graph.keys()){
            Dictionary<V,W> dic = graph.valueOf(src);

            for(V dst : dic.keys()){
                W weight = dic.valueOf(dst);
                s.insert(new WE<>(src,dst,weight));
            }
        }
    	
        return s;
    }



    /** DON'T EDIT ANYTHING BELOW THIS COMMENT **/


    public Set<V> vertices() {
        Set<V> vs = new HashSet<>();
        for (V v : graph.keys())
            vs.insert(v);
        return vs;
    }


    public boolean isEmpty() {
        return graph.isEmpty();
    }

    public int numVertices() {
        return graph.size();
    }


    public int numEdges() {
        int num = 0;
        for (Dictionary<V, W> d : graph.values())
            num += d.size();
        return num / 2;
    }


    public String toString() {
        String className = getClass().getSimpleName();
        String s = className + "(vertices=(";

        Iterator<V> it1 = vertices().iterator();
        while (it1.hasNext())
            s += it1.next() + (it1.hasNext() ? ", " : "");
        s += ")";

        s += ", edges=(";
        Iterator<WeightedEdge<V, W>> it2 = edges().iterator();
        while (it2.hasNext())
            s += it2.next() + (it2.hasNext() ? ", " : "");
        s += "))";

        return s;
    }
}

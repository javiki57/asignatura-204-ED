/*
  Student's name: ??????????
  Identity number (DNI if Spanish/passport if Erasmus): ???????????
 */

package exercises;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.graph.DiGraph;
import dataStructures.graph.DictionaryDiGraph;
import dataStructures.set.Set;


/*
  Arrival and departure time of vertices in DFT
  Given a graph, find the arrival and departure time of its vertices in DFT.
  The arrival time is the time at which the vertex was explored for the first
  time in the DFT, and departure time is the time at which we have explored
  all the successors of the vertex, and we are ready to backtrack.
  Times are defined as a sequence of integers (0 for first visited node, 1 for second
  one, and so on).
 */

public class DiGraphDftTimer<V> {
  private int time;
  private Dictionary<V, Integer> arrivalD, departureD;
  private Set<V> visited;

  public DiGraphDftTimer(DiGraph<V> diGraphg) {
    arrivalD = new HashDictionary<>();
    departureD = new HashDictionary<>();
    dfsTimer(diGraphg, diGraphg.vertices(),arrivalD, departureD, 0);
  }

  public int dfsTimer(DiGraph<V> dg, Set<V> vs, Dictionary<V,Integer> arr, Dictionary<V,Integer> dep, int timer) {
    for (V v : vs) {
      if (!arr.isDefinedAt(v)) {
        arr.insert(v, timer);
        timer++;
        timer = dfsTimer(dg, dg.successors(v), arr, dep, timer);
        dep.insert(v, timer);
        timer++;
      }
    }
    return timer;
  }


  public int arrivalTime(V v) {
    return arrivalD.valueOf(v);
  }

  public int departureTime(V v) {
    return departureD.valueOf(v);
  }
}

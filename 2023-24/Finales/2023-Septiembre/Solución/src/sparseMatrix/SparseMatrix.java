/******************************************************************************
 * Student's name: ?????????????????????????????????????
 * Identity number (DNI if Spanish/passport if Erasmus): ???????????????????
 * Student's group: ?
 * PC code: ???
 *
 * Data Structures. Grados en Informatica. UMA.
 *****************************************************************************/

package sparseMatrix;

import dataStructures.dictionary.AVLDictionary;
import dataStructures.dictionary.Dictionary;
import dataStructures.list.List;
import dataStructures.tuple.Tuple2;

import java.util.Iterator;

// | = Exercise a - SparseMatrix constructor
public class SparseMatrix {
    public static int rows;
    public static int columns;
    private Dictionary<Index, Integer> nonZeros;

    public SparseMatrix(int r, int c) {
        if(r < 0 || c < 0){
            throw new RuntimeException("Error, dimensiones erroneas");
        }

        rows = r;
        columns = c;
        nonZeros = new AVLDictionary<>();
    }

    // | = Exercise b - value
    private int value(Index ind) {

        if(nonZeros.valueOf(ind) == null){
            return 0;

        }else{
            return nonZeros.valueOf(ind);
        }
    }

    // | = Exercise c - update
    private void update(Index ind, int value) {

        if(value == 0){
            nonZeros.delete(ind);

        }else{
            nonZeros.insert(ind,value);
        }

    }

    // | = Exercise d - index
    private static Index index(int r, int c) {

        if(r >= rows && c >= columns || r < 0 && c < 0){
            throw new RuntimeException("Error, dimensiones erróneas");
        }

        return new Index(r,c);
    }

    // | = Exercise e - set
    public void set(int r, int c, int value) {
        update(index(r,c),value);
    }

    // | = Exercise f - get
    public int get(int r, int c) {

        return value(index(r,c));
    }

    // | = Exercise g - add
    public static SparseMatrix add(SparseMatrix m1, SparseMatrix m2) {

        if(m1.columns != m2.columns || m1.rows != m2.rows){
            throw new RuntimeException("Error, las dimensiones no son iguales.");
        }

        //Creamos la matriz m3 con los valores de m1 de manera independiente.
        SparseMatrix m3 = new SparseMatrix(m1.rows,m2.columns);
        for(Tuple2<Index, Integer> i : m1.nonZeros.keysValues()){
            m3.nonZeros.insert(i._1(),i._2());
        }

        //Hacemos la suma con m2

        for(Tuple2<Index, Integer> i : m2.nonZeros.keysValues()){

            if(m3.nonZeros.isDefinedAt(i._1())){
                m3.update(i._1(),i._2()+m3.value(i._1()));

            }else{
                m3.update(i._1(),i._2());
            }
        }

        return m3;
    }

    // | = Exercise h - transpose
    public SparseMatrix transpose() {

        SparseMatrix m = new SparseMatrix(columns,rows);

        for(Tuple2<Index,Integer> t : nonZeros.keysValues()){

            m.nonZeros.insert(index(t._1().getColumn(),t._1().getRow()),t._2());
        }

        return m;
    }

    // | = Exercise i - toString
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<rows; i++){

            for(int j=0; j<columns; j++){

                if(nonZeros.isDefinedAt(index(i,j))){
                    sb.append(nonZeros.valueOf(index(i,j)));

                }else{
                    sb.append(0);
                }
                sb.append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // | = Exercise j - fromList and fromList2
    // Complexity using get and ArrayList:
    // Complexity using get and LinkedList:
    public static SparseMatrix fromList(int r, int c, List<Integer> list) {

        if(list.size() % 3 != 0) throw new RuntimeException("Error, el tamaño de la lista no es correcto.");

        SparseMatrix m = new SparseMatrix(r,c);
        for(int i=0; i<list.size(); i+=3){

            m.set(list.get(i),list.get(i+1),list.get(i+2));
        }

        return m;
    }

    // Complexity using iterator and ArrayList:
    // Complexity using iterator and LinkedList:
    public static SparseMatrix fromList2(int r, int c, List<Integer> list) {

        if (list.size() % 3 != 0) {
            throw new RuntimeException("Error, the list length must be a multiple of 3.");
        }

        SparseMatrix m = new SparseMatrix(r,c);
        Iterator<Integer> it = list.iterator();

        while(it.hasNext()){

            m.set(it.next(),it.next(),it.next());
        }

        return m;
    }
}

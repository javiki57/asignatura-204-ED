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
    public final int rows;
    public final int columns;
    private Dictionary<Index, Integer> nonZeros;

    public SparseMatrix(int r, int c) {
        if(r <= 0 || c <= 0){
            throw new RuntimeException("Error, columnas o filas menores que cero.");
        }

        this.rows = r;
        this.columns = c;
        nonZeros = new AVLDictionary<>();

    }

    // | = Exercise b - value
    private int value(Index ind) {

        if(nonZeros.isDefinedAt(ind)){
            return nonZeros.valueOf(ind);
        }

        return 0;
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
    private Index index(int r, int c) {

    if(r < 0 && c < 0 || r>=rows && c>=columns){
        throw new RuntimeException("Error, valor de filas o columnas no válido.");
    }

        return new Index(r,c);
    }

    // | = Exercise e - set
    public void set(int r, int c, int value) {
        Index indice = index(r,c);
        update(indice,value);

    }

    // | = Exercise f - get
    public int get(int r, int c) {

        return value(index(r,c));
    }

    // | = Exercise g - add
    public static SparseMatrix add(SparseMatrix m1, SparseMatrix m2) {

        if (m1.rows != m2.rows || m1.columns != m2.columns) {
            throw new RuntimeException("Error, matrices incompatibles.");
        }

        SparseMatrix suma = new SparseMatrix(m1.rows,m1.columns);

        Iterator<Tuple2<Index,Integer>> it1 = m1.nonZeros.keysValues().iterator();

        while(it1.hasNext()){
            Tuple2<Index,Integer> entrada = it1.next();
            Index i = entrada._1();
            int value = entrada._2();

            suma.set(i.getRow(),i.getColumn(),value);
        }

        Iterator<Tuple2<Index,Integer>> it2 = m2.nonZeros.keysValues().iterator();

        while(it2.hasNext()){
            Tuple2<Index,Integer> entrada = it2.next();
            Index i = entrada._1();
            int valor = entrada._2();

            int valorActual = suma.get(i.getRow(),i.getColumn());
            suma.set(i.getRow(),i.getColumn(),valorActual+valor);
        }

        return suma;
    }

    // | = Exercise h - transpose
    public SparseMatrix transpose() {
        SparseMatrix transposedMatrix = new SparseMatrix(columns, rows);

        // Iterate over non-zero elements of the original matrix
        Iterator<Tuple2<Index, Integer>> iterator = nonZeros.keysValues().iterator();
        while (iterator.hasNext()) {
            Tuple2<Index, Integer> entry = iterator.next();
            Index originalIndex = entry._1();
            int value = entry._2();

            // Swap row and column indices to transpose the matrix
            Index transposedIndex = new Index(originalIndex.getColumn(), originalIndex.getRow());

            // Add element to transposed matrix
            transposedMatrix.set(transposedIndex.getRow(), transposedIndex.getColumn(), value);
        }

        return transposedMatrix;
    }

    // | = Exercise i - toString
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < columns; j++) {

                int value = get(i, j);
                result.append(String.format("%6d", value));

                if (j < columns - 1) {
                    result.append(" ");

                } else {
                    result.append("\n");
                }
            }
        }

        return result.toString();
    }

    // | = Exercise j - fromList and fromList2
    // Complexity using get and ArrayList:
    // Complexity using get and LinkedList:
    public static SparseMatrix fromList(int r, int c, List<Integer> list) {
        if (list.size() % 3 != 0) {
            throw new RuntimeException("Error, la lista debe ser múltiplo de 3.");
        }

        SparseMatrix result = new SparseMatrix(r, c);

        for (int i = 0; i < list.size(); i += 3) {
            int row = list.get(i);
            int column = list.get(i + 1);
            int value = list.get(i + 2);

            result.set(row, column, value);
        }

        return result;
    }

    // Complexity using iterator and ArrayList:
    // Complexity using iterator and LinkedList:
    public static SparseMatrix fromList2(int r, int c, List<Integer> list) {
        if (list.size() % 3 != 0) {
            throw new RuntimeException("Error, la lista debe ser múltiplo de 3.");
        }

        SparseMatrix result = new SparseMatrix(r, c);
        Iterator<Integer> iterator = list.iterator();

        while (iterator.hasNext()) {
            int row = iterator.next();
            int column = iterator.next();
            int value = iterator.next();

            result.set(row, column, value);
        }

        return result;
    }
}

package dataStructures.bag;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class SortedArrayBag<T extends Comparable<? super T>> implements Bag<T> {

    private final static int INITIAL_CAPACITY = 1;

    /**
     * <strong>Representation invariant:</strong>
     * <p>
     * 1. {@code nextFree} refers to the first available position in the arrays {@code value} and {@code count}
     * <p>
     * 2. the slice {@code [0..nextFree)} of the array {@code value} must be sorted, with no duplicates
     * <p>
     * 3. the slice {@code [0..nextFree)} of the array {@code count} must store positive integers
     * <p>
     * 4. {@code size} is the sum of the integers in the slice {@code [0..nextFree)} of the array {@code count}
     * <p>
     * {@code value[i]} stores the <em>ith</em> item in the bag, while {@code count[i]} stores its number of occurrences
     * <p>
     * <strong>Example:</strong>
     * <pre>
     *    value -> {'a', 'd', 't', 'z'}
     *    count -> { 5 ,  1,   3,   2 }
     *    nextFree = 4
     *    size = 11
     * </pre>
     */
    private T[] value;
    private int[] count;
    private int nextFree;
    private int size;

    @SuppressWarnings("unchecked")
    public SortedArrayBag() {
        value = (T[]) new Comparable[INITIAL_CAPACITY];
        count = new int[INITIAL_CAPACITY];
        nextFree = 0;
        size = 0;

    }

    private void ensureCapacity() {
        if(nextFree == value.length){
            value = Arrays.copyOf(value, value.length*2);
            count = Arrays.copyOf(count, count.length*2);
        }
    }

    @Override
    public boolean isEmpty() {

        return size() == 0;
    }

    @Override
    public int size() {

        return size;
    }

    /**
     * Localiza la posición donde está o debería estar un elemento.
     * <p>
     * Si {@code item} aparece en el array {@code value}, devuelve su índice;
     * en otro caso devuelve el índice donde {@code item} debería estar.
     *
     * @param item el elemento a localizar.
     * @return índice donde está o debería estar {@code item}
     */
    private int locate(T item) {
        int lower = 0;
        int upper = nextFree - 1;
        int mid = 0;
        boolean found = false;

        // Búsqueda binaria
        while (lower <= upper && !found) {
            mid = lower + ((upper - lower) / 2); // == (lower + upper) / 2;
            found = value[mid].equals(item);
            if (!found) {
                if (value[mid].compareTo(item) > 0) {
                    upper = mid - 1;
                } else {
                    lower = mid + 1;
                }
            }
        }

        if (found) {
            return mid; // el índice donde "item" está almacenado
        } else {
            return lower; // el índice donde "item" debería insertarse
        }
    }

    @Override
    public void insert(T item) {
        int index = locate(item);

        if(index < nextFree && value[index].equals(item)){
            count[index]++;
        }else{
            ensureCapacity();
            value[nextFree] = item;

            for(int i=nextFree -1; i>=index; i--){
                value[i+1] = value[i];
                count[i+1] = count[i];
            }
            value[index] = item;
            count[index] = 1;
            nextFree++;
            size++;
        }

    }

    @Override
    public int occurrences(T item) {
        int index = locate(item);

        if(index < nextFree && value[index].equals(item)){
            return count[index];
        }

        return 0;
    }

    @Override
    public void delete(T item) {
        int index = locate(item);

        if(index < nextFree && value[index].equals(item)){
            if(occurrences(item) > 1) count[index]--;

            else{
                for (int i = index; i < nextFree - 1; i++) {
                    value[i] = value[i + 1];
                    count[i] = count[i + 1];
                }
                nextFree--;
                size--;
            }

        }/*else{
            System.err.println("El item no pertenece a la bolsa");
        }*/
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" ", "Bag(", ")");
        for (int i = 0; i < nextFree; i++) {
            sj.add(String.format("(%s, %s)", value[i], count[i]));
        }
        return sj.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;
            @Override
            public boolean hasNext() {
                return currentIndex < nextFree;
            }

            @Override
            public T next() {
                if(!hasNext()){
                    throw new NoSuchElementException();
                }
                return value[currentIndex++];
            }
        };
    }
}

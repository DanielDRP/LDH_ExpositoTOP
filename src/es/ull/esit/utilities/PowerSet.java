package es.ull.esit.utilities;

import java.util.BitSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * La clase {@code PowerSet} permite calcular todos los subconjuntos posibles de un conjunto dado.
 * Utiliza una {@code BitSet} para iterar sobre todos los subconjuntos posibles.
 *
 * @param <E> El tipo de elementos en el conjunto de entrada.
 */
public class PowerSet<E> implements Iterator<Set<E>>, Iterable<Set<E>> {

    /** Arreglo que contiene los elementos del conjunto original. */
    private E[] arr = null;

    /** BitSet que representa los subconjuntos como secuencias binarias. */
    private BitSet bset = null;

    /**
     * Constructor que inicializa el PowerSet con el conjunto dado.
     *
     * @param set El conjunto para el cual se generarán todos los subconjuntos.
     */
    @SuppressWarnings("unchecked")
    public PowerSet(Set<E> set) {
        this.arr = (E[]) set.toArray();
        this.bset = new BitSet(this.arr.length + 1);
    }

    /**
     * Verifica si aún existen subconjuntos no generados.
     *
     * @return {@code true} si hay más subconjuntos, {@code false} en caso contrario.
     */
    @Override
    public boolean hasNext() {
        return !this.bset.get(this.arr.length);
    }

    /**
     * Genera el siguiente subconjunto del conjunto original.
     *
     * @return El siguiente subconjunto como un {@code Set<E>}.
     */
    @Override
    public Set<E> next() {
        Set<E> returnSet = new TreeSet<>();
        for (int i = 0; i < this.arr.length; i++) {
            if (this.bset.get(i)) {
                returnSet.add(this.arr[i]);
            }
        }
        for (int i = 0; i < this.bset.size(); i++) {
            if (!this.bset.get(i)) {
                this.bset.set(i);
                break;
            } else {
                this.bset.clear(i);
            }
        }
        return returnSet;
    }

    /**
     * Este método no es soportado en la implementación de PowerSet.
     *
     * @throws UnsupportedOperationException Siempre lanza esta excepción.
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not Supported!");
    }

    /**
     * Retorna un iterador para los subconjuntos del conjunto original.
     *
     * @return Un iterador que permite recorrer todos los subconjuntos del conjunto dado.
     */
    @Override
    public Iterator<Set<E>> iterator() {
        return this;
    }
}

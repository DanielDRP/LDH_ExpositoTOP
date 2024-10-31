package es.ull.esit.utils;

import java.util.Objects;

/**
 * La clase {@code Pair} representa un par de valores genéricos inmutables.
 *
 * @param <F> El tipo del primer elemento del par.
 * @param <S> El tipo del segundo elemento del par.
 */
public class Pair<F, S> {

    /** El primer elemento del par. */
    public final F first;

    /** El segundo elemento del par. */
    public final S second;

    /**
     * Constructor que inicializa el par con dos elementos.
     *
     * @param first  El primer elemento del par.
     * @param second El segundo elemento del par.
     */
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Verifica si este par es igual a otro objeto.
     *
     * @param o El objeto a comparar con este par.
     * @return {@code true} si ambos pares contienen los mismos elementos en el mismo orden, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair<?, ?> p = (Pair<?, ?>) o;
        return Objects.equals(p.first, first) && Objects.equals(p.second, second);
    }

    /**
     * Calcula el código hash para el par.
     *
     * @return El código hash calculado usando ambos elementos del par.
     */
    @Override
    public int hashCode() {
        return (first == null ? 0 : first.hashCode()) ^ (second == null ? 0 : second.hashCode());
    }

    /**
     * Crea una nueva instancia de {@code Pair} con los elementos especificados.
     *
     * @param <A> El tipo del primer elemento.
     * @param <B> El tipo del segundo elemento.
     * @param a   El primer elemento del par.
     * @param b   El segundo elemento del par.
     * @return Un nuevo par {@code Pair<A, B>} que contiene los elementos dados.
     */
    public static <A, B> Pair<A, B> create(A a, B b) {
        return new Pair<>(a, b);
    }
}

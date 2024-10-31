package top;

/**
 * Clase para evaluar la solución de un problema de Team Orienteering con Ventanas de Tiempo (TOPTW).
 * <p>
 * Proporciona un método para calcular el valor de la función objetivo de una solución, evaluando
 * la distancia acumulativa entre los nodos de cada ruta generada.
 */
public class TOPTWEvaluator {
    public static double NO_EVALUATED = -1.0; ///< Valor constante para indicar que la solución no ha sido evaluada.

    /**
     * Evalúa la solución dada, calculando el valor de la función objetivo.
     * <p>
     * Este método está diseñado para calcular la distancia acumulativa entre nodos de cada ruta,
     * sumando las distancias desde y hacia el depósito en cada iteración.
     * <p>
     * Nota: Este método está actualmente comentado.
     *
     * @param solution La solución de TOPTW que se desea evaluar.
     */
    public void evaluate(TOPTWSolution solution) {
        /* CumulativeCVRP problem = solution.getProblem();
         * double objectiveFunctionValue = 0.0;
         *
         * // Itera sobre todos los depósitos en la solución.
         * for (int i = 0; i < solution.getIndexDepot().size(); i++) {
         *     double cumulative = 0; // Variable para acumular la distancia en cada ruta.
         *     int depot = solution.getAnIndexDepot(i);
         *     int actual = depot;
         *
         *     // Se obtiene el sucesor del nodo actual.
         *     actual = solution.getSuccessor(actual);
         *     cumulative += problem.getDistanceMatrix(0, actual);
         *     objectiveFunctionValue += problem.getDistanceMatrix(0, actual);
         *     System.out.println("Desde " + 0 + " a " + actual + " = " + cumulative);
         *
         *     // Ciclo para recorrer todos los nodos en la ruta hasta regresar al depósito.
         *     while (actual != depot) {
         *         int ant = actual;
         *         actual = solution.getSuccessor(actual);
         *
         *         // Suma la distancia entre nodos en la ruta si no es el depósito.
         *         if (actual != depot) {
         *             cumulative += problem.getDistanceMatrix(ant, actual);
         *             objectiveFunctionValue += cumulative;
         *             System.out.println("Desde " + ant + " a " + actual + " = " + cumulative);
         *         } else {
         *             // Suma la distancia final de regreso al depósito.
         *             cumulative += problem.getDistanceMatrix(ant, 0);
         *             objectiveFunctionValue += cumulative;
         *             System.out.println("Desde " + ant + " a " + 0 + " = " + cumulative);
         *         }
         *     }
         *     System.out.println("");
         * }
         *
         * // Establece el valor de la función objetivo en la solución.
         * solution.setObjectiveFunctionValue(objectiveFunctionValue);
         */
    }
}

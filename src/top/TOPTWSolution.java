package top;

import java.util.Arrays;

import es.ull.esit.utilities.ExpositoUtilities;

public class TOPTWSolution {
    public static final int NO_INITIALIZED = -1;
    private TOPTW problem;
    private int[] predecessors;
    private int[] successors;
    private double[] waitingTime;
    private int[] positionInRoute;

    private int[] routes;
    private int availableVehicles;
    private double objectiveFunctionValue;

    /**
     * Constructor de la clase TOPTWSolution.
     *
     * @param problem
     */
    public TOPTWSolution(TOPTW problem) {
        this.problem = problem;
        this.availableVehicles = this.problem.getVehicles();
        this.predecessors = new int[this.problem.getPOIs() + this.problem.getVehicles()];
        this.successors = new int[this.problem.getPOIs() + this.problem.getVehicles()];
        this.waitingTime = new double[this.problem.getPOIs()];
        this.positionInRoute = new int[this.problem.getPOIs()];
        Arrays.fill(this.predecessors, TOPTWSolution.NO_INITIALIZED);
        Arrays.fill(this.successors, TOPTWSolution.NO_INITIALIZED);
        Arrays.fill(this.waitingTime, TOPTWSolution.NO_INITIALIZED);
        Arrays.fill(this.positionInRoute, TOPTWSolution.NO_INITIALIZED);
        this.routes = new int[this.problem.getVehicles()];
        this.objectiveFunctionValue = TOPTWEvaluator.NO_EVALUATED;
    }

    /**
     * Inicializa la solución.
     */
    public void initSolution() {
        this.predecessors = new int[this.problem.getPOIs() + this.problem.getVehicles()];
        this.successors = new int[this.problem.getPOIs() + this.problem.getVehicles()];
        Arrays.fill(this.predecessors, TOPTWSolution.NO_INITIALIZED);
        Arrays.fill(this.successors, TOPTWSolution.NO_INITIALIZED);
        this.routes = new int[this.problem.getVehicles()];
        Arrays.fill(this.routes, TOPTWSolution.NO_INITIALIZED);
        this.routes[0] = 0;
        this.predecessors[0] = 0;
        this.successors[0] = 0;
        this.availableVehicles = this.problem.getVehicles() - 1;
    }

    /**
     * Verifica si el cliente dado es un depósito.
     *
     * @param c El identificador del cliente a verificar.
     * @return true si el cliente es un depósito; false en caso contrario.
     */
    public boolean isDepot(int c) {
        for (int i = 0; i < this.routes.length; i++) {
            if (c == this.routes[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compara este objeto con otro objeto de tipo TOPTWSolution.
     *
     * @param otherSolution La otra solución a comparar.
     * @return true si ambas soluciones son iguales; false en caso contrario.
     */
    public boolean equals(TOPTWSolution otherSolution) {
        for (int i = 0; i < this.predecessors.length; i++) {
            if (this.predecessors[i] != otherSolution.predecessors[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Obtiene la cantidad de vehículos disponibles.
     *
     * @return La cantidad de vehículos disponibles.
     */
    public int getAvailableVehicles() {
        return this.availableVehicles;
    }

    /**
     * Calcula y devuelve el número de rutas creadas.
     *
     * @return El número de rutas creadas.
     */
    public int getCreatedRoutes() {
        return this.problem.getVehicles() - this.availableVehicles;
    }

    /**
     * Obtiene la distancia entre dos puntos.
     *
     * @param x El primer punto.
     * @param y El segundo punto.
     * @return La distancia entre los puntos x e y.
     */
    public double getDistance(int x, int y) {
        return this.problem.getDistance(x, y);
    }

    /**
     * Establece la cantidad de vehículos disponibles.
     *
     * @param availableVehicles La nueva cantidad de vehículos disponibles.
     */
    public void setAvailableVehicles(int availableVehicles) {
        this.availableVehicles = availableVehicles;
    }

    /**
     * Obtiene el predecesor del cliente dado.
     *
     * @param customer El identificador del cliente.
     * @return El predecesor del cliente.
     */
    public int getPredecessor(int customer) {
        return this.predecessors[customer];
    }

    /**
     * Obtiene todos los predecesores.
     *
     * @return Un arreglo de todos los predecesores.
     */
    public int[] getPredecessors() {
        return this.predecessors;
    }

    /**
     * Obtiene el problema asociado a esta solución.
     *
     * @return El objeto problema de tipo TOPTW.
     */
    public TOPTW getProblem() {
        return this.problem;
    }

    /**
     * Obtiene el valor de la función objetivo.
     *
     * @return El valor de la función objetivo.
     */
    public double getObjectiveFunctionValue() {
        return this.objectiveFunctionValue;
    }

    /**
     * Obtiene la posición del cliente en su ruta.
     *
     * @param customer El identificador del cliente.
     * @return La posición del cliente en la ruta.
     */
    public int getPositionInRoute(int customer) {
        return this.positionInRoute[customer];
    }

    /**
     * Obtiene el sucesor del cliente dado.
     *
     * @param customer El identificador del cliente.
     * @return El sucesor del cliente.
     */
    public int getSuccessor(int customer) {
        return this.successors[customer];
    }

    /**
     * Obtiene todos los sucesores.
     *
     * @return Un arreglo de todos los sucesores.
     */
    public int[] getSuccessors() {
        return this.successors;
    }

    /**
     * Obtiene el índice de la ruta en la posición dada.
     *
     * @param index La posición de la ruta.
     * @return El índice de la ruta.
     */
    public int getIndexRoute(int index) {
        return this.routes[index];
    }

    /**
     * Obtiene el tiempo de espera del cliente.
     *
     * @param customer El identificador del cliente.
     * @return El tiempo de espera del cliente.
     */
    public double getWaitingTime(int customer) {
        return this.waitingTime[customer];
    }

    /**
     * Establece el valor de la función objetivo.
     *
     * @param objectiveFunctionValue El nuevo valor de la función objetivo.
     */
    public void setObjectiveFunctionValue(double objectiveFunctionValue) {
        this.objectiveFunctionValue = objectiveFunctionValue;
    }

    /**
     * Establece la posición del cliente en su ruta.
     *
     * @param customer El identificador del cliente.
     * @param position La nueva posición del cliente en la ruta.
     */
    public void setPositionInRoute(int customer, int position) {
        this.positionInRoute[customer] = position;
    }

    /**
     * Establece el predecesor del cliente dado.
     *
     * @param customer    El identificador del cliente.
     * @param predecessor El nuevo predecesor del cliente.
     */
    public void setPredecessor(int customer, int predecessor) {
        this.predecessors[customer] = predecessor;
    }

    /**
     * Establece el sucesor del cliente dado.
     *
     * @param customer El identificador del cliente.
     * @param succesor El nuevo sucesor del cliente.
     */
    public void setSuccessor(int customer, int succesor) {
        this.successors[customer] = succesor;
    }

    /**
     * Establece el tiempo de espera del cliente.
     *
     * @param customer    El identificador del cliente.
     * @param waitingTime El nuevo tiempo de espera del cliente.
     */
    public void setWaitingTime(int customer, int waitingTime) {
        this.waitingTime[customer] = waitingTime;
    }


    /**
     * Genera un informe detallado de la solución actual.
     * <p>
     * Este método compila información sobre los nodos, el tiempo máximo por ruta,
     * el número máximo de rutas y detalla cada ruta creada, incluyendo información
     * sobre cada cliente, coordenadas, tiempos de llegada y salida, y tiempo de servicio.
     *
     * @return Un string que contiene la información detallada de la solución.
     */
    public String getInfoSolution() {
        final int COLUMN_WIDTH = 15; // Ancho de columna para la presentación.
        String text = "\n" + "NODES: " + this.problem.getPOIs() + "\n" +
                "MAX TIME PER ROUTE: " + this.problem.getMaxTimePerRoute() + "\n" +
                "MAX NUMBER OF ROUTES: " + this.problem.getMaxRoutes() + "\n";
        String textSolution = "\n" + "SOLUTION: " + "\n";
        double costTimeSolution = 0.0, fitnessScore = 0.0;
        boolean validSolution = true;

        // Itera sobre las rutas creadas.
        for (int k = 0; k < this.getCreatedRoutes(); k++) {
            String[] strings = new String[]{"\n" + "ROUTE " + k};
            int[] width = new int[strings.length];
            Arrays.fill(width, COLUMN_WIDTH); // Inicializa el ancho de las columnas.
            text += ExpositoUtilities.getFormat(strings, width) + "\n";

            // Encabezados para la tabla de información de clientes.
            strings = new String[]{"CUST NO.", "X COORD.", "Y. COORD.", "READY TIME",
                    "DUE DATE", "ARRIVE TIME", "LEAVE TIME", "SERVICE TIME"};
            width = new int[strings.length];
            Arrays.fill(width, COLUMN_WIDTH); // Inicializa el ancho de las columnas.
            text += ExpositoUtilities.getFormat(strings, width) + "\n";

            strings = new String[strings.length];
            int depot = this.getIndexRoute(k); // Obtiene el depósito de la ruta.
            int pre = -1, suc = -1;
            double costTimeRoute = 0.0, fitnessScoreRoute = 0.0;
            pre = depot; // Inicializa el predecesor en el depósito.
            int index = 0;

            // Información del depósito.
            strings[index++] = "" + pre;
            strings[index++] = "" + this.getProblem().getX(pre);
            strings[index++] = "" + this.getProblem().getY(pre);
            strings[index++] = "" + this.getProblem().getReadyTime(pre);
            strings[index++] = "" + this.getProblem().getDueTime(pre);
            strings[index++] = "" + 0; // Tiempo de llegada al depósito es 0.
            strings[index++] = "" + 0; // Tiempo de salida al depósito es 0.
            strings[index++] = "" + this.getProblem().getServiceTime(pre);

            text += ExpositoUtilities.getFormat(strings, width);
            text += "\n";

            // Recorre la ruta desde el depósito hasta regresar.
            do {
                index = 0;
                suc = this.getSuccessor(pre); // Obtiene el sucesor del predecesor.
                textSolution += pre + " - "; // Agrega a la solución.

                // Información del sucesor.
                strings[index++] = "" + suc;
                strings[index++] = "" + this.getProblem().getX(suc);
                strings[index++] = "" + this.getProblem().getY(suc);
                strings[index++] = "" + this.getProblem().getReadyTime(suc);
                strings[index++] = "" + this.getProblem().getDueTime(suc);
                costTimeRoute += this.getDistance(pre, suc); // Calcula el costo del tiempo de ruta.

                // Verifica tiempos de llegada y de servicio.
                if (costTimeRoute < (this.getProblem().getDueTime(suc))) {
                    if (costTimeRoute < this.getProblem().getReadyTime(suc)) {
                        costTimeRoute = this.getProblem().getReadyTime(suc);
                    }
                    strings[index++] = "" + costTimeRoute; // Tiempo de llegada.
                    costTimeRoute += this.getProblem().getServiceTime(suc);
                    strings[index++] = "" + costTimeRoute; // Tiempo de salida.
                    strings[index++] = "" + this.getProblem().getServiceTime(pre);

                    // Verifica si la solución es válida.
                    if (costTimeRoute > this.getProblem().getMaxTimePerRoute()) {
                        validSolution = false; // La solución no es válida si se excede el tiempo máximo.
                    }
                    fitnessScoreRoute += this.problem.getScore(suc); // Suma el puntaje de la ruta.
                } else {
                    validSolution = false; // La solución no es válida si el tiempo de llegada excede el tiempo límite.
                }
                pre = suc; // Avanza al siguiente cliente en la ruta.
                text += ExpositoUtilities.getFormat(strings, width);
                text += "\n";
            } while (suc != depot); // Continúa hasta regresar al depósito.

            textSolution += suc + "\n"; // Agrega el depósito al final de la ruta.
            costTimeSolution += costTimeRoute; // Suma el costo de tiempo de la ruta.
            fitnessScore += fitnessScoreRoute; // Suma el puntaje total.
        }

        // Información final sobre la solución.
        textSolution += "FEASIBLE SOLUTION: " + validSolution + "\n" +
                "SCORE: " + fitnessScore + "\n" +
                "TIME COST: " + costTimeSolution + "\n";

        return textSolution + text; // Devuelve el texto con la información de la solución.
    }

    /**
     * Evalúa y calcula el valor de la función objetivo (fitness) de la solución actual.
     * <p>
     * Este método recorre todas las rutas creadas y suma el puntaje de cada cliente
     * para obtener un valor total de la función objetivo.
     *
     * @return El valor total de la función objetivo (fitness) de la solución.
     */
    public double evaluateFitness() {
        double objectiveFunction = 0.0; // Valor total de la función objetivo.
        double objectiveFunctionPerRoute = 0.0; // Valor de la función objetivo por ruta.

        // Itera sobre las rutas creadas.
        for (int k = 0; k < this.getCreatedRoutes(); k++) {
            int depot = this.getIndexRoute(k); // Obtiene el depósito de la ruta.
            int pre = depot, suc = -1;

            // Recorre la ruta desde el depósito.
            do {
                suc = this.getSuccessor(pre); // Obtiene el sucesor.
                objectiveFunctionPerRoute += this.problem.getScore(suc); // Suma el puntaje del cliente.
                pre = suc; // Avanza al siguiente cliente.
            } while ((suc != depot)); // Continúa hasta regresar al depósito.

            objectiveFunction += objectiveFunctionPerRoute; // Suma el puntaje por ruta al total.
            objectiveFunctionPerRoute = 0.0; // Reinicia el puntaje por ruta.
        }

        return objectiveFunction; // Devuelve el valor total de la función objetivo.
    }

    /**
     * Agrega una nueva ruta al conjunto de rutas existentes.
     * <p>
     * Este método incrementa el depósito, actualiza la posición de la ruta en el arreglo,
     * y actualiza la cantidad de vehículos disponibles. También agrega el nodo del nuevo depósito
     * al problema.
     *
     * @return El identificador del nuevo depósito creado.
     */
    public int addRoute() {
        int depot = this.problem.getPOIs(); // Obtiene el número de puntos de interés.
        depot++;
        int routePos = 1;

        // Busca la siguiente posición disponible para la nueva ruta.
        for (int i = 0; i < this.routes.length; i++) {
            if (this.routes[i] != -1 && this.routes[i] != 0) {
                depot = this.routes[i]; // Actualiza el depósito al último existente.
                depot++;
                routePos = i + 1; // Establece la posición de la nueva ruta.
            }
        }

        this.routes[routePos] = depot; // Asigna el nuevo depósito en la ruta.
        this.availableVehicles--; // Disminuye la cantidad de vehículos disponibles.
        this.predecessors[depot] = depot; // Establece el predecesor del nuevo depósito.
        this.successors[depot] = depot; // Establece el sucesor del nuevo depósito.
        this.problem.addNodeDepot(); // Agrega el nuevo nodo de depósito al problema.

        return depot; // Devuelve el identificador del nuevo depósito.
    }

    /**
     * Imprime la solución actual en la consola.
     * <p>
     * Este método recorre todas las rutas creadas y muestra la secuencia de clientes en cada ruta.
     * También evalúa la función de ajuste (fitness) y la imprime.
     *
     * @return El valor de fitness de la solución impresa.
     */
    public double printSolution() {
        // Itera sobre las rutas creadas.
        for (int k = 0; k < this.getCreatedRoutes(); k++) {
            int depot = this.getIndexRoute(k); // Obtiene el depósito de la ruta.
            int pre = depot, suc = -1;

            // Recorre la ruta desde el depósito.
            do {
                suc = this.getSuccessor(pre); // Obtiene el sucesor.
                System.out.print(pre + " - "); // Imprime el cliente actual.
                pre = suc; // Avanza al siguiente cliente.
            } while ((suc != depot)); // Continúa hasta regresar al depósito.

            System.out.println(suc + "  "); // Imprime el depósito al final de la ruta.
        }

        double fitness = this.evaluateFitness(); // Evalúa y obtiene el fitness.
        System.out.println("SC=" + fitness); // Imprime el valor de fitness.

        return fitness; // Devuelve el valor de fitness.
    }
}

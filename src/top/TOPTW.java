package top;

import java.util.ArrayList;
import java.util.Arrays;
import es.ull.esit.utilities.ExpositoUtilities;

/**
 * Clase que representa una instancia del problema de Team Orienteering con Ventanas de Tiempo (TOPTW).
 * <p>
 * Esta clase almacena la información sobre nodos, vehículos y parámetros de tiempo y distancia
 * necesarios para resolver el problema.
 */
public class TOPTW {
    private int nodes; ///< Número de nodos (puntos de interés).
    private double[] x; ///< Coordenada X de cada nodo.
    private double[] y; ///< Coordenada Y de cada nodo.
    private double[] score; ///< Puntuación asociada a cada nodo.
    private double[] readyTime; ///< Tiempo de inicio de la ventana de tiempo para cada nodo.
    private double[] dueTime; ///< Tiempo de finalización de la ventana de tiempo para cada nodo.
    private double[] serviceTime; ///< Tiempo de servicio en cada nodo.
    private int vehicles; ///< Número de vehículos disponibles.
    private int depots; ///< Número de depósitos.
    private double maxTimePerRoute; ///< Tiempo máximo permitido por ruta.
    private double maxRoutes; ///< Máximo número de rutas permitidas.
    private double[][] distanceMatrix; ///< Matriz de distancias entre los nodos.

    /**
     * Constructor de la clase TOPTW.
     *
     * @param nodes Número de nodos.
     * @param routes Número máximo de rutas permitidas.
     */
    public TOPTW(int nodes, int routes) {
        this.nodes = nodes;
        this.depots = 0;
        this.x = new double[this.nodes + 1];
        this.y = new double[this.nodes + 1];
        this.score = new double[this.nodes + 1];
        this.readyTime = new double[this.nodes + 1];
        this.dueTime = new double[this.nodes + 1];
        this.serviceTime = new double[this.nodes + 1];
        this.distanceMatrix = new double[this.nodes + 1][this.nodes + 1];
        for (int i = 0; i < this.nodes + 1; i++) {
            for (int j = 0; j < this.nodes + 1; j++) {
                this.distanceMatrix[i][j] = 0.0;
            }
        }
        this.maxRoutes = routes;
        this.vehicles = routes;
    }

    /**
     * Verifica si un nodo dado es un depósito.
     *
     * @param a ID del nodo a verificar.
     * @return true si el nodo es un depósito; de lo contrario, false.
     */
    public boolean isDepot(int a) {
        return a > this.nodes;
    }

    /**
     * Calcula la distancia total de una ruta especificada.
     *
     * @param route Array de enteros que representa una ruta de nodos.
     * @return La distancia total de la ruta.
     */
    public double getDistance(int[] route) {
        double distance = 0.0;
        for (int i = 0; i < route.length - 1; i++) {
            int node1 = route[i];
            int node2 = route[i + 1];
            distance += this.getDistance(node1, node2);
        }
        return distance;
    }

    /**
     * Calcula la distancia total de una ruta especificada en forma de lista.
     *
     * @param route Lista de enteros que representa una ruta de nodos.
     * @return La distancia total de la ruta.
     */
    public double getDistance(ArrayList<Integer> route) {
        double distance = 0.0;
        for (int i = 0; i < route.size() - 1; i++) {
            int node1 = route.get(i);
            int node2 = route.get(i + 1);
            distance += this.getDistance(node1, node2);
        }
        return distance;
    }

    /**
     * Calcula la distancia total de un conjunto de rutas especificadas en listas.
     *
     * @param routes Array de listas, donde cada lista representa una ruta de nodos.
     * @return La distancia total de todas las rutas.
     */
    public double getDistance(ArrayList<Integer>[] routes) {
        double distance = 0.0;
        for (ArrayList<Integer> route : routes) {
            distance += this.getDistance(route);
        }
        return distance;
    }

    /**
     * Calcula la matriz de distancias entre los nodos en base a sus coordenadas.
     */
    public void calculateDistanceMatrix() {
        for (int i = 0; i < this.nodes + 1; i++) {
            for (int j = 0; j < this.nodes + 1; j++) {
                if (i != j) {
                    double diffXs = this.x[i] - this.x[j];
                    double diffYs = this.y[i] - this.y[j];
                    this.distanceMatrix[i][j] = Math.sqrt(diffXs * diffXs + diffYs * diffYs);
                    this.distanceMatrix[j][i] = this.distanceMatrix[i][j];
                } else {
                    this.distanceMatrix[i][j] = 0.0;
                }
            }
        }
    }

    // Métodos getter y setter para maxTimePerRoute, maxRoutes y vehicles

    /**
     * Retorna el tiempo máximo permitido por ruta.
     * @return Tiempo máximo por ruta.
     */
    public double getMaxTimePerRoute() {
        return maxTimePerRoute;
    }

    /**
     * Configura el tiempo máximo permitido por ruta.
     * @param maxTimePerRoute Tiempo máximo por ruta.
     */
    public void setMaxTimePerRoute(double maxTimePerRoute) {
        this.maxTimePerRoute = maxTimePerRoute;
    }

    /**
     * Retorna el número máximo de rutas permitidas.
     * @return Número máximo de rutas.
     */
    public double getMaxRoutes() {
        return maxRoutes;
    }

    /**
     * Configura el número máximo de rutas permitidas.
     * @param maxRoutes Número máximo de rutas.
     */
    public void setMaxRoutes(double maxRoutes) {
        this.maxRoutes = maxRoutes;
    }

    // Otros métodos como getPOIs(), getDistance(), getTime(), y getters y setters para coordenadas y tiempos...

    @Override
    public String toString() {
        final int COLUMN_WIDTH = 15;
        String text = "Nodes: " + this.nodes + "\n";
        String[] strings = new String[]{"CUST NO.", "XCOORD.", "YCOORD.", "SCORE", "READY TIME", "DUE DATE", "SERVICE TIME"};
        int[] width = new int[strings.length];
        Arrays.fill(width, COLUMN_WIDTH);
        text += ExpositoUtilities.getFormat(strings, width.length) + "\n";
        for (int i = 0; i < this.nodes; i++) {
            strings = new String[strings.length];
            int index = 0;
            strings[index++] = Integer.toString(i);
            strings[index++] = "" + this.x[i];
            strings[index++] = "" + this.y[i];
            strings[index++] = "" + this.score[i];
            strings[index++] = "" + this.readyTime[i];
            strings[index++] = "" + this.dueTime[i];
            strings[index++] = "" + this.serviceTime[i];
            text += ExpositoUtilities.getFormat(strings, width);
            text += "\n";
        }
        text += "Vehicles: " + this.vehicles + "\n";
        strings = new String[]{"VEHICLE", "CAPACITY"};
        width = new int[strings.length];
        Arrays.fill(width, COLUMN_WIDTH);
        text += ExpositoUtilities.getFormat(strings, width) + "\n";
        return text;
    }

    /**
     * Añade un nodo al problema incrementando el contador de nodos.
     * @return Número total de nodos.
     */
    public int addNode() {
        this.nodes++;
        return this.nodes;
    }

    /**
     * Añade un depósito al problema incrementando el contador de depósitos.
     * @return Número total de depósitos.
     */
    public int addNodeDepot() {
        this.depots++;
        return this.depots;
    }

    public int getVehicles() {
        return vehicles;
    }

    public int getPOIs() {
        return nodes;
    }

    public double[] getReadyTime() {
        return readyTime;
    }
}
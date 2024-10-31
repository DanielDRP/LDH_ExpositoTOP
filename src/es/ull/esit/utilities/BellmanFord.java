package es.ull.esit.utilities;

import java.util.ArrayList;

public class BellmanFord {

    /**
     * Constante que representa el valor de infinito utilizado en el algoritmo.
     */
    private static final int INFINITY = 999999;

    /**
     * Matriz de distancias que representa el grafo ponderado.
     */
    private final int[][] distanceMatrix;

    /**
     * Lista de nodos de origen para cada arista en el grafo.
     */
    private ArrayList<Integer> edges1 = null;

    /**
     * Lista de nodos de destino para cada arista en el grafo.
     */
    private ArrayList<Integer> edges2 = null;

    /**
     * Número de nodos en el grafo.
     */
    private final int nodes;

    /**
     * Lista que almacena el camino más corto desde el nodo inicial hasta el nodo final.
     */
    private final ArrayList<Integer> path;

    /**
     * Arreglo de distancias mínimas desde el nodo inicial hasta cada nodo.
     */
    private int[] distances = null;

    /**
     * Valor del camino más corto encontrado por el algoritmo.
     */
    private int value;

    /**
     * Constructor de la clase BellmanFord.
     *
     * @param distanceMatrix Matriz de distancias que representa el grafo.
     * @param nodes Número de nodos en el grafo.
     * @param path Lista para almacenar el camino más corto calculado.
     */
    public BellmanFord(int[][] distanceMatrix, int nodes, ArrayList<Integer> path) {
        this.distanceMatrix = distanceMatrix;
        this.nodes = nodes;
        this.path = path;
        this.calculateEdges();
        this.value = BellmanFord.INFINITY;
    }

    /**
     * Método privado que calcula las aristas del grafo basándose en la matriz de distancias.
     */
    private void calculateEdges() {
        this.edges1 = new ArrayList<>();
        this.edges2 = new ArrayList<>();
        for (int i = 0; i < this.nodes; i++) {
            for (int j = 0; j < this.nodes; j++) {
                if (this.distanceMatrix[i][j] != Integer.MAX_VALUE) {
                    this.edges1.add(i);
                    this.edges2.add(j);
                }
            }
        }
    }

    /**
     * Obtiene las distancias mínimas desde el nodo inicial hasta cada nodo.
     *
     * @return Arreglo de distancias mínimas.
     */
    public int[] getDistances() {
        return this.distances;
    }

    /**
     * Obtiene el valor del camino más corto calculado.
     *
     * @return Valor del camino más corto.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Método que ejecuta el algoritmo de Bellman-Ford para encontrar el camino más corto
     * desde el nodo inicial hasta el nodo final.
     */
    public void solve() {
        int numEdges = this.edges1.size();
        int[] predecessor = new int[this.nodes];
        this.distances = new int[this.nodes];
        for (int i = 0; i < this.nodes; i++) {
            this.distances[i] = BellmanFord.INFINITY;
            predecessor[i] = -1;
        }
        this.distances[0] = 0;
        for (int i = 0; i < (this.nodes - 1); i++) {
            for (int j = 0; j < numEdges; j++) {
                int u = this.edges1.get(j);
                int v = this.edges2.get(j);
                if (this.distances[v] > this.distances[u] + this.distanceMatrix[u][v]) {
                    this.distances[v] = this.distances[u] + this.distanceMatrix[u][v];
                    predecessor[v] = u;
                }
            }
        }
        this.path.add(this.nodes - 1);
        int pred = predecessor[this.nodes - 1];
        while (pred != -1) {
            this.path.add(pred);
            pred = predecessor[pred];
        }
        this.value = -this.distances[this.nodes - 1];
    }
}

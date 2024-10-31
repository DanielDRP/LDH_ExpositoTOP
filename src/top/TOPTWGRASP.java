package top;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Implementación del algoritmo GRASP para el problema Team Orienteering con Ventanas de Tiempo (TOPTW).
 * <p>
 * Este algoritmo aplica un enfoque de construcción aleatoria voraz, seguido de una búsqueda local,
 * para encontrar una solución viable a un problema de optimización.
 */
public class TOPTWGRASP {
    public static double NO_EVALUATED = -1.0; ///< Constante que indica que la solución no ha sido evaluada.

    private TOPTWSolution solution; ///< Solución del problema.
    private int solutionTime; ///< Tiempo requerido para la solución.

    /**
     * Constructor de la clase TOPTWGRASP.
     *
     * @param sol La solución inicial para aplicar GRASP.
     */
    public TOPTWGRASP(TOPTWSolution sol){
        this.solution = sol;
        this.solutionTime = 0;
    }

    /**
     * Método principal del algoritmo GRASP.
     * <p>
     * Ejecuta el proceso GRASP para un número de iteraciones determinado.
     * En cada iteración, construye una solución aleatoria voraz y luego aplica una búsqueda local.
     *
     * @param maxIterations Número máximo de iteraciones para GRASP.
     * @param maxSizeRCL Tamaño máximo de la Lista de Candidatos Restringida (RCL).
     */
    public void GRASP(int maxIterations, int maxSizeRCL) {
        double averageFitness = 0.0;
        double bestSolution = 0.0;
        for(int i = 0; i < maxIterations; i++) {

            this.computeGreedySolution(maxSizeRCL);

            // Evaluar y mostrar la solución generada en esta iteración
            double fitness = this.solution.evaluateFitness();
            System.out.println(this.solution.getInfoSolution());

            averageFitness += fitness;
            if(bestSolution < fitness) {
                bestSolution = fitness;
            }
        }
        averageFitness = averageFitness / maxIterations;
        System.out.println(" --> MEDIA: "+averageFitness);
        System.out.println(" --> MEJOR SOLUCION: "+bestSolution);
    }

    /**
     * Selección aleatoria de un elemento de la Lista de Candidatos Restringida (RCL).
     *
     * @param maxTRCL Tamaño máximo de la RCL.
     * @return La posición seleccionada de forma aleatoria.
     */
    public int aleatorySelectionRCL(int maxTRCL) {
        Random r = new Random();
        int low = 0;
        int high = maxTRCL;
        return r.nextInt(high - low) + low;
    }

    /**
     * Selección difusa del mejor candidato basado en la función de membresía y la función fuzzy de corte alpha.
     *
     * @param rcl Lista de candidatos restringida.
     * @return La posición seleccionada.
     */
    public int fuzzySelectionBestFDRCL(ArrayList<double[]> rcl) {
        double[] membershipFunction = new double[rcl.size()];
        double maxSc = this.getMaxScore();
        for(int j = 0; j < rcl.size(); j++) {
            membershipFunction[j] = 1 - ((rcl.get(j)[4]) / maxSc);
        }
        double minMemFunc = Double.MAX_VALUE;
        int posSelected = -1;
        for(int i = 0; i < rcl.size(); i++) {
            if(minMemFunc > membershipFunction[i]) {
                minMemFunc = membershipFunction[i];
                posSelected = i;
            }
        }
        return posSelected;
    }

    /**
     * Selección difusa con corte alpha en la Lista de Candidatos Restringida.
     *
     * @param rcl Lista de candidatos restringida.
     * @param alpha Valor de corte alpha para la selección.
     * @return La posición seleccionada.
     */
    public int fuzzySelectionAlphaCutRCL(ArrayList<double[]> rcl, double alpha) {
        ArrayList<double[]> rclAlphaCut = new ArrayList<>();
        ArrayList<Integer> rclPos = new ArrayList<>();
        double[] membershipFunction = new double[rcl.size()];
        double maxSc = this.getMaxScore();
        for(int j = 0; j < rcl.size(); j++) {
            membershipFunction[j] = 1 - ((rcl.get(j)[4]) / maxSc);
            if(membershipFunction[j] <= alpha) {
                rclAlphaCut.add(rcl.get(j));
                rclPos.add(j);
            }
        }
        int posSelected;
        if(!rclAlphaCut.isEmpty()) {
            posSelected = rclPos.get(aleatorySelectionRCL(rclAlphaCut.size()));
        } else {
            posSelected = aleatorySelectionRCL(rcl.size());
        }
        return posSelected;
    }

    /**
     * Computa la solución voraz a través de la Lista de Candidatos Restringida (RCL).
     * <p>
     * Utiliza una construcción aleatoria con selección fuzzy.
     *
     * @param maxSizeRCL Tamaño máximo de la RCL.
     */
    public void computeGreedySolution(int maxSizeRCL) {
        this.solution.initSolution();
        ArrayList<ArrayList<Double>> departureTimesPerClient = new ArrayList<>();
        ArrayList<Double> init = new ArrayList<>();
        for(int z = 0; z < this.solution.getProblem().getPOIs() + this.solution.getProblem().getVehicles(); z++) {
            init.add(0.0);
        }
        departureTimesPerClient.add(0, init);

        ArrayList<Integer> customers = new ArrayList<>();
        for(int j = 1; j <= this.solution.getProblem().getPOIs(); j++) {
            customers.add(j);
        }

        ArrayList<double[]> candidates = this.comprehensiveEvaluation(customers, departureTimesPerClient);

        // Ordenamiento de los candidatos según su coste incremental
        Collections.sort(candidates, new Comparator<double[]>() {
            public int compare(double[] a, double[] b) {
                return Double.compare(a[a.length - 2], b[b.length - 2]);
            }
        });

        int maxTRCL = maxSizeRCL;
        boolean existCandidates = true;

        while(!customers.isEmpty() && existCandidates) {
            if(!candidates.isEmpty()) {
                ArrayList<double[]> rcl = new ArrayList<>();
                maxTRCL = Math.min(maxSizeRCL, candidates.size());
                for(int j = 0; j < maxTRCL; j++) {
                    rcl.add(candidates.get(j));
                }

                int posSelected = this.fuzzySelectionAlphaCutRCL(rcl, 0.8);

                double[] candidateSelected = rcl.get(posSelected);
                customers.removeIf(customer -> customer == candidateSelected[0]);

                updateSolution(candidateSelected, departureTimesPerClient);
            } else if(this.solution.getCreatedRoutes() < this.solution.getProblem().getVehicles()) {
                int newDepot = this.solution.addRoute();
                ArrayList<Double> initNew = new ArrayList<>();
                for(int z = 0; z < this.solution.getProblem().getPOIs() + this.solution.getProblem().getVehicles(); z++) {
                    initNew.add(0.0);
                }
                departureTimesPerClient.add(initNew);
            } else {
                existCandidates = false;
            }
            candidates.clear();
            candidates = this.comprehensiveEvaluation(customers, departureTimesPerClient);
            Collections.sort(candidates, new Comparator<double[]>() {
                public int compare(double[] a, double[] b) {
                    return Double.compare(a[a.length - 2], b[b.length - 2]);
                }
            });
        }
    }

    /**
     * Actualiza la solución con el candidato seleccionado.
     *
     * @param candidateSelected Candidato seleccionado.
     * @param departureTimes Tiempos de salida por cliente y ruta.
     */
    public void updateSolution(double[] candidateSelected, ArrayList<ArrayList<Double>> departureTimes) {
        this.solution.setPredecessor((int)candidateSelected[0], (int)candidateSelected[2]);
        this.solution.setSuccessor((int)candidateSelected[0], this.solution.getSuccessor((int)candidateSelected[2]));
        this.solution.setSuccessor((int)candidateSelected[2], (int)candidateSelected[0]);
        this.solution.setPredecessor(this.solution.getSuccessor((int)candidateSelected[0]), (int)candidateSelected[0]);

        double costInsertionPre = departureTimes.get((int)candidateSelected[1]).get((int)candidateSelected[2]);
        ArrayList<Double> route = departureTimes.get((int)candidateSelected[1]);
        int pre = (int)candidateSelected[2], suc;
        int depot = this.solution.getIndexRoute((int)candidateSelected[1]);

        do {
            suc = this.solution.getSuccessor(pre);
            costInsertionPre += this.solution.getDistance(pre, suc);

            if(costInsertionPre < this.solution.getProblem().getReadyTime(suc)) {
                costInsertionPre = this.solution.getProblem().getReadyTime(suc);
            }
            costInsertionPre += this.solution.getProblem().getServiceTime(suc);

            if(!this.solution.isDepot(suc)) {
                route.set(suc, costInsertionPre);
            }
            pre = suc;
        } while(suc != depot);

        departureTimes.set((int)candidateSelected[1], route);
    }

    /**
     * Evalúa exhaustivamente los clientes y genera una lista de candidatos.
     *
     * @param customers Lista de clientes.
     * @param departureTimes Tiempos de salida por cliente y ruta.
     * @return Lista de candidatos.
     */
    public ArrayList<double[]> comprehensiveEvaluation(ArrayList<Integer> customers, ArrayList<ArrayList<Double>> departureTimes) {
        ArrayList<double[]> candidates = new ArrayList<>();
        for(int i = 0; i < customers.size(); i++) {
            int customer = customers.get(i);
            ArrayList<int[]> feasiblePositions = this.solution.getFeasiblePositions(customer, departureTimes);
            if(!feasiblePositions.isEmpty()) {
                for(int j = 0; j < feasiblePositions.size(); j++) {
                    int[] position = feasiblePositions.get(j);
                    double incrementalCost = this.solution.evaluateIncrementalCost(customer, position[0], position[1], departureTimes);
                    double candidate[] = {customer, position[0], position[1], position[2], incrementalCost};
                    candidates.add(candidate);
                }
            }
        }
        return candidates;
    }

    /**
     * Obtiene el puntaje máximo de la solución.
     *
     * @return Puntaje máximo.
     */
    public double getMaxScore() {
        return this.solution.getProblem().getMaxScore();
    }
}

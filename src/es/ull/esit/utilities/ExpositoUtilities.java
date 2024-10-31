package es.ull.esit.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExpositoUtilities {

    /** Ancho de columna predeterminado para formateo de texto. */
    public static final int DEFAULT_COLUMN_WIDTH = 10;

    /** Constante para alinear texto a la izquierda. */
    public static final int ALIGNMENT_LEFT = 1;

    /** Constante para alinear texto a la derecha. */
    public static final int ALIGNMENT_RIGHT = 2;

    /**
     * Encuentra la primera aparición de un elemento en un vector.
     *
     * @param vector Vector en el que buscar.
     * @param element Elemento a buscar.
     * @return Índice de la primera aparición del elemento o -1 si no se encuentra.
     */
    private static int getFirstAppearance(int[] vector, int element) {
        for (int i = 0; i < vector.length; i++) {
            if (vector[i] == element) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Imprime el contenido de un archivo en la consola.
     *
     * @param file Ruta del archivo a leer.
     */
    public static void printFile(String file) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception ex) {
            Logger.getLogger(ExpositoUtilities.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(ExpositoUtilities.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Simplifica una cadena eliminando tabulaciones y espacios dobles.
     *
     * @param string Cadena a simplificar.
     * @return Cadena simplificada.
     */
    public static String simplifyString(String string) {
        string = string.replaceAll("\t", " ");
        for (int i = 0; i < 50; i++) {
            string = string.replaceAll("  ", " ");
        }
        string = string.trim();
        return string;
    }

    /**
     * Multiplica dos matrices de tipo double.
     *
     * @param a Primera matriz.
     * @param b Segunda matriz.
     * @return Matriz resultado de la multiplicación, o null si no son compatibles.
     */
    public static double[][] multiplyMatrices(double a[][], double b[][]) {
        if (a.length == 0) {
            return new double[0][0];
        }
        if (a[0].length != b.length) {
            return null;
        }
        int n = a[0].length;
        int m = a.length;
        int p = b[0].length;
        double ans[][] = new double[m][p];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < p; j++) {
                for (int k = 0; k < n; k++) {
                    ans[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return ans;
    }

    /**
     * Escribe texto en un archivo.
     *
     * @param file Ruta del archivo.
     * @param text Texto a escribir.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    public static void writeTextToFile(String file, String text) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(text);
        writer.flush();
        writer.close();
    }

    /**
     * Aplica formato a una cadena, detectando si es un número para formatearlo en consecuencia.
     *
     * @param string Cadena a formatear.
     * @return Cadena formateada.
     */
    public static String getFormat(String string) {
        if (!ExpositoUtilities.isInteger(string)) {
            if (ExpositoUtilities.isDouble(string)) {
                double value = Double.parseDouble(string);
                string = ExpositoUtilities.getFormat(value);
            }
        }
        return string;
    }

    /**
     * Aplica formato a un número double con tres decimales.
     *
     * @param value Número a formatear.
     * @return Cadena formateada.
     */
    public static String getFormat(double value) {
        DecimalFormat decimalFormatter = new DecimalFormat("0.000");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        decimalFormatter.setDecimalFormatSymbols(symbols);
        return decimalFormatter.format(value);
    }

    /**
     * Aplica formato a un número double con un número específico de ceros decimales.
     *
     * @param value Número a formatear.
     * @param zeros Número de ceros decimales.
     * @return Cadena formateada.
     */
    public static String getFormat(double value, int zeros) {
        String format = "0.";
        for (int i = 0; i < zeros; i++) {
            format += "0";
        }
        DecimalFormat decimalFormatter = new DecimalFormat(format);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        decimalFormatter.setDecimalFormatSymbols(symbols);
        return decimalFormatter.format(value);
    }

    // (Otros métodos similares)

    /**
     * Verifica si una cadena representa un entero.
     *
     * @param str Cadena a verificar.
     * @return true si la cadena es un entero, false en caso contrario.
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * Verifica si una cadena representa un número decimal (double).
     *
     * @param str Cadena a verificar.
     * @return true si la cadena es un double, false en caso contrario.
     */
    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * Determina si una matriz de distancias representa un grafo acíclico.
     *
     * @param distanceMatrix Matriz de distancias.
     * @return true si el grafo es acíclico, false en caso contrario.
     */
    public static boolean isAcyclic(int[][] distanceMatrix) {
        int numRealTasks = distanceMatrix.length - 2;
        int node = 1;
        boolean acyclic = true;
        while (acyclic && node <= numRealTasks) {
            if (ExpositoUtilities.thereIsPath(distanceMatrix, node)) {
                return false;
            }
            node++;
        }
        return true;
    }

    /**
     * Verifica si existe un camino desde el nodo dado a sí mismo, en una matriz de distancias.
     *
     * @param distanceMatrix Matriz de distancias.
     * @param node Nodo a verificar.
     * @return true si existe un camino cíclico, false en caso contrario.
     */
    public static boolean thereIsPath(int[][] distanceMatrix, int node) {
        HashSet<Integer> visits = new HashSet<>();
        HashSet<Integer> noVisits = new HashSet<>();
        for (int i = 0; i < distanceMatrix.length; i++) {
            if (i != node) {
                noVisits.add(i);
            }
        }
        visits.add(node);
        while (!visits.isEmpty()) {
            Iterator<Integer> it = visits.iterator();
            int toCheck = it.next();
            visits.remove(toCheck);
            for (int i = 0; i < distanceMatrix.length; i++) {
                if (toCheck != i && distanceMatrix[toCheck][i] != Integer.MAX_VALUE) {
                    if (i == node) {
                        return true;
                    }
                    if (noVisits.contains(i)) {
                        noVisits.remove(i);
                        visits.add(i);
                    }
                }
            }
        }
        return false;
    }
}

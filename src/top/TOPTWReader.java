package top;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import es.ull.esit.utilities.ExpositoUtilities;

/**
 * Clase para leer y cargar problemas de la clase TOPTW (Tiempo de Espera y Rutas de Transporte).
 *
 * Esta clase proporciona métodos para leer archivos que contienen la definición de un problema
 * TOPTW y crear una instancia de la clase TOPTW a partir de los datos leídos.
 */
public class TOPTWReader {

    /**
     * Lee un problema TOPTW desde un archivo especificado por su ruta.
     *
     * Este método procesa el archivo de entrada, lee las coordenadas de los puntos de interés
     * (POIs), los tiempos de servicio, las puntuaciones, los tiempos de disponibilidad y de
     * vencimiento, y configura una instancia de la clase TOPTW con esta información.
     *
     * @param filePath La ruta del archivo que contiene la descripción del problema TOPTW.
     * @return Una instancia de la clase TOPTW configurada con los datos leídos del archivo.
     */
    public static TOPTW readProblem(String filePath) {
        TOPTW problem = null; // Instancia del problema a retornar.
        BufferedReader reader = null; // BufferedReader para leer el archivo.

        try {
            // Crea un objeto File con la ruta del archivo.
            File instaceFile = new File(filePath);
            reader = new BufferedReader(new FileReader(instaceFile)); // Inicializa el BufferedReader.
            String line = reader.readLine(); // Lee la primera línea del archivo.
            line = ExpositoUtilities.simplifyString(line); // Simplifica la línea para eliminar espacios innecesarios.
            String[] parts = line.split(" "); // Divide la línea en partes utilizando espacios como delimitador.

            // Crea una nueva instancia de TOPTW con los parámetros leídos del archivo.
            problem = new TOPTW(Integer.parseInt(parts[2]), Integer.parseInt(parts[1]));

            line = reader.readLine(); // Lee la siguiente línea.
            line = null; // Limpia la referencia de línea.
            parts = null; // Limpia la referencia de partes.

            // Lee las coordenadas, tiempos de servicio, y puntuaciones de cada POI.
            for (int i = 0; i < problem.getPOIs() + 1; i++) {
                line = reader.readLine(); // Lee una línea para un POI.
                line = ExpositoUtilities.simplifyString(line); // Simplifica la línea.
                parts = line.split(" "); // Divide la línea en partes.

                // Asigna valores leídos a la instancia de TOPTW.
                problem.setX(i, Double.parseDouble(parts[1])); // Establece la coordenada X.
                problem.setY(i, Double.parseDouble(parts[2])); // Establece la coordenada Y.
                problem.setServiceTime(i, Double.parseDouble(parts[3])); // Establece el tiempo de servicio.
                problem.setScore(i, Double.parseDouble(parts[4])); // Establece la puntuación.

                // Asigna tiempos de disponibilidad y vencimiento, diferenciando el depósito.
                if (i == 0) {
                    problem.setReadyTime(i, Double.parseDouble(parts[7])); // Tiempo listo del depósito.
                    problem.setDueTime(i, Double.parseDouble(parts[8])); // Tiempo de vencimiento del depósito.
                } else {
                    problem.setReadyTime(i, Double.parseDouble(parts[8])); // Tiempo listo de los POIs.
                    problem.setDueTime(i, Double.parseDouble(parts[9])); // Tiempo de vencimiento de los POIs.
                }

                line = null; // Limpia la referencia de línea.
                parts = null; // Limpia la referencia de partes.
            }

            // Calcula la matriz de distancias entre los POIs.
            problem.calculateDistanceMatrix();
        } catch (IOException e) {
            System.err.println(e); // Imprime el error si ocurre una excepción de entrada/salida.
            System.exit(0); // Termina el programa en caso de error.
        } finally {
            // Asegura que el BufferedReader se cierra correctamente.
            if (reader != null) {
                try {
                    reader.close(); // Cierra el BufferedReader.
                } catch (IOException ex) {
                    System.err.println(ex); // Imprime el error si no se puede cerrar el BufferedReader.
                    System.exit(0); // Termina el programa en caso de error.
                }
            }
        }

        // Establece el tiempo máximo por ruta basado en el tiempo de vencimiento del depósito.
        problem.setMaxTimePerRoute(problem.getDueTime(0));
        return problem; // Devuelve la instancia del problema TOPTW configurada.
    }

}

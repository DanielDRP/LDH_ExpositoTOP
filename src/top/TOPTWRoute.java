package top;

/**
 * Clase para representar una ruta en un problema de Team Orienteering con Ventanas de Tiempo (TOPTW).
 */
public class TOPTWRoute {
    int predecessor;
    int succesor;
    int id;
    
    TOPTWRoute() {
        
    }

    /**
     * Constructor de la clase TOPTWRoute.
     * @param pre
     * @param succ
     * @param id
     */
    TOPTWRoute(int pre, int succ, int id) {
        this.predecessor = pre;
        this.succesor = succ;
        this.id = id;
    }

    /**
     * Método para obtener el predecesor de la ruta.
     * @return
     */
    public int getPredeccesor() {
        return this.predecessor;
    }

    /**
     * Método para obtener el sucesor de la ruta.
     * @return
     */
    public int getSuccesor() {
        return this.succesor;
    }

    /**
     * Método para obtener el id de la ruta.
     * @return
     */
    public int getId() {
        return this.id;
    }

    /**
     * Método para establecer el predecesor de la ruta.
     * @param pre
     */
    public void setPredeccesor(int pre) {
        this.predecessor = pre;
    }

    /**
     * Método para establecer el sucesor de la ruta.
     * @param suc
     */
    public void setSuccesor(int suc) {
        this.succesor = suc;
    }

    /**
     * Método para establecer el id de la ruta.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
}

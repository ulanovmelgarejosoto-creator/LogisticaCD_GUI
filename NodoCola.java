package logistica;

/**
 * Nodo de la lista enlazada que implementa la cola de camiones.
 * Contiene un Camion y la referencia al siguiente nodo.
 *
 * @author Melgarejo Soto Gian Pool
 */
public class NodoCola {

    Camion camion;       // Dato almacenado en el nodo
    NodoCola siguiente;  // Referencia al siguiente nodo de la cola

    /**
     * Crea un nodo sin siguiente.
     * @param camion camión a almacenar
     */
    public NodoCola(Camion camion) {
        this.camion = camion;
        this.siguiente = null;
    }
}

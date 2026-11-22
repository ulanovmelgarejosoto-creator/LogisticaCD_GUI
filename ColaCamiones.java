package logistica;

/**
 * Cola FIFO de camiones implementada con nodos enlazados manualmente
 * (no usa la clase LinkedList de Java).
 * El primer camión en llegar es el primero en ser asignado a una entrega.
 *
 * @author Melgarejo Soto Gian Pool
 */
public class ColaCamiones {

    private NodoCola frente; // Primer nodo (sale primero)
    private NodoCola fin;    // Último nodo (entra último)

    public ColaCamiones() {
        this.frente = null;
        this.fin = null;
    }

    /**
     * Agrega un camión al final de la cola.
     * @param c camión a encolar
     */
    public void encolar(Camion c) {
        NodoCola nuevo = new NodoCola(c);
        if (estaVacia()) {
            frente = nuevo;       // Primer elemento: frente y fin apuntan al mismo nodo
        } else {
            fin.siguiente = nuevo; // Enlaza al final
        }
        fin = nuevo;
    }

    /**
     * Retira y devuelve el camión del frente de la cola.
     * @return el camión del frente, o null si la cola está vacía
     */
    public Camion desencolar() {
        if (estaVacia()) {
            return null;
        }
        Camion c = frente.camion;
        frente = frente.siguiente;
        if (frente == null) {
            fin = null; // La cola quedó vacía: se reinicia también el fin
        }
        return c;
    }

    /**
     * Indica si la cola no tiene camiones.
     * @return true si está vacía
     */
    public boolean estaVacia() {
        return frente == null;
    }

    /**
     * Construye el texto con los camiones de la cola, del frente al fin.
     * @return texto listo para mostrar en el formulario
     */
    public String mostrar() {
        if (estaVacia()) {
            return "La cola de camiones está vacía.";
        }
        StringBuilder sb = new StringBuilder();
        NodoCola actual = frente;
        int posicion = 1;
        while (actual != null) {
            sb.append(posicion).append(". ").append(actual.camion);
            if (actual == frente) {
                sb.append("  <-- FRENTE");
            }
            if (actual == fin) {
                sb.append("  <-- FIN");
            }
            sb.append("\n");
            actual = actual.siguiente;
            posicion++;
        }
        return sb.toString();
    }
}

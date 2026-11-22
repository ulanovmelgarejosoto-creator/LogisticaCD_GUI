package logistica;

/**
 * Nodo del árbol binario de búsqueda (ABB) del inventario.
 * Contiene un Producto y las referencias a sus hijos izquierdo y derecho.
 *
 * @author Melgarejo Soto Gian Pool
 */
public class NodoArbol {

    Producto producto;     // Dato almacenado en el nodo
    NodoArbol izquierdo;   // Hijo con código menor
    NodoArbol derecho;     // Hijo con código mayor

    /**
     * Crea un nodo hoja con el producto indicado.
     * @param producto producto a almacenar
     */
    public NodoArbol(Producto producto) {
        this.producto = producto;
        this.izquierdo = null;
        this.derecho = null;
    }
}

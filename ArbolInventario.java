package logistica;

import java.util.List;

/**
 * Árbol binario de búsqueda (ABB) que gestiona el inventario de productos.
 * Los productos se ordenan por su código: menores a la izquierda, mayores a la derecha.
 * Se construye manualmente con nodos (NodoArbol); no se usa ninguna colección de Java
 * para almacenar el inventario.
 *
 * @author Melgarejo Soto Gian Pool
 */
public class ArbolInventario {

    private NodoArbol raiz; // Raíz del árbol (null si el inventario está vacío)

    public ArbolInventario() {
        this.raiz = null;
    }

    /**
     * Inserta un producto respetando el orden por código.
     * @param p producto a insertar
     * @return true si se insertó; false si ya existe un producto con ese código
     */
    public boolean insertar(Producto p) {
        if (buscar(p.getCodigo()) != null) {
            return false; // Código duplicado
        }
        raiz = insertarRec(raiz, p);
        return true;
    }

    // Inserción recursiva: devuelve la raíz del subárbol actualizado
    private NodoArbol insertarRec(NodoArbol actual, Producto p) {
        if (actual == null) {
            return new NodoArbol(p);
        }
        if (p.getCodigo() < actual.producto.getCodigo()) {
            actual.izquierdo = insertarRec(actual.izquierdo, p);
        } else {
            actual.derecho = insertarRec(actual.derecho, p);
        }
        return actual;
    }

    /**
     * Busca un producto por su código. Recorre un solo camino desde la raíz: O(h).
     * @param codigo código a buscar
     * @return el producto, o null si no existe
     */
    public Producto buscar(int codigo) {
        NodoArbol actual = raiz;
        while (actual != null) {
            if (codigo == actual.producto.getCodigo()) {
                return actual.producto;
            }
            actual = (codigo < actual.producto.getCodigo()) ? actual.izquierdo : actual.derecho;
        }
        return null;
    }

    /**
     * Recorrido inOrden (izquierdo, raíz, derecho): agrega los productos a la lista
     * ordenados por código ascendente.
     * @param lista lista de salida donde se agregan los productos
     */
    public void inOrden(List<Producto> lista) {
        inOrdenRec(raiz, lista);
    }

    private void inOrdenRec(NodoArbol nodo, List<Producto> lista) {
        if (nodo != null) {
            inOrdenRec(nodo.izquierdo, lista);
            lista.add(nodo.producto);
            inOrdenRec(nodo.derecho, lista);
        }
    }

    /**
     * Calcula la altura del árbol (cantidad de niveles). Árbol vacío = 0.
     * @return altura del árbol
     */
    public int altura() {
        return alturaRec(raiz);
    }

    private int alturaRec(NodoArbol nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + Math.max(alturaRec(nodo.izquierdo), alturaRec(nodo.derecho));
    }

    /**
     * Descuenta stock de un producto.
     * @param codigo   código del producto
     * @param cantidad unidades a descontar (debe ser mayor que 0)
     * @return true si se descontó; false si el producto no existe, la cantidad no es
     *         válida o el stock es insuficiente
     */
    public boolean descontarStock(int codigo, int cantidad) {
        Producto p = buscar(codigo);
        if (p == null || cantidad <= 0 || p.getStock() < cantidad) {
            return false;
        }
        p.setStock(p.getStock() - cantidad);
        return true;
    }
}

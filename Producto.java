package logistica;

/**
 * Objeto de dominio: producto del inventario del Centro de Distribución.
 * Se almacena dentro de los nodos del árbol binario de búsqueda (NodoArbol).
 *
 * @author Melgarejo Soto Gian Pool
 */
public class Producto {

    private int codigo;      // Identificador único (clave de ordenamiento del ABB)
    private String nombre;   // Nombre descriptivo del producto
    private int stock;       // Unidades disponibles en el inventario

    /**
     * Crea un producto.
     * @param codigo código único del producto
     * @param nombre nombre del producto
     * @param stock  unidades disponibles
     */
    public Producto(int codigo, String nombre, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.stock = stock;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Código: " + codigo + " | Nombre: " + nombre + " | Stock: " + stock;
    }
}

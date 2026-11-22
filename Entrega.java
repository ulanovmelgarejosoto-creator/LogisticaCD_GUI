package logistica;

/**
 * Objeto de dominio: entrega registrada en el historial de despachos.
 * Se almacena en el ArrayList del historial de la clase principal.
 *
 * @author Melgarejo Soto Gian Pool
 */
public class Entrega {

    private final int id;           // Número correlativo de la entrega
    private final String producto;  // Nombre del producto entregado
    private final String camion;    // Placa del camión asignado
    private final String destino;   // Ciudad de destino
    private final int cantidad;     // Unidades entregadas

    /**
     * Crea una entrega.
     * @param id       número correlativo
     * @param producto nombre del producto
     * @param camion   placa del camión asignado
     * @param destino  ciudad destino
     * @param cantidad unidades entregadas
     */
    public Entrega(int id, String producto, String camion, String destino, int cantidad) {
        this.id = id;
        this.producto = producto;
        this.camion = camion;
        this.destino = destino;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public String getProducto() {
        return producto;
    }

    public String getCamion() {
        return camion;
    }

    public String getDestino() {
        return destino;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return "Entrega #" + id + " | Producto: " + producto + " | Camión: " + camion
                + " | Destino: " + destino + " | Cantidad: " + cantidad;
    }
}

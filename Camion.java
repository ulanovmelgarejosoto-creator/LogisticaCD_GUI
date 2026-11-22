package logistica;

/**
 * Objeto de dominio: camión de reparto.
 * Se almacena dentro de los nodos de la cola (NodoCola).
 *
 * @author Melgarejo Soto Gian Pool
 */
public class Camion {

    private final String placa;        // Placa del vehículo
    private final String conductor;    // Nombre del conductor asignado
    private final double capacidadTon; // Capacidad de carga en toneladas

    /**
     * Crea un camión.
     * @param placa        placa del vehículo
     * @param conductor    nombre del conductor
     * @param capacidadTon capacidad de carga en toneladas
     */
    public Camion(String placa, String conductor, double capacidadTon) {
        this.placa = placa;
        this.conductor = conductor;
        this.capacidadTon = capacidadTon;
    }

    public String getPlaca() {
        return placa;
    }

    public String getConductor() {
        return conductor;
    }

    public double getCapacidadTon() {
        return capacidadTon;
    }

    @Override
    public String toString() {
        return "Placa: " + placa + " | Conductor: " + conductor
                + " | Capacidad: " + capacidadTon + " ton";
    }
}

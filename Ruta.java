package logistica;

/**
 * Objeto de dominio: ruta (arista) de la red de entregas.
 * Representa un tramo hacia una ciudad destino con su distancia en kilómetros.
 * Se almacena en las listas de adyacencia del grafo (GrafoRutas).
 *
 * @author Melgarejo Soto Gian Pool
 */
public class Ruta {

    private final String destino;   // Ciudad destino del tramo
    private final int distanciaKm;  // Distancia del tramo en kilómetros

    /**
     * Crea una ruta.
     * @param destino     ciudad destino
     * @param distanciaKm distancia en kilómetros
     */
    public Ruta(String destino, int distanciaKm) {
        this.destino = destino;
        this.distanciaKm = distanciaKm;
    }

    public String getDestino() {
        return destino;
    }

    public int getDistanciaKm() {
        return distanciaKm;
    }

    @Override
    public String toString() {
        return destino + " (" + distanciaKm + " km)";
    }
}

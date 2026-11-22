package logistica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Grafo no dirigido y ponderado que representa la red de rutas de entrega.
 * Se implementa con lista de adyacencia: HashMap&lt;String, LinkedList&lt;Ruta&gt;&gt;,
 * donde la clave es una ciudad y el valor es la lista de rutas que salen de ella.
 *
 * @author Melgarejo Soto Gian Pool
 */
public class GrafoRutas {

    // Lista de adyacencia: ciudad -> rutas hacia ciudades vecinas
    private final HashMap<String, LinkedList<Ruta>> adyacencia;

    public GrafoRutas() {
        this.adyacencia = new HashMap<>();
    }

    // Devuelve el nombre registrado de la ciudad (sin distinguir mayúsculas), o null si no existe
    private String nombreRegistrado(String ciudad) {
        if (ciudad == null) {
            return null;
        }
        for (String registrada : adyacencia.keySet()) {
            if (registrada.equalsIgnoreCase(ciudad.trim())) {
                return registrada;
            }
        }
        return null;
    }

    /**
     * Verifica si una ciudad existe en el grafo.
     * @param ciudad nombre de la ciudad
     * @return true si existe
     */
    public boolean existeCiudad(String ciudad) {
        return nombreRegistrado(ciudad) != null;
    }

    /**
     * Agrega una ciudad (vértice) sin rutas.
     * @param ciudad nombre de la ciudad
     * @return true si se agregó; false si el nombre está vacío o ya existe
     */
    public boolean agregarCiudad(String ciudad) {
        if (ciudad == null || ciudad.trim().isEmpty() || existeCiudad(ciudad)) {
            return false;
        }
        adyacencia.put(ciudad.trim(), new LinkedList<Ruta>());
        return true;
    }

    /**
     * Agrega una ruta bidireccional entre dos ciudades existentes.
     * @param origen  ciudad de origen
     * @param destino ciudad de destino
     * @param km      distancia en kilómetros (mayor que 0)
     * @return true si se agregó; false si alguna ciudad no existe, son la misma,
     *         la distancia no es válida o la ruta ya existe
     */
    public boolean agregarRuta(String origen, String destino, int km) {
        String o = nombreRegistrado(origen);
        String d = nombreRegistrado(destino);
        if (o == null || d == null || o.equals(d) || km <= 0) {
            return false;
        }
        for (Ruta r : adyacencia.get(o)) {
            if (r.getDestino().equals(d)) {
                return false; // La ruta ya existe
            }
        }
        adyacencia.get(o).add(new Ruta(d, km));
        adyacencia.get(d).add(new Ruta(o, km));
        return true;
    }

    /**
     * Construye el texto con la lista de adyacencia completa.
     * @return texto listo para mostrar en el formulario
     */
    public String mostrarRutas() {
        if (adyacencia.isEmpty()) {
            return "No hay ciudades registradas en la red de rutas.";
        }
        StringBuilder sb = new StringBuilder();
        List<String> ciudades = new ArrayList<>(adyacencia.keySet());
        java.util.Collections.sort(ciudades); // Orden alfabético para una salida estable
        for (String ciudad : ciudades) {
            sb.append(ciudad).append(" -> ");
            LinkedList<Ruta> rutas = adyacencia.get(ciudad);
            if (rutas.isEmpty()) {
                sb.append("(sin rutas)");
            } else {
                boolean primera = true;
                for (Ruta r : rutas) {
                    if (!primera) {
                        sb.append(", ");
                    }
                    sb.append(r);
                    primera = false;
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Recorrido en anchura (BFS) desde una ciudad.
     * También sirve para verificar que una ciudad existe: si no existe, devuelve lista vacía.
     * @param inicio ciudad de inicio
     * @return ciudades visitadas en orden BFS; lista vacía si la ciudad no existe.
     *         El primer elemento es el nombre registrado de la ciudad de inicio.
     */
    public List<String> bfs(String inicio) {
        List<String> recorrido = new ArrayList<>();
        String origen = nombreRegistrado(inicio);
        if (origen == null) {
            return recorrido;
        }
        Set<String> visitadas = new HashSet<>();
        LinkedList<String> pendientes = new LinkedList<>(); // Cola auxiliar del BFS
        visitadas.add(origen);
        pendientes.add(origen);
        while (!pendientes.isEmpty()) {
            String actual = pendientes.removeFirst();
            recorrido.add(actual);
            for (Ruta r : adyacencia.get(actual)) {
                if (!visitadas.contains(r.getDestino())) {
                    visitadas.add(r.getDestino());
                    pendientes.add(r.getDestino());
                }
            }
        }
        return recorrido;
    }

    /**
     * Suma la distancia de un camino dado como lista de ciudades consecutivas.
     * @param camino ciudades del camino, en orden
     * @return distancia total en km; -1 si el camino es inválido
     *         (menos de 2 ciudades o dos ciudades consecutivas sin ruta directa)
     */
    public int distanciaRuta(List<String> camino) {
        if (camino == null || camino.size() < 2) {
            return -1;
        }
        int total = 0;
        for (int i = 0; i < camino.size() - 1; i++) {
            String a = nombreRegistrado(camino.get(i));
            String b = nombreRegistrado(camino.get(i + 1));
            if (a == null || b == null) {
                return -1;
            }
            int tramo = -1;
            for (Ruta r : adyacencia.get(a)) {
                if (r.getDestino().equals(b)) {
                    tramo = r.getDistanciaKm();
                    break;
                }
            }
            if (tramo < 0) {
                return -1;
            }
            total += tramo;
        }
        return total;
    }
}

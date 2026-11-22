# LogisticaCD_GUI

Sistema de Gestión Logística con interfaz gráfica en Java (NetBeans) — Práctica Calificada N° 3 de Algoritmo y Estructura de Datos (UTP).

**Autor:** Melgarejo Soto Gian Pool — Código U24259842
**Docente:** Ing. Hernán Peña Carnero

## Estructuras de datos

| Estructura | Clases | Uso |
| --- | --- | --- |
| Árbol binario de búsqueda | `Producto`, `NodoArbol`, `ArbolInventario` | Inventario ordenado por código |
| Lista enlazada (cola FIFO) | `Camion`, `NodoCola`, `ColaCamiones` | Cola de camiones, nodos manuales |
| Grafo (lista de adyacencia) | `Ruta`, `GrafoRutas` | Red de rutas de entrega, BFS |
| ArrayList | `Entrega` (en `Main`) | Historial cronológico de entregas |

`Main.java` contiene la ventana (JFrame con pestañas) y la lógica de interacción.

## Cómo ejecutar

1. Abrir el proyecto en NetBeans (File > Open Project) y ejecutar `logistica.Main`.
2. O desde terminal (requiere JDK 8 o superior):

```
javac -encoding UTF-8 -d build/classes src/logistica/*.java
java -cp build/classes logistica.Main
```

## Opción 9: Registrar entrega

Integra las cuatro estructuras en orden: ABB `buscar` → ABB `descontarStock` → Grafo `bfs` → Cola `desencolar` → ArrayList `add`.
Muestra un `JOptionPane` de error si el producto no existe, el stock es insuficiente, el destino no existe en el grafo o no hay camiones en la cola.

package logistica;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Sistema de Gestión Logística del Centro de Distribución (CD).
 * Ventana principal (JFrame) con pestañas para cada estructura de datos:
 * inventario (ABB), cola de camiones (lista enlazada), rutas (grafo) y entregas (ArrayList).
 * Esta clase solo contiene los componentes gráficos y la lógica de interacción;
 * la lógica de las estructuras está en sus propias clases.
 *
 * @author Melgarejo Soto Gian Pool
 */
public class Main extends JFrame {

    // ---------- Estructuras de datos ----------
    private final ArbolInventario inventario = new ArbolInventario();   // ABB de productos
    private final ColaCamiones colaCamiones = new ColaCamiones();       // Cola FIFO de camiones
    private final GrafoRutas grafo = new GrafoRutas();                  // Red de rutas
    private final ArrayList<Entrega> historial = new ArrayList<>();     // Historial cronológico
    private int siguienteIdEntrega = 1;                                 // Correlativo de entregas

    // ---------- Componentes gráficos ----------
    private JTabbedPane pestanas;
    private JTextArea areaResultados;

    // Pestaña Inventario
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtStock;

    // Pestaña Camiones
    private JTextField txtPlaca;
    private JTextField txtConductor;
    private JTextField txtCapacidad;

    // Pestaña Rutas
    private JTextField txtCiudad;
    private JTextField txtOrigen;
    private JTextField txtDestinoRuta;
    private JTextField txtKm;

    // Pestaña Entregas
    private JTextField txtEntCodigo;
    private JTextField txtEntCantidad;
    private JTextField txtEntDestino;

    /** Construye la ventana principal. */
    public Main() {
        super("Sistema de Gestión Logística - Centro de Distribución");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        construirInterfaz();
        setSize(900, 680);
        setMinimumSize(new Dimension(820, 600));
        setLocationRelativeTo(null);
    }

    // =====================================================================
    //  CONSTRUCCIÓN DE LA INTERFAZ
    // =====================================================================

    private void construirInterfaz() {
        // Menú superior con la opción 11: Salir
        JMenuBar barra = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.setName("menuSalir");
        itemSalir.addActionListener(e -> salir());
        menuArchivo.add(itemSalir);
        barra.add(menuArchivo);
        setJMenuBar(barra);

        // Pestañas: una por estructura de datos
        pestanas = new JTabbedPane();
        pestanas.setName("pestanas");
        pestanas.addTab("Inventario (ABB)", crearPanelInventario());
        pestanas.addTab("Camiones (Cola)", crearPanelCamiones());
        pestanas.addTab("Rutas (Grafo)", crearPanelRutas());
        pestanas.addTab("Entregas", crearPanelEntregas());

        // Área de resultados compartida por todas las opciones
        areaResultados = new JTextArea(14, 60);
        areaResultados.setName("areaResultados");
        areaResultados.setEditable(false);
        areaResultados.setLineWrap(true);
        areaResultados.setWrapStyleWord(true);
        areaResultados.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        areaResultados.setMargin(new Insets(8, 8, 8, 8));
        areaResultados.setText("Bienvenido al Sistema de Gestión Logística del Centro de Distribución.\n"
                + "Seleccione una pestaña para trabajar con el inventario, los camiones,\n"
                + "las rutas o las entregas. Los resultados se muestran en esta área.");
        JScrollPane scroll = new JScrollPane(areaResultados);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados"));

        // Botón Salir (opción 11)
        JButton btnSalir = crearBoton("11. Salir", "btnSalir", e -> salir());
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.add(btnSalir);

        JPanel contenido = new JPanel(new BorderLayout(0, 6));
        contenido.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        contenido.add(pestanas, BorderLayout.NORTH);
        contenido.add(scroll, BorderLayout.CENTER);
        contenido.add(panelSur, BorderLayout.SOUTH);
        setContentPane(contenido);
    }

    private JPanel crearPanelInventario() {
        txtCodigo = crearCampo("txtCodigo");
        txtNombre = crearCampo("txtNombre");
        txtStock = crearCampo("txtStock");
        JPanel p = new JPanel(new GridBagLayout());
        agregarFila(p, 0, "Código:", txtCodigo);
        agregarFila(p, 1, "Nombre:", txtNombre);
        agregarFila(p, 2, "Stock:", txtStock);
        agregarBotones(p, 3,
                crearBoton("1. Insertar producto", "btnInsertar", e -> insertarProducto()),
                crearBoton("2. Buscar por código", "btnBuscar", e -> buscarProducto()),
                crearBoton("3. Mostrar inventario ordenado", "btnMostrarInventario", e -> mostrarInventario()));
        return p;
    }

    private JPanel crearPanelCamiones() {
        txtPlaca = crearCampo("txtPlaca");
        txtConductor = crearCampo("txtConductor");
        txtCapacidad = crearCampo("txtCapacidad");
        JPanel p = new JPanel(new GridBagLayout());
        agregarFila(p, 0, "Placa:", txtPlaca);
        agregarFila(p, 1, "Conductor:", txtConductor);
        agregarFila(p, 2, "Capacidad (ton):", txtCapacidad);
        agregarBotones(p, 3,
                crearBoton("4. Encolar camión", "btnEncolar", e -> encolarCamion()),
                crearBoton("5. Desencolar camión", "btnDesencolar", e -> desencolarCamion()),
                crearBoton("6. Mostrar cola", "btnMostrarCola", e -> mostrarCola()));
        return p;
    }

    private JPanel crearPanelRutas() {
        txtCiudad = crearCampo("txtCiudad");
        txtOrigen = crearCampo("txtOrigen");
        txtDestinoRuta = crearCampo("txtDestinoRuta");
        txtKm = crearCampo("txtKm");
        JPanel p = new JPanel(new GridBagLayout());
        agregarFila(p, 0, "Ciudad:", txtCiudad);
        agregarFila(p, 1, "Ruta - Origen:", txtOrigen);
        agregarFila(p, 2, "Ruta - Destino:", txtDestinoRuta);
        agregarFila(p, 3, "Ruta - Distancia (km):", txtKm);
        agregarBotones(p, 4,
                crearBoton("Agregar ciudad", "btnAgregarCiudad", e -> agregarCiudad()),
                crearBoton("Agregar ruta", "btnAgregarRuta", e -> agregarRuta()),
                crearBoton("7. Mostrar rutas", "btnMostrarRutas", e -> mostrarRutas()),
                crearBoton("8. BFS desde ciudad", "btnBfs", e -> ejecutarBfs()));
        return p;
    }

    private JPanel crearPanelEntregas() {
        txtEntCodigo = crearCampo("txtEntCodigo");
        txtEntCantidad = crearCampo("txtEntCantidad");
        txtEntDestino = crearCampo("txtEntDestino");
        JPanel p = new JPanel(new GridBagLayout());
        agregarFila(p, 0, "Código de producto:", txtEntCodigo);
        agregarFila(p, 1, "Cantidad:", txtEntCantidad);
        agregarFila(p, 2, "Ciudad destino:", txtEntDestino);
        agregarBotones(p, 3,
                crearBoton("9. Registrar entrega", "btnRegistrarEntrega", e -> registrarEntrega()),
                crearBoton("10. Mostrar historial", "btnMostrarHistorial", e -> mostrarHistorial()));
        return p;
    }

    // ----- Utilidades de construcción de componentes -----

    private JTextField crearCampo(String nombre) {
        JTextField campo = new JTextField(22);
        campo.setName(nombre);
        return campo;
    }

    private JButton crearBoton(String texto, String nombre, ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setName(nombre);
        boton.addActionListener(accion);
        return boton;
    }

    private void agregarFila(JPanel panel, int fila, String etiqueta, JComponent campo) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = fila;
        c.insets = new Insets(4, 8, 4, 8);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        panel.add(new JLabel(etiqueta), c);
        c.gridx = 1;
        panel.add(campo, c);
    }

    private void agregarBotones(JPanel panel, int fila, JButton... botones) {
        JPanel fila_botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        for (JButton b : botones) {
            fila_botones.add(b);
        }
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = fila;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(8, 4, 8, 4);
        panel.add(fila_botones, c);
    }

    // ----- Utilidades de validación y mensajes -----

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /** Lee un entero del campo; si no es válido muestra un error y devuelve null. */
    private Integer leerEntero(JTextField campo, String nombreCampo) {
        try {
            return Integer.valueOf(campo.getText().trim());
        } catch (NumberFormatException ex) {
            mostrarError("El campo \"" + nombreCampo + "\" debe ser un número entero válido.");
            return null;
        }
    }

    // =====================================================================
    //  LÓGICA DE INTERACCIÓN - INVENTARIO (ABB)
    // =====================================================================

    /** Opción 1: insertar producto en el inventario. */
    private void insertarProducto() {
        Integer codigo = leerEntero(txtCodigo, "Código");
        if (codigo == null) {
            return;
        }
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            mostrarError("Ingrese el nombre del producto.");
            return;
        }
        Integer stock = leerEntero(txtStock, "Stock");
        if (stock == null) {
            return;
        }
        if (codigo < 0 || stock < 0) {
            mostrarError("El código y el stock no pueden ser negativos.");
            return;
        }
        Producto nuevo = new Producto(codigo, nombre, stock);
        if (inventario.insertar(nuevo)) {
            areaResultados.setText("Producto insertado correctamente en el inventario:\n" + nuevo);
            txtCodigo.setText("");
            txtNombre.setText("");
            txtStock.setText("");
        } else {
            mostrarError("Ya existe un producto con el código " + codigo + ".");
        }
    }

    /** Opción 2: buscar producto por código. */
    private void buscarProducto() {
        Integer codigo = leerEntero(txtCodigo, "Código");
        if (codigo == null) {
            return;
        }
        Producto p = inventario.buscar(codigo);
        if (p == null) {
            areaResultados.setText("No se encontró ningún producto con el código " + codigo + ".");
        } else {
            areaResultados.setText("Producto encontrado:\n" + p);
        }
    }

    /** Opción 3: mostrar inventario ordenado por código (recorrido inOrden). */
    private void mostrarInventario() {
        if (inventario.altura() == 0) {
            areaResultados.setText("El inventario está vacío.");
            return;
        }
        // Lista temporal solo para recibir el recorrido inOrden; el inventario vive en el ABB
        List<Producto> lista = new ArrayList<>();
        inventario.inOrden(lista);
        StringBuilder sb = new StringBuilder("INVENTARIO ORDENADO POR CÓDIGO (inOrden)\n");
        sb.append("------------------------------------------------------------\n");
        for (Producto p : lista) {
            sb.append(p).append("\n");
        }
        sb.append("------------------------------------------------------------\n");
        sb.append("Total de productos: ").append(lista.size())
                .append(" | Altura del árbol: ").append(inventario.altura());
        areaResultados.setText(sb.toString());
    }

    // =====================================================================
    //  LÓGICA DE INTERACCIÓN - CAMIONES (COLA)
    // =====================================================================

    /** Opción 4: encolar camión. */
    private void encolarCamion() {
        String placa = txtPlaca.getText().trim();
        String conductor = txtConductor.getText().trim();
        if (placa.isEmpty() || conductor.isEmpty()) {
            mostrarError("Ingrese la placa y el conductor del camión.");
            return;
        }
        double capacidad;
        try {
            capacidad = Double.parseDouble(txtCapacidad.getText().trim().replace(',', '.'));
        } catch (NumberFormatException ex) {
            mostrarError("El campo \"Capacidad (ton)\" debe ser un número válido.");
            return;
        }
        if (capacidad <= 0) {
            mostrarError("La capacidad debe ser mayor que 0.");
            return;
        }
        Camion camion = new Camion(placa, conductor, capacidad);
        colaCamiones.encolar(camion);
        areaResultados.setText("Camión encolado correctamente:\n" + camion);
        txtPlaca.setText("");
        txtConductor.setText("");
        txtCapacidad.setText("");
    }

    /** Opción 5: desencolar camión (FIFO). */
    private void desencolarCamion() {
        Camion camion = colaCamiones.desencolar();
        if (camion == null) {
            areaResultados.setText("La cola de camiones está vacía. No hay camión para desencolar.");
        } else {
            areaResultados.setText("Camión desencolado (salió del frente de la cola):\n" + camion);
        }
    }

    /** Opción 6: mostrar la cola de camiones. */
    private void mostrarCola() {
        areaResultados.setText("COLA DE CAMIONES (FIFO)\n"
                + "------------------------------------------------------------\n"
                + colaCamiones.mostrar());
    }

    // =====================================================================
    //  LÓGICA DE INTERACCIÓN - RUTAS (GRAFO)
    // =====================================================================

    /** Agrega una ciudad a la red de rutas. */
    private void agregarCiudad() {
        String ciudad = txtCiudad.getText().trim();
        if (ciudad.isEmpty()) {
            mostrarError("Ingrese el nombre de la ciudad.");
            return;
        }
        if (grafo.agregarCiudad(ciudad)) {
            areaResultados.setText("Ciudad agregada a la red de rutas: " + ciudad);
            txtCiudad.setText("");
        } else {
            mostrarError("La ciudad \"" + ciudad + "\" ya existe en la red de rutas.");
        }
    }

    /** Agrega una ruta bidireccional entre dos ciudades existentes. */
    private void agregarRuta() {
        String origen = txtOrigen.getText().trim();
        String destino = txtDestinoRuta.getText().trim();
        if (origen.isEmpty() || destino.isEmpty()) {
            mostrarError("Ingrese la ciudad de origen y la de destino de la ruta.");
            return;
        }
        Integer km = leerEntero(txtKm, "Distancia (km)");
        if (km == null) {
            return;
        }
        if (km <= 0) {
            mostrarError("La distancia debe ser mayor que 0 km.");
            return;
        }
        if (!grafo.existeCiudad(origen)) {
            mostrarError("La ciudad de origen \"" + origen + "\" no existe. Agréguela primero.");
            return;
        }
        if (!grafo.existeCiudad(destino)) {
            mostrarError("La ciudad de destino \"" + destino + "\" no existe. Agréguela primero.");
            return;
        }
        if (grafo.agregarRuta(origen, destino, km)) {
            areaResultados.setText("Ruta agregada: " + origen + " <-> " + destino + " (" + km + " km)");
            txtOrigen.setText("");
            txtDestinoRuta.setText("");
            txtKm.setText("");
        } else {
            mostrarError("No se pudo agregar la ruta: ya existe o origen y destino son la misma ciudad.");
        }
    }

    /** Opción 7: mostrar la red de rutas (lista de adyacencia). */
    private void mostrarRutas() {
        areaResultados.setText("RED DE RUTAS (lista de adyacencia)\n"
                + "------------------------------------------------------------\n"
                + grafo.mostrarRutas());
    }

    /** Opción 8: recorrido BFS desde una ciudad. */
    private void ejecutarBfs() {
        String ciudad = txtCiudad.getText().trim();
        if (ciudad.isEmpty()) {
            mostrarError("Ingrese en el campo \"Ciudad\" la ciudad de inicio del recorrido.");
            return;
        }
        List<String> recorrido = grafo.bfs(ciudad);
        if (recorrido.isEmpty()) {
            mostrarError("La ciudad \"" + ciudad + "\" no existe en la red de rutas.");
            return;
        }
        StringBuilder sb = new StringBuilder("RECORRIDO BFS DESDE " + recorrido.get(0) + "\n");
        sb.append("------------------------------------------------------------\n");
        for (int i = 0; i < recorrido.size(); i++) {
            if (i > 0) {
                sb.append(" -> ");
            }
            sb.append(recorrido.get(i));
        }
        sb.append("\n\nCiudades alcanzadas: ").append(recorrido.size());
        areaResultados.setText(sb.toString());
    }

    // =====================================================================
    //  LÓGICA DE INTERACCIÓN - ENTREGAS (INTEGRACIÓN)
    // =====================================================================

    /**
     * Opción 9: registrar entrega. Integra las cuatro estructuras en este orden:
     * Paso 1 ABB buscar -> Paso 2 ABB descontarStock -> Paso 3 Grafo bfs ->
     * Paso 4 Cola desencolar -> Paso 5 ArrayList add.
     * Si falla el paso 3 o el 4, se devuelve el stock descontado en el paso 2
     * para que el inventario no quede alterado por una entrega que no se registró.
     */
    private void registrarEntrega() {
        Integer codigo = leerEntero(txtEntCodigo, "Código de producto");
        if (codigo == null) {
            return;
        }
        Integer cantidad = leerEntero(txtEntCantidad, "Cantidad");
        if (cantidad == null) {
            return;
        }
        String destino = txtEntDestino.getText().trim();
        if (destino.isEmpty()) {
            mostrarError("Ingrese la ciudad destino de la entrega.");
            return;
        }
        if (cantidad <= 0) {
            mostrarError("La cantidad debe ser mayor que 0.");
            return;
        }

        // Paso 1: Árbol - buscar(codigo) para obtener el Producto
        Producto producto = inventario.buscar(codigo);
        if (producto == null) {
            mostrarError("El producto con código " + codigo + " no existe en el inventario.");
            return;
        }

        // Paso 2: Árbol - descontarStock(codigo, cantidad)
        int stockAntes = producto.getStock();
        if (!inventario.descontarStock(codigo, cantidad)) {
            mostrarError("Stock insuficiente de \"" + producto.getNombre() + "\".\n"
                    + "Stock disponible: " + stockAntes + " | Cantidad solicitada: " + cantidad);
            return;
        }

        // Paso 3: Grafo - bfs(destino) para verificar que el destino existe
        List<String> alcanzables = grafo.bfs(destino);
        if (alcanzables.isEmpty()) {
            producto.setStock(stockAntes); // Se revierte el descuento del paso 2
            mostrarError("El destino \"" + destino + "\" no existe en la red de rutas.");
            return;
        }
        String destinoRegistrado = alcanzables.get(0); // Nombre tal como está en el grafo

        // Paso 4: Cola - desencolar() para obtener el Camion asignado
        Camion camion = colaCamiones.desencolar();
        if (camion == null) {
            producto.setStock(stockAntes); // Se revierte el descuento del paso 2
            mostrarError("No hay camiones disponibles en la cola.");
            return;
        }

        // Paso 5: ArrayList - historial.add(...) para registrar la Entrega
        Entrega entrega = new Entrega(siguienteIdEntrega++, producto.getNombre(),
                camion.getPlaca(), destinoRegistrado, cantidad);
        historial.add(entrega);

        areaResultados.setText("ENTREGA REGISTRADA CORRECTAMENTE\n"
                + "------------------------------------------------------------\n"
                + "Paso 1 (ABB)    Producto encontrado: " + producto.getNombre() + "\n"
                + "Paso 2 (ABB)    Stock actualizado: " + stockAntes + " -> " + producto.getStock() + "\n"
                + "Paso 3 (Grafo)  Destino verificado: " + destinoRegistrado + "\n"
                + "Paso 4 (Cola)   Camión asignado: " + camion + "\n"
                + "Paso 5 (Lista)  " + entrega);
        JOptionPane.showMessageDialog(this,
                "Entrega #" + entrega.getId() + " registrada correctamente.\n"
                + "Camión asignado: " + camion.getPlaca() + " (" + camion.getConductor() + ")",
                "Entrega registrada", JOptionPane.INFORMATION_MESSAGE);
        txtEntCodigo.setText("");
        txtEntCantidad.setText("");
        txtEntDestino.setText("");
    }

    /** Opción 10: mostrar el historial de entregas en orden cronológico. */
    private void mostrarHistorial() {
        if (historial.isEmpty()) {
            areaResultados.setText("El historial de entregas está vacío.");
            return;
        }
        StringBuilder sb = new StringBuilder("HISTORIAL DE ENTREGAS (orden cronológico)\n");
        sb.append("------------------------------------------------------------\n");
        for (Entrega e : historial) { // Recorrido con for-each
            sb.append(e).append("\n");
        }
        sb.append("------------------------------------------------------------\n");
        sb.append("Total de entregas: ").append(historial.size());
        areaResultados.setText(sb.toString());
    }

    // =====================================================================
    //  SALIR Y PUNTO DE ENTRADA
    // =====================================================================

    /** Opción 11: cerrar la aplicación. */
    private void salir() {
        dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            // Si Nimbus no está disponible se usa el aspecto por defecto
        }
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}

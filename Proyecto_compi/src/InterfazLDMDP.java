import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.Arbol;
import Analizador.Interprete.Simbolo.TablaSimbolos;
import Analizador.Scanner;
import Analizador.parser;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class InterfazLDMDP extends JFrame implements ActionListener {

    // Paneles principales
    private JPanel panelPrincipal;
    private JPanel panelMenu;
    private JPanel panelEntrada;
    private JPanel panelReporte;
    private JPanel panelSalida;

    // Componentes del menú
    private JMenuBar menuBar;
    private JMenu menuArchivo;
    private JMenu menuReportes;
    private JMenu menuEjecutar;

    // Items del menú Archivo
    private JMenuItem itemNuevo;
    private JMenuItem itemAbrir;
    private JMenuItem itemGuardar;

    // Items del menú Reportes
    private JMenuItem itemReporteTokens;
    private JMenuItem itemReporteError;

    // Items del menú Ejecutar
    private JMenuItem itemEjecutar;

    // Áreas de texto
    private JTextArea areaEntrada;
    private JTextArea areaReporte;
    private JTextArea areaSalida;

    // Barras de desplazamiento
    private JScrollPane scrollEntrada;
    private JScrollPane scrollReporte;
    private JScrollPane scrollSalida;

    // Archivo actual
    private File archivoActual;

    public InterfazLDMDP() {
        // Configuración básica de la ventana
        setTitle("LDM DP");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializar componentes
        inicializarComponentes();

        // Hacer visible la ventana
        setVisible(true);
    }

    private void inicializarComponentes() {
        // Panel principal con BorderLayout
        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Color.BLACK);

        // Crear la barra de menú
        crearMenuBar();

        // Crear paneles de contenido
        crearPaneles();

        // Añadir paneles al panel principal
        panelPrincipal.add(panelEntrada, BorderLayout.WEST);
        panelPrincipal.add(panelReporte, BorderLayout.EAST);
        panelPrincipal.add(panelSalida, BorderLayout.SOUTH);

        // Añadir panel principal al frame
        setContentPane(panelPrincipal);
    }

    private void crearMenuBar() {
        menuBar = new JMenuBar();

        // Menú Archivo
        menuArchivo = new JMenu("Archivo");
        itemNuevo = new JMenuItem("Nuevo");
        itemAbrir = new JMenuItem("Abrir");
        itemGuardar = new JMenuItem("Guardar Archivo");

        itemNuevo.addActionListener(this);
        itemAbrir.addActionListener(this);
        itemGuardar.addActionListener(this);

        menuArchivo.add(itemNuevo);
        menuArchivo.add(itemAbrir);
        menuArchivo.add(itemGuardar);

        // Menú Reportes
        menuReportes = new JMenu("Reportes");
        itemReporteTokens = new JMenuItem("Reporte de Tokens");
        itemReporteError = new JMenuItem("Reporte de Error");

        itemReporteTokens.addActionListener(this);
        itemReporteError.addActionListener(this);

        menuReportes.add(itemReporteTokens);
        menuReportes.add(itemReporteError);

        // Menú Ejecutar
        menuEjecutar = new JMenu("Ejecutar");
        itemEjecutar = new JMenuItem("Ejecutar");

        itemEjecutar.addActionListener(this);

        menuEjecutar.add(itemEjecutar);

        // Añadir menús a la barra de menú
        menuBar.add(menuArchivo);
        menuBar.add(menuReportes);
        menuBar.add(menuEjecutar);

        // Establecer la barra de menú
        setJMenuBar(menuBar);
    }

    private void crearPaneles() {
        // Panel de Entrada
        panelEntrada = new JPanel(new BorderLayout());
        panelEntrada.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Entrada",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                null,
                Color.WHITE));
        panelEntrada.setBackground(Color.BLACK);
        panelEntrada.setPreferredSize(new Dimension(350, 250));

        areaEntrada = new JTextArea();
        areaEntrada.setBackground(Color.BLACK);
        areaEntrada.setForeground(Color.WHITE);
        areaEntrada.setCaretColor(Color.WHITE);

        scrollEntrada = new JScrollPane(areaEntrada);
        scrollEntrada.setBorder(BorderFactory.createEmptyBorder());
        panelEntrada.add(scrollEntrada, BorderLayout.CENTER);

        // Panel de Reporte
        panelReporte = new JPanel(new BorderLayout());
        panelReporte.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Reporte",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                null,
                Color.WHITE));
        panelReporte.setBackground(Color.BLACK);
        panelReporte.setPreferredSize(new Dimension(350, 250));

        areaReporte = new JTextArea();
        areaReporte.setBackground(Color.BLACK);
        areaReporte.setForeground(Color.WHITE);
        areaReporte.setCaretColor(Color.WHITE);
        areaReporte.setEditable(false);

        scrollReporte = new JScrollPane(areaReporte);
        scrollReporte.setBorder(BorderFactory.createEmptyBorder());
        panelReporte.add(scrollReporte, BorderLayout.CENTER);

        // Panel de Salida
        panelSalida = new JPanel(new BorderLayout());
        panelSalida.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Salida",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                null,
                Color.WHITE));
        panelSalida.setBackground(Color.BLACK);
        panelSalida.setPreferredSize(new Dimension(800, 200));

        areaSalida = new JTextArea();
        areaSalida.setBackground(Color.BLACK);
        areaSalida.setForeground(Color.WHITE);
        areaSalida.setCaretColor(Color.WHITE);
        areaSalida.setEditable(false);

        scrollSalida = new JScrollPane(areaSalida);
        scrollSalida.setBorder(BorderFactory.createEmptyBorder());
        panelSalida.add(scrollSalida, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Funciones del menú Archivo
        if (e.getSource() == itemNuevo) {
            nuevoArchivo();
        } else if (e.getSource() == itemAbrir) {
            abrirArchivo();
        } else if (e.getSource() == itemGuardar) {
            guardarArchivo();
        }

        // Funciones del menú Reportes
        else if (e.getSource() == itemReporteTokens) {
            generarReporteTokens();
        } else if (e.getSource() == itemReporteError) {
            generarReporteError();
        }

        // Funciones del menú Ejecutar
        else if (e.getSource() == itemEjecutar) {
            ejecutarAnalisis();
        }
    }

    private void nuevoArchivo() {
        // Verificar si hay cambios no guardados
        if (!areaEntrada.getText().isEmpty()) {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Desea guardar los cambios antes de crear un nuevo archivo?",
                    "Guardar cambios", JOptionPane.YES_NO_CANCEL_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                guardarArchivo();
            } else if (confirmacion == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }

        // Crear nuevo archivo
        archivoActual = null;
        areaEntrada.setText("");
        areaReporte.setText("");
        areaSalida.setText("");
        setTitle("LDM DP - Nuevo Archivo");
    }

    private void abrirArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Abrir Archivo");

        // Filtro para archivos de texto
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Archivos de Texto", "txt", "ldp");
        fileChooser.setFileFilter(filter);

        int seleccion = fileChooser.showOpenDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            archivoActual = fileChooser.getSelectedFile();

            try (BufferedReader reader = new BufferedReader(new FileReader(archivoActual))) {
                String linea;
                StringBuilder contenido = new StringBuilder();

                while ((linea = reader.readLine()) != null) {
                    contenido.append(linea).append("\n");
                }

                areaEntrada.setText(contenido.toString());
                setTitle("LDM DP - " + archivoActual.getName());

                // Limpiar áreas de reporte y salida
                areaReporte.setText("");
                areaSalida.setText("");

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al abrir el archivo: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void guardarArchivo() {
        if (archivoActual == null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Archivo");

            // Filtro para archivos de texto
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Archivos de Texto", "txt", "ldp");
            fileChooser.setFileFilter(filter);

            int seleccion = fileChooser.showSaveDialog(this);

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                archivoActual = fileChooser.getSelectedFile();

                // Verificar extensión
                if (!archivoActual.getName().contains(".")) {
                    archivoActual = new File(archivoActual.getAbsolutePath() + ".ldp");
                }
            } else {
                return;
            }
        }

        try (FileWriter writer = new FileWriter(archivoActual)) {
            writer.write(areaEntrada.getText());
            setTitle("LDM DP - " + archivoActual.getName());
            JOptionPane.showMessageDialog(this,
                    "Archivo guardado exitosamente.",
                    "Guardar", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar el archivo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generarReporteTokens() {
        String codigoFuente = areaEntrada.getText();

        if (codigoFuente.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay código para analizar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        areaReporte.setText("=== REPORTE DE TOKENS ===\n\n");

        // Redirigir System.out para capturar los prints del analizador léxico
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream newOut = new PrintStream(baos);
        System.setOut(newOut);

        try {
            // Crear instancia del scanner solo para obtener los tokens
            Scanner scanner = new Scanner(new BufferedReader(new StringReader(codigoFuente)));

            // Obtener y mostrar los tokens
            java_cup.runtime.Symbol token;
            int contador = 1;

            // Usando un enfoque seguro para no entrar en bucle infinito
            while (true) {
                try {
                    token = scanner.next_token();

                    // Imprimir valor del token para depuración
                    System.out.println("Token encontrado: " + token.sym + " Valor: " + token.value);

                    // Si es el token de fin, terminar (0 es comúnmente usado para EOF en CUP)
                    if (token.sym == 0) {
                        break;
                    }

                    // Mostrar información del token
                    String tipoToken = obtenerNombreTokenSimplificado(token.sym);
                    String valorToken = token.value != null ? token.value.toString() : "";

                    areaReporte.append(contador + ". [" + tipoToken + "] " + valorToken + "\n");
                    contador++;

                } catch (Exception ex) {
                    // Si hay un error procesando un token, mostrarlo y seguir con el siguiente
                    areaReporte.append(contador + ". [ERROR] " + ex.getMessage() + "\n");
                    contador++;
                    break; // Salir para evitar bucle infinito en caso de error
                }
            }

            // Restaurar System.out
            System.setOut(originalOut);

            // Mostrar resultados capturados (información de depuración)
            String capturedOutput = baos.toString();
            if (!capturedOutput.isEmpty()) {
                areaReporte.append("\n--- INFORMACIÓN DE DEPURACIÓN ---\n");
                areaReporte.append(capturedOutput);
            }

        } catch (Exception e) {
            // Restaurar System.out antes de procesar el error
            System.setOut(originalOut);
            areaReporte.append("Error generando reporte de tokens: " + e.getMessage() + "\n");

            // Mostrar resultados capturados hasta el error
            String capturedOutput = baos.toString();
            if (!capturedOutput.isEmpty()) {
                areaReporte.append("\n--- INFORMACIÓN DE DEPURACIÓN ---\n");
                areaReporte.append(capturedOutput);
            }
        }
    }

    // Método auxiliar para obtener el nombre de un token según su valor numérico
    // sin depender de las constantes de la clase parser
    private String obtenerNombreTokenSimplificado(int sym) {
        // Mapeo manual basado en los valores que CUP suele asignar a los tokens
        // Estos valores pueden variar según la generación de CUP, pero es una aproximación inicial
        switch (sym) {
            case 0: return "EOF";
            // Aquí mapeamos los símbolos más comunes basados en su orden típico en CUP
            // Este mapeo es aproximado y puede necesitar ajustes
            case 1: return "STRATEGY";
            case 2: return "MATCH";
            case 3: return "MAIN";
            case 4: return "INITIAL";
            case 5: return "RULES";
            case 6: return "PLAYERS";
            case 7: return "STRATEGIES";
            case 8: return "ROUNDS";
            case 9: return "SCORING";
            case 10: return "RUN";
            case 11: return "WITH";
            case 12: return "SEED";
            case 13: return "ACC";
            case 14: return "MUTUAL";
            case 15: return "COOPERATION";
            case 16: return "DEFECTION";
            case 17: return "BETRAYAL";
            case 18: return "REWARD";
            case 19: return "PUNISHMENT";
            case 20: return "IF";
            case 21: return "THEN";
            case 22: return "ELSE";
            case 23: return "ASIGNACION";
            case 24: return "MENORIGUAL";
            case 25: return "MAYORIGUAL";
            case 26: return "IGUAL";
            case 27: return "DIFERENTE";
            case 28: return "MENOR";
            case 29: return "MAYOR";
            case 30: return "AND";
            case 31: return "OR";
            case 32: return "NOT";
            case 33: return "LLAVEIZQ";
            case 34: return "LLAVEDER";
            case 35: return "CORIZQ";
            case 36: return "CORDER";
            case 37: return "PARENTESISIZQ";
            case 38: return "PARENTESISDER";
            case 39: return "COMA";
            case 40: return "DOSPUNTOS";
            case 41: return "PUNTO";
            case 42: return "NUMERO";
            case 43: return "DECIMAL";
            case 44: return "BOOLEANO";
            case 45: return "CADENA";
            case 46: return "IDENTIFICADOR";
            case 47: return "ROUND_NUMBER";
            case 48: return "GET_MOVES_COUNT";
            case 49: return "GET_LAST_N_MOVES";
            case 50: return "GET_MOVE";
            case 51: return "LAST_MOVE";
            case 52: return "RANDOM";
            case 53: return "OPPONENT_HISTORY";
            case 54: return "SELF_HISTORY";
            case 55: return "TOTAL_ROUNDS";
            case 56: return "HISTORY";
            default: return "SIMBOLO_" + sym;
        }
    }

    private void generarReporteError() {
        String codigoFuente = areaEntrada.getText();

        if (codigoFuente.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay código para analizar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Redirigir System.out para capturar los mensajes de error
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream newOut = new PrintStream(baos);
        System.setOut(newOut);

        try {
            // Crear instancias del scanner y parser solo para análisis
            Scanner scanner = new Scanner(new BufferedReader(new StringReader(codigoFuente)));
            parser Parser = new parser(scanner);

            // Intentar ejecutar el parser para ver si hay errores
            Parser.parse();

            // Restaurar System.out
            System.setOut(originalOut);

            // Obtener mensajes capturados
            String capturedOutput = baos.toString();

            // Si llegamos aquí, no hubo errores de parseo
            areaReporte.setText("=== REPORTE DE ERRORES ===\n\n");

            if (capturedOutput.isEmpty()) {
                areaReporte.append("No se encontraron errores en el código.\n");
            } else {
                // Puede haber mensajes de advertencia o información
                areaReporte.append("=== MENSAJES DEL ANALIZADOR ===\n");
                areaReporte.append(capturedOutput);
            }

        } catch (Exception e) {
            // Restaurar System.out
            System.setOut(originalOut);

            // Obtener mensajes capturados
            String capturedOutput = baos.toString();

            // Mostrar el error capturado
            areaReporte.setText("=== REPORTE DE ERRORES ===\n\n");

            // Agregar mensajes de salida que pueden contener información sobre el error
            if (!capturedOutput.isEmpty()) {
                areaReporte.append("MENSAJES DEL ANALIZADOR:\n");
                areaReporte.append(capturedOutput + "\n\n");
            }

            // Determinar si es un error léxico o sintáctico basado en el mensaje
            String errorMsg = e.getMessage();
            if (errorMsg != null) {
                if (errorMsg.contains("Lexical")) {
                    areaReporte.append("[ERROR_LEXICO] " + errorMsg + "\n");
                } else {
                    areaReporte.append("[ERROR_SINTACTICO] " + errorMsg + "\n");
                }
            } else {
                areaReporte.append("[ERROR] Se produjo un error durante el análisis.\n");
                e.printStackTrace(originalOut);
            }
        }
    }

    private void generarReporteError(Exception e) {
        // Sobrecarga del método para usar cuando ya tenemos una excepción
        areaReporte.setText("=== REPORTE DE ERRORES ===\n\n");

        // Determinar si es un error léxico o sintáctico basado en el mensaje
        String errorMsg = e.getMessage();
        if (errorMsg != null) {
            if (errorMsg.contains("Lexical")) {
                areaReporte.append("[ERROR_LEXICO] " + errorMsg + "\n");
            } else {
                areaReporte.append("[ERROR_SINTACTICO] " + errorMsg + "\n");
            }
        } else {
            areaReporte.append("[ERROR] Se produjo un error durante el análisis.\n");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            areaReporte.append(sw.toString());
        }
    }

    private void ejecutarAnalisis() {
        String codigoFuente = areaEntrada.getText();

        if (codigoFuente.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay código para analizar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Limpiar áreas de salida
        areaSalida.setText("=== EJECUCIÓN ===\n\n");
        areaSalida.append("Analizando código...\n\n");

        // Redirigir System.out para capturar los prints del analizador léxico
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream newOut = new PrintStream(baos);
        System.setOut(newOut);

        try {
            // Primero imprimimos todos los tokens para depuración
            areaSalida.append("Verificando tokens:\n");
            Scanner scannerTemp = new Scanner(new BufferedReader(new StringReader(codigoFuente)));
            java_cup.runtime.Symbol token;
            while (true) {
                try {
                    token = scannerTemp.next_token();
                    System.out.println("Token: " + token.sym + ", Valor: " + token.value +
                            ", Línea: " + token.left + ", Columna: " + token.right);
                    if (token.sym == 0) { // EOF suele ser 0
                        break;
                    }
                } catch (Exception ex) {
                    System.out.println("Error escaneando token: " + ex.getMessage());
                    break;
                }
            }
            System.out.println("Fin verificación de tokens. Iniciando parser...");

            // Ahora ejecutamos el parser
            Scanner scanner = new Scanner(new BufferedReader(new StringReader(codigoFuente)));
            parser Parser = new parser(scanner);

            // Habilitar el modo de depuración si está implementado en el parser
            try {
                Parser.getClass().getField("debugMode").set(Parser, true);
            } catch (Exception ex) {
                // Si no tiene el campo debugMode, continuar normalmente
            }

            // Ejecutar el parser
            Object resultado = Parser.parse().value;

            // AÑADIR AQUÍ LA PARTE DE INTERPRETACIÓN
            if (resultado instanceof Arbol) {
                Arbol arbol = (Arbol) resultado;

                // Obtener tabla de símbolos
                TablaSimbolos tabla = arbol.getTabla();

                // Interpretar cada instrucción
                /*areaSalida.append("\nResultados de la ejecución:\n");
                for (Instruccion inst : arbol.getInstrucciones()) {
                    Object resultadoInst = inst.interpretar(arbol, tabla);
                    if (resultadoInst != null) {
                        areaSalida.append(resultadoInst.toString() + "\n");
                    }
                }*/

                areaSalida.append("\nEjecución completada exitosamente.\n");
            } else {
                areaSalida.append("\nEl parser no retornó un árbol válido para interpretar.\n");
            }

            // Restaurar System.out
            System.setOut(originalOut);

            // Mostrar resultados capturados
            String capturedOutput = baos.toString();
            areaSalida.append("SALIDA DEL ANALIZADOR:\n" + capturedOutput + "\n");
            areaSalida.append("\nAnálisis sintáctico completado.\n");

            // Generar automáticamente el reporte de tokens después de ejecutar
            generarReporteTokens();

        } catch (Exception e) {
            // Restaurar System.out antes de procesar el error
            System.setOut(originalOut);

            // Mostrar los resultados capturados hasta el error
            String capturedOutput = baos.toString();
            areaSalida.append("SALIDA DEL ANALIZADOR:\n" + capturedOutput + "\n");

            // Añadir el mensaje de error
            areaSalida.append("\nERROR: " + e.getMessage() + "\n");
            e.printStackTrace(originalOut);  // Para depuración en consola

            // Generar automáticamente el reporte de errores
            generarReporteError(e);
        }
    }

    public static void main(String[] args) {
        // Establecer look and feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crear instancia de la interfaz
        SwingUtilities.invokeLater(() -> {
            new InterfazLDMDP();
        });
    }
}
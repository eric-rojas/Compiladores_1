import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java_cup.runtime.Symbol;

import Analizador.Interprete.Simbolo.Arbol;
import Analizador.Simulador;

public class Interfaz extends JFrame {
    private JTextArea txtAreaEntrada;
    private JTextArea txtAreaSalida;
    private JTextArea txtAreaReporte;
    private JMenuBar menuBar;
    private JMenu menuArchivo, menuReportes, menuEjecutar;
    private JMenuItem menuNuevo, menuAbrir, menuGuardar;
    private JMenuItem menuEjecutarItem;
    private JMenuItem menuReporteTokens, menuReporteErrores;
    private File archivoActual;

    public Interfaz() {
        // Configuración básica de la ventana
        setTitle("LDM DP - Dilema del Prisionero");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear componentes de la interfaz
        crearComponentes();
        
        // Hacer visible la ventana
        setVisible(true);
    }

    private void crearComponentes() {
        // Crear el panel principal con color de fondo negro
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBackground(Color.BLACK);
        panelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Crear menú
        crearMenu();
        
        // Crear panel central con los componentes principales
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(2, 1, 0, 10)); // Dividir en dos filas de igual tamaño
        panelCentral.setBackground(Color.BLACK);
        
        // Panel superior para entrada y reporte
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new GridLayout(1, 2, 10, 0));
        panelSuperior.setBackground(Color.BLACK);
        
        // Panel para entrada
        JPanel panelEntrada = new JPanel();
        panelEntrada.setLayout(new BorderLayout());
        panelEntrada.setBackground(Color.BLACK);
        
        JLabel lblEntrada = new JLabel("Entrada");
        lblEntrada.setForeground(Color.WHITE);
        lblEntrada.setHorizontalAlignment(SwingConstants.CENTER);
        
        txtAreaEntrada = new JTextArea();
        txtAreaEntrada.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtAreaEntrada.setBackground(Color.BLACK);
        txtAreaEntrada.setForeground(Color.WHITE);
        txtAreaEntrada.setCaretColor(Color.WHITE);
        JScrollPane scrollEntrada = new JScrollPane(txtAreaEntrada);
        scrollEntrada.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        
        panelEntrada.add(lblEntrada, BorderLayout.NORTH);
        panelEntrada.add(scrollEntrada, BorderLayout.CENTER);
        
        // Panel para reporte
        JPanel panelReporte = new JPanel();
        panelReporte.setLayout(new BorderLayout());
        panelReporte.setBackground(Color.BLACK);
        
        JLabel lblReporte = new JLabel("Reporte");
        lblReporte.setForeground(Color.WHITE);
        lblReporte.setHorizontalAlignment(SwingConstants.CENTER);
        
        txtAreaReporte = new JTextArea();
        txtAreaReporte.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtAreaReporte.setBackground(Color.BLACK);
        txtAreaReporte.setForeground(Color.WHITE);
        txtAreaReporte.setEditable(false);
        JScrollPane scrollReporte = new JScrollPane(txtAreaReporte);
        scrollReporte.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        
        panelReporte.add(lblReporte, BorderLayout.NORTH);
        panelReporte.add(scrollReporte, BorderLayout.CENTER);
        
        // Añadir paneles a panelSuperior
        panelSuperior.add(panelEntrada);
        panelSuperior.add(panelReporte);
        
        // Panel para salida (ahora ocupa toda la fila inferior)
        JPanel panelSalida = new JPanel();
        panelSalida.setLayout(new BorderLayout());
        panelSalida.setBackground(Color.BLACK);
        
        JLabel lblSalida = new JLabel("Salida");
        lblSalida.setForeground(Color.WHITE);
        lblSalida.setHorizontalAlignment(SwingConstants.CENTER);
        
        txtAreaSalida = new JTextArea();
        txtAreaSalida.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtAreaSalida.setBackground(Color.BLACK);
        txtAreaSalida.setForeground(Color.WHITE);
        txtAreaSalida.setEditable(false);
        JScrollPane scrollSalida = new JScrollPane(txtAreaSalida);
        scrollSalida.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        
        panelSalida.add(lblSalida, BorderLayout.NORTH);
        panelSalida.add(scrollSalida, BorderLayout.CENTER);
        
        // Añadir componentes al panel central
        panelCentral.add(panelSuperior);
        panelCentral.add(panelSalida);
        
        // Añadir componentes al panel principal
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        
        // Añadir panel principal a la ventana
        setContentPane(panelPrincipal);
        
        // Escuchadores de eventos
        configurarEventos();
    }

    private void crearMenu() {
        // Crear un fondo negro para la barra de menú
        menuBar = new JMenuBar();
        menuBar.setBackground(Color.DARK_GRAY);
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
        
        // Menú Archivo
        menuArchivo = new JMenu("Archivo");
        menuArchivo.setForeground(Color.BLACK);
        menuArchivo.setBackground(Color.DARK_GRAY);
        
        menuNuevo = new JMenuItem("Nuevo");
        menuNuevo.setBackground(Color.DARK_GRAY);
        menuNuevo.setForeground(Color.BLACK);
        
        menuAbrir = new JMenuItem("Abrir");
        menuAbrir.setBackground(Color.DARK_GRAY);
        menuAbrir.setForeground(Color.BLACK);
        
        menuGuardar = new JMenuItem("Guardar Archivo");
        menuGuardar.setBackground(Color.DARK_GRAY);
        menuGuardar.setForeground(Color.BLACK);
        
        menuArchivo.add(menuNuevo);
        menuArchivo.add(menuAbrir);
        menuArchivo.add(menuGuardar);
        
        // Menú Reportes
        menuReportes = new JMenu("Reportes");
        menuReportes.setForeground(Color.BLACK);
        menuReportes.setBackground(Color.DARK_GRAY);
        
        menuReporteTokens = new JMenuItem("Reporte de Tokens");
        menuReporteTokens.setBackground(Color.DARK_GRAY);
        menuReporteTokens.setForeground(Color.BLACK);
        
        menuReporteErrores = new JMenuItem("Reporte de Errores");
        menuReporteErrores.setBackground(Color.DARK_GRAY);
        menuReporteErrores.setForeground(Color.BLACK);
        
        menuReportes.add(menuReporteTokens);
        menuReportes.add(menuReporteErrores);
        
        // Menú Ejecutar
        menuEjecutar = new JMenu("Ejecutar");
        menuEjecutar.setForeground(Color.BLACK);
        menuEjecutar.setBackground(Color.DARK_GRAY);
        
        menuEjecutarItem = new JMenuItem("Ejecutar");
        menuEjecutarItem.setBackground(Color.DARK_GRAY);
        menuEjecutarItem.setForeground(Color.BLACK);
        
        menuEjecutar.add(menuEjecutarItem);
        
        // Añadir menús a la barra
        menuBar.add(menuArchivo);
        menuBar.add(menuReportes);
        menuBar.add(menuEjecutar);
        
        // Establecer la barra de menú
        setJMenuBar(menuBar);
    }
    

    private void configurarEventos() {
        // Evento de Nuevo
        menuNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtAreaEntrada.setText("");
                txtAreaSalida.setText("");
                txtAreaReporte.setText("");
                archivoActual = null;
            }
        });
        
        // Evento de Abrir
        menuAbrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirArchivo();
            }
        });
        
        // Evento de Guardar
        menuGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarArchivo();
            }
        });
        
        // Evento de Ejecutar
        menuEjecutarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarCodigo();
            }
        });
        
        // Eventos de Reportes
        menuReporteTokens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporteTokens();
            }
        });
        
        menuReporteErrores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporteErrores();
            }
        });
    }

    private void abrirArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));
        
        int seleccion = fileChooser.showOpenDialog(this);
        
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            archivoActual = fileChooser.getSelectedFile();
            
            try {
                BufferedReader br = new BufferedReader(new FileReader(archivoActual));
                String linea;
                StringBuilder contenido = new StringBuilder();
                
                while ((linea = br.readLine()) != null) {
                    contenido.append(linea).append("\n");
                }
                
                br.close();
                
                txtAreaEntrada.setText(contenido.toString());
                txtAreaSalida.setText("");
                txtAreaReporte.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                        "Error al abrir el archivo: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void guardarArchivo() {
        if (archivoActual == null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));
            
            int seleccion = fileChooser.showSaveDialog(this);
            
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                archivoActual = fileChooser.getSelectedFile();
                
                // Añadir extensión .txt si no la tiene
                if (!archivoActual.getName().toLowerCase().endsWith(".txt")) {
                    archivoActual = new File(archivoActual.getAbsolutePath() + ".txt");
                }
            } else {
                return; // El usuario canceló la operación
            }
        }
        
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(archivoActual));
            pw.print(txtAreaEntrada.getText());
            pw.close();
            
            JOptionPane.showMessageDialog(this, 
                    "Archivo guardado correctamente.", 
                    "Información", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                    "Error al guardar el archivo: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ejecutarCodigo() {
        try {
            // Limpiar áreas de salida y reporte
            txtAreaSalida.setText("");
            txtAreaReporte.setText("");
            
            // Crear analizador léxico con el texto de entrada
            String input = txtAreaEntrada.getText();
            
            // Redirigir la salida estándar para capturarla
            TextAreaOutputStream taos = new TextAreaOutputStream(txtAreaSalida);
            PrintStream printOut = new PrintStream(taos, true);
            
            // Guardar la salida estándar original
            PrintStream originalOut = System.out;
            PrintStream originalErr = System.err;
            
            // Redirigir la salida estándar
            System.setOut(printOut);
            System.setErr(printOut);
            
            try {
                // Crear analizador léxico y sintáctico
                Analizador.Scanner lexer = new Analizador.Scanner(new java.io.StringReader(input));
                Analizador.parser p = new Analizador.parser(lexer);
                
                // Parsear el archivo
                Symbol s = p.parse();
                
                // Obtener el AST
                if (s != null && s.value instanceof Arbol) {
                    Arbol ast = (Arbol) s.value;
                    
                    txtAreaSalida.append("Análisis completado exitosamente.\n");
                    txtAreaSalida.append("Número de instrucciones: " + ast.getInstrucciones().size() + "\n\n");
                    
                    // Crear y ejecutar el simulador
                    Simulador simulador = new Simulador(ast);
                    simulador.ejecutar();
                } else {
                    txtAreaSalida.append("Error: No se pudo construir el AST\n");
                }
            } finally {
                // Restaurar la salida estándar
                System.setOut(originalOut);
                System.setErr(originalErr);
            }
            
        } catch (Exception ex) {
            txtAreaSalida.append("Error durante el análisis: " + ex.getMessage() + "\n");
            ex.printStackTrace();
        }
    }

    private void generarReporteTokens() {
        try {
            // Limpiar área de reporte
            txtAreaReporte.setText("");
            
            // Crear analizador léxico con el texto de entrada
            String input = txtAreaEntrada.getText();
            Analizador.Scanner lexer = new Analizador.Scanner(new java.io.StringReader(input));
            
            txtAreaReporte.append("=== REPORTE DE TOKENS ===\n\n");
            txtAreaReporte.append(String.format("%-15s %-15s %-15s %-15s\n", "LEXEMA", "TOKEN", "LÍNEA", "COLUMNA"));
            txtAreaReporte.append("-------------------------------------------------------------\n");
            
            // Obtener todos los tokens
            Symbol token;
            while (true) {
                token = lexer.next_token();
                if (token.sym == Analizador.sym.EOF) break;
                
                String tokenName = Analizador.sym.terminalNames[token.sym];
                String lexema = token.value != null ? token.value.toString() : "null";
                
                txtAreaReporte.append(String.format("%-15s %-15s %-15d %-15d\n", 
                        lexema, tokenName, token.left, token.right));
            }
            
        } catch (Exception ex) {
            txtAreaReporte.append("Error al generar reporte de tokens: " + ex.getMessage() + "\n");
            ex.printStackTrace();
        }
    }

    private void generarReporteErrores() {
        try {
            // Limpiar área de reporte
            txtAreaReporte.setText("");
            
            // Crear analizador léxico y sintáctico con el texto de entrada
            String input = txtAreaEntrada.getText();
            Analizador.Scanner lexer = new Analizador.Scanner(new java.io.StringReader(input));
            Analizador.parser p = new Analizador.parser(lexer);
            
            // Redirigir la salida de errores a un buffer
            List<String> errorMessages = new ArrayList<>();
            
            // ParseController para capturar errores
            ErrorCaptureStream errorCapture = new ErrorCaptureStream(errorMessages);
            
            // Guardar la salida estándar original
            PrintStream originalErr = System.err;
            
            // Redirigir la salida de error
            System.setErr(new PrintStream(errorCapture));
            
            try {
                // Intentar parsear y capturar errores
                p.parse();
            } catch (Exception ex) {
                // Los errores ya fueron capturados en errorMessages
            } finally {
                // Restaurar la salida estándar
                System.setErr(originalErr);
            }
            
            txtAreaReporte.append("=== REPORTE DE ERRORES ===\n\n");
            
            if (errorMessages.isEmpty()) {
                txtAreaReporte.append("No se encontraron errores.\n");
            } else {
                for (String error : errorMessages) {
                    txtAreaReporte.append(error + "\n");
                }
            }
            
        } catch (Exception ex) {
            txtAreaReporte.append("Error al generar reporte de errores: " + ex.getMessage() + "\n");
            ex.printStackTrace();
        }
    }

    // Clase auxiliar para redirigir la salida estándar a un JTextArea
    private class TextAreaOutputStream extends java.io.OutputStream {
        private JTextArea textArea;
        private StringBuilder stringBuilder = new StringBuilder();
        
        public TextAreaOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }
        
        @Override
        public void write(int b) {
            if (b == '\n') {
                final String text = stringBuilder.toString();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        textArea.append(text + "\n");
                    }
                });
                stringBuilder.setLength(0);
            } else {
                stringBuilder.append((char)b);
            }
        }
    }
    
    // Clase para capturar errores
    private class ErrorCaptureStream extends java.io.OutputStream {
        private List<String> errorMessages;
        private StringBuilder stringBuilder = new StringBuilder();
        
        public ErrorCaptureStream(List<String> errorMessages) {
            this.errorMessages = errorMessages;
        }
        
        @Override
        public void write(int b) {
            if (b == '\n') {
                String text = stringBuilder.toString();
                if (text.contains("Error")) {
                    errorMessages.add(text);
                }
                stringBuilder.setLength(0);
            } else {
                stringBuilder.append((char)b);
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Usar el look and feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Interfaz();
            }
        });
    }
}
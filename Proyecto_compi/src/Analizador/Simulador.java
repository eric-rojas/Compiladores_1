package Analizador;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.Arbol;
import Analizador.Interprete.Simbolo.TablaSimbolos;
import Analizador.Interprete.Simbolo.Simbolo;
import Analizador.Interprete.Instruccion.Juego;
import Analizador.Interprete.Instruccion.Estrategia;
import Analizador.Interprete.Instruccion.MainSeccion;
import Analizador.Interprete.Instruccion.Ejecucion;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

/**
 * Clase encargada de simular la ejecución de los juegos del Dilema del Prisionero
 */
public class Simulador {
    private Arbol ast;
    private TablaSimbolos tablaGlobal;
    
    /**
     * Constructor de la clase Simulador
     * @param ast Árbol de sintaxis abstracta
     */
    public Simulador(Arbol ast) {
        this.ast = ast;
        this.tablaGlobal = ast.getTabla();
    }
    
    /**
     * Ejecuta la simulación completa
     */
    /*public void ejecutar() {
        try {
            System.out.println("Iniciando simulación del Dilema del Prisionero...");
            
            // Ejecutar todas las instrucciones para registrar estrategias y juegos
            for (Instruccion instruccion : ast.getInstrucciones()) {
                instruccion.ejecutar(tablaGlobal);
            }
            
            // Buscar la sección main para ejecutar los juegos
            MainSeccion seccionMain = null;
            for (Instruccion instruccion : ast.getInstrucciones()) {
                if (instruccion instanceof MainSeccion) {
                    seccionMain = (MainSeccion) instruccion;
                    System.out.println("Sección main encontrada");
                    break;
                }
            }
            
            if (seccionMain == null) {
                System.err.println("Error: No se encontró la sección main");
                return;
            }
            
            // Obtener la ejecución del main
            Object resultado = seccionMain.ejecutar(tablaGlobal);
            System.out.println("Resultado de ejecutar main: " + (resultado != null ? resultado.getClass().getName() : "null"));

            
            if (resultado instanceof Ejecucion) {
                Ejecucion ejecucion = (Ejecucion) resultado;
                List<String> nombresJuegos = ejecucion.getNombresJuegos();
                long semilla = ejecucion.getSemilla();
                
                // Ejecutar cada juego especificado
                for (String nombreJuego : nombresJuegos) {
                    if (tablaGlobal.existeJuego(nombreJuego)) {
                        Juego juego = tablaGlobal.getJuego(nombreJuego);
                        simularJuego(juego, semilla);
                    } else {
                        System.err.println("Error: El juego '" + nombreJuego + "' no está definido");
                    }
                }
            } else {
                System.err.println("Error: La sección main no retornó una ejecución válida");
            }
            
        } catch (Exception e) {
            System.err.println("Error durante la simulación: " + e.getMessage());
            e.printStackTrace();
        }
    }*/

    public void ejecutar() {
        try {
            System.out.println("Iniciando simulación del Dilema del Prisionero...");
            
            // Ejecutar todas las instrucciones para registrar estrategias y juegos
            for (Instruccion instruccion : ast.getInstrucciones()) {
                instruccion.ejecutar(tablaGlobal);
            }
            
            // Buscar la sección main para ejecutar los juegos
            MainSeccion seccionMain = null;
            for (Instruccion instruccion : ast.getInstrucciones()) {
                if (instruccion instanceof MainSeccion) {
                    seccionMain = (MainSeccion) instruccion;
                    break;
                }
            }
            
            if (seccionMain == null) {
                System.err.println("Error: No se encontró la sección main");
                return;
            }
            
            // Ejecutar todas las ejecuciones
            for (Instruccion instrEjecucion : seccionMain.getEjecuciones()) {
                if (instrEjecucion instanceof Ejecucion) {
                    Ejecucion ejecucion = (Ejecucion) instrEjecucion;
                    List<String> nombresJuegos = ejecucion.getNombresJuegos();
                    long semilla = ejecucion.getSemilla();
                    
                    // Ejecutar cada juego especificado
                    for (String nombreJuego : nombresJuegos) {
                        if (tablaGlobal.existeJuego(nombreJuego)) {
                            Juego juego = tablaGlobal.getJuego(nombreJuego);
                            simularJuego(juego, semilla);
                        } else {
                            System.err.println("Error: El juego '" + nombreJuego + "' no está definido");
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error durante la simulación: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Simula un juego específico
     * @param juego Juego a simular
     * @param semilla Semilla para el generador de números aleatorios
     */
    public void simularJuego(Juego juego, long semilla) {
        System.out.println("\n=== Partida: " + juego.getNombre() + " ===");
        
        // Obtener la configuración del juego
        String[] nombresEstrategias = juego.getEstrategias();
        if (nombresEstrategias.length != 2) {
            System.err.println("Error: El juego debe tener exactamente 2 estrategias");
            return;
        }
        
        // Obtener estrategias
        Estrategia estrategia1 = tablaGlobal.getEstrategia(nombresEstrategias[0]);
        Estrategia estrategia2 = tablaGlobal.getEstrategia(nombresEstrategias[1]);
        
        if (estrategia1 == null || estrategia2 == null) {
            System.err.println("Error: No se encontraron las estrategias especificadas");
            return;
        }
        
        // Obtener número de rondas
        int numRondas = juego.getRondas(tablaGlobal);
        
        // Obtener configuración de puntaje
        Map<String, Instruccion> configPuntaje = juego.getConfiguracionPuntaje();
        Map<String, Integer> puntajes = extraerPuntajes(configPuntaje, tablaGlobal);
        
        // Validar condiciones del dilema del prisionero
        if (!validarPuntajes(puntajes)) {
            System.err.println("Error: La configuración de puntajes no cumple con las condiciones del Dilema del Prisionero (T > R > P > S)");
            return;
        }
        
        // Mostrar configuración
        mostrarConfiguracion(juego, numRondas, nombresEstrategias, puntajes);
        
        // Inicializar historiales
        List<String> historialJugador1 = new ArrayList<>();
        List<String> historialJugador2 = new ArrayList<>();
        
        // Inicializar puntuaciones
        int puntajeJugador1 = 0;
        int puntajeJugador2 = 0;
        
        // Inicializar generadores de números aleatorios con la semilla
        Random random1 = new Random(semilla);
        Random random2 = new Random(semilla);
        
        System.out.println("\nDesarrollo:");
        
        // Simular rondas
        for (int ronda = 0; ronda < numRondas; ronda++) {
            // Crear contextos para cada jugador
            TablaSimbolos contextoJugador1 = new TablaSimbolos(tablaGlobal);
            TablaSimbolos contextoJugador2 = new TablaSimbolos(tablaGlobal);
            
            // Establecer estados del sistema para el jugador 1
            contextoJugador1.setSimbolo(new Simbolo("ROUND_NUMBER", ronda));
            contextoJugador1.setSimbolo(new Simbolo("TOTAL_ROUNDS", numRondas));
            contextoJugador1.setSimbolo(new Simbolo("OPPONENT_HISTORY", new ArrayList<>(historialJugador2)));
            contextoJugador1.setSimbolo(new Simbolo("SELF_HISTORY", new ArrayList<>(historialJugador1)));
            contextoJugador1.setSimbolo(new Simbolo("RANDOM", random1.nextDouble()));
            
            // Establecer estados del sistema para el jugador 2
            contextoJugador2.setSimbolo(new Simbolo("ROUND_NUMBER", ronda));
            contextoJugador2.setSimbolo(new Simbolo("TOTAL_ROUNDS", numRondas));
            contextoJugador2.setSimbolo(new Simbolo("OPPONENT_HISTORY", new ArrayList<>(historialJugador1)));
            contextoJugador2.setSimbolo(new Simbolo("SELF_HISTORY", new ArrayList<>(historialJugador2)));
            contextoJugador2.setSimbolo(new Simbolo("RANDOM", random2.nextDouble()));
            
            // Ejecutar estrategias para determinar movimientos
            String movimientoJugador1 = estrategia1.ejecutarReglas(contextoJugador1);
            String movimientoJugador2 = estrategia2.ejecutarReglas(contextoJugador2);
            
            // Registrar movimientos en el historial
            historialJugador1.add(movimientoJugador1);
            historialJugador2.add(movimientoJugador2);
            
            // Calcular puntajes para esta ronda
            int[] puntajesRonda = calcularPuntajesRonda(movimientoJugador1, movimientoJugador2, puntajes);
            puntajeJugador1 += puntajesRonda[0];
            puntajeJugador2 += puntajesRonda[1];
            
            // Mostrar resultado de la ronda
            System.out.println("Ronda " + (ronda + 1) + ": " + 
                               nombresEstrategias[0] + "=" + movimientoJugador1 + ", " + 
                               nombresEstrategias[1] + "=" + movimientoJugador2 + " (" + 
                               puntajesRonda[0] + "-" + puntajesRonda[1] + ")");
        }
        
        // Mostrar resumen final
        mostrarResumenFinal(nombresEstrategias, historialJugador1, historialJugador2, puntajeJugador1, puntajeJugador2);
    }
    
    /**
     * Extrae los puntajes de la configuración
     */
    private Map<String, Integer> extraerPuntajes(Map<String, Instruccion> configPuntaje, TablaSimbolos tabla) {
        Map<String, Integer> puntajes = new HashMap<>();
        
        for (Map.Entry<String, Instruccion> entry : configPuntaje.entrySet()) {
            Object valor = entry.getValue().ejecutar(tabla);
            if (valor instanceof Number) {
                puntajes.put(entry.getKey(), ((Number) valor).intValue());
            } else {
                System.err.println("Error: El valor de puntuación para '" + entry.getKey() + "' no es un número");
            }
        }
        
        return puntajes;
    }
    
    /**
     * Valida que los puntajes cumplan con las condiciones del Dilema del Prisionero
     */
    private boolean validarPuntajes(Map<String, Integer> puntajes) {
        // Verificar que estén todos los puntajes necesarios
        if (!puntajes.containsKey("betrayal reward") || 
            !puntajes.containsKey("mutual cooperation") || 
            !puntajes.containsKey("mutual defection") || 
            !puntajes.containsKey("betrayal punishment")) {
            return false;
        }
        
        // Verificar condición T > R > P > S
        int T = puntajes.get("betrayal reward");
        int R = puntajes.get("mutual cooperation");
        int P = puntajes.get("mutual defection");
        int S = puntajes.get("betrayal punishment");
        
        // Verificar que todos los puntajes sean no negativos
        if (T < 0 || R < 0 || P < 0 || S < 0) {
            return false;
        }
        
        // Verificar condición T > R > P > S
        return T > R && R > P && P > S;
    }
    
    /**
     * Calcula los puntajes para una ronda específica
     */
    private int[] calcularPuntajesRonda(String movimiento1, String movimiento2, Map<String, Integer> puntajes) {
        int[] resultado = new int[2];
        
        if (movimiento1.equals("C") && movimiento2.equals("C")) {
            // Cooperación mutua
            resultado[0] = puntajes.get("mutual cooperation");
            resultado[1] = puntajes.get("mutual cooperation");
        } else if (movimiento1.equals("D") && movimiento2.equals("D")) {
            // Defección mutua
            resultado[0] = puntajes.get("mutual defection");
            resultado[1] = puntajes.get("mutual defection");
        } else if (movimiento1.equals("C") && movimiento2.equals("D")) {
            // Jugador 1 coopera, Jugador 2 traiciona
            resultado[0] = puntajes.get("betrayal punishment");
            resultado[1] = puntajes.get("betrayal reward");
        } else if (movimiento1.equals("D") && movimiento2.equals("C")) {
            // Jugador 1 traiciona, Jugador 2 coopera
            resultado[0] = puntajes.get("betrayal reward");
            resultado[1] = puntajes.get("betrayal punishment");
        }
        
        return resultado;
    }
    
    /**
     * Muestra la configuración del juego
     */
    private void mostrarConfiguracion(Juego juego, int numRondas, String[] estrategias, Map<String, Integer> puntajes) {
        System.out.println("Configuración:");
        System.out.println("\tRondas: " + numRondas);
        System.out.println("\tEstrategias: " + estrategias[0] + " vs " + estrategias[1]);
        System.out.println("\tScoring:");
        System.out.println("\t\t- Cooperación mutua: " + puntajes.get("mutual cooperation"));
        System.out.println("\t\t- Defección mutua: " + puntajes.get("mutual defection"));
        System.out.println("\t\t- Traición: " + puntajes.get("betrayal reward") + "/" + 
                          puntajes.get("betrayal punishment") + " (traidor/traicionado)");
    }
    
    /**
     * Muestra el resumen final del juego
     */
    private void mostrarResumenFinal(String[] estrategias, List<String> historial1, List<String> historial2, 
                                    int puntajeTotal1, int puntajeTotal2) {
        // Calcular porcentajes de cooperación y defección
        int cooperaciones1 = contarMovimientos(historial1, "C");
        int defecciones1 = contarMovimientos(historial1, "D");
        int cooperaciones2 = contarMovimientos(historial2, "C");
        int defecciones2 = contarMovimientos(historial2, "D");
        
        double porcentajeCooperacion1 = (double) cooperaciones1 / historial1.size() * 100;
        double porcentajeDefeccion1 = (double) defecciones1 / historial1.size() * 100;
        double porcentajeCooperacion2 = (double) cooperaciones2 / historial2.size() * 100;
        double porcentajeDefeccion2 = (double) defecciones2 / historial2.size() * 100;
        
        System.out.println("\nResumen:");
        
        // Mostrar resumen de la primera estrategia
        System.out.println("\t" + estrategias[0] + ":");
        System.out.println("\t\t- Puntuación final: " + puntajeTotal1);
        System.out.println("\t\t- Cooperaciones: " + String.format("%.1f", porcentajeCooperacion1) + "%");
        System.out.println("\t\t- Defecciones: " + String.format("%.1f", porcentajeDefeccion1) + "%");
        
        // Mostrar resumen de la segunda estrategia
        System.out.println("\t" + estrategias[1] + ":");
        System.out.println("\t\t- Puntuación final: " + puntajeTotal2);
        System.out.println("\t\t- Cooperaciones: " + String.format("%.1f", porcentajeCooperacion2) + "%");
        System.out.println("\t\t- Defecciones: " + String.format("%.1f", porcentajeDefeccion2) + "%");
        
        // Determinar ganador
        if (puntajeTotal1 > puntajeTotal2) {
            System.out.println("\nGanador: " + estrategias[0] + " con " + puntajeTotal1 + " puntos");
        } else if (puntajeTotal2 > puntajeTotal1) {
            System.out.println("\nGanador: " + estrategias[1] + " con " + puntajeTotal2 + " puntos");
        } else {
            System.out.println("\nEmpate con " + puntajeTotal1 + " puntos");
        }
    }
    
    /**
     * Cuenta el número de veces que aparece un movimiento en el historial
     */
    private int contarMovimientos(List<String> historial, String movimiento) {
        int contador = 0;
        for (String mov : historial) {
            if (mov.equals(movimiento)) {
                contador++;
            }
        }
        return contador;
    }
}

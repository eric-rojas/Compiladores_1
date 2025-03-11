package Analizador.Interprete.Instruccion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;
import Analizador.Interprete.Simbolo.Simbolo;
import java.util.List;

/**
 * Clase que representa la instrucción de ejecución de un juego
 */
public class Ejecucion extends Instruccion {
    private List<String> nombresJuegos;
    private long semilla;
    
    public Ejecucion(List<String> nombresJuegos, long semilla, int linea, int columna) {
        super(linea, columna);
        this.nombresJuegos = nombresJuegos;
        this.semilla = semilla;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        // Guardar la semilla en la tabla de símbolos
        tabla.setSimbolo(new Simbolo("SEED", semilla));
        
        // Ejecutar cada juego
        for (String nombreJuego : nombresJuegos) {
            if (tabla.existeJuego(nombreJuego)) {
                Juego juego = tabla.getJuego(nombreJuego);
                
                // Aquí se realizaría la simulación del juego
                // Esto probablemente lo harías en una clase separada
                // como Simulador.java
            }
        }
        
        return null;
    }
    
    public List<String> getNombresJuegos() {
        return nombresJuegos;
    }
    
    public long getSemilla() {
        return semilla;
    }
}
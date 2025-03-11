package Analizador.Interprete.Instruccion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;
import java.util.Map;

/**
 * Clase que representa un juego/match
 */
public class Juego extends Instruccion {
    private String nombre;
    private String[] estrategias;
    private Instruccion rondas;
    private Map<String, Instruccion> configuracionPuntaje;
    
    public Juego(String nombre, String[] estrategias, Instruccion rondas, Map<String, Instruccion> configuracionPuntaje, int linea, int columna) {
        super(linea, columna);
        this.nombre = nombre;
        this.estrategias = estrategias;
        this.rondas = rondas;
        this.configuracionPuntaje = configuracionPuntaje;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        // Registrar el juego en la tabla de símbolos
        System.out.println("Registrando juego: " + nombre + " en la tabla de símbolos");
        tabla.setJuego(nombre, this);
        return nombre;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String[] getEstrategias() {
        return estrategias;
    }
    
    public int getRondas(TablaSimbolos tabla) {
        return ((Number)rondas.ejecutar(tabla)).intValue();
    }
    
    public Map<String, Instruccion> getConfiguracionPuntaje() {
        return configuracionPuntaje;
    }
}
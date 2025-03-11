package Analizador.Interprete.Instruccion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;
import java.util.List;

/**
 * Clase que representa una estrategia completa
 */
public class Estrategia extends Instruccion {
    private String nombre;
    private Instruccion movimientoInicial;
    private List<Instruccion> reglas;
    
    public Estrategia(String nombre, Instruccion movimientoInicial, List<Instruccion> reglas, int linea, int columna) {
        super(linea, columna);
        this.nombre = nombre;
        this.movimientoInicial = movimientoInicial;
        this.reglas = reglas;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        // Registrar la estrategia en la tabla de símbolos
        tabla.setEstrategia(nombre, this);
        return nombre;
    }
    
    /**
     * Ejecuta la estrategia para determinar el siguiente movimiento
     */
    public String ejecutarReglas(TablaSimbolos contexto) {
        // Si es la primera ronda, devolver el movimiento inicial
        int rondaActual = ((Number)contexto.getSimbolo("ROUND_NUMBER").getValor()).intValue();
        if (rondaActual == 1) {
            return (String)movimientoInicial.ejecutar(contexto);
        }
        
        // Evaluar cada regla hasta encontrar una que se cumpla
        for (Instruccion regla : reglas) {
            Object resultado = regla.ejecutar(contexto);
            if (resultado != null) {
                return (String)resultado;
            }
        }
        
        // Si ninguna regla se cumple, devolver el movimiento inicial por defecto
        return (String)movimientoInicial.ejecutar(contexto);
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public Instruccion getMovimientoInicial() {
        return movimientoInicial;
    }
    
    public List<Instruccion> getReglas() {
        return reglas;
    }
}
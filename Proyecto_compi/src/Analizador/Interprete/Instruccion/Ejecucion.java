/*package Analizador.Interprete.Instruccion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;
import Analizador.Interprete.Simbolo.Simbolo;
import java.util.List;


// Clase que representa la instrucción de ejecución de un juego

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
        
        // Importante: Retornar this para que MainSeccion pueda obtener la instancia
        return this;
    }
    
    public List<String> getNombresJuegos() {
        return nombresJuegos;
    }
    
    public long getSemilla() {
        return semilla;
    }
}*/

package Analizador.Interprete.Instruccion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;
import Analizador.Interprete.Simbolo.Simbolo;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase que representa la instrucción de ejecución de un juego
 */
public class Ejecucion extends Instruccion {
    private List<String> nombresJuegos;
    private long semilla;
    
    // Constructor para un solo juego (compatibilidad con código existente)
    public Ejecucion(String nombreJuego, long semilla, int linea, int columna) {
        super(linea, columna);
        this.nombresJuegos = new ArrayList<>();
        this.nombresJuegos.add(nombreJuego);
        this.semilla = semilla;
    }
    
    // Constructor para múltiples juegos
    public Ejecucion(List<String> nombresJuegos, long semilla, int linea, int columna) {
        super(linea, columna);
        this.nombresJuegos = nombresJuegos;
        this.semilla = semilla;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        // Guardar la semilla en la tabla de símbolos
        tabla.setSimbolo(new Simbolo("SEED", semilla));
        
        // Importante: Retornar this para que MainSeccion pueda obtener la instancia
        return this;
    }
    
    public List<String> getNombresJuegos() {
        return nombresJuegos;
    }
    
    public long getSemilla() {
        return semilla;
    }
}
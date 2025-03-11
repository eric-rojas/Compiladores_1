package Analizador.Interprete.Expresion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;

/**
 * Clase que representa una acción en el juego (C o D)
 */
public class AccionLiteral extends Instruccion {
    private String accion; // "C" o "D"
    
    public AccionLiteral(String accion, int linea, int columna) {
        super(linea, columna);
        this.accion = accion;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        return accion;
    }
    
    public String getAccion() {
        return accion;
    }
}
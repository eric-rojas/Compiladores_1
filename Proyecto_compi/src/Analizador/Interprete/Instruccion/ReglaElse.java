package Analizador.Interprete.Instruccion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;

/**
 * Clase que representa una regla else (sin condición)
 */
public class ReglaElse extends Instruccion {
    private Instruccion accion;
    
    public ReglaElse(Instruccion accion, int linea, int columna) {
        super(linea, columna);
        this.accion = accion;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        return accion.ejecutar(tabla);
    }
}
package Analizador.Interprete.Instruccion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;

/**
 * Clase que representa una regla condicional (if-then-else)
 */
public class ReglaCondicional extends Instruccion {
    private Instruccion condicion;
    private Instruccion accionSiVerdadero;
    private Instruccion accionSiFalso;  // Puede ser null si no hay cláusula else
    
    public ReglaCondicional(Instruccion condicion, Instruccion accionSiVerdadero, Instruccion accionSiFalso, int linea, int columna) {
        super(linea, columna);
        this.condicion = condicion;
        this.accionSiVerdadero = accionSiVerdadero;
        this.accionSiFalso = accionSiFalso;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        Object resultadoCondicion = condicion.ejecutar(tabla);
        
        if (resultadoCondicion instanceof Boolean && (Boolean)resultadoCondicion) {
            return accionSiVerdadero.ejecutar(tabla);
        } else if (accionSiFalso != null) {
            return accionSiFalso.ejecutar(tabla);
        }
        
        return null;  // No se cumplió la condición y no hay cláusula else
    }
}
package Analizador.Interprete.LlamadaFuncion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;
import java.util.List;

/**
 * Clase que implementa la función last_move
 */
public class LastMove extends Instruccion {
    private Instruccion historialExp;
    
    public LastMove(Instruccion historialExp, int linea, int columna) {
        super(linea, columna);
        this.historialExp = historialExp;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        List<String> historial = (List<String>)historialExp.ejecutar(tabla);
        
        if (historial.isEmpty()) {
            // Si no hay movimientos previos
            return "C"; // Por defecto, cooperar
        } else {
            return historial.get(historial.size() - 1);
        }
    }
}
package Analizador.Interprete.LlamadaFuncion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;
import java.util.List;

/**
 * Clase que implementa la función get_moves_count
 */
public class GetMovesCount extends Instruccion {
    private Instruccion historialExp;
    private Instruccion movimientoExp;
    
    public GetMovesCount(Instruccion historialExp, Instruccion movimientoExp, int linea, int columna) {
        super(linea, columna);
        this.historialExp = historialExp;
        this.movimientoExp = movimientoExp;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        List<String> historial = (List<String>)historialExp.ejecutar(tabla);
        String movimiento = (String)movimientoExp.ejecutar(tabla);
        
        long count = historial.stream()
                    .filter(m -> m.equals(movimiento))
                    .count();
        
        return (int)count;
    }
}
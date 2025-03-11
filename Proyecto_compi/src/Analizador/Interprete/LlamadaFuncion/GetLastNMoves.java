package Analizador.Interprete.LlamadaFuncion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa la función get_last_n_moves
 */
public class GetLastNMoves extends Instruccion {
    private Instruccion historialExp;
    private Instruccion cantidadExp;
    
    public GetLastNMoves(Instruccion historialExp, Instruccion cantidadExp, int linea, int columna) {
        super(linea, columna);
        this.historialExp = historialExp;
        this.cantidadExp = cantidadExp;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        List<String> historial = (List<String>)historialExp.ejecutar(tabla);
        int cantidad = ((Number)cantidadExp.ejecutar(tabla)).intValue();
        
        List<String> ultimosMovimientos = new ArrayList<>();
        int size = historial.size();
        
        // Obtener los últimos N movimientos
        for (int i = Math.max(0, size - cantidad); i < size; i++) {
            ultimosMovimientos.add(historial.get(i));
        }
        
        return ultimosMovimientos;
    }
}
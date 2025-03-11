package Analizador.Interprete.LlamadaFuncion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;
import java.util.List;

/**
 * Clase que implementa la función get_move
 */
public class GetMove extends Instruccion {
    private Instruccion historialExp;
    private Instruccion indiceExp;
    
    public GetMove(Instruccion historialExp, Instruccion indiceExp, int linea, int columna) {
        super(linea, columna);
        this.historialExp = historialExp;
        this.indiceExp = indiceExp;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        List<String> historial = (List<String>)historialExp.ejecutar(tabla);
        int indice = ((Number)indiceExp.ejecutar(tabla)).intValue();
        
        if (indice >= 0 && indice < historial.size()) {
            return historial.get(indice);
        } else {
            // Error de índice fuera de rango
            return ""; // O manejar el error adecuadamente
        }
    }
}
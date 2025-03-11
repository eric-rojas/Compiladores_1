package Analizador.Interprete.Expresion;

import Analizador.Interprete.Abstracto.Instruccion;

import java.util.List;
import Analizador.Interprete.Simbolo.TablaSimbolos;
/**
 * Clase que representa las variables de historial (opponent_history, self_history)
 */
public class VariableHistorial extends Instruccion {
    public enum TipoHistorial {
        OPONENTE,
        PROPIO,
        GENERAL
    }
    
    private TipoHistorial tipo;
    
    public VariableHistorial(TipoHistorial tipo, int linea, int columna) {
        super(linea, columna);
        this.tipo = tipo;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        switch (tipo) {
            case OPONENTE:
                return tabla.getSimbolo("OPPONENT_HISTORY").getValor();
            case PROPIO:
                return tabla.getSimbolo("SELF_HISTORY").getValor();
            case GENERAL:
                return tabla.getSimbolo("HISTORY").getValor();
            default:
                return null;
        }
    }
}
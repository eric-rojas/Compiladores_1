package Analizador.Interprete.Expresion;

import Analizador.Interprete.Abstracto.Instruccion;

import Analizador.Interprete.Simbolo.TablaSimbolos;

/**
 * Clase que representa las variables relacionadas con rondas (round_number, total_rounds)
 */
public class VariableRonda extends Instruccion {
    public enum TipoRonda {
        NUMERO_ACTUAL,
        TOTAL
    }
    
    private TipoRonda tipo;
    
    public VariableRonda(TipoRonda tipo, int linea, int columna) {
        super(linea, columna);
        this.tipo = tipo;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        switch (tipo) {
            case NUMERO_ACTUAL:
                return tabla.getSimbolo("ROUND_NUMBER").getValor();
            case TOTAL:
                return tabla.getSimbolo("TOTAL_ROUNDS").getValor();
            default:
                return null;
        }
    }
}
package Analizador.Interprete.Expresion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;

/**
 * Clase que representa un valor literal como número, booleano, cadena, etc.
 */
public class Literal extends Instruccion {
    private Object valor;
    private TipoLiteral tipo;
    
    public enum TipoLiteral {
        NUMERO,
        DECIMAL,
        BOOLEANO,
        CADENA
    }
    
    public Literal(Object valor, TipoLiteral tipo, int linea, int columna) {
        super(linea, columna);
        this.valor = valor;
        this.tipo = tipo;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        return valor;
    }
    
    public TipoLiteral getTipo() {
        return tipo;
    }
}
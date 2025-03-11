package Analizador.Interprete.Simbolo;

import Analizador.Interprete.Abstracto.Instruccion;

/**
 * Clase que representa valores nativos como números, cadenas o booleanos
 */
public class Nativo extends Instruccion {
    private Object valor;
    
    public Nativo(Object valor, int linea, int columna) {
        super(linea, columna);
        this.valor = valor;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        return valor;
    }
    
    public Object getValor() {
        return valor;
    }
}
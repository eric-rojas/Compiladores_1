package Analizador.Interprete.Abstracto;

import Analizador.Interprete.Simbolo.TablaSimbolos;
import Analizador.Interprete.Simbolo.Arbol;

public abstract class Instruccion implements NodoAST {
    protected int linea;
    protected int columna;
    
    public Instruccion(int linea, int columna) {
        this.linea = linea;
        this.columna = columna;
    }
    
    @Override
    public int getLinea() {
        return linea;
    }
    
    @Override
    public int getColumna() {
        return columna;
    }
    
    @Override
    public abstract Object ejecutar(TablaSimbolos tabla);
    
    
}
package Analizador.Interprete.Abstracto;
import Analizador.Interprete.Simbolo.TablaSimbolos;

/**
 * Interfaz que define los métodos comunes a todos los nodos del AST
 */
public interface NodoAST {
    /**
     * Devuelve la línea donde se encontró el nodo en el código fuente
     */
    int getLinea();
    
    /**
     * Devuelve la columna donde se encontró el nodo en el código fuente
     */
    int getColumna();
    
    /**
     * Ejecuta el nodo y devuelve el resultado de su ejecución
     */
    Object ejecutar(TablaSimbolos tabla);
}
package Analizador.Interprete.Expresion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;
import Analizador.Interprete.Simbolo.Arbol;
import java.util.List;

/**
 * Clase que maneja la comparación entre el resultado de una función y una lista de valores
 */
public class ComparacionLista extends Instruccion {
    private Instruccion funcion;
    private List<Instruccion> listaValores;
    
    public ComparacionLista(Instruccion funcion, List<Instruccion> listaValores, int linea, int columna) {
        super(linea, columna);
        this.funcion = funcion;
        this.listaValores = listaValores;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        // Ejecutar la función y obtener su resultado
        Object resultadoFuncion = funcion.ejecutar(tabla);
        
        // Verificar que el resultado sea una lista
        if (!(resultadoFuncion instanceof List)) {
            System.err.println("Error: Se esperaba una lista como resultado de la función");
            return false;
        }
        
        List<?> listaResultado = (List<?>)resultadoFuncion;
        
        // Si las listas tienen diferente tamaño, no son iguales
        if (listaResultado.size() != listaValores.size()) {
            return false;
        }
        
        // Comparar elementos uno a uno
        for (int i = 0; i < listaResultado.size(); i++) {
            Object valorResultado = listaResultado.get(i);
            Object valorEsperado = listaValores.get(i).ejecutar(tabla);
            
            // Si algún elemento no coincide, las listas no son iguales
            if (!valorResultado.equals(valorEsperado)) {
                return false;
            }
        }
        
        // Si llegamos aquí, todas las comparaciones fueron exitosas
        return true;
    }
    /* 
    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        return ejecutar(tabla);
    }*/
}
package Analizador.Interprete.Instruccion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;

/**
 * Clase que representa la sección main del programa
 */
public class MainSeccion extends Instruccion {
    private Instruccion ejecucion;
    
    public MainSeccion(Instruccion ejecucion, int linea, int columna) {
        super(linea, columna);
        this.ejecucion = ejecucion;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        // Debe retornar directamente el objeto Ejecucion
        if (ejecucion instanceof Ejecucion) {
            return ejecucion; // Retornar el objeto Ejecucion directamente
        } else {
            // Si necesitamos ejecutar algo primero
            Object resultado = ejecucion.ejecutar(tabla);
            if (resultado instanceof Ejecucion) {
                return resultado;
            } else {
                System.err.println("Error: La instrucción de ejecución no retornó un objeto Ejecucion válido");
                return null;
            }
        }
    }
}
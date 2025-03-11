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
        return ejecucion.ejecutar(tabla);
    }
}

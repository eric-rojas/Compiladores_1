/*package Analizador.Interprete.Instruccion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;


 //* Clase que representa la sección main del programa
 
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
*/

package Analizador.Interprete.Instruccion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase que representa la sección main del programa
 */
public class MainSeccion extends Instruccion {
    private List<Instruccion> ejecuciones;
    
    // Constructor para una sola ejecución (compatibilidad con código existente)
    public MainSeccion(Instruccion ejecucion, int linea, int columna) {
        super(linea, columna);
        this.ejecuciones = new ArrayList<>();
        this.ejecuciones.add(ejecucion);
    }
    
    // Constructor para múltiples ejecuciones
    public MainSeccion(List<Instruccion> ejecuciones, int linea, int columna) {
        super(linea, columna);
        this.ejecuciones = ejecuciones;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        // Ejecutar todas las ejecuciones en orden
        List<Object> resultados = new ArrayList<>();
        for (Instruccion ejecucion : ejecuciones) {
            resultados.add(ejecucion.ejecutar(tabla));
        }
        
        // Devolver la primera ejecución por compatibilidad con código existente
        if (!ejecuciones.isEmpty()) {
            return ejecuciones.get(0);
        } else {
            return null;
        }
    }
    
    public List<Instruccion> getEjecuciones() {
        return ejecuciones;
    }
}
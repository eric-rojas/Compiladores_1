package Analizador.Interprete.Expresion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.LlamadaFuncion.GetLastNMoves;
import Analizador.Interprete.Simbolo.TablaSimbolos;
import java.util.List;

/**
 * Clase que maneja la comparación entre get_last_n_moves y una lista de valores
 */
public class ComparacionListaFuncion extends Instruccion {
    private Instruccion historial;
    private Instruccion cantidad;
    private List<Instruccion> valores;
    
    public ComparacionListaFuncion(Instruccion historial, Instruccion cantidad, List<Instruccion> valores, int linea, int columna) {
        super(linea, columna);
        this.historial = historial;
        this.cantidad = cantidad;
        this.valores = valores;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        try {
            // Primero obtenemos la lista de últimos movimientos
            GetLastNMoves getLastNMoves = new GetLastNMoves(historial, cantidad, this.getLinea(), this.getColumna());
            List<String> ultimosMovimientos = (List<String>)getLastNMoves.ejecutar(tabla);
            
            // Si las listas tienen diferente tamaño, no son iguales
            if (ultimosMovimientos.size() != valores.size()) {
                return false;
            }
            
            // Comparar elementos uno a uno
            for (int i = 0; i < ultimosMovimientos.size(); i++) {
                String movimiento = ultimosMovimientos.get(i);
                Object valorEsperado = valores.get(i).ejecutar(tabla);
                
                if (!movimiento.equals(valorEsperado)) {
                    return false;
                }
            }
            
            // Si llegamos aquí, todas las comparaciones fueron exitosas
            return true;
        } catch (Exception e) {
            System.err.println("Error al comparar get_last_n_moves con lista: " + e.getMessage());
            return false;
        }
    }
}
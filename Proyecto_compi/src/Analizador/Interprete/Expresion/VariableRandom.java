package Analizador.Interprete.Expresion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;
import java.util.Random;

/**
 * Clase que representa la función random
 */
public class VariableRandom extends Instruccion {
    private static Random random = new Random();
    
    public VariableRandom(int linea, int columna) {
        super(linea, columna);
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        // Verificar si hay una semilla en la tabla de símbolos
        if (tabla.existeSimbolo("SEED")) {
            Long semilla = (Long)tabla.getSimbolo("SEED").getValor();
            random.setSeed(semilla);
        }
        return random.nextDouble();
    }
}

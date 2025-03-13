package Analizador.Interprete.Expresion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;
import Analizador.Interprete.Util.RandomGenerator;
import Analizador.Interprete.Util.DeterministicRandomGenerator;

/**
 * Clase que representa la función random
 */
public class VariableRandom extends Instruccion {
    
    public VariableRandom(int linea, int columna) {
        super(linea, columna);
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        // Verificar si hay una semilla en la tabla de símbolos
        if (tabla.existeSimbolo("SEED")) {
            Long semilla = (Long)tabla.getSimbolo("SEED").getValor();
            
            // Usar el generador determinista con la semilla proporcionada
            RandomGenerator generator = DeterministicRandomGenerator.create(semilla);
            return generator.nextDouble();
        } else {
            // Por defecto, usar una semilla fija si no se proporciona
            RandomGenerator generator = DeterministicRandomGenerator.create(42);
            return generator.nextDouble();
        }
    }
}
package Analizador.Interprete.Util;

/**
 * Interfaz que define el contrato para generadores de números aleatorios.
 * Permite a los estudiantes implementar diferentes estrategias si lo desean.
 */
public interface RandomGenerator {
    /**
     * Genera un número decimal aleatorio entre 0.0 y 1.0.
     * @return Número decimal aleatorio
     */
    double nextDouble();
}
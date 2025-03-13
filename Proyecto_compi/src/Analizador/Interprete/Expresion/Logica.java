package Analizador.Interprete.Expresion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;
import java.util.List;

/**
 * Clase que maneja operaciones lógicas y comparaciones entre expresiones
 */
public class Logica extends Instruccion {
    private Instruccion izquierdo;
    private Instruccion derecho;
    private String operador;
    
    public Logica(Instruccion izquierdo, Instruccion derecho, String operador, int linea, int columna) {
        super(linea, columna);
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        this.operador = operador;
    }
    
    @Override
    public Object ejecutar(TablaSimbolos tabla) {
        // Para los operadores lógicos && y ||, aplicamos evaluación de cortocircuito
        if (operador.equals("&&")) {
            // Evaluamos primero el lado izquierdo
            Object valorIzq = izquierdo.ejecutar(tabla);
            //System.out.println("Evaluando operación lógica: " + operador);
            //System.out.println("  Valor izquierdo: " + valorIzq + " (" + (valorIzq != null ? valorIzq.getClass().getName() : "null") + ")");
            
            // Si el lado izquierdo no es booleano o es falso, retornamos falso sin evaluar el lado derecho
            if (!(valorIzq instanceof Boolean)) {
                //System.err.println("Error: Operación && requiere operandos booleanos");
                return false;
            }
            
            if (!(Boolean)valorIzq) {
                // Cortocircuito - no evaluamos el lado derecho si el izquierdo es falso
                return false;
            }
            
            // Si el lado izquierdo es verdadero, evaluamos el lado derecho
            Object valorDer = derecho.ejecutar(tabla);
            //System.out.println("  Valor derecho: " + valorDer + " (" + (valorDer != null ? valorDer.getClass().getName() : "null") + ")");
            
            // Si el lado derecho es una lista, evaluamos si debería ser una comparación
            if (valorDer instanceof List) {
                //System.out.println("Lado derecho es una lista, evaluando si debería ser una comparación");
                // En este caso, probablemente queríamos hacer una comparación y no un AND lógico
                //System.err.println("Error: Operación && requiere operandos booleanos, se encontró una lista");
                return false;
            } else if (valorDer instanceof Boolean) {
                return (Boolean)valorDer; // Ya sabemos que valorIzq es true
            } else {
                //System.err.println("Error: Operación && requiere operandos booleanos");
                return false;
            }
        } else if (operador.equals("||")) {
            // Evaluamos primero el lado izquierdo
            Object valorIzq = izquierdo.ejecutar(tabla);
            //System.out.println("Evaluando operación lógica: " + operador);
            //System.out.println("  Valor izquierdo: " + valorIzq + " (" + (valorIzq != null ? valorIzq.getClass().getName() : "null") + ")");
            
            // Si el lado izquierdo no es booleano, reportamos error
            if (!(valorIzq instanceof Boolean)) {
                //System.err.println("Error: Operación || requiere operandos booleanos");
                return false;
            }
            
            if ((Boolean)valorIzq) {
                // Cortocircuito - no evaluamos el lado derecho si el izquierdo es verdadero
                return true;
            }
            
            // Si el lado izquierdo es falso, evaluamos el lado derecho
            Object valorDer = derecho.ejecutar(tabla);
            //System.out.println("  Valor derecho: " + valorDer + " (" + (valorDer != null ? valorDer.getClass().getName() : "null") + ")");
            
            if (valorDer instanceof Boolean) {
                return (Boolean)valorDer;
            } else {
                //System.err.println("Error: Operación || requiere operandos booleanos");
                return false;
            }
        } else {
            // Para los demás operadores, evaluamos ambos lados normalmente
            Object valorIzq = izquierdo.ejecutar(tabla);
            Object valorDer = derecho.ejecutar(tabla);
            
            // Imprimir información de depuración
            //System.out.println("Evaluando operación lógica: " + operador);
            //System.out.println("  Valor izquierdo: " + valorIzq + " (" + (valorIzq != null ? valorIzq.getClass().getName() : "null") + ")");
            //System.out.println("  Valor derecho: " + valorDer + " (" + (valorDer != null ? valorDer.getClass().getName() : "null") + ")");
            
            switch (operador) {
                case "==":
                    // Comparación especial para listas
                    if (valorIzq instanceof List && valorDer instanceof List) {
                        return compararListas((List<?>)valorIzq, (List<?>)valorDer);
                    } else {
                        // Para otros tipos, usar equals
                        return valorIzq != null && valorDer != null && valorIzq.equals(valorDer);
                    }
                case "!=":
                    // Comparación especial para listas
                    if (valorIzq instanceof List && valorDer instanceof List) {
                        return !compararListas((List<?>)valorIzq, (List<?>)valorDer);
                    } else {
                        // Para otros tipos, usar equals
                        return valorIzq != null && valorDer != null && !valorIzq.equals(valorDer);
                    }
                case "<":
                    return compararValores(valorIzq, valorDer) < 0;
                case ">":
                    return compararValores(valorIzq, valorDer) > 0;
                case "<=":
                    return compararValores(valorIzq, valorDer) <= 0;
                case ">=":
                    return compararValores(valorIzq, valorDer) >= 0;
                default:
                    // Error: operador no reconocido
                    System.err.println("Error: Operador no reconocido: " + operador);
                    return false;
            }
        }
    }
    
    /**
     * Compara dos listas elemento por elemento
     */
    private boolean compararListas(List<?> lista1, List<?> lista2) {
        if (lista1.size() != lista2.size()) {
            return false;
        }
        
        for (int i = 0; i < lista1.size(); i++) {
            Object elem1 = lista1.get(i);
            Object elem2 = lista2.get(i);
            
            if (elem1 == null && elem2 == null) {
                continue; // Ambos son null, considerados iguales
            }
            
            if (elem1 == null || elem2 == null) {
                return false; // Uno es null y el otro no
            }
            
            if (!elem1.equals(elem2)) {
                return false; // Elementos diferentes
            }
        }
        
        return true; // Todas las comparaciones fueron iguales
    }
    
    /**
     * Compara valores numéricos o strings
     */
    private int compararValores(Object v1, Object v2) {
        if (v1 instanceof Number && v2 instanceof Number) {
            double d1 = ((Number)v1).doubleValue();
            double d2 = ((Number)v2).doubleValue();
            return Double.compare(d1, d2);
        } else if (v1 instanceof String && v2 instanceof String) {
            return ((String)v1).compareTo((String)v2);
        } else {
            System.err.println("Error: No se pueden comparar valores de tipos diferentes o no comparables");
            return 0; // Valor por defecto
        }
    }
}
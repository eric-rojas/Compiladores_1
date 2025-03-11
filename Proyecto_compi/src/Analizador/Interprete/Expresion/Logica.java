package Analizador.Interprete.Expresion;

import Analizador.Interprete.Abstracto.Instruccion;
import Analizador.Interprete.Simbolo.TablaSimbolos;
import Analizador.Interprete.Simbolo.TablaSimbolos;

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
        Object valorIzq = izquierdo.ejecutar(tabla);
        Object valorDer = derecho.ejecutar(tabla);
        
        switch (operador) {
            case "&&":
                return (Boolean)valorIzq && (Boolean)valorDer;
            case "||":
                return (Boolean)valorIzq || (Boolean)valorDer;
            case "==":
                return valorIzq.equals(valorDer);
            case "!=":
                return !valorIzq.equals(valorDer);
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
                return null;
        }
    }
    
    private int compararValores(Object v1, Object v2) {
        if (v1 instanceof Number && v2 instanceof Number) {
            double d1 = ((Number)v1).doubleValue();
            double d2 = ((Number)v2).doubleValue();
            return Double.compare(d1, d2);
        }
        // Para otros tipos, puedes implementar comparaciones específicas
        return v1.toString().compareTo(v2.toString());
    }
}
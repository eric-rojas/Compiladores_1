package Analizador.Interprete.Simbolo;

import Analizador.Interprete.Instruccion.Estrategia;
import Analizador.Interprete.Instruccion.Juego;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que maneja la tabla de símbolos
 */
public class TablaSimbolos {
    private Map<String, Simbolo> tabla;
    private Map<String, Estrategia> estrategias;
    private Map<String, Juego> juegos;
    private TablaSimbolos padre;
    
    public TablaSimbolos() {
        this.tabla = new HashMap<>();
        this.estrategias = new HashMap<>();
        this.juegos = new HashMap<>();
    }
    
    public TablaSimbolos(TablaSimbolos padre) {
        this.tabla = new HashMap<>();
        this.estrategias = new HashMap<>();
        this.juegos = new HashMap<>();
        this.padre = padre;
    }
    
    public void setSimbolo(Simbolo simbolo) {
        tabla.put(simbolo.getId(), simbolo);
    }
    
    public boolean existeSimbolo(String id) {
        if (tabla.containsKey(id)) {
            return true;
        }
        return padre != null && padre.existeSimbolo(id);
    }
    
    public Simbolo getSimbolo(String id) {
        if (tabla.containsKey(id)) {
            return tabla.get(id);
        }
        if (padre != null) {
            return padre.getSimbolo(id);
        }
        return null;
    }
    
    public void setEstrategia(String nombre, Estrategia estrategia) {
        estrategias.put(nombre, estrategia);
    }
    
    public boolean existeEstrategia(String nombre) {
        if (estrategias.containsKey(nombre)) {
            return true;
        }
        return padre != null && padre.existeEstrategia(nombre);
    }
    
    public Estrategia getEstrategia(String nombre) {
        if (estrategias.containsKey(nombre)) {
            return estrategias.get(nombre);
        }
        if (padre != null) {
            return padre.getEstrategia(nombre);
        }
        return null;
    }
    
    public void setJuego(String nombre, Juego juego) {
        juegos.put(nombre, juego);
    }
    
    public boolean existeJuego(String nombre) {
        if (juegos.containsKey(nombre)) {
            return true;
        }
        return padre != null && padre.existeJuego(nombre);
    }
    
    public Juego getJuego(String nombre) {
        if (juegos.containsKey(nombre)) {
            return juegos.get(nombre);
        }
        if (padre != null) {
            return padre.getJuego(nombre);
        }
        return null;
    }
    
    public TablaSimbolos getPadre() {
        return padre;
    }
}
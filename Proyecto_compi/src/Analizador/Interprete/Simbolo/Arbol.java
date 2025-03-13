package Analizador.Interprete.Simbolo;
import Analizador.Interprete.Abstracto.Instruccion;
import java.util.ArrayList;

public class Arbol {
  private ArrayList<Instruccion> instrucciones;
  private TablaSimbolos tabla;
  private ArrayList<Instruccion> funciones;

  public Arbol(ArrayList<Instruccion> instrucciones) {
    this.instrucciones = instrucciones;
    this.tabla = new TablaSimbolos(); // Usar constructor sin parámetros
    this.funciones = new ArrayList<Instruccion>();
  }

  public ArrayList<Instruccion> getInstrucciones() { return instrucciones; }

  public TablaSimbolos getTabla() { return tabla; }

  public void agregarFuncion(Instruccion funcion) {
    this.funciones.add(funcion);
  }

  public ArrayList<Instruccion> getFunciones() { return funciones; }
  
  /**
   * Método para ejecutar todas las instrucciones del árbol
   * (reemplaza interpretar por ejecutar)
   */
  public void ejecutar() {
    for (Instruccion instruccion : instrucciones) {
      instruccion.ejecutar(tabla);
    }
  }
}
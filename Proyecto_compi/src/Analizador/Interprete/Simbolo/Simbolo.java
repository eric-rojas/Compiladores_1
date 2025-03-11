package Analizador.Interprete.Simbolo;

public class Simbolo {
  private String id;
  private Object valor;
  public Simbolo(String id, Object valor) {
    this.id = id;
    this.valor = valor;
  }
  public void setId(String id) {
    this.id = id;
  }
  public void setValor(Object valor) {
    this.valor = valor;
  }
  public String getId() {
    return id;
  }
  public Object getValor() {
    return valor;
  }
}

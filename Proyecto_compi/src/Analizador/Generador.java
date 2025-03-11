
package Analizador;

public class Generador {

    public static void main(String[] args) {
        Generar();
    }

    public static void Generar() {
        try {
            String ruta = "Proyecto_compi/src/Analizador/";
            String[] Flex = {ruta + "lexico.flex", "-d", ruta};
            jflex.Main.generate(Flex);
            String[] Cup = {"-destdir", ruta, "-parser", "parser", ruta + "parser.cup"};
            java_cup.Main.main(Cup);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




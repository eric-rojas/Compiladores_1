package Analizador;

import java.io.File;
import java.io.FileReader;
import java_cup.runtime.Symbol;
import Analizador.Interprete.Simbolo.Arbol;

public class TestParser {
    public static void main(String[] args) {
        try {
            String ruta = "Proyecto_compi/src/Analizador/entrada.txt"; // Cambia esto a la ruta de tu archivo
            File file = new File(ruta);
            
            // Crear analizador léxico
            Scanner lexer = new Scanner(new FileReader(file));
            
            // Crear parser
            parser p = new parser(lexer);
            
            // Parsear el archivo
            Symbol s = p.parse();
            
            // Obtener el AST
            Arbol ast = (Arbol) s.value;
            
            System.out.println("Análisis completado exitosamente.");
            System.out.println("Número de instrucciones: " + ast.getInstrucciones().size());
            
            // Ejecutar el árbol
            ast.ejecutar();
            
            System.out.println("Ejecución completada.");
            
        } catch (Exception e) {
            System.err.println("Error durante el análisis: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
package Analizador;

import java.io.File;
import java.io.FileReader;
import java_cup.runtime.Symbol;
import Analizador.Interprete.Abstracto.Instruccion;
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
            
            if (ast != null) {
                System.out.println("Análisis completado exitosamente.");
                System.out.println("Número de instrucciones: " + ast.getInstrucciones().size());
                
                // Imprimir información sobre las instrucciones
                for (int i = 0; i < ast.getInstrucciones().size(); i++) {
                    Instruccion inst = ast.getInstrucciones().get(i);
                    System.out.println("Instrucción " + i + ": " + inst.getClass().getName());
                }
                
                // Crear y ejecutar el simulador
                Simulador simulador = new Simulador(ast);
                simulador.ejecutar();
            } else {
                System.err.println("Error: No se pudo construir el AST");
            }
            
        } catch (Exception e) {
            System.err.println("Error durante el análisis: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
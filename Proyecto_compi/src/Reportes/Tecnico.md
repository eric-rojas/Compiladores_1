# Manual Técnico - Compilador del Dilema del Prisionero

## Información General

**Nombre del Proyecto:** Compilador para la simulación del Dilema del Prisionero  
**Autor:** Eric David Rojas De León  

## 1. Introducción

Este manual técnico documenta la implementación de un compilador e intérprete para un lenguaje específico diseñado para simular estrategias en el Dilema del Prisionero. El proyecto permite a los usuarios definir estrategias personalizadas, configurar partidas entre estas estrategias y ejecutarlas con diferentes parámetros.

El Dilema del Prisionero es un concepto clásico de la teoría de juegos que modela situaciones donde dos participantes deben decidir entre cooperar o traicionar, recibiendo diferentes recompensas según la combinación de sus decisiones.

## 2. Tecnologías y Herramientas Utilizadas

### 2.1 Lenguajes de Programación
- **Java:** Lenguaje principal para el desarrollo del compilador e intérprete.

### 2.2 Herramientas de Generación de Compiladores
- **JFlex:** Generador de analizadores léxicos para Java.
- **CUP (Constructor of Useful Parsers):** Herramienta para la generación de analizadores sintácticos en Java.

### 2.3 Entorno de Desarrollo
- **IDE:** VSCode
- **JDK:** Java Development Kit 11+

## 3. Arquitectura del Sistema

El compilador sigue una arquitectura típica de procesamiento de lenguajes con las siguientes fases:

1. **Análisis Léxico:** Descompone el código fuente en tokens.
2. **Análisis Sintáctico:** Verifica la estructura gramatical y construye un árbol de sintaxis abstracta (AST).
3. **Interpretación:** Ejecuta el programa recorriendo el AST.
4. **Simulación:** Ejecuta las partidas del Dilema del Prisionero según las estrategias definidas.

La arquitectura del proyecto está organizada en los siguientes paquetes y clases:

```
src
|---Analizador
|------Interprete
|---------Abstracto
|------------Instruccion.java (Clase base abstracta para representar instrucciones en el lenguaje)
|------------NodoAST.java (Interfaz para todos los nodos del AST)
|---------Expresion
|------------Logica.java (Clase que maneja operaciones lógicas y comparaciones entre expresiones)
|------------VariableHistorial.java (Representa historiales como opponent_history, self_history)
|------------VariableRandom.java (Implementa la variable random)
|------------VariableRonda.java (Maneja round_number y total_rounds)
|------------ComparacionListaFuncion.java (Maneja comparaciones de listas de movimientos)
|---------Instruccion
|------------Estrategia.java (Representa una definición de estrategia)
|------------ReglaCondicional.java (Representa una regla if-then)
|------------ReglaElse.java (Representa una regla else)
|------------Juego.java (Representa la configuración de un juego/match)
|------------MainSeccion.java (Representa la sección main)
|------------Ejecucion.java (Representa la instrucción run dentro de main)
|---------LlamadaFuncion
|------------GetMovesCount.java (Implementa la función get_moves_count)
|------------GetLastNMoves.java (Implementa la función get_last_n_moves)
|------------GetMove.java (Implementa la función get_move)
|------------LastMove.java (Implementa la función last_move)
|---------Simbolo
|------------Arbol.java (Representa el árbol sintáctico completo)
|------------Nativo.java (Representa valores primitivos)
|------------Simbolo.java (Representa una entrada en la tabla de símbolos)
|------------TablaSimbolos.java (Almacena variables, estrategias y juegos)
|---------Util
|------------RandomGenerator.java (Interfaz para generadores de números aleatorios)
|------------DeterministicRandomGenerator.java (Implementación determinista del generador aleatorio)
|------Simulador.java (Clase que ejecuta las simulaciones)
|------lexico.flex (Definición del analizador léxico)
|------parser.cup (Definición del analizador sintáctico)
|---Interfaz.java (Interfaz gráfica del sistema)
|---Main.java (Punto de entrada de la aplicación)
```

## 4. Componentes Principales

### 4.1 Analizador Léxico (Scanner.java generado por JFlex)

El analizador léxico procesa el texto de entrada y lo convierte en tokens que representan elementos del lenguaje como palabras clave, identificadores, operadores y literales.

#### Tokens Principales
- Palabras reservadas: `strategy`, `match`, `main`, `initial`, `rules`, etc.
- Identificadores: nombres de estrategias definidas por el usuario
- Operadores: `&&`, `||`, `!`, `==`, `!=`, `>`, `<`, `>=`, `<=`
- Literales: acciones `C` (cooperar) y `D` (delatar), números, booleanos

### 4.2 Analizador Sintáctico (parser.java generado por CUP)

El analizador sintáctico verifica que las secuencias de tokens sigan la gramática del lenguaje y construye el árbol de sintaxis abstracta (AST).

#### Producciones Principales
- Definición de estrategias
- Definición de reglas condicionales
- Definición de juegos/partidas
- Configuración de la sección main

### 4.3 Árbol de Sintaxis Abstracta (AST)

El AST se construye durante el análisis sintáctico y representa la estructura del programa. Cada nodo del árbol representa una construcción del lenguaje y está implementado como una subclase de `Instruccion`.

### 4.4 Tabla de Símbolos

La tabla de símbolos almacena información sobre los identificadores declarados en el programa, incluyendo estrategias y juegos.

### 4.5 Intérprete

El intérprete ejecuta el programa recorriendo el AST y evaluando cada nodo.

### 4.6 Simulador

El simulador ejecuta las partidas del Dilema del Prisionero según las estrategias definidas y genera resultados.

## 5. Flujo de Ejecución

1. **Inicialización:**
   - Se carga y analiza el archivo de entrada.
   - Se construye el AST.
   - Se inicializa la tabla de símbolos.

2. **Ejecución:**
   - Se ejecutan todas las instrucciones para registrar estrategias y juegos.
   - Se encuentra la sección main y se ejecutan las instrucciones dentro de ella.
   - Para cada juego especificado, se simula la partida:
     - Se inicializan los historiales de los jugadores.
     - Para cada ronda:
       - Se evalúan las estrategias de ambos jugadores.
       - Se calculan los puntajes según las acciones.
       - Se actualizan los historiales.
     - Se muestran los resultados finales.

## 6. Funcionalidades Principales

### 6.1 Definición de Estrategias

Las estrategias se definen mediante reglas condicionales que determinan la acción a tomar en cada ronda.

```
strategy TitForTat {
    initial: C
    rules: [
        else last_move(opponent_history)
    ]
}
```

### 6.2 Funciones del Sistema

- `get_move(history, n)`: Obtiene la acción en la posición n del historial.
- `last_move(history)`: Obtiene la última acción del historial.
- `get_moves_count(history, action)`: Cuenta cuántas veces aparece una acción en el historial.
- `get_last_n_moves(history, n)`: Obtiene las últimas n jugadas del historial.

### 6.3 Estados del Sistema

- `round_number`: Número de la ronda actual.
- `opponent_history`: Historial de movimientos del oponente.
- `self_history`: Historial de movimientos propios.
- `total_rounds`: Número total de rondas en la partida.
- `random`: Generador de números aleatorios determinista.

### 6.4 Definición de Partidas

Cada partida especifica las estrategias participantes, el número de rondas y el sistema de puntuación.

```
match LastMoveOpponent {
    players strategies: [TitForTat, Joss]
    rounds: 28
    scoring: {
        mutual cooperation: 3,
        mutual defection: 2,
        betrayal reward: 6,
        betrayal punishment: 1
    }
}
```

### 6.5 Sección Main

La sección main define qué partidas ejecutar y con qué semilla aleatoria.

```
main {
    run [BestTester, LastMoveOpponent] with {
        seed: 80
    }
}
```

## 7. Métodos y Funciones Importantes

### 7.1 Clase `Simulador`

#### Método `ejecutar()`
Coordina la ejecución de todas las partidas definidas en la sección main.

```java
public void ejecutar() {
    // Ejecutar todas las instrucciones para registrar estrategias y juegos
    for (Instruccion instruccion : ast.getInstrucciones()) {
        instruccion.ejecutar(tablaGlobal);
    }
    
    // Buscar la sección main
    MainSeccion seccionMain = null;
    for (Instruccion instruccion : ast.getInstrucciones()) {
        if (instruccion instanceof MainSeccion) {
            seccionMain = (MainSeccion) instruccion;
            break;
        }
    }
    
    // Ejecutar las partidas definidas en la sección main
    // ...
}
```

#### Método `simularJuego(Juego juego, long semilla)`
Simula una partida entre dos estrategias.

```java
public void simularJuego(Juego juego, long semilla) {
    // Obtener estrategias
    String[] nombresEstrategias = juego.getEstrategias();
    Estrategia estrategia1 = tablaGlobal.getEstrategia(nombresEstrategias[0]);
    Estrategia estrategia2 = tablaGlobal.getEstrategia(nombresEstrategias[1]);
    
    // Inicializar historiales y puntuaciones
    List<String> historialJugador1 = new ArrayList<>();
    List<String> historialJugador2 = new ArrayList<>();
    int puntajeJugador1 = 0;
    int puntajeJugador2 = 0;
    
    // Inicializar generador aleatorio
    RandomGenerator random = DeterministicRandomGenerator.create(semilla);
    
    // Simular rondas
    for (int ronda = 0; ronda < numRondas; ronda++) {
        // Crear contextos para cada jugador
        // Ejecutar estrategias
        // Actualizar historiales y puntuaciones
        // ...
    }
    
    // Mostrar resultados
    // ...
}
```

### 7.2 Clase `Estrategia`

#### Método `ejecutarReglas(TablaSimbolos contexto)`
Evalúa las reglas de una estrategia para determinar la acción a tomar.

```java
public String ejecutarReglas(TablaSimbolos contexto) {
    // Si es la primera ronda, devolver el movimiento inicial
    int rondaActual = ((Number)contexto.getSimbolo("ROUND_NUMBER").getValor()).intValue();
    if (rondaActual == 0) {
        return (String)movimientoInicial.ejecutar(contexto);
    }
    
    // Evaluar cada regla en cascada
    for (Instruccion regla : reglas) {
        Object resultado = regla.ejecutar(contexto);
        if (resultado != null) {
            return (String)resultado;
        }
    }
    
    // Si ninguna regla se cumple, situación no esperada
    return "C"; // Valor por defecto
}
```

### 7.3 Clase `Logica`

#### Método `ejecutar(TablaSimbolos tabla)`
Evalúa operaciones lógicas y comparaciones.

```java
@Override
public Object ejecutar(TablaSimbolos tabla) {
    if (operador.equals("&&")) {
        // Evaluación cortocircuitada para AND
        Object valorIzq = izquierdo.ejecutar(tabla);
        if (!(valorIzq instanceof Boolean) || !((Boolean)valorIzq)) {
            return false;
        }
        Object valorDer = derecho.ejecutar(tabla);
        if (valorDer instanceof Boolean) {
            return (Boolean)valorDer;
        } else {
            return false;
        }
    } else if (operador.equals("||")) {
        // Evaluación cortocircuitada para OR
        // ...
    } else {
        // Evaluación normal para comparaciones
        Object valorIzq = izquierdo.ejecutar(tabla);
        Object valorDer = derecho.ejecutar(tabla);
        
        switch (operador) {
            case "==":
                // Manejar comparación de igualdad
                // ...
            case "!=":
                // ...
            // Otros casos
        }
    }
}
```

### 7.4 Clase `LastMove`

#### Método `ejecutar(TablaSimbolos tabla)`
Obtiene el último movimiento de un historial.

```java
@Override
public Object ejecutar(TablaSimbolos tabla) {
    List<String> historial = (List<String>)historialExp.ejecutar(tabla);
    
    if (historial.isEmpty()) {
        // Comportamiento para historial vacío
        return "C"; // Valor por defecto
    } else {
        return historial.get(historial.size() - 1);
    }
}
```

## 8. Manejo de Errores

### 8.1 Errores Léxicos

Los errores léxicos se detectan cuando un carácter o secuencia de caracteres no coincide con ningún patrón definido. Se registran en una lista y se muestran en el reporte de errores.

```java
// En lexico.flex
public ArrayList<String> errores = new ArrayList<>();
// ...
. { errores.add("Error en la línea " + yyline + " columna " + yycolumn + ": " + yytext());}
```

### 8.2 Errores Sintácticos

Los errores sintácticos se detectan cuando una secuencia de tokens no sigue las reglas gramaticales definidas.

```java
// En parser.cup
public void syntax_error(Symbol s) {
    System.out.println("Error de sintaxis en línea " + s.left + ", columna " + s.right + ": " + s.value);
}

public void unrecovered_syntax_error(Symbol s) {
    System.out.println("Error irrecuperable de sintaxis en línea " + s.left + ", columna " + s.right + ": " + s.value);
}
```

### 8.3 Errores de Ejecución

Los errores de ejecución (como acceder más allá del historial disponible) se manejan terminando la ejecución inmediatamente, según lo especificado en el enunciado del proyecto.

## 9. Interfaz Gráfica

La interfaz gráfica proporciona un entorno integrado para la edición, ejecución y visualización de resultados.

### 9.1 Componentes Principales

- Área de entrada: Editor de texto para el código fuente.
- Área de salida: Muestra los resultados de la ejecución.
- Área de reportes: Muestra reportes de tokens y errores.
- Menú: Opciones para abrir archivos, guardar, ejecutar y generar reportes.

### 9.2 Funcionalidades

- **Nuevo:** Crea un nuevo archivo.
- **Abrir:** Abre un archivo existente.
- **Guardar:** Guarda el archivo actual.
- **Ejecutar:** Analiza y ejecuta el código en el área de entrada.
- **Reportes:** Genera reportes de tokens y errores.

## 10. Consideraciones de Implementación

### 10.1 Generador Aleatorio Determinista

Para garantizar la reproducibilidad de los resultados, se utiliza un generador de números aleatorios determinista.

```java
/**
 * Interfaz que define el contrato para generadores de números aleatorios.
 */
interface RandomGenerator {
    /**
     * Genera un número decimal aleatorio entre 0.0 y 1.0.
     * @return Número decimal aleatorio
     */
    double nextDouble();
}

/**
 * Implementación determinista de un generador de números aleatorios.
 */
class DeterministicRandomGenerator implements RandomGenerator {
    private final long originalSeed;
    private Random random;
    
    private DeterministicRandomGenerator(long seed) {
        this.originalSeed = seed;
        this.random = new Random(seed);
    }
    
    public static RandomGenerator create(long seed) {
        return new DeterministicRandomGenerator(seed);
    }
    
    @Override
    public double nextDouble() {
        return random.nextDouble();
    }
}
```

### 10.2 Evaluación de Reglas en Cascada

Las reglas en una estrategia se evalúan en cascada, deteniéndose en la primera que se cumple.

### 10.3 Manejo de Casos Especiales

- Historia vacía en la primera ronda
- Evaluación de expresiones complejas
- Comparación de listas de movimientos

## 11. Conclusiones y Recomendaciones

### 11.1 Conclusiones

El compilador e intérprete implementado proporciona una plataforma completa para definir y ejecutar estrategias en el contexto del Dilema del Prisionero. La arquitectura modular facilita la extensión y mantenimiento del sistema.

### 11.2 Recomendaciones para Mantenimiento

- Mantener la coherencia en la evaluación de reglas y funciones del sistema.
- Asegurar que el generador aleatorio siga siendo determinista para garantizar la reproducibilidad.
- Considerar agregar más validaciones para evitar errores de usuario.

## 12. Apéndice: Gramática del Lenguaje

```
// Definición de estrategia
estrategia ::= STRATEGY IDENTIFICADOR LLAVEIZQ definiciones LLAVEDER

// Definiciones
definiciones ::= definicion_estrategia rules

// Definición inicial
definicion_estrategia ::= INITIAL DOSPUNTOS expresion

// Reglas
rules ::= RULES DOSPUNTOS CORIZQ lista_reglas CORDER

// Lista de reglas
lista_reglas ::= lista_reglas COMA reglas
               | reglas

// Regla individual
reglas ::= IF expresion THEN expresion
         | ELSE expresion

// Expresiones
expresion ::= NUMERO
            | DECIMAL
            | ACC
            | BOOLEANO
            | RANDOM
            | OPPONENT_HISTORY
            | SELF_HISTORY
            | TOTAL_ROUNDS
            | ROUND_NUMBER
            | HISTORY
            | llamada_funcion
            | expresion MENORIGUAL expresion
            | expresion MAYORIGUAL expresion
            | expresion IGUAL expresion
            | expresion MENOR expresion
            | expresion MAYOR expresion
            | expresion DIFERENTE expresion
            | expresion AND expresion
            | expresion OR expresion
            | expresion NOT expresion

// Llamadas a funciones
llamada_funcion ::= GET_MOVES_COUNT PARENTESISIZQ expresion COMA expresion PARENTESISDER
                  | GET_LAST_N_MOVES PARENTESISIZQ expresion COMA expresion PARENTESISDER
                  | GET_MOVE PARENTESISIZQ expresion COMA expresion PARENTESISDER
                  | LAST_MOVE PARENTESISIZQ expresion PARENTESISDER

// Definición de juego
juego ::= MATCH IDENTIFICADOR definicion_juego

// Definición del juego
definicion_juego ::= LLAVEIZQ lista_definicion_juego LLAVEDER

// Lista de definiciones de juego
lista_definicion_juego ::= lista_definicion_juego definicion_juego_elemento
                        | definicion_juego_elemento

// Elemento de definición de juego
definicion_juego_elemento ::= PLAYERS STRATEGIES DOSPUNTOS CORIZQ IDENTIFICADOR COMA IDENTIFICADOR CORDER
                          | ROUNDS DOSPUNTOS expresion
                          | SCORING DOSPUNTOS LLAVEIZQ parametros LLAVEDER

// Parámetros de puntuación
parametros ::= parametro
             | parametros COMA parametro

// Parámetro individual
parametro ::= MUTUAL COOPERATION DOSPUNTOS expresion
            | MUTUAL DEFECTION DOSPUNTOS expresion
            | BETRAYAL REWARD DOSPUNTOS expresion
            | BETRAYAL PUNISHMENT DOSPUNTOS expresion

// Sección main
main_section ::= MAIN LLAVEIZQ lista_ejecuciones LLAVEDER

// Lista de ejecuciones
lista_ejecuciones ::= lista_ejecuciones ejecucion
                    | ejecucion

// Ejecución individual
ejecucion ::= RUN CORIZQ lista_identificadores CORDER WITH LLAVEIZQ SEED DOSPUNTOS NUMERO LLAVEDER

// Lista de identificadores
lista_identificadores ::= lista_identificadores COMA IDENTIFICADOR
                        | IDENTIFICADOR
```


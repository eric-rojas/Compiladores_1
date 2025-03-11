==========================================================================================================
como es la entrada?
==========================================================================================================
```
strategy Graaskamp {
    initial: D
    rules: [
        if round_number <= 2 then D,
        if round_number == 3 && get_moves_count(opponent_history, D) == 2 then C,
        if round_number > 3 && get_last_n_moves(opponent_history, 2) == [D, D] then D,
        else last_move(opponent_history)
    ]
}

strategy Random {
    initial: C
    rules: [
        if random < 0.5 then C,
        else D
    ]
}

match GraaskampvsRandom {
    players strategies: [Graaskamp, Random]
    rounds: 100
    scoring: {
        mutual cooperation: 3,
        mutual defection: 1,
        betrayal reward: 5,
        betrayal punishment: 0
    }
}

main {
    run [GraaskampvsRandom] with {
        seed: 42
    }
}

```


==========================================================================================================
como es la gramatica basica que se creo?
==========================================================================================================
```
// 6. Producciones (Reglas de la gramática)
start with ini;

// 6.1 Inicio del programa
ini ::= entrada:e  ;

// ArrayList<Instruccion>
entrada ::= entrada:e lista_estrategias:e1 
            | lista_estrategias:e ;

// de tipo Instruccion
lista_estrategias ::= estrategia:e 
                    | juego
                    | main_section;



estrategia ::= STRATEGY:e IDENTIFICADOR LLAVEIZQ definiciones:e1 LLAVEDER ;

definiciones ::= definicion_estrategia:e rules:e1 ;

definicion_estrategia ::= INITIAL DOSPUNTOS expresion:e ;

rules ::= RULES DOSPUNTOS CORIZQ lista_reglas:e1 CORDER ;



lista_reglas ::= lista_reglas:e1 COMA reglas:e2
               | reglas:e ;
            

reglas ::= IF expresion:e1 THEN expresion:e2 
         | ELSE expresion:a ;





// Definición de expresiones
expresion ::= NUMERO:e 
            | DECIMAL:e 
            | ACC:e 
            | BOOLEANO: e 
            | RANDOM:e
            | OPPONENT_HISTORY:e
            | SELF_HISTORY:e
            | TOTAL_ROUNDS:e
            | ROUND_NUMBER:e
            | HISTORY:e
            | llamada_funcion:e
            | expresion:e1 MENORIGUAL expresion:e2
            | expresion:e1 MAYORIGUAL expresion:e2
            | expresion:e1 IGUAL expresion:e2
            | expresion:e1 MENOR expresion:e2
            | expresion:e1 MAYOR expresion:e2
            | expresion:e1 DIFERENTE expresion:e2
            | expresion:e1 AND expresion:e2
            | expresion:e1 OR expresion:e2
            | expresion:e1 NOT expresion:e2;

lista_expresiones ::= lista_expresiones COMA expresion
                    | expresion;

// Definición de llamadas a funciones
llamada_funcion ::= GET_MOVES_COUNT:e PARENTESISIZQ expresion:e1 COMA expresion:e2 PARENTESISDER
                  | GET_LAST_N_MOVES:e PARENTESISIZQ expresion:e1 COMA expresion:e2 PARENTESISDER IGUAL CORIZQ lista_expresiones CORDER
                  | GET_MOVE:e PARENTESISIZQ expresion:e1 COMA  expresion:e2 PARENTESISDER
                  | LAST_MOVE:e PARENTESISIZQ expresion:e1 PARENTESISDER ;


// 6.3 Definición de juegos (Match)

juego ::= MATCH IDENTIFICADOR definicion_juego:e ;



definicion_juego ::= LLAVEIZQ lista_definicion_juego:e LLAVEDER;

// Lista de definiciones del juego (pueden ser múltiples)
lista_definicion_juego ::= lista_definicion_juego:e1 definicion_juego_elemento:e2
                        | definicion_juego_elemento:e ;
                          


definicion_juego_elemento ::= PLAYERS STRATEGIES DOSPUNTOS CORIZQ IDENTIFICADOR:e1 COMA IDENTIFICADOR:e2 CORDER 
                          | ROUNDS DOSPUNTOS expresion:e
                          | SCORING DOSPUNTOS LLAVEIZQ parametros LLAVEDER ;


parametros ::= parametro  
             | parametros COMA parametro  ;


parametro ::= MUTUAL COOPERATION DOSPUNTOS expresion
               
            | MUTUAL DEFECTION DOSPUNTOS expresion
               
            | BETRAYAL REWARD DOSPUNTOS expresion
               
            | BETRAYAL PUNISHMENT DOSPUNTOS expresion
              ;


// 6.4 Sección Main
main_section ::= MAIN LLAVEIZQ ejecucion:e LLAVEDER ;

ejecucion ::= RUN CORIZQ IDENTIFICADOR:e1 CORDER WITH LLAVEIZQ SEED DOSPUNTOS NUMERO:e2 LLAVEDER ;
```



==========================================================================================================
Arquitectura que se usa
==========================================================================================================

```
src
|---Analizador
|------Interprete
|---------Abstracto
|------------Instruccion.java (Clase base abstracta para representar instrucciones en el lenguaje)
|------------NodoAST.java (clase para todos los nodos del AST)
|---------Expresion
|------------Logica.java (Clase que maneja operaciones lógicas y comparaciones entre expresiones)
|------------Aritmetica.java (Para operaciones aritméticas si las necesitas)
|------------Literal.java (Para representar literales como números, strings, etc.)
|------------AccionLiteral.java (Para representar acciones como C o D)
|------------Random.java (Para la función random)
|------------Historial.java (Para funciones de historial como opponent_history, self_history)
|------------Ronda.java (Para round_number, total_rounds)
|---------Instruccion
|------------Estrategia.java (Representa una definición de estrategia)
|------------ReglaCondicional.java (Representa una regla con if-then-else)
|------------ReglaSimple.java (Representa una regla match-then)
|------------Juego.java (Representa la definición de un juego/match)
|------------Main.java (Representa la sección main con la ejecución)
|------------ConfiguracionJuego.java (Para configuraciones como players, rounds, scoring)
|---------LlamadaFuncion
|------------GetMovesCount.java (Implementa la función get_moves_count)
|------------GetLastNMoves.java (Implementa la función get_last_n_moves)
|------------GetMove.java (Implementa la función get_move)
|------------LastMove.java (Implementa la función last_move)
|---------Simbolo
|------------Arbol.java (Representa el árbol sintáctico del programa y almacena instrucciones y funciones)
|------------Nativo.java (Clase que representa valores nativos como números, cadenas o booleanos)
|------------Simbolo.java (Clase que representa un símbolo en la tabla de símbolos, como una variable o función)
|------------TablaSimbolos.java (Clase que maneja la tabla de símbolos, almacenando identificadores y sus valores)
|------Generador.java (Clase que genera código intermedio o ejecutable a partir del árbol sintáctico)
|------Simulador.java (Clase para simular la ejecución del juego con las estrategias definidas)
|------lexico.flex (Archivo de configuración para el analizador léxico, define los tokens del lenguaje)
|------parser.cup (Archivo de configuración para el analizador sintáctico, define la gramática del lenguaje)
|---InterfazDMDP.java (Interfaz gráfica)
|---Main.java (Punto de entrada de la aplicación)
```




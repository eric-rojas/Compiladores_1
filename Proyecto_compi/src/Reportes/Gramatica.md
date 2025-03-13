# Gramática para el Lenguaje del Dilema del Prisionero

## Programa Principal

```bnf
<programa> ::= <lista_elementos>

<lista_elementos> ::= <lista_elementos> <elemento> | <elemento>

<elemento> ::= <estrategia> | <juego> | <seccion_main>
```

## Definición de Estrategias

```bnf
<estrategia> ::= **strategy** <identificador> **{** <definicion_inicial> <reglas> **}**

<definicion_inicial> ::= **initial:** <accion> 

<reglas> ::= **rules:** **[** <lista_reglas> **]**

<lista_reglas> ::= <lista_reglas> **,** <regla> | <regla>

<regla> ::= <regla_condicional> | <regla_else>

<regla_condicional> ::= **if** <expresion> **then** <accion>

<regla_else> ::= **else** <accion>
```

## Expresiones

```bnf
<expresion> ::= <expresion_logica> | <expresion_comparacion> | <expresion_terminal>

<expresion_logica> ::= <expresion> **&&** <expresion> 
                     | <expresion> **||** <expresion> 
                     | **!** <expresion>

<expresion_comparacion> ::= <expresion> **==** <expresion>
                          | <expresion> **!=** <expresion>
                          | <expresion> **<** <expresion>
                          | <expresion> **>** <expresion>
                          | <expresion> **<=** <expresion>
                          | <expresion> **>=** <expresion>
                          | <llamada_funcion> **==** **[** <lista_expresiones> **]**

<expresion_terminal> ::= <numero> 
                        | <decimal> 
                        | <accion> 
                        | <booleano> 
                        | <variable_sistema> 
                        | <llamada_funcion> 
                        | **(**<expresion>**)**

<variable_sistema> ::= **round_number** | **random** | **opponent_history** | **self_history** | **total_rounds** | **history**

<llamada_funcion> ::= **get_moves_count** **(** <expresion> **,** <expresion> **)**
                     | **get_last_n_moves** **(** <expresion> **,** <expresion> **)**
                     | **get_move** **(** <expresion> **,** <expresion> **)**
                     | **last_move** **(** <expresion> **)**

<lista_expresiones> ::= <lista_expresiones> **,** <expresion> | <expresion>
```

## Definición de Juegos

```bnf
<juego> ::= **match** <identificador> **{** <lista_definicion_juego> **}**

<lista_definicion_juego> ::= <lista_definicion_juego> <definicion_juego_elemento> | <definicion_juego_elemento>

<definicion_juego_elemento> ::= <definicion_players>
                              | <definicion_rounds>
                              | <definicion_scoring>

<definicion_players> ::= **players** **strategies:** **[** <identificador> **,** <identificador> **]**

<definicion_rounds> ::= **rounds:** <expresion>

<definicion_scoring> ::= **scoring:** **{** <parametros> **}**

<parametros> ::= <parametros> **,** <parametro> | <parametro>

<parametro> ::= **mutual** **cooperation:** <expresion>
               | **mutual** **defection:** <expresion>
               | **betrayal** **reward:** <expresion>
               | **betrayal** **punishment:** <expresion>
```

## Sección Main

```bnf
<seccion_main> ::= **main** **{** <lista_ejecuciones> **}**

<lista_ejecuciones> ::= <lista_ejecuciones> <ejecucion> | <ejecucion>

<ejecucion> ::= **run** **[** <lista_identificadores> **]** **with** **{** <definicion_seed> **}**

<lista_identificadores> ::= <lista_identificadores> **,** <identificador> | <identificador>

<definicion_seed> ::= **seed:** <numero>
```

## Terminales Básicos

```bnf
<identificador> ::= **IDENTIFICADOR**  # Secuencia de letras, dígitos y _ que comienza con letra

<numero> ::= **NUMERO**  # Secuencia de dígitos

<decimal> ::= **DECIMAL**  # Número con punto decimal

<booleano> ::= **true** | **false**

<accion> ::= **C** | **D**  # Cooperar (C) o Delatar (D)
```

## Comentarios

```bnf
<comentario> ::= <comentario_linea> | <comentario_bloque>

<comentario_linea> ::= **//** <texto_hasta_fin_linea>

<comentario_bloque> ::= **/*** <texto_hasta_cierre> ***/
```


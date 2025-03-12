package Analizador;

import java_cup.runtime.Symbol;
import java.util.ArrayList;

%%

%{
   // Código Java que te sirva al principio del archivo
   // normalmente para inicialzar variables
%}


%init{
   yyline = 1;
   yycolumn = 1;
   // inicializar en el constructor del analizador
%init}

// Definicion de reglas especificas de flex
//%char
%full
%cup
%class Scanner
%public
%line
%column


/*

// esta es la entrada de ejemplo que se va a analizar

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

*/

// Definicion de tokens

// Palabras clave
STRATEGY = "strategy"
MATCH = "match"
MAIN = "main"
INITIAL = "initial"
RULES = "rules"
PLAYERS = "players"
STRATEGIES = "strategies"
ROUNDS = "rounds"
SCORING = "scoring"
RUN = "run"
WITH = "with"
SEED = "seed"
ACC = "D" | "C"

// Operadores... siguendo la lectura de arriba hacia abajo, colo camos < y > al final para que no se confunda con los operadores <= y >=
MENORIGUAL = "<="
MAYORIGUAL = ">="
IGUAL = "=="
DIFERENTE = "!="
ASIGNACION = "="
MENOR = "<"
MAYOR = ">"
AND = "&&"
OR = "||"
NOT = "!"

// Operadores if then...
IF = "if"
THEN = "then"
ELSE = "else"

// acciones
MUTUAL = "mutual"
DEFECTION = "defection"
REWARD = "reward"
PUNISHMENT = "punishment"
BETRAYAL = "betrayal"
COOPERATION = "cooperation"
DEFECTION = "defection"

// Delimitadores
LLAVEIZQ = "{"
LLAVEDER = "}"
CORIZQ = "["
CORDER = "]"
PARENTESISIZQ = "("
PARENTESISDER = ")"
COMA = ","
DOSPUNTOS = ":"

PUNTO = "."

// valores
CADENA = "\""[^\"\n]*"\""
DECIMAL = [0-9]+"."[0-9]+
NUMERO = [0-9]+
BOOLEANO = "true" | "false"



// Funciones
ROUND_NUMBER = "round_number"  // numero de ronda actual
GET_MOVES_COUNT = "get_moves_count"
GET_LAST_N_MOVES = "get_last_n_moves"
GET_MOVE = "get_move"
LAST_MOVE = "last_move"
RANDOM = "random"
OPPONENT_HISTORY = "opponent_history"
SELF_HISTORY = "self_history"
TOTAL_ROUNDS = "total_rounds"
HISTORY = "history"


// identificadores... Se pone despues de Funciones pues puede crear conflicto ya que leería primero identificador y no llegaria a leer la funciones.
IDENTIFICADOR = [a-zA-Z][a-zA-Z0-9_]*

// Comentarios
//COMENTARIO = "//".*"\n"
//COMENTARIO2 = "/\*"(~"\*/")*"\*/"

// Comentarios de una línea
COMENTARIO = "//"([^\r\n]*)?

// Comentarios multilínea (permitiendo múltiples líneas correctamente)
COMENTARIO2 = [/][*][^*]*[*]+([^/*][^*]*[*]+)*[/]

ESPACIOS = [ \t\n\r\f]+









%%

// DEFINIR EL NOMBRE DEL TOKEN EN CUP y enviarlo

{STRATEGY} { return new Symbol(sym.STRATEGY, yyline, yycolumn, yytext()); }
{MATCH} { return new Symbol(sym.MATCH, yyline, yycolumn, yytext()); }
{MAIN} { return new Symbol(sym.MAIN, yyline, yycolumn, yytext()); }
{INITIAL} { return new Symbol(sym.INITIAL, yyline, yycolumn, yytext()); }
{RULES} { return new Symbol(sym.RULES, yyline, yycolumn, yytext()); }
{PLAYERS} { return new Symbol(sym.PLAYERS, yyline, yycolumn, yytext()); }
{STRATEGIES} { return new Symbol(sym.STRATEGIES, yyline, yycolumn, yytext()); }
{ROUNDS} { return new Symbol(sym.ROUNDS, yyline, yycolumn, yytext()); }
{SCORING} { return new Symbol(sym.SCORING, yyline, yycolumn, yytext()); }
{RUN} { return new Symbol(sym.RUN, yyline, yycolumn, yytext()); }
{WITH} { return new Symbol(sym.WITH, yyline, yycolumn, yytext()); }
{SEED} { return new Symbol(sym.SEED, yyline, yycolumn, yytext()); }
{ACC} { return new Symbol(sym.ACC, yyline, yycolumn, yytext()); }



{MENORIGUAL} { return new Symbol(sym.MENORIGUAL, yyline, yycolumn, yytext()); }
{MAYORIGUAL} { return new Symbol(sym.MAYORIGUAL, yyline, yycolumn, yytext()); }
{IGUAL} { return new Symbol(sym.IGUAL, yyline, yycolumn, yytext()); }
{DIFERENTE} { return new Symbol(sym.DIFERENTE, yyline, yycolumn, yytext()); }
{ASIGNACION} { return new Symbol(sym.ASIGNACION, yyline, yycolumn, yytext()); }
{MENOR} { return new Symbol(sym.MENOR, yyline, yycolumn, yytext()); }
{MAYOR} { return new Symbol(sym.MAYOR, yyline, yycolumn, yytext()); }
{AND} { return new Symbol(sym.AND, yyline, yycolumn, yytext()); }
{OR} { return new Symbol(sym.OR, yyline, yycolumn, yytext()); }
{NOT} { return new Symbol(sym.NOT, yyline, yycolumn, yytext()); }

{IF} { return new Symbol(sym.IF, yyline, yycolumn, yytext()); }
{THEN} { return new Symbol(sym.THEN, yyline, yycolumn, yytext()); }
{ELSE} { return new Symbol(sym.ELSE, yyline, yycolumn, yytext()); }

{MUTUAL} { return new Symbol(sym.MUTUAL, yyline, yycolumn, yytext()); }
{DEFECTION} { return new Symbol(sym.DEFECTION, yyline, yycolumn, yytext()); }
{REWARD} { return new Symbol(sym.REWARD, yyline, yycolumn, yytext()); }
{PUNISHMENT} { return new Symbol(sym.PUNISHMENT, yyline, yycolumn, yytext()); }
{BETRAYAL} { return new Symbol(sym.BETRAYAL, yyline, yycolumn, yytext()); }
{COOPERATION} { return new Symbol(sym.COOPERATION, yyline, yycolumn, yytext()); }
{DEFECTION} { return new Symbol(sym.DEFECTION, yyline, yycolumn, yytext()); }



{LLAVEIZQ} { return new Symbol(sym.LLAVEIZQ, yyline, yycolumn, yytext()); }
{LLAVEDER} { return new Symbol(sym.LLAVEDER, yyline, yycolumn, yytext()); }
{CORIZQ} { return new Symbol(sym.CORIZQ, yyline, yycolumn, yytext()); }
{CORDER} { return new Symbol(sym.CORDER, yyline, yycolumn, yytext()); }
{PARENTESISIZQ} { return new Symbol(sym.PARENTESISIZQ, yyline, yycolumn, yytext()); }
{PARENTESISDER} { return new Symbol(sym.PARENTESISDER, yyline, yycolumn, yytext()); }
{COMA} { return new Symbol(sym.COMA, yyline, yycolumn, yytext()); }
{DOSPUNTOS} { return new Symbol(sym.DOSPUNTOS, yyline, yycolumn, yytext()); }
{PUNTO} { return new Symbol(sym.PUNTO, yyline, yycolumn, yytext()); }

{CADENA} { return new Symbol(sym.CADENA, yyline, yycolumn, yytext()); }
{DECIMAL} { return new Symbol(sym.DECIMAL, yyline, yycolumn, yytext()); }
{NUMERO} { return new Symbol(sym.NUMERO, yyline, yycolumn, yytext()); }
{BOOLEANO} { return new Symbol(sym.BOOLEANO, yyline, yycolumn, yytext()); }

{ROUND_NUMBER} { return new Symbol(sym.ROUND_NUMBER, yyline, yycolumn, yytext()); }
{GET_MOVES_COUNT} { return new Symbol(sym.GET_MOVES_COUNT, yyline, yycolumn, yytext()); }
{GET_LAST_N_MOVES} { return new Symbol(sym.GET_LAST_N_MOVES, yyline, yycolumn, yytext()); }
{GET_MOVE} { return new Symbol(sym.GET_MOVE, yyline, yycolumn, yytext()); }
{LAST_MOVE} { return new Symbol(sym.LAST_MOVE, yyline, yycolumn, yytext()); }
{RANDOM} { return new Symbol(sym.RANDOM, yyline, yycolumn, yytext()); }
{OPPONENT_HISTORY} { return new Symbol(sym.OPPONENT_HISTORY, yyline, yycolumn, yytext()); }
{SELF_HISTORY} { return new Symbol(sym.SELF_HISTORY, yyline, yycolumn, yytext()); }
{TOTAL_ROUNDS} { return new Symbol(sym.TOTAL_ROUNDS, yyline, yycolumn, yytext()); }
{HISTORY} { return new Symbol(sym.HISTORY, yyline, yycolumn, yytext()); }

{IDENTIFICADOR} { return new Symbol(sym.IDENTIFICADOR, yyline, yycolumn, yytext()); }

{COMENTARIO}  { /* Ignorar comentarios de una línea */ }
{COMENTARIO2} { /* Ignorar comentarios multilínea */ }

{ESPACIOS} { /* Ignorar espacios */ }

. { System.out.println("Error en la linea " + yyline + " columna " + yycolumn + ": " + yytext()); }
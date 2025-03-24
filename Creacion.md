# Creación de Proyecto

> **Nota de Creador:**  
> Voy a tratar de explicar como empece a desarrollar el proyecto de manera amigable, sincera y que se pueda entender, sin uso de terminos muy tecnicos para la facil comprensión.

## Aspectos iniciales

### Creacion de Proyecto VSCode
Para un inicio optimo en el proyecto, en java, mediante VSC se debe de tener instaladas ciertas extenciones.

1. Instalación de extensiones
- [ ] Instalar la extensión 'Extension Pack for Java' Creador: Microsoft.
- [ ] Instalar la extensión 'Debugger for Java' Creador: Microsoft.
- [ ] Instalar la extensión 'MAven for Java' Creador: Microsoft.
- [ ] Instalar la extensión 'Proyect Manager for Java' Creador: Microsoft.
- [ ] Instalar la extensión 'JFlex' Creador: Mathias Weber
- [ ] Instalar la extensión 'Cup' Creador: Mathias Weber

2. Creacion de proyectos:
    - Para la creacion de proyectos se debe segur el siguiente orden. 
    1. ```ctrl + shift + p``` Esto nos abre el panel central donde podemos crear un proyecto escribiendo ```>java: Create JAva Project...```. Posteriormente se podrá elegir direccion de guardado de proyecto y nombre del mismo
    2. Una vez creado el pryecto se ingresa a "Explorer" o ```ctrl + shift + E``` saldrán 4 pestañas:
        - (Nombre del proyecto)
        - OUTLINE
        - TIMELINE
        - JAVA PROJECTS
            - (nombre del proyecto)
                - Referenced Libaries 

        en "Referenced Libaries" podemos ingresar los archivos de librerias proporcionadas en este repositorio ```/Librerias```.

3. Creacion de archivos base
    - Para la iniciacion de los archivos base, se crean 3 archivos inciales: lexico.flex, parser.cup y Generador.java. Estos archivos contendran nuestra gramatica la cual nos hará poder leer las entradas de manera especifica, poder analizarla y comunicarse entre ellas, es el corazon de nuestro proyecto. Problemas de logica del funcionamiento se deberan al manejo de esta gramatica. 
    > El Generador es muy similar al que uso, depende de la direccion de su flex y cup especifico, por lo demas es igual, se ejecuta el Generador y este generará: Scanner.java, parser.java, sym.java...Los cuales nos ayudan a encontrar cada error y poder comunicar el flex y cup. 
    Al hacer cambios tanto en el cup como en el flex, siempre se tiene que volver a generar los .java desde el Generador (se ejecuta esa clase "Generador.java").  

## Conceptualizacion de Proyecto

Para un desarrollo entendible de un proyecto se debe tener estructurado tu proyecto de maera que sea mas facil el entender los objetivos, estructuras, alcances, caminos y opciones a las que podemos acudir. 

Al iniciar el proyecto se nescesita entender en que consiste el proyecto, cuales son los puntos clave y como es el flujo de trabajo, esto ultimo es escencial para que podamos comprender de mejor manera lo importante y lo que podemos delegar a nuestro yo del futuro. Lo principal es entender como vamos a crear nuestro arbol y en base a ello poder ir contruyendo nuestra gramatica. 

1. **Flex**
    Como paso principal podemos concentrarnos en reconocer cuales son nuestros tokens y palabras reservadas dentro de nuestro proyecto, el el enunciado de este proyecto se puede observar que palabras son las reservadas y los tokens que se estaran utilizando. (flex)

    ```java
    // como definir que son palabras reservadas y tokens
    // entrada: 
    strategy Graaskamp { // en esta primera linea tenemos "strategy" (palabra reservada), "Graaskmap" (en este caso es un identificador) y "{" 
        initial: D
        rules: [
            if round_number <= 2 then D,
            if round_number == 3 && get_moves_count(opponent_history, D) == 2 then C,
            if round_number > 3 && get_last_n_moves(opponent_history, 2) == [D, D] then D,
            else last_move(opponent_history)
        ]
    }

    /*
    por cada una de las lineas vamos identificando que tenemos y que son, y entonces vamos entendiendo como los podemos agrupar para entender mas facil, en mi flex estan todos los tokens y palabras reservadas por conjuntos para que entiendas que tipo de cosas son todos los aspectos.
    */

    ```

    - Construir nuestro jflex
    ```java

    /* en este caso tenemos palabras reservadas y token que se utilizan.
    Si nos damos cuenta tenemos la palabra o token, en este caso Initial que corresponde  a la palabra reservada "initial" (esta ultima es la que viene en la entrada a analizar) esta se define como token para el cup retornando el simbolo con su columna y fila para poder localizarlo

        
    
    */

    INITIAL = "initial"
    RULES = "rules"
    PLAYERS = "players"
    STRATEGIES = "strategies"

    MENORIGUAL = "<="
    MAYORIGUAL = ">="

    {INITIAL} { return new Symbol(sym.INITIAL, yyline, yycolumn, yytext()); }
    {RULES} { return new Symbol(sym.RULES, yyline, yycolumn, yytext()); }
    {PLAYERS} { return new Symbol(sym.PLAYERS, yyline, yycolumn, yytext()); }
    ```

    Algo muy importante dentro de este apartado de flex es la **El orden de declaración**. 

    De que nos sirve el orden? Bueno para poder saber como leer nuestra gramatica, el compilador ira desde arriba hacia abajo analizando los tokens y, dependiendo de como este organizado, se podra interpretar de una u otra forma:

    ```java
    /*
    Supongamos tenemos la siguiente entrada
    1 > = 0.5
    bueno para analizar tenemos 2 diferentes ordenes en nuestro flex, uno esta bien y el otro mal */

    //ordenamiento1
    MENOR = "<"
    MAYOR = ">"
    MENORIGUAL = "<="
    MAYORIGUAL = ">="

    {MENOR} { return new Symbol(sym.MENOR, yyline, yycolumn, yytext()); }
    {MAYOR} { return new Symbol(sym.MAYOR, yyline, yycolumn, yytext()); }
    {MENORIGUAL} { return new Symbol(sym.MENORIGUAL, yyline, yycolumn, yytext()); }
    {MAYORIGUAL} { return new Symbol(sym.MAYORIGUAL, yyline, yycolumn, yytext()); }


    // ordenamiento2
    MENORIGUAL = "<="
    MAYORIGUAL = ">="
    MENOR = "<"
    MAYOR = ">"

    {MENORIGUAL} { return new Symbol(sym.MENORIGUAL, yyline, yycolumn, yytext()); }
    {MAYORIGUAL} { return new Symbol(sym.MAYORIGUAL, yyline, yycolumn, yytext()); }
    {MENOR} { return new Symbol(sym.MENOR, yyline, yycolumn, yytext()); }
    {MAYOR} { return new Symbol(sym.MAYOR, yyline, yycolumn, yytext()); }

    /*
    Bueno teniendo en cuenta que el compilador lee de arriba hacia abajo, entonces para leer la entrada  "1 > = 0.5"  irá leyendo los tokens que le hayamos indicado, en el ordenamiento1 tenemos que encontrara a "> y <" primero y despues "> = ó < =" esto genera un error, pues una vez encontrado < ó > ya no leera el "=" y esto causara que el resultado sea diferente. 
    en cambio en el ordenamiento2 leera primero < = o > = y si podra entender que es un > = la comparacion de numeros.

    Dentro de la gramatica que tengo se puede apreciar esta regla en los tokens como "= y ==", entre otros... es importante poder entender como se lee y en base a ello poder estructurar bien nuestro proyecto
    */

    ```

    Una vez tenemos estructurado nuestro flex (la mayoria de detalles en mi flex tienen comentarios, puedes leerlos, lo demas preguntaselo a la inteligencia artificial no seas webón) podemos seguir con la estructuración de nuestro cup... se vienen cositas!

2. **Cup** 

    Cup es una herramienta que utilizamos para generar analizadores sintácticos. Específicamente, se emplea para procesar gramáticas libres de contexto y construir un parser que pueda interpretar y validar estructuras de entrada basadas en dichas gramáticas.

    Para la construcicon de cup nescesitamos entender 3 etiquetas que estaremos viendo y usando a lo largo de este codigo: 
    - terminal

        Son aquellos "estados" en los cuales no hay forma de desglozarlos, derivarlos o desarrollarlos... osea no podemos derivar de ellos en otros estados, en un arbol serian las hojas.

    - non terminal

        En estos "estados" si podemos seguir desarrollando, se pueden derivar en otros "estados" o en hojas (terminales)

    - precedence

        Que es la precedencia, como se maneja y para que nos sirve?
        La **precedencia** es el orden en el que se evalúan las operaciones o reglas dentro de una gramática. En el contexto de Flex y Cup, se utiliza para resolver ambigüedades al analizar expresiones, definiendo qué operadores o tokens tienen mayor prioridad sobre otros. Esto es crucial para garantizar que el análisis sintáctico sea correcto y consistente, especialmente en casos donde múltiples reglas puedan coincidir. Por ejemplo, al definir operadores matemáticos, se puede establecer que la multiplicación (*) tenga mayor precedencia que la suma (+), asegurando que las expresiones se interpreten adecuadamente.

    En esta gramatica cup tenemos los 3:
    ```java
    //estos son terminales pues de ellos no se deriva nada mas.. if no puede derivar nada es solamente un if y ya... por si solo es if
    terminal String IF, THEN, ELSE; 

    // estos son no terminales pues de ellos se pueden derivar mas estados o terminales... reglas en si no es nada, pero se deriva en lista_reglas que contiene expresiones que asi mismo contiene un if (por ejemplo) se va derivando poco a poco.
    non terminal reglas, lista_reglas, expresion;

    // precedencia, and y or tienen precedencia o mayor importancia por la izquierda, eso quiere decir que en una comparacion donde tenemos varios conjuntos de opoeraciones, and es una preoridad. 
    precedence left AND, OR;

    ```

## Creacion y desarrollo de Gramatica (cup)

1. **Importacion de tokens y palabras reservadas**

    En el archivo flex tenemos nuestros tokens definidos, bueno entonces para poder utilizar estos tokens definidos en el cup, tenemos que importarlos. Las palabras asignadas a estos tokens son los que importamos:

    ```java
    terminal String STRATEGY, MATCH, MAIN, INITIAL, RULES, PLAYERS, STRATEGIES, ROUNDS, SCORING, RUN, WITH, SEED;
    terminal String ACC; 

    // de esta forma podemos ir importando, teniendo en cuenta lo anteriormente especificado de terminal, non termianl y precedence

    ```

    Una vez importados todos los tokens desde el jflex entonces continuamos con:

2. **Desarrollo de Arbol**

    Para el desarrollo de nuestra gramatica tenemos que entender ciertas cosas, entre ellas esta el como crear un arbol de nodos. 
    Un árbol es una estructura jerárquica que nos permite representar las relaciones entre diferentes elementos de nuestra gramática. Cada nodo del árbol puede ser un terminal o un no terminal, y las hojas del árbol siempre serán terminales.

    Para construir un árbol en Cup, debemos definir las reglas de producción que describen cómo se derivan los no terminales a partir de otros no terminales o terminales. Estas reglas se escriben en el archivo `.cup` y siguen una sintaxis específica.

    Para este proyecto el enunciado nos explica su funcionamiento, este se basa en un juego donde definimos estrategias para jugadores con nombres(id) las cuales tienen condiciones (round_number > 1) las cuales en dado caso se cumplan pues devuelven un valor C ó D; asi mismo tenemos Partidas con la palbra reservada "match" lo que hace es darnos valores como numero de partidas, que jugadores se enfrengan y las directrices de sus resultados. Por ultimo tenemos el main donde se llaman esos juegos y los corremos. 

    > **Nota**
        Bueno si te das cuenta, poco a poco fuimos entendiendo el como funciona el juego, esto no se entiende con la primera lectura. Lo que recomiendo es copiar todo el proyecto y hacer anotaciones, sustraer lo que nescesitamos para iniciar, leerlo varias veces y entender, entender. Si te cuesta leer y comprender puedes usar AI para poder entender a grandes razgos y empezar a estructurar la idea en tu cabeza. 

    bueno teniendo la idea empezamos a crear una idea de como desarrollar el arbol...[veré si puedo adjuntar una imagen de un arbol que hice]

    ```java
    strategy Joss { 
        initial: C
        rules: [
            if random < 0.1 then D,
        ]
    }

    /*
    bueno tenemos esta entrada sencilla, empecemos con ella; en base a ella crearemos un arbol pequeño para ver que ramas y hojas tiene.

    Podemos iniciar de lo mas abajo del arbol (el final o cosas que no se derivan mas) en este caso seria "if random < 0.1 then D,"
    o bien podemos inicar desde arriba y ver en que se derivan, independientemente llegamos a esta idea:
    */


    strategy
    ├── Nombre: Joss
    ├── initial
    │   └── Valor: C
    └── rules
        └── regla
            ├── if
            ├── condición
            │   ├── variable: random
            │   ├── operador: <
            │   └── valor: 0.1
            └── then
                └── acción: D

    // tecnicamente se vería asi dibujado

                            strategy
                            /    |    \
                Nombre  initial  rules
                    |       |        |
                Joss   Valor: C     [   regla     ]
                                    /    \                        
                                if   condición        \
                                        /   |   \      
                            variable operador valor      \
                                |           |     |       
                                random      <     0.1      |
                                                            
                                                        then
                                                            |
                                                        acción
                                                            |
                                                            D


    /*
    Strategy se deriva en: Nombre (ID), initial y rules
    estas se derivan en otras, initial en un valor C/D y rules en algunas reglas...
    en estas reglas podemos ver if que no se deriva mas (una hoja), condicion que tenemos a random, > y numero...

    */

    ```
    Habiendo entendido esto empezaremos con: 

3. **Gramatica**

    empezamos con 
    ```java

    start with ini;
    // ini lo elegí, puede ser inicio o comienzo o... lo que quieras

    ```
    Aqui sera donde todo inicia y donde todo "sube" (posteriormente hablaremos de ese termino "subir")

    Bueno teniendo la idea de como funciona el juego empezamos con ello.
    > **Atencion** algo importante es que al momento no se va a parecer a la gramatica finalizada pues hay cosas que posteriormente se añaden y consideraciones que se añaden depues de pensarlo. 
    lo que quiero que entiendas es como **YO** pensaba, que entres en mi cabeza y veas y pienses lo que yo pensaba en aquel momento, eso es lo supongo que te servirá, son los problemas que te vas a enfrentar.

    Bueno teniendo en cuenta que el juego se divide en 3 fases: estrategia, match y main entones podemos continuar con eso...

    ```java
    lista_estrategias ::= estrategia
                        | juego
                        | main_section;


    ```
    el | quiere decir como un "o", es decir que puede venir estragegia o (|) juego o (|) main_section. y finalizamos con ;

    En tu caso esta divicion puede ser de mas o menos cantidad de bloques, lo importante es diferenciarlos o encontrarlos. 

    Bueno ya definimos "lista_estrategias" esto ahora lo tenemos que declarar como un non terminal en la parte de definicion de "tokens y palabras reservadas"

    Bueno, una vez definidas las 3 partes principales... podemos iniciar con una de ellas y seguir desarrollandola. Por efectos de aprendizaje solo explicaré la parte de estrategia que es la más extensa y pues porque no tiene el mismo proyecto si no que quieres aprender a hacer algo similar. 

4. **Desarrollo de rama especifica**

    Nos concentrarémos en la rama de estrategia, esta es la mas completa y veremos algunos detalles en ella.

    Tendiendo esta entrada de ejemplo:
    ```java
    strategy SteinRapoport {
        initial: C
        rules: [
            if round_number <= 2 then C,
            if round_number == 3 then D,
            if round_number == 4 then C,
            if get_moves_count(opponent_history, D) >= 2 then D,
            else C
        ]
    }

    strategy Joss { 
        initial: C
        rules: [
            if random < 0.1 then D,
            else last_move(opponent_history)
        ]
    }

    strategy Nydegger {
        initial: C
        rules: [
            if round_number == 1 then C,
            if round_number == 2 then D,
            if get_last_n_moves(opponent_history, 3) == [D, D, D] then D,
            if get_last_n_moves(opponent_history, 2) == [C, D] then D,
            else C
        ]
    }

    ```

    Podemos ir analizando desde los mas generar a lo especifico. Primero podemos observar a Strategy (una palabra reservada), esta la sucede otra palabra que puede cambiar, entonces esta palabra la podemos tomar como un ID. Porque? bueno si notamos ese nombre le pertenece solo a una estrategia osea es un jugador, "SteinRapoport" es un id. Posteriormente podemos encontrar "{" y su cierre "}", bueno esto ya es algo que si o si viene en la entrada cuando se llama a strategy. Esto ya es un estado de nuestra gramatica y si vemos en el archivo cup de este proyecto, esta misma estructura que acabamos de analizar esta definada como 'estrategia'

    ```java

    estrategia ::= STRATEGY IDENTIFICADOR LLAVEIZQ definiciones LLAVEDER;

    ```

    Bueno ya habiendo definizo esto vemos que dentro de las llaves viene algo: 
    ```java
    strategy SteinRapoport {// desde aqui que abre
        initial: C
        rules: [
            if round_number <= 2 then C,
            if round_number == 3 then D,
            if round_number == 4 then C,
            if get_moves_count(opponent_history, D) >= 2 then D,
            else C
        ]
    }// hasta aqui que cierra
    ```
    Toda esta parte es otro estado que lo llamaremos 'definiciones' pues aqui definimos que estrategia tiene el jugador, es de esta manera que definimos nuetro otro estado:
    ```java
    definiciones ::= definicion_estrategia rules;
    ```
    dentro del estado 'definiciones' pueden venir 2 cosas:definicion_estrategia
    ```java
    definicion_estrategia ::= INITIAL DOSPUNTOS expresion;
    //que seria esta parte de la entrada: initial: C
    ```
    y la otra cosa que seria rules
    ```java
    rules ::= RULES DOSPUNTOS CORIZQ lista_reglas CORDER;

    /*que corresponde a esta otra parte de la entrada:
    rules: [
            if round_number <= 2 then C,
            if round_number == 3 then D,
            ...
    */
    ```
    Aqui podemos ver que de estas 2 derivaciones de 'definiciones' (definicion_estrategia y rules) hay mas derivaciones tales son: lista_reglas y expresion.

    Si vamos a 'expresion' podemos ver que alli definimos varias cosas, tales como que es un booleano, un decimal, un numero... varias cosas que podemos llamar con solo escribir expresion y cualquiera de ellas puede reemplazar ese lugar y existir en ese estado. 

    Podemos ver la funcionalidad variada del uso de estados con derivaciones para que podamos estructurar un arbol avl de manera optima y segura, para que sea una gramatica limpia y ordenada.

5. **Recursividad**

    Antes de continuar con el desarrollo de la gramatica, hay un tema muy importante que es nescesario tocar. 

    La recursividad es una técnica en programación y diseño de gramáticas donde una función o regla se llama a sí misma para resolver un problema más pequeño o derivar un subconjunto de datos. En el contexto de gramáticas, la recursividad permite definir estructuras repetitivas o anidadas de manera elegante y compacta.

    Por ejemplo podemos definir la entrada como un elemento seguido opcionalmente de otra entrada:

    ```java
   entrada ::= entrada lista_estrategias
            | lista_estrategias;

    lista_estrategias ::= estrategia
                        | juego
                        | main_section;
    ```

   En este caso podemos ver que puede venir, como entrada; una estrategia, o un juego o un main; pero tambien puede venir varias veces "lista_estrategias" eso quiere decir que pueden venir varias estrategias o varioes juegos... Esto se debe a que "entrada" esta definida para que venga lista_estrategias o una entrada y lista_estrategias, esto provoca una recursividad de y enotnces entrada puede ingresar a entrada cuantas veces queira y nos dara lista_estrategias cuantas veces sean nescesarias. Espero se haya entendido.

6. **Detalles y aspectos adicionales de Gramatica cup** 
    
    Dentro de nuetra gramatica añadimos ciertos elementos para que este pueda funcionar. Para que nuestra gramatica pueda ser util usamos estructuras de datos como listas, arraylist, hashmaps, entre otras. Esto nos ayuda a que todo lo que declaramos y entre, pueda ser analizado y utilizado para que **retorne** valores utilizables. 

    Esta ultima palabra **retorne** es importante. Si te detienes a ver en la gramatica cup, podemos ver estas estructuras:
    ```java
    rules ::= RULES DOSPUNTOS CORIZQ lista_reglas:reglas CORDER {: RESULT = reglas; :};

    /*esto que esta entre corchetes es importante en cada estado, lo que hace es retornar o subir los elementos del estado como conjunto para poder utilizarlo en el estado mas arriba de el. */
    
    ```
    En otras palabras al retornar (o subir viendolo en el arbol) con la palabra "RESULT" lo que tenemos en rules podemos utilizarlo en el estado mas arriba de el, en este caso:
    ```java
    definiciones ::= definicion_estrategia rules
    ```
    Definiciones puede utilizar lo que result subio desde rules, de esta manera es como desde las hojas o estados mas abajo, se puede ir formando una gramatica con contexto. 

    Dentro de nuestra estructura  {: RESULT = reglas; :} podemos añadir listas, hashmaps, entre otras estructuras para poder manejar los dato de mejor manera:
    ```java
    lista_reglas ::= lista_reglas:lista COMA reglas:regla
                {:
                   
                    ArrayList<Instruccion> listaReglas = (ArrayList<Instruccion>)lista;
                    listaReglas.add((Instruccion)regla);
                    RESULT = listaReglas;
                :}
    /* para saber que token se debe "subir" se le asigna un nombre, en este ejemplo 
    se utiliza lista_reglas y se la asigna ":lista" y esta nueva definicio se puede llamar en nuestra estructura {:....:} como lista. dentro del proyecto se puede observar que se utilizan asiganaciones como :e, :e1, :e2, estas tienen la misma funcionalidad. 
    ```
    Obviamente esto ya depende de como estructuremos nuestro proyecto y que clases tenemos para manejar los datos obtenidos.

    En si, la gramatica trabaja leyendo la entrada, recibiendo los datos de las hojas o estados mas primitivos, enviandolos a las clases para ser analizados y operados; recibirlos de regreso de las clases y subirlos al siguiente estado... esto en cada estado, los envia a las clases, recibe de las clases el resultado y lo sube al siguente estado.

    Las clases las definimos para ayudarnos segun nescesitemos.

7. **Arquitectura**

    Una vez la gramatica pueda recibir la entrada o entradas sin generar errores, podemos empezar a crear una arquitectura para el desarrollo del proyecto. 
    En mi caso, desarrollé el proyecto utilizando el método intérprete, el cual se caracteriza por procesar y ejecutar el código fuente directamente, sin necesidad de compilarlo previamente en un archivo ejecutable. Este método es útil para proyectos donde se requiere una retroalimentación inmediata o cuando el análisis y la ejecución deben realizarse en tiempo real.

    #### Implementación del Método Intérprete

    1. **Análisis Léxico**  
        El primer paso es utilizar el archivo generado por JFlex (`Scanner.java`) para dividir el código fuente en tokens. Estos tokens representan las unidades mínimas del lenguaje, como palabras reservadas, identificadores, operadores, etc.

        ```java
        Scanner scanner = new Scanner(new FileReader("entrada.txt"));
        Symbol token;
        while ((token = scanner.next_token()).sym != sym.EOF) {
            System.out.println("Token: " + token.sym + " Valor: " + token.value);
        }
        ```

    2. **Análisis Sintáctico**  
        Con el archivo generado por Cup (`parser.java`), se valida que la estructura del código fuente cumpla con las reglas de la gramática definida. Si el código es válido, se genera un árbol de sintaxis abstracta (AST).

        ```java
        parser parser = new parser(new Scanner(new FileReader("entrada.txt")));
        NodoAST ast = (NodoAST) parser.parse().value;
        ```

    3. **Ejecución del Árbol de Sintaxis Abstracta (AST)**  
        Una vez generado el AST, se recorre y evalúa cada nodo. Cada nodo representa una instrucción o expresión que debe ser ejecutada. Esto se logra implementando un patrón de diseño como el Visitor o utilizando métodos específicos en las clases de los nodos.

        ```java
        Resultado resultado = ast.ejecutar(contexto);
        System.out.println("Resultado: " + resultado.valor);
        ```

    4. **Manejo de Contexto**  
        Durante la ejecución, se utiliza una estructura de datos (como un `HashMap`) para almacenar variables, funciones y otros elementos del contexto actual. Esto permite realizar operaciones como asignaciones, llamadas a funciones y evaluaciones de expresiones.

        ```java
        HashMap<String, Object> contexto = new HashMap<>();
        contexto.put("variable", 10);
        ```

    5. **Soporte para Errores**  
        Es importante manejar errores léxicos, sintácticos y semánticos para proporcionar retroalimentación clara al usuario. Esto se puede lograr mediante excepciones personalizadas o estructuras de datos que almacenen los errores encontrados.

        ```java
        try {
            parser.parse();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        ```
    > Siendote franco, puedes resumir tu proyecto e ingresarlo a la AI para que te cree una arquitectura para el desarrollo de tu proyecto, esto te puede ayudar a entender mejor el manejo de listas, arrays, hashmap y otras erramientas para estar entendido de su uso y aplicacion. 

    Yo desarrolle una arquitectura antes de empezar con el proyecto, esto me ayudó a poder analizar y crear a grandes razgos una idea de como funcionaria cada aspecto del proyecto y su uso.

    **arquitectura**
    ```java
    src
    |---Analizador
    |------Interprete
    |---------Abstracto
    |------------Instruccion.java (Clase base abstracta para representar instrucciones en el lenguaje)
    |------------NodoAST.java (clase para todos los nodos del AST)
    |---------Expresion
    |------------Logica.java (Clase que maneja operaciones lógicas y comparaciones entre expresiones)
    |------------Aritmetica.java (Para operaciones aritméticas)
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
    |---Interfaz.java (Interfaz gráfica)
    |---Main.java (Punto de entrada de la aplicación)
    ```

    Esta no es la arquitectura final, pero si es la mayoria de ella, esto me ayudo a poder organizar y estructurar mejor mi flujo de trabajo, es una gran practica que puedes implementar en tu desarrollo de proyectos. 

8. Desarrollo de clase
    
    Dentro de la arquitectura del proyecto destacan 6 carpetas, trataré de desarrollar el uso de estas y como se pueden ir aplicando la misma idea en otros proyectos. 

    ### 1. **Carpeta `Abstracto`**
    - **Uso**:  
        Contiene las clases base abstractas que definen la estructura general de los nodos del Árbol de Sintaxis Abstracta (AST). Estas clases son heredadas por las demás clases del proyecto, como expresiones e instrucciones.
    - **Implementación**:
        - Define clases abstractas como `NodoAST` o `Instruccion` que sirven como base para las demás clases.
        - Estas clases suelen incluir métodos abstractos como `ejecutar()` o `compilar()` que serán implementados por las subclases.

        ```typescript
        // filepath: Interprete/Abstracto/NodoAST.ts
        export abstract class NodoAST {
            constructor(public linea: number, public columna: number) {}
            abstract ejecutar(contexto: any): any;
        }
        ```

    - **Reutilización**:  
        En otros proyectos, esta carpeta puede ser utilizada para definir estructuras base para cualquier tipo de árbol jerárquico, como árboles de decisión, árboles de expresión, etc.

    ---

    ### 2. **Carpeta `Expresion`**
    - **Uso**:  
        Contiene las clases que representan expresiones del lenguaje, como operaciones aritméticas, lógicas, literales, y funciones específicas. Estas clases heredan de `NodoAST` y se encargan de evaluar y devolver valores.
    - **Implementación**:
        - Crea clases para cada tipo de expresión, como `Aritmetica`, `Logica`, `Literal`, etc.
        - Implementa el método `ejecutar()` para evaluar la expresión.

        ```typescript
        // filepath: Interprete/Expresion/Aritmetica.ts
        import { NodoAST } from "../Abstracto/NodoAST";

        export class Aritmetica extends NodoAST {
            constructor(
                private izquierda: NodoAST,
                private derecha: NodoAST,
                private operador: string,
                linea: number,
                columna: number
            ) {
                super(linea, columna);
            }

            ejecutar(contexto: any): any {
                const valorIzq = this.izquierda.ejecutar(contexto);
                const valorDer = this.derecha.ejecutar(contexto);
                switch (this.operador) {
                    case "+":
                        return valorIzq + valorDer;
                    case "-":
                        return valorIzq - valorDer;
                    // Otros operadores...
                }
            }
        }
        ```

    - **Reutilización**:  
        En proyectos similares, esta carpeta puede ser utilizada para manejar cualquier tipo de expresión evaluable, como expresiones matemáticas, condiciones lógicas, o incluso consultas SQL.

    ---

    ### 3. **Carpeta `Instruccion`**
    - **Uso**:  
        Contiene las clases que representan instrucciones del lenguaje, como declaraciones de variables, ciclos, condicionales, y bloques de código. Estas clases también heredan de `NodoAST`.
    - **Implementación**:
        - Crea clases para cada tipo de instrucción, como `DeclaracionVariable`, `If`, `While`, etc.
        - Implementa el método `ejecutar()` para realizar la acción correspondiente.

        ```typescript
        // filepath: Interprete/Instruccion/If.ts
        import { NodoAST } from "../Abstracto/NodoAST";

        export class If extends NodoAST {
            constructor(
                private condicion: NodoAST,
                private cuerpo: NodoAST[],
                private cuerpoElse: NodoAST[] | null,
                linea: number,
                columna: number
            ) {
                super(linea, columna);
            }

            ejecutar(contexto: any): any {
                if (this.condicion.ejecutar(contexto)) {
                    this.cuerpo.forEach(instr => instr.ejecutar(contexto));
                } else if (this.cuerpoElse) {
                    this.cuerpoElse.forEach(instr => instr.ejecutar(contexto));
                }
            }
        }
        ```

    - **Reutilización**:  
        Esta carpeta puede ser utilizada en cualquier proyecto que requiera manejar instrucciones ejecutables, como scripts, lenguajes de programación personalizados, o motores de reglas.

    ---

    ### 4. **Carpeta `LlamadaFuncion`**
    - **Uso**:  
        Contiene las clases que representan llamadas a funciones o métodos definidos en el lenguaje. Estas clases manejan la lógica de pasar parámetros, ejecutar el cuerpo de la función, y devolver resultados.
    - **Implementación**:
        - Define clases para cada tipo de función o llamada, como `GetMovesCount`, `LastMove`, etc.
        - Implementa el método `ejecutar()` para realizar la lógica de la función.

        ```typescript
        // filepath: Interprete/LlamadaFuncion/GetMovesCount.ts
        import { NodoAST } from "../Abstracto/NodoAST";

        export class GetMovesCount extends NodoAST {
            constructor(
                private historial: NodoAST,
                private movimiento: NodoAST,
                linea: number,
                columna: number
            ) {
                super(linea, columna);
            }

            ejecutar(contexto: any): any {
                const historial = this.historial.ejecutar(contexto);
                const movimiento = this.movimiento.ejecutar(contexto);
                return historial.filter((mov: any) => mov === movimiento).length;
            }
        }
        ```

    - **Reutilización**:  
        Esta carpeta puede ser utilizada en proyectos que requieran implementar funciones personalizadas o llamadas a métodos en un lenguaje interpretado.

    ---

    ### 5. **Carpeta `Simbolo`**
    - **Uso**:  
        Contiene clases relacionadas con la tabla de símbolos, como variables, funciones, y estructuras de datos que almacenan el estado del programa durante la ejecución.
    - **Implementación**:
        - Define clases como `Simbolo`, `TablaSimbolos`, y `Nativo` para manejar el almacenamiento y recuperación de valores.

        ```typescript
        // filepath: Interprete/Simbolo/TablaSimbolos.ts
        export class TablaSimbolos {
            private tabla: Map<string, any> = new Map();

            setSimbolo(nombre: string, valor: any): void {
                this.tabla.set(nombre, valor);
            }

            getSimbolo(nombre: string): any {
                return this.tabla.get(nombre);
            }
        }
        ```

    - **Reutilización**:  
        Esta carpeta es esencial en cualquier proyecto que requiera manejar variables, funciones, o cualquier tipo de almacenamiento de estado.

    ---

    ### 6. **Carpeta `Util`**
    - **Uso**:  
        Contiene clases o funciones auxiliares que no pertenecen directamente a las demás categorías, como herramientas para manejo de errores, generación de código, o utilidades generales.
    - **Implementación**:
        - Define clases o funciones para tareas específicas, como manejo de errores o generación de identificadores únicos.

        ```typescript
        // filepath: Interprete/Util/Error.ts
        export class Error {
            constructor(public tipo: string, public mensaje: string, public linea: number, public columna: number) {}
        }
        ```

    - **Reutilización**:  
        Esta carpeta puede ser utilizada en cualquier proyecto para centralizar utilidades comunes.

    ---

    ### Resumen de Implementación en Otros Proyectos
    1. **Flexibilidad**:  
    Esta estructura es modular y puede ser adaptada a cualquier proyecto que requiera un intérprete o un analizador de lenguaje.
    2. **Reutilización**:  
    Las carpetas `Abstracto`, `Simbolo`, y `Util` son altamente reutilizables en cualquier proyecto que maneje árboles sintácticos o tablas de símbolos.
    3. **Escalabilidad**:  
    Puedes añadir nuevas carpetas o clases según las necesidades del proyecto, manteniendo la organización.


9. **Mayor entendimiento del proyecto**

    Algo importante es que este proyecto no fue, es, ni será igual al tuyo, pero si es similar el funcionamiento y el propocito del mismo. Te animo a que sigas leyendo cada clase, archivo y carpeta del proyecto, en la mayoria hay comentarios tratando de explicar cada parrafo, linea y expresion. Espero te haya sido de ayuda de algo, investiga y concentrate, no es facil pero no imposible. Ayudate de las herramientas que tienes a tu alrededor para **aprender**, "no copies sin entender, no repitas sin comprender". 

    Como ultimo consejo, al terminar tu proyecto o proyectos, intenta hacer lo mismo de este proyecto. Explicaselo a alguien (aunq sea imaginaria esa persona) haciendolo aprendi más e interiorice más los temas. Esto te ayudara y ayudará a los que lo lean. 

    Suerte y hasta el proximo proyecto!!!


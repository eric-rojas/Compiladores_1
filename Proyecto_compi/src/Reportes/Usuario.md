# Manual de Usuario - Simulador del Dilema del Prisionero

## Introducción

Esta aplicación permite simular el Dilema del Prisionero, un concepto clásico de la teoría de juegos, mediante la definición de estrategias personalizadas y la ejecución de partidas entre ellas.

## Requisitos del Sistema

- **Java**: Versión 8 o superior
- **Memoria RAM**: 2GB o superior
- **Espacio en Disco**: 100MB

## Instalación

1. Descargue el archivo **JAR** desde el repositorio.
2. Coloque el archivo en la ubicación deseada.
3. Ejecute el programa con doble clic o mediante la línea de comandos:
   ```sh
   java -jar OLC1_Proyecto1_202200331.jar
   ```

## Interfaz de Usuario

### Vista General

![Interfaz principal](src/Reportes/Imagenes/1.png)

La interfaz cuenta con las siguientes áreas principales:

1. **Barra de menú**: Acceso a funciones como abrir/guardar archivos y generar reportes.
2. **Área de entrada**: Editor para escribir el código fuente.
3. **Área de reportes**: Muestra reportes de tokens y errores.
4. **Área de salida**: Muestra los resultados de la ejecución.

## Funcionalidades Principales

### Crear un Nuevo Archivo

1. Seleccione **"Archivo > Nuevo"** en la barra de menú.
2. El área de edición se limpiará para permitir escribir nuevo código.

### Abrir un Archivo Existente

1. Seleccione **"Archivo > Abrir"** en la barra de menú.
2. Navegue hasta el archivo deseado (**extensión .cmp**) y ábralo.
3. El contenido se cargará en el área de edición.

![Abrir archivo](Reportes/Imagenes/2.png)
*Diálogo de apertura de archivos*

### Guardar un Archivo

1. Seleccione **"Archivo > Guardar"** en la barra de menú.
2. Si es un archivo nuevo, se abrirá un diálogo para seleccionar la ubicación y nombre.
3. Si es un archivo existente, se guardará directamente.

### Ejecutar una Simulación

1. Escriba o cargue el código de la simulación en el área de entrada.
2. Seleccione **"Ejecutar > Ejecutar"** en la barra de menú.
3. Los resultados aparecerán en el área de salida.
4. Si hay errores, se mostrarán mensajes descriptivos.

![Resultados de la ejecución](Reportes/Imagenes/3.png)
*Resultados de una simulación exitosa*

### Generar Reportes

1. Después de ejecutar el código, puede generar reportes:
   - **"Reportes > Reporte de Tokens"** para ver todos los tokens identificados.
   - **"Reportes > Reporte de Errores"** para ver errores léxicos o sintácticos.

![Reporte de tokens](Reportes/Imagenes/4.png)
*Ejemplo de reporte de tokens*

## Sintaxis del Lenguaje

### Definición de Estrategias

```cmp
strategy NombreEstrategia {
    initial: C
    rules: [
        if condición then acción,
        else acción_por_defecto
    ]
}
```

### Definición de Partidas

```cmp
match NombrePartida {
    players strategies: [Estrategia1, Estrategia2]
    rounds: 10
    scoring: {
        mutual cooperation: 3,
        mutual defection: 1,
        betrayal reward: 5,
        betrayal punishment: 0
    }
}
```

### Punto de Entrada

```cmp
main {
    run [NombrePartida1, NombrePartida2] with {
        seed: 42
    }
}
```

## Solución de Problemas Comunes

| **Problema**         | **Posible Solución** |
|----------------------|------------------|
| Error léxico        | Verifique que todos los símbolos utilizados sean válidos en el lenguaje |
| Error sintáctico    | Revise la estructura de las estrategias y partidas |
| Error en la ejecución | Asegúrese de que las condiciones sean válidas y los historiales tengan suficientes elementos |

## Ejemplos

Para comenzar, pruebe este ejemplo básico que enfrenta dos estrategias:

```cmp
strategy TitForTat {
    initial: C
    rules: [
        else last_move(opponent_history)
    ]
}

strategy AlwaysDefect {
    initial: D
    rules: [
        else D
    ]
}

match BasicMatch {
    players strategies: [TitForTat, AlwaysDefect]
    rounds: 10
    scoring: {
        mutual cooperation: 3,
        mutual defection: 1,
        betrayal reward: 5,
        betrayal punishment: 0
    }
}

main {
    run [BasicMatch] with {
        seed: 42
    }
}
```


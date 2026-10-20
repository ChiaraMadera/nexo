# NEXO · Consulta de tickets de soporte

Aplicación de consola en **Java** para consultar tickets de soporte almacenados en un archivo CSV y guardar los resultados en un reporte de texto.

Trabajo final del curso de Java de **CILSA, edición 2026**.

## Funcionalidades

- Listar todos los tickets disponibles.
- Buscar por prioridad: **Alta**, **Media** o **Baja**.
- Buscar por el nombre completo del área responsable.
- Buscar por un ID numérico exacto.
- Buscar por estado: **Abierto**, **En_Progreso** o **Resuelto**.
- Exportar el último listado o búsqueda con resultados.
- Informar entradas incorrectas, consultas sin coincidencias y errores de archivos.

Tres reglas de búsqueda:

- **Mayúsculas**: prioridad, área y estado no distinguen mayúsculas y minúsculas.
- **Coincidencia exacta**: la búsqueda por área pide el nombre completo. `sistemas` encuentra `Sistemas`, pero `sist` no.
- **Tildes**: deben coincidir con las del dato almacenado. Si el CSV guarda `Facturacion`, buscar `Facturación` no lo encuentra.

## Requisitos

- **JDK 21**, con `java` y `javac` disponibles.
- Una terminal o un editor como IntelliJ IDEA.

No requiere base de datos, bibliotecas externas, Maven ni Gradle.

## Cómo ejecutar

> **La terminal tiene que estar en la raíz del proyecto**, donde están `src` y `data`. `Main` busca `data/tickets.csv` de forma relativa: si lo ejecutás desde otra carpeta, falla con `No se pudo leer el CSV`.

### Desde la terminal

En Linux, macOS o Git Bash:

```sh
javac -encoding UTF-8 -d out src/*.java
java -cp out Main
```

En Windows (CMD):

```bat
dir /b src\*.java > fuentes.txt
javac -encoding UTF-8 -d out @fuentes.txt
java -cp out Main
```

En PowerShell:

```powershell
$fuentes = Get-ChildItem src -Filter *.java | Select-Object -ExpandProperty FullName
javac -encoding UTF-8 -d out $fuentes
java -cp out Main
```

La compilación crea la carpeta `out` con los archivos `.class`. En bash y en PowerShell el comodín lo expande el shell; `javac` no lo hace por su cuenta, por eso CMD necesita el archivo de argumentos.

### Desde IntelliJ IDEA

1. Abrí la carpeta del proyecto.
2. Seleccioná **JDK 21** como SDK del proyecto.
3. Verificá que `src` esté marcada como **Sources Root**.
4. Ejecutá el método `main` de `Main.java`.

En IntelliJ, verificá que **Working directory** apunte a la raíz del proyecto en la configuración de ejecución.

## Uso

Al iniciar, NEXO carga los registros válidos y muestra este menú:

```text
*** NEXO - Soporte de Tickets ***
1. Listar tickets
2. Buscar por prioridad
3. Buscar por area
4. Buscar por ID
5. Buscar por estado
6. Guardar resultados
7. Salir
*********************************
Seleccione una opcion:
```

Para probar el flujo completo: opción **4**, ID **1**, y después opción **6** para guardar el reporte.

También podés buscar `alta` con la opción 2 o `Sistemas` con la opción 3. Para buscar por estado, usá la opción 5 y escribí, por ejemplo, `en_progreso`. El menú vuelve a mostrarse después de cada operación.

## Archivo de datos

El proyecto incluye **20 tickets de ejemplo** en [`data/tickets.csv`](data/tickets.csv). El archivo utiliza **UTF-8**, punto y coma como separador y cinco campos por registro:

```csv
id;titulo;prioridad;area;estado
001;Error de acceso;Alta;Sistemas;Abierto
002;Consulta de factura;Baja;Facturacion;Resuelto
```

| Campo | Contenido |
| --- | --- |
| `id` | Entero positivo que identifica al ticket. |
| `titulo` | Descripción breve del problema o solicitud. |
| `prioridad` | Alta, Media o Baja. |
| `area` | Sistemas, Facturacion o Infraestructura en los datos de ejemplo. |
| `estado` | Abierto, En_Progreso o Resuelto. |

Los tickets se mantienen editando el CSV. Los cambios se leen **al volver a iniciar el programa**; no se recarga el archivo durante una sesión.

### Validaciones de lectura

- Se omiten las líneas vacías y el encabezado de la primera línea.
- Cada registro debe tener cinco campos completos.
- El ID debe ser un entero mayor que cero, dentro del rango de `int`.
- La prioridad y el estado deben ser uno de los valores admitidos; no distinguen mayúsculas y minúsculas.
- Los espacios al principio y al final de cada campo se eliminan.
- Una fila incorrecta genera un aviso y se continúa con la siguiente.

Por ejemplo, si falta el área:

```text
001;Error de acceso;Alta;;Abierto
```

El programa informa:

```text
Linea 2 omitida: hay campos vacios.
```

El CSV original no se modifica. Corregí la fila y reiniciá el programa para cargarla.

**Al editar el archivo:** mantené los IDs únicos y no uses `;` dentro de los campos. La aplicación no detecta IDs duplicados ni interpreta campos entre comillas. Un ID escrito como `001` se guarda en memoria y se muestra como `1`.

## Exportación de resultados

La opción **6** guarda los resultados de la última consulta o del listado completo en:

```text
reportes/ultima_consulta.txt
```

La carpeta `reportes` se crea automáticamente. El archivo incluye la descripción de la consulta, la cantidad de resultados y los datos de los tickets:

```text
NEXO - Reporte de tickets
Consulta: ID: 1
Total: 1
----------------------------------------
1 | Error de acceso | Alta | Sistemas | Abierto
```

**Cada exportación reemplaza el reporte anterior.** Si querés conservarlo, copiá o renombrá el archivo antes de exportar nuevamente. No se exportan consultas sin resultados.

## Organización del proyecto

```text
Nexo/
├── src/
│   ├── Main.java
│   ├── MenuConsola.java
│   ├── Ticket.java
│   ├── Prioridad.java
│   ├── Estado.java
│   ├── GestorTickets.java
│   └── GestorArchivos.java
├── data/
│   └── tickets.csv
├── Nexo.iml
└── README.md
```

| Clase | Responsabilidad |
| --- | --- |
| `Main` | Cargar los datos, preparar los gestores e iniciar el menú. |
| `MenuConsola` | Mostrar opciones, validar la entrada y presentar resultados. |
| `Ticket` | Representar los datos de un ticket mediante atributos privados y getters. |
| `Prioridad` | Enumeración con las prioridades válidas y su etiqueta. |
| `Estado` | Enumeración con los estados válidos y su etiqueta. |
| `GestorTickets` | Listar y buscar los tickets guardados en un `ArrayList`. |
| `GestorArchivos` | Leer el CSV y escribir el reporte con `FileReader`, `FileWriter` y buffers. |

Los archivos se cierran automáticamente mediante `try` con recursos. Los errores de lectura y escritura se manejan con `IOException` y se informan en consola.

`Nexo.iml` es una configuración opcional de IntelliJ IDEA. Las carpetas `out` y `reportes` se generan al compilar y exportar, respectivamente.

## Contenidos aplicados

- Clases, objetos, constructores y encapsulamiento.
- Enumeraciones (`enum`) para representar valores válidos.
- Variables, tipos de datos y operadores.
- Condicionales, `switch`, ciclos `while` y `for`.
- Colecciones `ArrayList`.
- Procesamiento de cadenas y conversión de texto a números.
- Lectura y escritura de archivos CSV y TXT.
- Manejo de excepciones con `try`, `catch` y `throws`.
- Separación de responsabilidades y documentación Javadoc.

## Posibles mejoras

- Registrar y actualizar tickets desde el menú.
- Detectar IDs duplicados al cargar los datos.
- Buscar por título.
- Conservar varios reportes con nombres según la consulta.

Estas funcionalidades se plantean como futuras ampliaciones; la versión actual se centra en consultar datos del CSV y exportar resultados.

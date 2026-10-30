import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Muestra el menú, valida la entrada y presenta los resultados de las consultas.
 */
public class MenuConsola {
    private static final String ARCHIVO_REPORTE = "reportes/ultima_consulta.txt";
    private final GestorTickets gestor;
    private final GestorArchivos archivos;
    private List<Ticket> resultados = new ArrayList<>();
    private String consulta = "";

    /**
     * Recibe los gestores que realizan las consultas y guardan los reportes.
     *
     * @param gestor gestor de consultas
     * @param archivos gestor de archivos
     */
    public MenuConsola(GestorTickets gestor, GestorArchivos archivos) {
        this.gestor = gestor;
        this.archivos = archivos;
    }

    private void mostrarMenu() {
        System.out.println("\n==== NEXO - Soporte de Tickets ====");
        System.out.println("1. Listar tickets");
        System.out.println("2. Buscar por prioridad");
        System.out.println("3. Buscar por area");
        System.out.println("4. Buscar por ID");
        System.out.println("5. Buscar por estado");
        System.out.println("6. Guardar resultados");
        System.out.println("7. Salir");
        System.out.println("=====================================");
        System.out.print("Seleccione una opcion: ");
    }

    /**
     * Ejecuta el menú hasta salir o finalizar la entrada de consola.
     */
    public void iniciar() {
        resultados = new ArrayList<>();
        consulta = "";

        try (Scanner scanner = new Scanner(System.in)) {
            boolean continuar = true;
            mostrarMenu();
            while (continuar && scanner.hasNextLine()) {
                String opcion = scanner.nextLine().trim();
                switch (opcion) {
                    case "1":
                        listarTickets();
                        break;
                    case "2":
                        continuar = buscarPorPrioridad(scanner);
                        break;
                    case "3":
                        continuar = buscarPorArea(scanner);
                        break;
                    case "4":
                        continuar = buscarPorId(scanner);
                        break;
                    case "5":
                        continuar = buscarPorEstado(scanner);
                        break;
                    case "6":
                        guardarResultados();
                        break;
                    case "7":
                        continuar = false;
                        System.out.println("Gracias por usar NEXO.");
                        break;
                    default:
                        System.out.println("Opcion invalida. Ingrese un numero del 1 al 7.");
                }
                if (continuar) {
                    mostrarMenu();
                }
            }
        }
    }

    /**
     * Opción 1: muestra todos los tickets.
     */
    private void listarTickets() {
        resultados = gestor.listarTodos();
        consulta = "Listado completo";
        mostrarTickets(resultados);
    }

    /**
     * Opción 2: busca tickets por prioridad.
     *
     * @return {@code false} si la entrada terminó mientras se pedía el dato
     */
    private boolean buscarPorPrioridad(Scanner scanner) {
        String texto = pedirTexto(scanner, "Ingrese prioridad (Alta, Media o Baja): ");
        if (texto == null) {
            return false;
        }
        if (texto.isEmpty()) {
            return true;
        }
        try {
            Prioridad prioridad = Prioridad.desdeTexto(texto);
            resultados = gestor.buscarPorPrioridad(prioridad);
            consulta = "Prioridad: " + prioridad;
            mostrarTickets(resultados);
        } catch (IllegalArgumentException e) {
            System.out.println("Prioridad invalida. Use Alta, Media o Baja.");
        }
        return true;
    }

    /**
     * Opción 3: busca tickets por el nombre completo del área.
     *
     * @return {@code false} si la entrada terminó mientras se pedía el dato
     */
    private boolean buscarPorArea(Scanner scanner) {
        String area = pedirTexto(scanner, "Ingrese el nombre completo del area: ");
        if (area == null) {
            return false;
        }
        if (area.isEmpty()) {
            return true;
        }
        resultados = gestor.buscarPorArea(area);
        consulta = "Area: " + area;
        mostrarTickets(resultados);
        return true;
    }

    /**
     * Opción 4: busca un ticket por su ID.
     *
     * @return {@code false} si la entrada terminó mientras se pedía el dato
     */
    private boolean buscarPorId(Scanner scanner) {
        String texto = pedirTexto(scanner, "Ingrese el ID numerico: ");
        if (texto == null) {
            return false;
        }
        if (texto.isEmpty()) {
            return true;
        }
        try {
            int id = Integer.parseInt(texto);
            if (id <= 0) {
                System.out.println("El ID debe ser mayor que 0.");
                return true;
            }
            resultados = gestor.buscarPorId(id);
            consulta = "ID: " + id;
            mostrarTickets(resultados);
        } catch (NumberFormatException e) {
            System.out.println("El ID debe ser un entero valido.");
        }
        return true;
    }

    /**
     * Opción 5: busca tickets por estado.
     *
     * @return {@code false} si la entrada terminó mientras se pedía el dato
     */
    private boolean buscarPorEstado(Scanner scanner) {
        String texto = pedirTexto(scanner, "Ingrese estado (Abierto, En_Progreso o Resuelto): ");
        if (texto == null) {
            return false;
        }
        if (texto.isEmpty()) {
            return true;
        }
        try {
            Estado estado = Estado.desdeTexto(texto);
            resultados = gestor.buscarPorEstado(estado);
            consulta = "Estado: " + estado;
            mostrarTickets(resultados);
        } catch (IllegalArgumentException e) {
            System.out.println("Estado invalido. Use Abierto, En_Progreso o Resuelto.");
        }
        return true;
    }

    /**
     * Opción 6: guarda en un archivo los resultados de la última consulta.
     */
    private void guardarResultados() {
        if (resultados.isEmpty()) {
            System.out.println("No hay resultados para guardar. Realice una consulta.");
            return;
        }
        try {
            archivos.exportarResultados(resultados, ARCHIVO_REPORTE, consulta);
            System.out.println("Reporte guardado en: " + ARCHIVO_REPORTE);
        } catch (IOException e) {
            System.out.println("No se pudo guardar el reporte: " + e.getMessage());
        }
    }

    /**
     * Muestra un mensaje y lee la respuesta del usuario sin espacios en los extremos.
     * Si la respuesta está vacía, avisa y devuelve una cadena vacía.
     *
     * @param scanner lector de la entrada de consola
     * @param mensaje texto que se muestra antes de leer
     * @return el texto ingresado, una cadena vacía si no escribió nada, o {@code null}
     *         si la entrada terminó
     */
    private String pedirTexto(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        if (!scanner.hasNextLine()) {
            return null;
        }
        String texto = scanner.nextLine().trim();
        if (texto.isEmpty()) {
            System.out.println("La busqueda no puede estar vacia.");
        }
        return texto;
    }

    /**
     * Muestra los tickets encontrados y su cantidad.
     */
    private void mostrarTickets(List<Ticket> tickets) {
        if (tickets.isEmpty()) {
            System.out.println("No se encontraron tickets.");
            return;
        }
        System.out.println("\nID | TITULO | PRIORIDAD | AREA | ESTADO");
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
        System.out.println("Total de resultados: " + tickets.size());
    }
}

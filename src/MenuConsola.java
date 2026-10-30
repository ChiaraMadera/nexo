import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Muestra el menú, valida la entrada y presenta los resultados de las consultas.
 */
public class MenuConsola {
    private static final String ARCHIVO_REPORTE = "reportes/ultima_consulta.txt";
    private final GestorTickets gestor;
    private final GestorArchivos archivos;

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
        System.out.println("\n*** NEXO - Soporte de Tickets ***");
        System.out.println("1. Listar tickets");
        System.out.println("2. Buscar por prioridad");
        System.out.println("3. Buscar por area");
        System.out.println("4. Buscar por ID");
        System.out.println("5. Buscar por estado");
        System.out.println("6. Guardar resultados");
        System.out.println("7. Salir");
        System.out.println("*********************************");
        System.out.print("Seleccione una opcion: ");
    }

    /**
     * Ejecuta el menú hasta salir o finalizar la entrada de consola.
     */
    public void iniciar() {
        ArrayList<Ticket> resultados = new ArrayList<>();
        String consulta = "";

        try (Scanner scanner = new Scanner(System.in)) {
            boolean continuar = true;
            mostrarMenu();
            while (continuar && scanner.hasNextLine()) {
                String opcion = scanner.nextLine().trim();
                switch (opcion) {
                    case "1":
                        resultados = gestor.listarTodos();
                        consulta = "Listado completo";
                        mostrarTickets(resultados);
                        break;
                    case "2":
                        String textoPrioridad = pedirTexto(scanner, "Ingrese prioridad (Alta, Media o Baja): ");
                        if (textoPrioridad == null) {
                            continuar = false;
                            break;
                        }
                        if (textoPrioridad.isEmpty()) {
                            break;
                        }
                        try {
                            Prioridad prioridad = Prioridad.desdeTexto(textoPrioridad);
                            resultados = gestor.buscarPorPrioridad(prioridad);
                            consulta = "Prioridad: " + prioridad;
                            mostrarTickets(resultados);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Prioridad invalida. Use Alta, Media o Baja.");
                        }
                        break;
                    case "3":
                        String area = pedirTexto(scanner, "Ingrese el nombre completo del area: ");
                        if (area == null) {
                            continuar = false;
                            break;
                        }
                        if (area.isEmpty()) {
                            break;
                        }
                        resultados = gestor.buscarPorArea(area);
                        consulta = "Area: " + area;
                        mostrarTickets(resultados);
                        break;
                    case "4":
                        String textoId = pedirTexto(scanner, "Ingrese el ID numerico: ");
                        if (textoId == null) {
                            continuar = false;
                            break;
                        }
                        if (textoId.isEmpty()) {
                            break;
                        }
                        try {
                            int id = Integer.parseInt(textoId);
                            if (id <= 0) {
                                System.out.println("El ID debe ser mayor que 0.");
                                break;
                            }
                            resultados = gestor.buscarPorId(id);
                            consulta = "ID: " + id;
                            mostrarTickets(resultados);
                        } catch (NumberFormatException e) {
                            System.out.println("El ID debe ser un entero valido.");
                        }
                        break;
                    case "5":
                        String textoEstado = pedirTexto(scanner, "Ingrese estado (Abierto, En_Progreso o Resuelto): ");
                        if (textoEstado == null) {
                            continuar = false;
                            break;
                        }
                        if (textoEstado.isEmpty()) {
                            break;
                        }
                        try {
                            Estado estado = Estado.desdeTexto(textoEstado);
                            resultados = gestor.buscarPorEstado(estado);
                            consulta = "Estado: " + estado;
                            mostrarTickets(resultados);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Estado invalido. Use Abierto, En_Progreso o Resuelto.");
                        }
                        break;
                    case "6":
                        if (resultados.isEmpty()) {
                            System.out.println("No hay resultados para guardar. Realice una consulta.");
                            break;
                        }
                        try {
                            archivos.exportarResultados(resultados, ARCHIVO_REPORTE, consulta);
                            System.out.println("Reporte guardado en: " + ARCHIVO_REPORTE);
                        } catch (IOException e) {
                            System.out.println("No se pudo guardar el reporte: " + e.getMessage());
                        }
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
    private void mostrarTickets(ArrayList<Ticket> tickets) {
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

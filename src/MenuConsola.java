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
                        System.out.print("Ingrese prioridad (Alta, Media o Baja): ");
                        if (!scanner.hasNextLine()) {
                            continuar = false;
                            break;
                        }
                        String textoPrioridad = scanner.nextLine().trim();
                        if (textoPrioridad.isEmpty()) {
                            System.out.println("La busqueda no puede estar vacia.");
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
                        System.out.print("Ingrese el nombre completo del area: ");
                        if (!scanner.hasNextLine()) {
                            continuar = false;
                            break;
                        }
                        String area = scanner.nextLine().trim();
                        if (area.isEmpty()) {
                            System.out.println("La busqueda no puede estar vacia.");
                            break;
                        }
                        resultados = gestor.buscarPorArea(area);
                        consulta = "Area: " + area;
                        mostrarTickets(resultados);
                        break;
                    case "4":
                        System.out.print("Ingrese el ID numerico: ");
                        if (!scanner.hasNextLine()) {
                            continuar = false;
                            break;
                        }
                        String textoId = scanner.nextLine().trim();
                        if (textoId.isEmpty()) {
                            System.out.println("La busqueda no puede estar vacia.");
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
                        System.out.print("Ingrese estado (Abierto, En_Progreso o Resuelto): ");
                        if (!scanner.hasNextLine()) {
                            continuar = false;
                            break;
                        }
                        String textoEstado = scanner.nextLine().trim();
                        if (textoEstado.isEmpty()) {
                            System.out.println("La busqueda no puede estar vacia.");
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

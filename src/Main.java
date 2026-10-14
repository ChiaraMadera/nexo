import java.io.IOException;
import java.util.ArrayList;

/**
 * Carga los tickets e inicia la aplicación NEXO.
 */
public class Main {

    private static final String ARCHIVO_DATOS = "data/tickets.csv";

    public static void main(String[] args) {
        GestorArchivos archivos = new GestorArchivos();
        try {
            ArrayList<Ticket> tickets = archivos.cargarTickets(ARCHIVO_DATOS);
            System.out.println("Se cargaron " + tickets.size() + " tickets.");
            GestorTickets gestor = new GestorTickets(tickets);
            MenuConsola menu = new MenuConsola(gestor, archivos);
            menu.iniciar();
        } catch (IOException e) {
            System.out.println("No se pudo leer el CSV: " + e.getMessage());
        }
    }
}

import java.util.ArrayList;

/**
 * Permite listar tickets y buscarlos por ID, prioridad, área o estado.
 */
public class GestorTickets {

    private final ArrayList<Ticket> tickets;

    /**
     * Recibe los tickets que se van a consultar.
     *
     * @param tickets lista de tickets cargados
     */
    public GestorTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    /**
     * Devuelve una copia de la lista, con los mismos tickets.
     *
     * @return lista de todos los tickets
     */
    public ArrayList<Ticket> listarTodos() {
        return new ArrayList<>(tickets);
    }

    /**
     * Busca un ticket por su ID exacto.
     *
     * @param id ID que se busca
     * @return lista con el ticket encontrado, o vacía si no existe
     */
    public ArrayList<Ticket> buscarPorId(int id) {
        ArrayList<Ticket> resultados = new ArrayList<>();

        for (Ticket ticket : tickets) {
            if (ticket.getId() == id) {
                resultados.add(ticket);
            }
        }

        return resultados;
    }

    /**
     * Busca por prioridad sin distinguir mayúsculas y minúsculas.
     *
     * @param prioridad prioridad que se busca
     * @return tickets encontrados, o una lista vacía
     */
    public ArrayList<Ticket> buscarPorPrioridad(String prioridad) {
        ArrayList<Ticket> resultados = new ArrayList<>();

        for (Ticket ticket : tickets) {
            if (ticket.getPrioridad().equalsIgnoreCase(
                    prioridad.trim())) {
                resultados.add(ticket);
            }
        }

        return resultados;
    }

    /**
     * Busca por el nombre completo del área, sin distinguir mayúsculas.
     *
     * @param area nombre del área que se busca
     * @return tickets encontrados, o una lista vacía
     */
    public ArrayList<Ticket> buscarPorArea(String area) {
        ArrayList<Ticket> resultados = new ArrayList<>();

        for (Ticket ticket : tickets) {
            if (ticket.getArea().equalsIgnoreCase(area.trim())) {
                resultados.add(ticket);
            }
        }

        return resultados;
    }

    /**
     * Busca por estado sin distinguir mayúsculas y minúsculas.
     *
     * @param estado estado que se busca
     * @return tickets encontrados, o una lista vacía
     */
    public ArrayList<Ticket> buscarPorEstado(String estado) {
        ArrayList<Ticket> resultados = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getEstado().equalsIgnoreCase(estado.trim())) {
                resultados.add(ticket);
            }
        }
        return resultados;
    }

}

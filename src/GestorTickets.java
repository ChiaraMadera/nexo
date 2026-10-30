import java.util.ArrayList;
import java.util.List;

/**
 * Permite listar tickets y buscarlos por ID, prioridad, área o estado.
 */
public class GestorTickets {

    private final List<Ticket> tickets;

    /**
     * Recibe los tickets que se van a consultar. Guarda una copia de la lista,
     * por lo que los cambios posteriores en la lista original no afectan al gestor.
     *
     * @param tickets lista de tickets cargados
     */
    public GestorTickets(List<Ticket> tickets) {
        this.tickets = new ArrayList<>(tickets);
    }

    /**
     * Devuelve una copia de la lista, con los mismos tickets.
     *
     * @return lista de todos los tickets
     */
    public List<Ticket> listarTodos() {
        return new ArrayList<>(tickets);
    }

    /**
     * Busca un ticket por su ID exacto.
     *
     * @param id ID que se busca
     * @return lista con el ticket encontrado, o vacía si no existe
     */
    public List<Ticket> buscarPorId(int id) {
        List<Ticket> resultados = new ArrayList<>();

        for (Ticket ticket : tickets) {
            if (ticket.getId() == id) {
                resultados.add(ticket);
            }
        }

        return resultados;
    }

    /**
     * Busca por prioridad.
     *
     * @param prioridad prioridad que se busca
     * @return tickets encontrados, o una lista vacía
     */
    public List<Ticket> buscarPorPrioridad(Prioridad prioridad) {
        List<Ticket> resultados = new ArrayList<>();

        for (Ticket ticket : tickets) {
            if (ticket.getPrioridad() == prioridad) {
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
    public List<Ticket> buscarPorArea(String area) {
        List<Ticket> resultados = new ArrayList<>();

        for (Ticket ticket : tickets) {
            if (ticket.getArea().equalsIgnoreCase(area.trim())) {
                resultados.add(ticket);
            }
        }

        return resultados;
    }

    /**
     * Busca por estado.
     *
     * @param estado estado que se busca
     * @return tickets encontrados, o una lista vacía
     */
    public List<Ticket> buscarPorEstado(Estado estado) {
        List<Ticket> resultados = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getEstado() == estado) {
                resultados.add(ticket);
            }
        }
        return resultados;
    }

}

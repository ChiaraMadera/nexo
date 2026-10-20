/**
 * Representa un ticket de soporte con su ID, título, prioridad, área y estado.
 */
public class Ticket {
    private final int id;
    private final String titulo;
    private final Prioridad prioridad;
    private final String area;
    private final Estado estado;

    /**
     * Crea un ticket con los datos recibidos.
     *
     * @param id        ID numérico del ticket
     * @param titulo    descripción breve del problema o solicitud
     * @param prioridad prioridad del ticket
     * @param area      área responsable
     * @param estado    estado actual del ticket
     */
    public Ticket(int id, String titulo, Prioridad prioridad, String area, Estado estado) {
        this.id = id;
        this.titulo = titulo;
        this.prioridad = prioridad;
        this.area = area;
        this.estado = estado;
    }

    /**
     * @return ID numérico del ticket
     */
    public int getId() {
        return id;
    }

    /**
     * @return título del ticket
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @return prioridad del ticket
     */
    public Prioridad getPrioridad() {
        return prioridad;
    }

    /**
     * @return área responsable del ticket
     */
    public String getArea() {
        return area;
    }

    /**
     * @return estado actual del ticket
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Prepara los datos para mostrarlos en consola o guardarlos en un reporte.
     * La prioridad y el estado se imprimen con su etiqueta, así que el formato
     * del reporte no cambia.
     *
     * @return datos del ticket separados por barras verticales
     */
    @Override
    public String toString() {
        return id + " | " + titulo + " | " + prioridad + " | " + area + " | " + estado;
    }
}

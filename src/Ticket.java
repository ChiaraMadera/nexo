/**
 * Representa un ticket de soporte con su ID, título, prioridad, área y estado.
 */
import java.util.Objects;

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
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que 0.");
        }
        Objects.requireNonNull(prioridad, "La prioridad no puede ser null.");
        Objects.requireNonNull(estado, "El estado no puede ser null.");
        String tituloLimpio = Objects.requireNonNull(titulo, "El titulo no puede ser null.").trim();
        String areaLimpia = Objects.requireNonNull(area, "El area no puede ser null.").trim();
        if (tituloLimpio.isEmpty()) {
            throw new IllegalArgumentException("El titulo no puede estar vacio.");
        }
        if (areaLimpia.isEmpty()) {
            throw new IllegalArgumentException("El area no puede estar vacia.");
        }
        this.id = id;
        this.titulo = tituloLimpio;
        this.prioridad = prioridad;
        this.area = areaLimpia;
        this.estado = estado;
    }

    /**
     * Dos tickets son iguales si tienen el mismo ID.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ticket)) {
            return false;
        }
        return id == ((Ticket) o).id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
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

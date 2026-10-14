/**
 * Representa un ticket de soporte con su ID, título, prioridad, área y estado.
 */
public class Ticket {
    private final int id;
    private final String titulo;
    private final String prioridad;
    private final String area;
    private final String estado;

    /**
     * Crea un ticket con los datos recibidos.
     */
    public Ticket(int id, String titulo, String prioridad, String area, String estado) {
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
    public String getPrioridad() {
        return prioridad;
    }

    /**
     * @return área responsable del ticket
     */
    public String getArea() {
        return area;
    }

    /**
     * @return estado del ticket
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Prepara los datos para mostrarlos en consola o guardarlos en un reporte.
     *
     * @return datos del ticket separados por barras verticales
     */
    public String toString(){
        return id + " | " + titulo + " | " + prioridad + " | " + area + " | " + estado;
    }
}

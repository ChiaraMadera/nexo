/**
 * Prioridad de un ticket de soporte.
 *
 * <p>Cada constante lleva la etiqueta que se usa en el CSV, en la consola y en
 * los reportes. El nombre de la constante queda como detalle interno de Java.</p>
 */
import java.util.HashMap;
import java.util.Map;

public enum Prioridad {
    ALTA("Alta"),
    MEDIA("Media"),
    BAJA("Baja");

    private final String etiqueta;

    private static final Map<String, Prioridad> POR_ETIQUETA = new HashMap<>();

    static {
        for (Prioridad p : values()) {
            POR_ETIQUETA.put(p.etiqueta.toLowerCase(), p);
        }
    }

    Prioridad(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    /**
     * @return etiqueta legible de la prioridad
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Convierte un texto del CSV o de la consola a una prioridad, sin distinguir
     * mayúsculas y minúsculas.
     *
     * @param texto texto a interpretar
     * @return la prioridad correspondiente
     * @throws IllegalArgumentException si el texto no corresponde a ninguna prioridad
     */
    public static Prioridad desdeTexto(String texto) {
        String buscado = texto.trim().toLowerCase();
        Prioridad resultado = POR_ETIQUETA.get(buscado);
        if (resultado == null) {
            throw new IllegalArgumentException("Prioridad desconocida: " + texto.trim());
        }
        return resultado;
    }

    /**
     * @return etiqueta de la prioridad, por ejemplo {@code Alta}
     */
    @Override
    public String toString() {
        return etiqueta;
    }
}

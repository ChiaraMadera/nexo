/**
 * Estado de un ticket de soporte.
 *
 * <p>Cada constante lleva la etiqueta que se usa en el CSV, en la consola y en
 * los reportes. El nombre de la constante queda como detalle interno de Java.</p>
 */
import java.util.HashMap;
import java.util.Map;

public enum Estado {
    ABIERTO("Abierto"),
    EN_PROGRESO("En_Progreso"),
    RESUELTO("Resuelto");

    private final String etiqueta;

    private static final Map<String, Estado> POR_ETIQUETA = new HashMap<>();

    static {
        for (Estado e : values()) {
            POR_ETIQUETA.put(e.etiqueta.toLowerCase(), e);
        }
    }

    Estado(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    /**
     * @return etiqueta legible del estado
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Convierte un texto del CSV o de la consola a un estado, sin distinguir
     * mayúsculas y minúsculas.
     *
     * @param texto texto a interpretar
     * @return el estado correspondiente
     * @throws IllegalArgumentException si el texto no corresponde a ningún estado
     */
    public static Estado desdeTexto(String texto) {
        String buscado = texto.trim().toLowerCase();
        Estado resultado = POR_ETIQUETA.get(buscado);
        if (resultado == null) {
            throw new IllegalArgumentException("Estado desconocido: " + texto.trim());
        }
        return resultado;
    }

    /**
     * @return etiqueta del estado, por ejemplo {@code En_Progreso}
     */
    @Override
    public String toString() {
        return etiqueta;
    }
}

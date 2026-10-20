/**
 * Estado de un ticket de soporte.
 *
 * <p>Cada constante lleva la etiqueta que se usa en el CSV, en la consola y en
 * los reportes. El nombre de la constante queda como detalle interno de Java.</p>
 */
public enum Estado {
    ABIERTO("Abierto"),
    EN_PROGRESO("En_Progreso"),
    RESUELTO("Resuelto");

    private final String etiqueta;

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
        String buscado = texto.trim();
        for (Estado estado : values()) {
            if (estado.etiqueta.equalsIgnoreCase(buscado)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado desconocido: " + buscado);
    }

    /**
     * @return etiqueta del estado, por ejemplo {@code En_Progreso}
     */
    @Override
    public String toString() {
        return etiqueta;
    }
}

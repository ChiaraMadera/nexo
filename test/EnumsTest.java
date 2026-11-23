import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnumsTest {

    @Test
    void prioridadDesdeTextoIgnoraMayusculas() {
        assertEquals(Prioridad.ALTA, Prioridad.desdeTexto("alta"));
        assertEquals(Prioridad.MEDIA, Prioridad.desdeTexto("  MEDIA  "));
        assertEquals(Prioridad.BAJA, Prioridad.desdeTexto("Baja"));
    }

    @Test
    void prioridadDesconocidaLanza() {
        assertThrows(IllegalArgumentException.class, () -> Prioridad.desdeTexto("Urgente"));
    }

    @Test
    void estadoDesdeTextoIgnoraMayusculas() {
        assertEquals(Estado.EN_PROGRESO, Estado.desdeTexto("en_progreso"));
        assertEquals(Estado.RESUELTO, Estado.desdeTexto("Resuelto"));
    }

    @Test
    void estadoDesconocidoLanza() {
        assertThrows(IllegalArgumentException.class, () -> Estado.desdeTexto("pendiente"));
    }
}
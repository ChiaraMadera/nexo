import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TicketTest {

    @Test
    void creaTicketValidoYNormalizaTextos() {
        Ticket t = new Ticket(1, "  Error  ", Prioridad.ALTA, "  Sistemas  ", Estado.ABIERTO);
        assertEquals(1, t.getId());
        assertEquals("Error", t.getTitulo());
        assertEquals("Sistemas", t.getArea());
    }

    @Test
    void idNoPositivoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new Ticket(0, "t", Prioridad.ALTA, "area", Estado.ABIERTO));
        assertThrows(IllegalArgumentException.class,
                () -> new Ticket(-1, "t", Prioridad.ALTA, "area", Estado.ABIERTO));
    }

    @Test
    void tituloOAreaVaciosLanzanExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new Ticket(1, "   ", Prioridad.ALTA, "area", Estado.ABIERTO));
        assertThrows(IllegalArgumentException.class,
                () -> new Ticket(1, "t", Prioridad.ALTA, "   ", Estado.ABIERTO));
    }

    @Test
    void prioridadOEstadoNullLanzanNPE() {
        assertThrows(NullPointerException.class,
                () -> new Ticket(1, "t", null, "area", Estado.ABIERTO));
        assertThrows(NullPointerException.class,
                () -> new Ticket(1, "t", Prioridad.ALTA, "area", null));
    }

    @Test
    void igualdadPorId() {
        Ticket a = new Ticket(5, "x", Prioridad.BAJA, "area", Estado.RESUELTO);
        Ticket b = new Ticket(5, "y", Prioridad.ALTA, "otra", Estado.ABIERTO);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void idsDistintosNoSonIguales() {
        Ticket a = new Ticket(1, "x", Prioridad.BAJA, "area", Estado.RESUELTO);
        Ticket b = new Ticket(2, "x", Prioridad.BAJA, "area", Estado.RESUELTO);
        assertNotEquals(a, b);
    }
}
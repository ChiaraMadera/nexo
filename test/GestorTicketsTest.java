import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GestorTicketsTest {

    private GestorTickets gestor;

    @BeforeEach
    void setUp() {
        List<Ticket> tickets = List.of(
                new Ticket(1, "Error", Prioridad.ALTA, "Sistemas", Estado.ABIERTO),
                new Ticket(2, "Factura", Prioridad.BAJA, "Facturacion", Estado.RESUELTO),
                new Ticket(3, "Servidor", Prioridad.ALTA, "Infraestructura", Estado.EN_PROGRESO)
        );
        gestor = new GestorTickets(tickets);
    }

    @Test
    void listarTodosDevuelveCopia() {
        assertEquals(3, gestor.listarTodos().size());
    }

    @Test
    void buscarPorIdExistente() {
        List<Ticket> r = gestor.buscarPorId(2);
        assertEquals(1, r.size());
        assertEquals(2, r.get(0).getId());
    }

    @Test
    void buscarPorIdInexistenteDevuelveVacio() {
        assertTrue(gestor.buscarPorId(99).isEmpty());
    }

    @Test
    void buscarPorPrioridad() {
        assertEquals(2, gestor.buscarPorPrioridad(Prioridad.ALTA).size());
    }

    @Test
    void buscarPorAreaIgnoraMayusculas() {
        assertEquals(1, gestor.buscarPorArea("sistemas").size());
    }

    @Test
    void buscarPorAreaSinCoincidencia() {
        assertTrue(gestor.buscarPorArea("sist").isEmpty());
    }

    @Test
    void buscarPorEstado() {
        assertEquals(1, gestor.buscarPorEstado(Estado.RESUELTO).size());
    }
}
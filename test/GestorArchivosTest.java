import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GestorArchivosTest {

    @TempDir
    Path tempDir;

    @Test
    void cargaCsvValido() throws IOException {
        Path csv = tempDir.resolve("t.csv");
        Files.writeString(csv, "id;titulo;prioridad;area;estado\n1;Error;Alta;Sistemas;Abierto\n2;Factura;Baja;Facturacion;Resuelto\n");
        List<Ticket> tickets = new GestorArchivos().cargarTickets(csv.toString());
        assertEquals(2, tickets.size());
        assertEquals(1, tickets.get(0).getId());
    }

    @Test
    void omiteLineasVaciasYEncabezado() throws IOException {
        Path csv = tempDir.resolve("t.csv");
        Files.writeString(csv, "\n  \nid;titulo;prioridad;area;estado\n1;Error;Alta;Sistemas;Abierto\n");
        assertEquals(1, new GestorArchivos().cargarTickets(csv.toString()).size());
    }

    @Test
    void omiteFilasMalformadas() throws IOException {
        Path csv = tempDir.resolve("t.csv");
        Files.writeString(csv, "id;titulo;prioridad;area;estado\n1;Error;Alta;Sistemas;Abierto\n2;X;Alta;;Abierto\n3;mala;Urgente;Sistemas;Abierto\n");
        assertEquals(1, new GestorArchivos().cargarTickets(csv.toString()).size());
    }

    @Test
    void omiteIdsDuplicados() throws IOException {
        Path csv = tempDir.resolve("t.csv");
        Files.writeString(csv, "id;titulo;prioridad;area;estado\n1;A;Alta;Sistemas;Abierto\n1;B;Baja;Sistemas;Resuelto\n");
        assertEquals(1, new GestorArchivos().cargarTickets(csv.toString()).size());
    }

    @Test
    void omiteIdNoNumericoONoPositivo() throws IOException {
        Path csv = tempDir.resolve("t.csv");
        Files.writeString(csv, "id;titulo;prioridad;area;estado\nabc;A;Alta;Sistemas;Abierto\n0;B;Baja;Sistemas;Resuelto\n");
        assertEquals(0, new GestorArchivos().cargarTickets(csv.toString()).size());
    }
}
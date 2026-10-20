import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Lee tickets desde un CSV y guarda reportes en archivos de texto.
 */
public class GestorArchivos {

    /**
     * Lee un CSV en UTF-8 con el formato {@code id;titulo;prioridad;area;estado}.
     * Omite líneas vacías y el encabezado de la primera línea. Avisa y descarta
     * registros incompletos, con un ID que no sea un entero positivo, o con una
     * prioridad o un estado que no exista.
     *
     * @param ruta ubicación del CSV
     * @return tickets válidos del archivo, en el mismo orden
     * @throws IOException si el archivo no existe o no se puede leer
     */
    public ArrayList<Ticket> cargarTickets(String ruta) throws IOException {
        ArrayList<Ticket> tickets = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(ruta, StandardCharsets.UTF_8))) {
            String linea;
            int numeroLinea = 0;
            while ((linea = lector.readLine()) != null) {
                numeroLinea++;
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] campos = linea.split(";", -1);
                if (numeroLinea == 1 && campos[0].trim().equalsIgnoreCase("id")) {
                    continue;
                }
                if (campos.length != 5) {
                    System.out.println("Linea " + numeroLinea + " omitida: debe tener 5 campos.");
                    continue;
                }
                boolean incompleto = false;
                for (int i = 0; i < campos.length; i++) {
                    campos[i] = campos[i].trim();
                    if (campos[i].isEmpty()) {
                        incompleto = true;
                        break;
                    }
                }
                if (incompleto) {
                    System.out.println("Linea " + numeroLinea + " omitida: hay campos vacios.");
                    continue;
                }
                int id;
                try {
                    id = Integer.parseInt(campos[0]);
                } catch (NumberFormatException e) {
                    System.out.println("Linea " + numeroLinea + " omitida: el ID debe ser un entero valido.");
                    continue;
                }
                if (id <= 0) {
                    System.out.println("Linea " + numeroLinea + " omitida: el ID debe ser mayor que 0.");
                    continue;
                }
                Prioridad prioridad;
                Estado estado;
                try {
                    prioridad = Prioridad.desdeTexto(campos[2]);
                    estado = Estado.desdeTexto(campos[4]);
                } catch (IllegalArgumentException e) {
                    System.out.println("Linea " + numeroLinea + " omitida: " + e.getMessage());
                    continue;
                }
                tickets.add(new Ticket(id, campos[1], prioridad, campos[3], estado));
            }
        }
        return tickets;
    }

    /**
     * Guarda un reporte en UTF-8 con la consulta, el total y los tickets.
     * Crea la carpeta de destino si hace falta y reemplaza el reporte anterior.
     *
     * @param resultados tickets que se guardarán
     * @param ruta destino con carpeta, por ejemplo {@code reportes/ultima_consulta.txt}
     * @param descripcion descripción de la consulta
     * @throws IOException si no se puede crear la carpeta o escribir el reporte
     */
    public void exportarResultados(ArrayList<Ticket> resultados, String ruta, String descripcion) throws IOException {
        File destino = new File(ruta);
        File carpeta = destino.getParentFile();
        if (carpeta != null && !carpeta.isDirectory() && !carpeta.mkdirs()) {
            throw new IOException("No se pudo crear la carpeta de reportes.");
        }
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(destino, StandardCharsets.UTF_8))) {
            escritor.write("NEXO - Reporte de tickets");
            escritor.newLine();
            escritor.write("Consulta: " + descripcion);
            escritor.newLine();
            escritor.write("Total: " + resultados.size());
            escritor.newLine();
            escritor.write("----------------------------------------");
            escritor.newLine();
            for (Ticket ticket : resultados) {
                escritor.write(ticket.toString());
                escritor.newLine();
            }
        }
    }
}

package biblioteca;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reserva {
    public String periodo;
    private boolean status;
    private Libro libro;
    private Miembro usuario;
    private String fecha_inicio;
    private String fecha_fin;

    Reserva(String periodo, boolean status, Libro libro, Miembro usuario, String fecha_inicio, String fecha_fin) {
        if (libro == null)
            throw new IllegalArgumentException("Se espera un Libro para 'libro', no puede ser nulo");
        if (usuario == null)
            throw new IllegalArgumentException("Se espera un Miembro para 'usuario', no puede ser nulo");
        if (fecha_inicio == null || fecha_inicio.isBlank())
            throw new IllegalArgumentException("Se espera una cadena para 'fecha_inicio', no puede ser nula o vacía");
        if (fecha_fin == null || fecha_fin.isBlank())
            throw new IllegalArgumentException("Se espera una cadena para 'fecha_fin', no puede ser nula o vacía");
        this.periodo = periodo != null ? periodo : fecha_inicio + " a " + fecha_fin;
        this.status = status;
        this.libro = libro;
        this.usuario = usuario;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }

    public boolean vigencia_reserva() {
        if (fecha_fin == null || fecha_fin.isBlank())
            return false;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaFin = LocalDate.parse(fecha_fin, formatter);
            LocalDate hoy = LocalDate.now();
            return status && !hoy.isAfter(fechaFin);
        } catch (java.time.format.DateTimeParseException e) {
            throw new IllegalArgumentException("Se espera fecha en formato yyyy-MM-dd (cadena). Recibido: " + fecha_fin);
        }
    }

    public static boolean esFechaValida(String fecha) {
        if (fecha == null || fecha.isBlank()) return false;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(fecha, formatter);
            return true;
        } catch (java.time.format.DateTimeParseException e) {
            return false;
        }
    }

    public Libro getLibro() { return libro; }
    public Miembro getUsuario() { return usuario; }
    public String getFechaInicio() { return fecha_inicio; }
    public String getFechaFin() { return fecha_fin; }
}

package biblioteca;

import java.util.ArrayList;
import java.util.List;

public class Miembro extends Cuenta {
    private String status;
    private ArrayList<Reserva> misReservas;

    Miembro(String usuario, String password, String correo, String tipo) {
        super(usuario, password, correo, tipo);
        this.status = "activo";
        this.misReservas = new ArrayList<>();
    }

    public void buscar_libro(Catalogo catalogo, String criterio) {
        if (catalogo == null)
            throw new IllegalArgumentException("Se espera un Catalogo para 'catalogo', no puede ser nulo");
        if (criterio == null || criterio.isBlank())
            throw new IllegalArgumentException("Se espera una cadena para 'criterio', no puede ser nula o vacia");
        List<Libro> resultados = catalogo.buscarPorCriterio(criterio);
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron libros para: " + criterio);
        } else {
            System.out.println("Resultados para '" + criterio + "':");
            for (Libro l : resultados) {
                if (l != null) l.ficha_bibliografica();
            }
        }
    }

    public void reservar_libro(Libro libro, Catalogo catalogo, String fecha_inicio, String fecha_fin) {
        if (!"activo".equalsIgnoreCase(status))
            throw new IllegalStateException("No puede reservar: su cuenta no esta"
                    + " activa.");
        if (libro == null)
            throw new IllegalArgumentException("Se espera un Libro para 'libro', no puede ser nulo");
        if (catalogo == null)
            throw new IllegalArgumentException("Se espera un Catalogo para 'catalogo', no puede ser nulo");
        if (catalogo.libros == null || !catalogo.libros.contains(libro))
            throw new IllegalArgumentException("El libro no está en el catálogo.");
        if (!Reserva.esFechaValida(fecha_inicio) || !Reserva.esFechaValida(fecha_fin))
            throw new IllegalArgumentException("Se esperan fechas en formato yyyy-MM-dd (cadena). Ejemplo: 2025-02-18");
        String periodo = fecha_inicio + " a " + fecha_fin;
        Reserva r = new Reserva(periodo, true, libro, this, fecha_inicio, fecha_fin);
        misReservas.add(r);
        System.out.println("Reserva realizada: " + libro.getAutor() + " del " + periodo);
    }

    public void leer_libro(Libro libro) {
        if (libro == null)
            throw new IllegalArgumentException("Se espera un Libro para 'libro', no puede ser nulo");
        libro.abrir();
        System.out.println("Leyendo: " + libro.getAutor() + " - " + libro.categoria);
    }

    public void historial() {
        if (misReservas == null || misReservas.isEmpty()) {
            System.out.println("No tiene reservas en su historial.");
            return;
        }
        System.out.println("=== Historial de reservas ===");
        for (Reserva r : misReservas) {
            try {
                boolean vigente = r.vigencia_reserva();
                System.out.println("- " + r.getLibro().getAutor() + " | " + r.getFechaInicio() + " a " + r.getFechaFin() + " | Vigente: " + vigente);
            } catch (IllegalArgumentException e) {
                System.err.println("- Reserva con fecha invalida: " + e.getMessage());
            }
        }
    }

    public void setStatus(String status) { this.status = status; }
    public String getStatus() { return status; }
}

package biblioteca;

import java.util.ArrayList;

public class Catalogo {
    public ArrayList<Libro> libros;

    Catalogo() {
        this.libros = new ArrayList<>();
    }

    Catalogo(ArrayList<Libro> libros) {
        this.libros = libros != null ? libros : new ArrayList<>();
    }

    public void mostrar_libros() {
        if (libros == null || libros.isEmpty()) {
            System.out.println("El catalogo esta vacio.");
            return;
        }
        System.out.println("=== Libros en el catalogo ===");
        for (int i = 0; i < libros.size(); i++) {
            Libro l = libros.get(i);
            if (l != null) {
                System.out.println((i + 1) + ". " + l.getAutor() + " - " + l.categoria + " (" + l.getEditorial() + ")");
            }
        }
    }

    public void agregarLibro(Libro libro) {
        if (libro == null)
            throw new IllegalArgumentException("Se espera un Libro para 'libro', no puede ser nulo");
        if (libros == null)
            libros = new ArrayList<>();
        libros.add(libro);
        System.out.println("Libro agregado al catalogo.");
    }

    public java.util.List<Libro> buscarPorCriterio(String criterio) {
        if (criterio == null || criterio.isBlank())
            throw new IllegalArgumentException("Se espera una cadena para 'criterio', no puede ser nula o vacia");
        if (libros == null)
            return new java.util.ArrayList<>();
        java.util.List<Libro> resultados = new java.util.ArrayList<>();
        String crit = criterio.toLowerCase();
        for (Libro l : libros) {
            if (l == null) continue;
            String autor = l.getAutor();
            String categoria = l.categoria;
            String editorial = l.getEditorial();
            String titulo = l.titulo;
            if ((autor != null && autor.toLowerCase().contains(crit)) ||
                (categoria != null && categoria.toLowerCase().contains(crit)) ||
                    (titulo != null && titulo.toLowerCase().contains(crit)) ||
                    (editorial != null && editorial.toLowerCase().contains(crit))) {
                resultados.add(l);
            }
        }
        return resultados;
    }
}

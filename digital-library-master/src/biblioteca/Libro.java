package biblioteca;

public class Libro {
    private String autor;
    private String expedicion;
    private String editorial;
    public String categoria;
    private int paginaActual;
    private boolean abierto;
    public String titulo;

    Libro(String tiitulo, String autor, String expedicion, String editorial, String categoria) {
        if (autor == null || autor.isBlank())
            throw new IllegalArgumentException("Se espera una cadena para 'autor', no puede ser nulo o vacio");
        if (editorial == null || editorial.isBlank())
            throw new IllegalArgumentException("Se espera una cadena para 'editorial', no puede ser nulo o vacio");
        if (categoria == null || categoria.isBlank())
            throw new IllegalArgumentException("Se espera una cadena para 'categoria', no puede ser nulo o vacio");
        this.autor = autor;
        this.expedicion = expedicion != null ? expedicion : "";
        this.editorial = editorial;
        this.categoria = categoria;
        this.paginaActual = 1;
        this.abierto = false;
        this.titulo =tiitulo;
    }
    public void ficha_bibliografica() {
        System.out.println("=== Ficha bibliografica ===");
        System.out.println("Titulo: " + (titulo != null ? titulo : "N/A"));
        System.out.println("Autor: " + (autor != null ? autor : "N/A"));
        System.out.println("Expedicion: " + (expedicion != null ? expedicion : "N/A"));
        System.out.println("Editorial: " + (editorial != null ? editorial : "N/A"));
        System.out.println("Categoria: " + (categoria != null ? categoria : "N/A"));

    }

    public void abrir() {
        abierto = true;
        paginaActual = 1;
        System.out.println("Libro abierto en la pagina " + paginaActual);
    }

    public void cerrar() {
        abierto = false;
        System.out.println("Libro cerrado. Ultima pagina vista: " + paginaActual);
    }

    public void pasar_pagina() {
        if (!abierto) {
            throw new IllegalStateException("El libro esta cerrado. Abrelo primero.");
        }
        paginaActual++;
        System.out.println("Pagina actual: " + paginaActual);
    }
    public String getAutor() { return autor; }
    public String getEditorial() { return editorial; }
    public String getCategoria() { return categoria; }
}

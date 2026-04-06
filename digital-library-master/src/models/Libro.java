package models;

import helpers.UpsertDataHelper;
import java.util.ArrayList;

public class Libro {
    public static String target = "libros.txt";

    private String autor;
    private String expedicion;
    private String editorial;
    public String categoria;
    private int paginaActual;
    private boolean abierto;
    public String titulo;
    private String uuid;

    public Libro(String tiitulo, String autor, String expedicion, String editorial, String categoria) {
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
    public String getLTitulo(){return titulo;}
    public String getExpedicion(){return expedicion;}
    public void setUuid(String uuid) { this.uuid = uuid; }
    public String getUuid() { return this.uuid;}

        public void guardarLibro() {
        UpsertDataHelper.insert("titulo="+getLTitulo()+",autor="+getAutor()+",editorial="+getEditorial()+",publicacion="+getExpedicion()+",categoria="+getCategoria(), target);
    }
    public static void actualizarLibro(String id, String nuevoTitulo, String nuevoAutor, String nuevaEditorial,String nuevaExpedicion, String nuevaCategoria) {
        ArrayList<String> viejasLineas = UpsertDataHelper.read(target);
        ArrayList<String> nuevasLineas = new ArrayList<>();

        for (String item : viejasLineas) {
            if (item.contains("id=" + id)) {
                item = "id=" + id + ",titulo=" + nuevoTitulo + ",autor=" + nuevoAutor + "" + ",editorial=" + nuevaEditorial + ",publicacion=" + nuevaExpedicion + ",categoria=" + nuevaCategoria;
            }

            nuevasLineas.add(item);
        }

        UpsertDataHelper.update(target, nuevasLineas);
    }
    public static ArrayList<Libro> obtenerLibros() {
        ArrayList<String> datos = UpsertDataHelper.read(target);
        ArrayList<Libro> libros = new ArrayList<>();

        /*
         * POSICION 0 ID
         * POSICION 1 Titulo
         * POSICION 2 Autor
         * POSICION 3 Editorial
         * POSICION 4 Publicacion
         * POSICION 5 Categoria
         */

        for(String item : datos) {
            String[] data = item.split(",");
            String uuid = data[0].split("=")[1];
            String titulo = data[1].split("=")[1];
            String autor = data[2].split("=")[1];
            String editorial = data[3].split("=")[1];
            String publicacion = data[4].split("=")[1];
            String categoria = data[5].split("=")[1];

            Libro libro = new Libro(titulo, autor, editorial, publicacion, categoria);
            libro.setUuid(uuid);
            libros.add(libro);
        }

        return libros;
    }
}
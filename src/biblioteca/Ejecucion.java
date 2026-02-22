
package biblioteca;

import java.util.Scanner;

/**
 *
 * @author ceci
 */
public class Ejecucion {
    
    public static void menu(){
        boolean continuar_ejecucion = true;
        Scanner sc = new Scanner(System.in);
        
        while(continuar_ejecucion){
        
        IO.println("Elija una opcion");
        IO.println("1. Ingresa libro");
        IO.println("2. Ingresa usuario");
        IO.println("3. Eliminar usuario ");
        IO.println("4. Ver historial");
        IO.println("5. salir ");
        
        int respuesta = 0;
        if(sc.hasNext()){
            respuesta = sc.nextInt();
        }else{
            sc.next();
                respuesta = -1;
            }
            
        switch (respuesta) {
            case 1:
                IO.println("seleccionaste 1");
                break;
             case 2:
                  IO.println("seleccionaste 21"
                          + "");
               
                break;
             case 3:
                  IO.println("seleccionaste 3");
             
                break;
             case 4:
                 IO.println("seleccionaste 4");
                
                break;
             case 5:
                 continuar_ejecucion = false;
                 IO.println("Adios");
                break;
            default:
                IO.println("Respuesta invalida");
                }
        }
    }
    
    public static void ejecutarDemo() {
        
        Catalogo catalogo = new Catalogo();

        // Crear administrador y agregar libros
        Administrador admin = new Administrador("admin", "1234", "admin@biblio.com", "administrador");
        Libro libro1 = new Libro("Garcia Marquez", "2020", "Norma", "Ficcion");
        Libro libro2 = new Libro("Borges", "2018", "Emece", "Literatura");
        Libro libro3 = new Libro("Isabel Allende", "2022", "Plaza & Janes", "Ficcion");

        admin.agregar_libro(catalogo, libro1);
        admin.agregar_libro(catalogo, libro2);
        admin.agregar_libro(catalogo, libro3);

        System.out.println("\n--- Catalogo ---");
        catalogo.mostrar_libros();

        // Crear miembro
        Miembro miembro = new Miembro("maria", "pass123", "maria@mail.com", "miembro", "activo");

        System.out.println("\n--- Busqueda ---");
        miembro.buscar_libro(catalogo, "Ficcion");

        System.out.println("\n--- Reserva ---");
        miembro.reservar_libro(libro1, catalogo, "2025-02-18", "2025-02-25");
        miembro.reservar_libro(libro2, catalogo, "2025-02-20", "2025-03-01");

        System.out.println("\n--- Lectura ---");
        miembro.leer_libro(libro3);
        libro3.pasar_pagina();
        libro3.pasar_pagina();
        libro3.cerrar();

        System.out.println("\n--- Historial del miembro ---");
        miembro.historial();

        System.out.println("\n--- Consulta de usuario (admin) ---");
        admin.consultar_usuario(miembro);

        System.out.println("\n--- Validacion de cuentas ---");
        System.out.println("Admin valido: " + admin.validar_usuario());
        System.out.println("Miembro valido: " + miembro.validar_usuario());

        // Demo: manejo de errores
        System.out.println("\n--- Demostracion de manejo de errores ---");
        demostrarManejoErrores(catalogo, libro1);
    }

    private static void demostrarManejoErrores(Catalogo catalogo, Libro libro1) {
        try {
           
            Miembro miembroBloqueado = new Miembro("bloqueado", "pass", "b@mail.com", "miembro", "bloqueado");
            miembroBloqueado.reservar_libro(libro1, catalogo, "2025-02-18", "2025-02-25");
        } catch (IllegalStateException e) {
            System.out.println("Capturado (estado invalido): " + e.getMessage());
        }

        try {
            
            Miembro miembro = new Miembro("maria", "pass", "m@mail.com", "miembro", "activo");
            miembro.reservar_libro(libro1, catalogo, "18-02-2025", "25-02-2025"); // formato incorrecto
        } catch (IllegalArgumentException e) {
            System.out.println("Capturado (argumento invalido): " + e.getMessage());
        }

        try {
            // Intento de pasar página con libro cerrado
            Libro libro = new Libro("Test", "2020", "Test", "Test");
            libro.pasar_pagina(); // libro no abierto
        } catch (IllegalStateException e) {
            System.out.println("Capturado (estado invalido): " + e.getMessage());
        }

        try {
           
            Catalogo cat = new Catalogo();
            cat.agregarLibro(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Capturado (argumento invalido): " + e.getMessage());
        }
    } 

    private static void registrar_usuarios() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

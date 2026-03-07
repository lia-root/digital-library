
package biblioteca;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ceci
 */
public class Ejecucion {
    
    public static ArrayList<Cuenta> miscuentas = new ArrayList<>();
    public static ArrayList<Libro> mislibros = new ArrayList<>();

    static boolean inicio=false;
    public static Cuenta usuario_actual;

    public static void inicializacion(){
        if(inicio==true){
            return;
        }
        Administrador superadmin = new Administrador("log", "123", "log@usi.com", "administrador");
        Miembro supermiembro = new Miembro("ceci", "123", "log@usi.com", "miembro");
        miscuentas.add(superadmin);
        miscuentas.add(supermiembro);

        Libro libro1 = new Libro("El sol","Garcia Marquez","2020", "Norma", "Ficcion");
        Libro libro2 = new Libro("La noche","Borges", "2018", "Emece", "Literatura");
        Libro libro3 = new Libro("Mediacnoche","Isabel Allende", "2022", "Plaza & Janes", "Ficcion");
        mislibros.add(libro1);
        mislibros.add(libro2);
        mislibros.add(libro3);
        inicio = true;
    }
    public static void log_in(){
        inicializacion();
        do {            
            IO.println("Bienvenido a la Bliblioteca");
            IO.println("Ingresa tu usuario");
            Scanner scaner = new Scanner(System.in);
             String variable = scaner.nextLine();

            IO.println("Ingresa tu contraseña");
            Scanner scaner2 = new Scanner(System.in);
            String variable2 = scaner2.nextLine();


            logger("Usuario:" + variable);
            logger("Contraseña:" + variable2);

            Cuenta cuenta = find_user(variable, variable2);

            if(cuenta == null){
                IO.println("Usuario no registrado intente denuevo");
                continue;
            }
            usuario_actual=cuenta;
            if(cuenta instanceof Administrador){
                menuAdmin();
            }else{
                menuMiembro();
            }
        } while (true);
    }
    
    public static Cuenta find_user(String usuario, String contrasenia){
        boolean found = false;
        Cuenta user = null;
        
        for(Cuenta item : miscuentas){
                                              
            if(usuario.equals(item.getUsuario()) && item.comparar_contra(contrasenia)){
                user=item;  
            }
        }
        
        return user;
    }
    
    public static void logger(String message){//comprobacion
         IO.println("=====================");
         
         IO.println(message);
         
         IO.println("======================");
    }
    public static void menuMiembro(){
        boolean continuar_ejecucion = true;
        Scanner sc = new Scanner(System.in);
        
        while(continuar_ejecucion){
        
        IO.println("Elija una opcion");
        IO.println("1. Buscar libro");
        IO.println("2. Ver catalogo");
        IO.println("3. Historial");
        IO.println("4. Regresar al inicio de sesion");
               
        int respuesta = 0;
        if(sc.hasNext()){
            respuesta = sc.nextInt();
        }else{
            sc.next();
                respuesta = -1;
            }
            
        switch (respuesta) {
            case 1:
                buscar_libro();
                break;
             case 2:
                  show_books();
               
                break;
             case 3:
                  historial();
                break;
             case 4:
                log_in();
                break;
             
            default:
                IO.println("Respuesta invalida");
                }
        }
    }
    
    public static void menuAdmin(){
        boolean continuar_ejecucion = true;
        Scanner sc = new Scanner(System.in);
        
        while(continuar_ejecucion){
        
        IO.println("Elija una opcion");
        IO.println("1. Nuevo libro");
        IO.println("2. Crear usuario");
        IO.println("3. Eliminar usuario ");
        IO.println("4. Consulta usuarios");
        IO.println("5. Regresar al inicio de sesion");
               
        int respuesta = 0;
        if(sc.hasNext()){
            respuesta = sc.nextInt();
        }else{
            sc.next();
                respuesta = -1;
            }
            
        switch (respuesta) {
            case 1:
                nuevo_libro();
                break;
             case 2:
                  registrar_usuario();
               
                break;
             case 3:
                  eliminar_usuario();
             
                break;
             case 4:
                 consultar_usuario();
              
                break;
             case 5:
                 log_in();
                 
                break;
            default:
                IO.println("Respuesta invalida");
                }
        }
    }
     
    public static void ejecutarDemo() {
        
     /*   Catalogo catalogo = new Catalogo();

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
        Miembro miembro = new Miembro("maria", "pass123", "maria@mail.com", "miembro");

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
        demostrarManejoErrores(catalogo, libro1);*/
    }

    private static void demostrarManejoErrores(Catalogo catalogo, Libro libro1) {
        try {
           
            Miembro miembroBloqueado = new Miembro("bloqueado", "pass", "b@mail.com", "miembro");
            miembroBloqueado.reservar_libro(libro1, catalogo, "2025-02-18", "2025-02-25");
        } catch (IllegalStateException e) {
            System.out.println("Capturado (estado invalido): " + e.getMessage());
        }

        try {
            
            Miembro miembro = new Miembro("maria", "pass", "m@mail.com", "miembro");
            miembro.reservar_libro(libro1, catalogo, "18-02-2025", "25-02-2025"); // formato incorrecto
        } catch (IllegalArgumentException e) {
            System.out.println("Capturado (argumento invalido): " + e.getMessage());
        }

        try {
            // Intento de pasar página con libro cerrado
           // Libro libro = new Libro("Test", "2020", "Test", "Test");
           //. libro.pasar_pagina(); // libro no abierto
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
    public static void nuevo_libro(){
        IO.println("Ingresa nombre del autor:");
               Scanner scanerautor = new Scanner(System.in);
               String auntor = scanerautor.nextLine();
               
        IO.println("Ingresa año de publicacion:");
               Scanner scanernanio = new Scanner(System.in);
               String anio = scanernanio.nextLine();
                              
        IO.println("Ingresa el nombre editorial:");
               Scanner scanereditorial = new Scanner(System.in);
               String editorial = scanereditorial.nextLine();
               
        IO.println("Categoria de libro:");
               Scanner scanerncetegoria = new Scanner(System.in);
               String categoria = scanerncetegoria.nextLine();     
               
        IO.println("Ingresa titulo del libro:");
               Scanner scanertitulo = new Scanner(System.in);
               String titulo = scanertitulo.nextLine();         
         
        Libro libro = new Libro(auntor, anio, editorial, categoria,titulo);
        mislibros.add(libro);
    }

    public static void registrar_usuario() {

            IO.println("Ingresa el nombre completo de usuario:");
               Scanner scanernombre = new Scanner(System.in);
               String nombre = scanernombre.nextLine();

            IO.println("Ingresa la contraseña");
               Scanner scanercontra = new Scanner(System.in);
               String contra = scanercontra.nextLine();

            IO.println("Ingresa un correo electronico");
               Scanner scanercorreo = new Scanner(System.in);
               String correo = scanercorreo.nextLine();

            IO.println("Este usuario es un administrador ? (si/no)");
               Scanner scanertipo = new Scanner(System.in);
               String tipo = (scanertipo.nextLine().equals("si")) ? "administrador" : "miembro";
            if (tipo.equals("administrador")){
                Administrador administrador = new Administrador(nombre, contra, correo, tipo);
                miscuentas.add(administrador);
            }else{
                 Miembro miembro = new Miembro(nombre, contra, correo, tipo);
                 miscuentas.add(miembro);
            }
            IO.println("creado");
    }

    public static void eliminar_usuario(){
        consultar_usuario();
        IO.println("Ingrese el numero del usuario que desee eliminar");
        Scanner eliminado = new Scanner(System.in);
        int dell = eliminado.nextInt();
        miscuentas.remove(dell);

    }
    public static  void consultar_usuario(){
        IO.println("Usuarios registrados");
        for (int i=1;i<=miscuentas.size()-1; i++){
            IO.println("Usuario #" + i);
            IO.println(miscuentas.get(i).getUsuario());
        }
    }
    public static  void show_books(){
        Catalogo catalogo = new Catalogo(mislibros);
        System.out.println("\n--- Catalogo ---");
        catalogo.mostrar_libros();
    }

    public static void buscar_libro(){

        IO.println("Buscar...");
        Scanner busqueda = new Scanner(System.in);
        String criterio = busqueda.nextLine();

        Catalogo nuevobusqueda = new Catalogo(mislibros);
        ArrayList<Libro> resultado= (ArrayList<Libro>) nuevobusqueda.buscarPorCriterio(criterio);

        IO.println("Resultados de la buscqueda");
        if(resultado.size()==0){
            IO.println("No se encontraron coincidencias");
            return;
        }
        for (int i=0;i<=resultado.size()-1; i++){
            IO.println("Libro # "+ i);
            resultado.get(i).ficha_bibliografica();
        }
        IO.println("Seleccione una opcion:");
        IO.println("1. Reservar libro");
        IO.println("2.Volver al menu principal");
        Scanner Rrespuesta = new Scanner(System.in);
        String reserv = Rrespuesta.nextLine();

        switch (reserv) {
            case "1":
                IO.println("Ingrese el numero de libro: ");
                Scanner librores = new Scanner(System.in);
                int respuesta = librores.nextInt();
                Miembro uno = (Miembro) usuario_actual;
                uno.reservar_libro(resultado.get(respuesta),nuevobusqueda,"2026-02-22", "2026-03-22");
                break;
            default:
        }
    }
    public static void historial(){
        Miembro nuevo = (Miembro) usuario_actual;
        nuevo.historial();
    }

}


package biblioteca;

import static biblioteca.Ejecucion.ejecutarDemo;

/**
 *
 * @author ceci
 */
public class Biblioteca {

public static void main(String[] args) {
        System.out.println("=== Sistema de Biblioteca ===\n");

        try {
            //ejecutarDemo();
            //Ejecucion.ejecutarDemo();
           Ejecucion.log_in();
        
           
        } catch (IllegalArgumentException e) {
            System.err.println("\n[ERROR] Argumento invalido: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("\n[ERROR] Estado invalido: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("\n[ERROR INESPERADO] " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("\n=== Fin del demo ===");
        }
    }

}

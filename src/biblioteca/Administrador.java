package biblioteca;

public class Administrador extends Cuenta {

    Administrador(String usuario, String password, String correo, String tipo) {
        super(usuario, password, correo, tipo);
    }

    public void agregar_libro(Catalogo catalogo, Libro libro) {
        if (catalogo == null)
            throw new IllegalArgumentException("Se espera un Catalogo para 'catalogo', no puede ser nulo");
        if (libro == null)
            throw new IllegalArgumentException("Se espera un Libro para 'libro', no puede ser nulo");
        catalogo.agregarLibro(libro);
    }

    public void bloqueo_usuario(Miembro miembro) {
        if (miembro == null)
            throw new IllegalArgumentException("Se espera un Miembro para 'miembro', no puede ser nulo");
        miembro.setStatus("bloqueado");
        System.out.println("Usuario " + miembro.getUsuario() + " ha sido bloqueado.");
    }

    @Override
    public boolean validar_usuario() {
        return super.validar_usuario() && "administrador".equalsIgnoreCase(getTipo());
    }

    public void consultar_usuario(Cuenta cuenta) {
        if (cuenta == null)
            throw new IllegalArgumentException("Se espera una Cuenta para 'cuenta', no puede ser nula");
        System.out.println("=== Datos del usuario ===");
        System.out.println("Usuario: " + cuenta.getUsuario());
        System.out.println("Correo: " + cuenta.getCorreo());
        System.out.println("Tipo: " + cuenta.getTipo());
        if (cuenta instanceof Miembro m) {
            System.out.println("Estado: " + m.getStatus());
        }
    }
}

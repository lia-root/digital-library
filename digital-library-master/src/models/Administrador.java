package models;

public class Administrador extends Cuenta {
    public Administrador(String usuario, String password, String correo, String tipo) {
        super(usuario, password, correo, tipo);
    }

    @Override
    public boolean validar_usuario() {
        return super.validar_usuario() && "administrador".equalsIgnoreCase(getTipo());
    }
}
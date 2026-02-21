package biblioteca;

public class Cuenta {
    private String usuario;
    protected String password;
    private String correo;
    private String tipo;

    Cuenta(String usuario, String password, String correo, String tipo) {
        if (usuario == null || usuario.isBlank())
            throw new IllegalArgumentException("Se espera una cadena para 'usuario', no puede ser nula o vacia");
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Se espera una cadena para 'password', no puede ser nula o vacia");
        this.usuario = usuario;
        this.password = password;
        this.correo = correo != null ? correo : "";
        this.tipo = tipo != null ? tipo : "";
    }

    public boolean validar_usuario() {
        return usuario != null && !usuario.isEmpty() && password != null && !password.isEmpty();
    }

    public String getUsuario() { return usuario; }
    public String getCorreo() { return correo; }
    public String getTipo() { return tipo; }
}

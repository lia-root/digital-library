package models;

import java.util.ArrayList;

import helpers.UpsertDataHelper;

public class Cuenta {
	public static String target = "cuentas.txt";

    private String usuario;
    protected String password;
    private String correo;
    private String tipo;
    private String uuid;

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
    public boolean comparar_contra(String password) {return this.password.equals(password);}
    public void setUuid(String uuid) { this.uuid = uuid; }
    public String getUuid() { return this.uuid; }
    
    public void guardarUsuario() {
		UpsertDataHelper.save("usuario="+getUsuario()+",password="+password+",correo="+getCorreo()+",tipo="+getTipo(), target);
	}

    public static ArrayList<Cuenta> obtenerUsuarios() {
    	ArrayList<String> datos = UpsertDataHelper.read(target);
    	ArrayList<Cuenta> cuentas = new ArrayList<>();

    	/*
    	 * POSICION 0 ID
    	 * POSICION 1 NOMBRE
    	 * POSICION 2 PASS
    	 * POSICION 3 CORREO
    	 * POSICION 4 TIPO
    	 */

    	for(String item : datos) {
    		String[] data = item.split(",");
    		String uuid = data[0].split("=")[1];
    		String nombre = data[1].split("=")[1];
    		String password = data[2].split("=")[1];
    		String correo = data[3].split("=")[1];
    		String tipo = data[4].split("=")[1];

    		Cuenta cuenta = (tipo.equals("administrador")) ? new Administrador(nombre, password, correo, tipo) : new Miembro(nombre, password, correo, tipo);
    		cuenta.setUuid(uuid);
    		cuentas.add(cuenta);
    	}

    	return cuentas;
    }

    public static void actualizarUsuario(String id, String nuevoUsuario, String nuevaPassword, String nuevoCorreo, String nuevoTipo) {
    	ArrayList<String> viejasLineas = UpsertDataHelper.read(target);
    	ArrayList<String> nuevasLineas = new ArrayList<>();

    	for(String item : viejasLineas) {
    		if (item.contains("id=" + id)) {
    			item = "id="+id+",usuario="+nuevoUsuario+""+",password="+nuevaPassword+",correo="+nuevoCorreo+",tipo="+nuevoTipo;
    		}

    		nuevasLineas.add(item);
    	}

    	UpsertDataHelper.actualizarUsuario(target, nuevasLineas);
    }
}
package models;

import java.time.LocalDate;
import java.util.ArrayList;

import helpers.ShowMessageHelper;
import helpers.UpsertDataHelper;

public class Cuenta {
	public static String target = "cuentas.txt";

    private String usuario;
    protected String password;
    private String correo;
    private String tipo;
    private String uuid;

    public Cuenta(String usuario, String password, String correo, String tipo) {
        if (usuario == null || usuario.isBlank())
            throw new IllegalArgumentException("Se espera una cadena para 'usuario', no puede ser nula o vacia");
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Se espera una cadena para 'password', no puede ser nula o vacia");
        this.usuario = usuario;
        this.password = password;
        this.correo = correo != null && !correo.equals(" ")  ? correo : "";
        this.tipo = tipo != null && !tipo.equals(" ") ? tipo : "";
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

	private boolean validarUsuarioExistente(){
		if(getUsuario().equals("root")) return true;

		ArrayList<String> datos = UpsertDataHelper.read(target);
		boolean existe = false;

		for(String item : datos) {
			/*


			[id=e874d408-caab-4726-8f19-69d6e0c36895,usuario=Armando,password=2,correo=elguapo67@gmail.com,tipo=miembro,vencimiento=08/05/2026,creacion=2026-04-08]
			[id=e874d408-caab-4726-8f19-69d6e0c3685],[[usuario]=[Armando]],[password=2],[correo=elguapo67@gmail.com],[tipo=miembro],[vencimiento=08/05/2026],[creacion=2026-04-08]


			*/
    		String[] data = item.split(",");
    		String uuid = data[0].split("=")[1];
    		String nombre = data[1].split("=")[1];
    		String password = data[2].split("=")[1];
    		String correo = data[3].split("=")[1];
    		String tipo = data[4].split("=")[1];

				if (nombre.equals(getUsuario()) || correo.equals(getCorreo())) { //pipe
					existe = true;
					break;
				}
    	}

		return existe;
	}

	public void guardarUsuario() {
		if (validarUsuarioExistente()) {
			ShowMessageHelper.showErrorMessage("Ya existe un usuario con ese nombre o correo");
		}else{
			if(getTipo().equals("administrador")){
				UpsertDataHelper.insert("usuario="+getUsuario()+",password="+password+",correo="+getCorreo()+",tipo="+getTipo(), target);
			}else{
				Miembro miembro = new Miembro(getUsuario(), password, getCorreo(), getTipo());
				miembro.calcularNuevaFechaVencimiento();
				UpsertDataHelper.insert("usuario="+getUsuario()+",password="+password+",correo="+getCorreo()+",tipo="+getTipo()+",vencimiento="+miembro.getFechaVencimiento()+",creacion="+LocalDate.now(), target);
			}

		}

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

    		Cuenta cuenta = null;

					if(tipo.equals("administrador")){
						cuenta = new Administrador(nombre, password, correo, tipo);
					}else{
						Miembro objetomiembro =	new Miembro(nombre, password, correo, tipo);
						objetomiembro.setFechaVencimiento(data[5].split("=")[1]);
						cuenta = objetomiembro;

					}
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
					if(nuevoTipo.equals("administrador")) {
						item = "id=" + id + ",usuario=" + nuevoUsuario + "" + ",password=" + nuevaPassword + ",correo=" + nuevoCorreo + ",tipo=" + nuevoTipo;
					}else{
						Miembro miembro = new Miembro(nuevoUsuario,nuevaPassword, nuevoCorreo , nuevoTipo);
						miembro.calcularNuevaFechaVencimiento();

						item = "id=" + id + ",usuario=" + nuevoUsuario + "" + ",password=" + nuevaPassword + ",correo=" + nuevoCorreo + ",tipo=" + nuevoTipo + ",vencimiento="+miembro.getFechaVencimiento();
					}
    		}

    		nuevasLineas.add(item);
    	}

    	UpsertDataHelper.update(target, nuevasLineas);
    }

	public static void eliminarUsuario(String id) {
		ArrayList<String> viejasLineas = UpsertDataHelper.read(target);
		ArrayList<String> nuevasLineas = new ArrayList<>();

		for(String item : viejasLineas) {
			if (item.contains("id=" + id)) {
			}
			else {
				nuevasLineas.add(item);
			}
		}
		UpsertDataHelper.update(target, nuevasLineas);
	}
}
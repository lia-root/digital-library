package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import models.Administrador;
import models.Cuenta;
import models.Miembro;

public class CurrentUserHelper {
    private static String target = "currentUser.txt";

	public static Cuenta get() {
		ArrayList<String> data = new ArrayList<String>();

		try (BufferedReader reader = new BufferedReader(new FileReader(target))) {
			String linea;

	        while ((linea = reader.readLine()) != null) {
				data.add(linea);
	        }

	    } catch (IOException e) {
	    	ShowMessageHelper.showErrorMessage("No fue posible leer los datos del usuario en contexto");
	    	e.printStackTrace();
	    }

        Cuenta accountFound = new Miembro("X","X","X","X");

		for(Cuenta account : Cuenta.obtenerUsuarios()){
            for(String item : data){
                String[] newData = item.split(",");
                String uuid = newData[0].split("=")[1];

                if (account.getUuid().equals(uuid)) {
                    String nombre = newData[1].split("=")[1];
                    String correo = newData[2].split("=")[1];
                    String tipo = newData[3].split("=")[1];

                    accountFound = (tipo.equals("administrador")) ? new Administrador(nombre, "x", correo, tipo) : new Miembro(nombre, "x", correo, tipo);
                    accountFound.setUuid(uuid);
                }
            }
        }

        return accountFound;
	}

	public static void set(Cuenta newCurrentUser) {
		// Sobrescribir archivo con los datos actualizados
        ArrayList<String> data = new ArrayList<String>();
        data.add("id="+newCurrentUser.getUuid()+","+"usuario="+newCurrentUser.getUsuario()+",correo="+newCurrentUser.getCorreo()+",tipo="+newCurrentUser.getTipo());

		upsertData(
			data,
			"BIENVENIDO",
			"A ocurrido un error"
		);
	}

	private static void upsertData(ArrayList<String> newCurrentUser, String infoMessage, String errorMessage){
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(target))) {
			for (String line : newCurrentUser) {
	    		writer.write(line);
	            writer.newLine();
	        }

	    	ShowMessageHelper.showInfoMessage(infoMessage);
	    } catch (IOException e) {
	    	ShowMessageHelper.showErrorMessage(errorMessage);
	    	e.printStackTrace();
	    }
	}
}

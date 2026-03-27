package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class UpsertDataHelper {
	public static void save(String texto, String target) {
		ArrayList<String> lines = read(target);
		lines.add("id="+UUID.randomUUID().toString()+","+texto);
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(target))) {
	    	 for (String l : lines) {
	    		 writer.write(l);
	             writer.newLine();
	         }
	    	 ShowMessageHelper.showInfoMessage("Datos guardados correctamente");
	    	 
	     } catch (IOException e) {
	    	 ShowMessageHelper.showErrorMessage("A ocurrido un error guardando los datos");
	    	 e.printStackTrace();
	     }
     }

	 public static ArrayList<String> read(String target) {
		 ArrayList<String> data = new ArrayList<String>(); 

		 try (BufferedReader reader = new BufferedReader(new FileReader(target))) {
			 String linea;
	         while ((linea = reader.readLine()) != null) {
	        	 data.add(linea);
	         }
	     } catch (IOException e) {
	    	 ShowMessageHelper.showErrorMessage("No fue posible leer los datos");
	    	 e.printStackTrace();
	     }
		 
		 return data;
	}

	 public static void actualizarUsuario(String target, ArrayList<String> newLines) {
	     // Sobrescribir archivo con los datos actualizados
	     try (BufferedWriter writer = new BufferedWriter(new FileWriter(target))) {
	    	 for (String l : newLines) {
	    		 writer.write(l);
	             writer.newLine();
	         }
	    	 ShowMessageHelper.showInfoMessage("Se han actualizado los datos");
	     } catch (IOException e) {
	    	 ShowMessageHelper.showErrorMessage("A ocurrido un error actualizando los datos");
	    	 e.printStackTrace();
	     }
	 }
}

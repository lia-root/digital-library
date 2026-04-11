package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class UpsertDataHelper {

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

	public static void insert(String texto, String target) {
		ArrayList<String> lines = read(target);
		lines.add("id="+UUID.randomUUID().toString()+","+texto);

		upsertData(
			target,
			lines,
			"Datos guardados correctamente",
			"A ocurrido un error guardando los datos"
		);
	}

	private static void upsertData(String target, ArrayList<String> lines, String infoMessage, String errorMessage){
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(target))) {
			for (String line : lines) {
				writer.write(line);
				writer.newLine();//salto de linea
			}

			ShowMessageHelper.showInfoMessage(infoMessage);
		} catch (IOException e) {
			ShowMessageHelper.showErrorMessage(errorMessage);
			e.printStackTrace();
		}
	}

	public static void update(String target, ArrayList<String> newLines) {
		// Sobrescribir archivo con los datos actualizados
		upsertData(
			target,
			newLines,
			"Se han actualizado los datos",
			"A ocurrido un error actualizando los datos"
		);
	}


}

package helpers;

import models.Libro;
import models.Miembro;

import java.io.*;
import java.util.ArrayList;

public class UserHistorialHelper {
    private static String target = "historial.txt";

    public static ArrayList<Libro> get(Miembro user) {
        ArrayList<String> lines = read();
        ArrayList<Libro> books = new ArrayList<Libro>();

        for(String item : lines){
            String[] data = item.split(",");
            String userId = data[0].split("=")[1];

            if(userId.equals(user.getUuid())){
                String idBook = data[1].split("=")[1];
                String fechaDeLectura = data[2].split("=")[1];

                Libro book = Libro.encontrarLibro(idBook);
                if(book!=null){
                    book.setFechaDeLectua(fechaDeLectura);
                    books.add(book);
                }
            }
        }

        return books;
    }

    public static void add(String newRecord) {
        ArrayList<String> lines = read();
        lines.add(newRecord);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(target))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            ShowMessageHelper.showErrorMessage("Ocurrio un error al intentar guardar en el historial");
            e.printStackTrace();
        }
    }

    private static ArrayList<String> read() {
        ArrayList<String> data = new ArrayList<String>();

        try (BufferedReader reader = new BufferedReader(new FileReader(target))) {
            String line;

            while ((line = reader.readLine()) != null) {
                data.add(line);
            }

        } catch (IOException e) {
            ShowMessageHelper.showErrorMessage("No fue posible leer los datos del historial");
            e.printStackTrace();
        }

        return data;
    }
}

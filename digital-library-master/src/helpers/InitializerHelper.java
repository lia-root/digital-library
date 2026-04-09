package helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InitializerHelper {
//?
    private static final String[] DATA_FILES = {
				"cuentas.txt",
		    "libros.txt",
        "historial.txt",
        "currentUser.txt"
    };

    public static void ensureDataFiles() {
			for (String name : DATA_FILES) {
				Path path = Paths.get(name);

				try {
					if (!Files.exists(path)) {
						Files.createFile(path);
					}
				} catch (IOException e) {
					ShowMessageHelper.showErrorMessage("No se pudo crear el archivo de datos: " + name);
					e.printStackTrace();
				}
			}
    }
}

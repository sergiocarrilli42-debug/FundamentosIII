
package nombresarraylist;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class NombresArrayList {

    public static void main(String[] args) {

        ArrayList<String> nombres = new ArrayList<>();

        try {
            File archivo = new File("listado.txt");
            Scanner scanner = new Scanner(archivo);

            while (scanner.hasNextLine()) {
                String nombre = scanner.nextLine();
                nombres.add(nombre);
            }

            scanner.close();

            System.out.println("Nombres cargados: " + nombres.size());

        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo listado.txt");
        }
    }
}



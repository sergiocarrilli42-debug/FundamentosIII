package expedientespersonas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Sergio Carrillo Osorio
 * 25/09/2026
 */
class Personas {

    private String nombre;
    private String expediente;
    private int edad;

    public Personas(String nombre, String expediente, int edad) {
        this.nombre = nombre;
        this.expediente = expediente;
        this.edad = edad;
    }

    public void mostrarDatos() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Expediente: " + expediente);
        System.out.println("Edad: " + edad);
        System.out.println("----------------------");
    }

    public String getNombre() {
        return nombre;
    }

    public String getExpediente() {
        return expediente;
    }

    public int getEdad() {
        return edad;
    }
}

public class Expedientespersonas {

    public static void main(String[] args) {

        String nombreArchivo = "listado_personas_expediente.csv";

        ArrayList<Personas> personas = cargaArchivoPersonas(nombreArchivo);

        if (personas.size() > 10) {
            Personas personaEncontrada = personas.get(10);
            personaEncontrada.mostrarDatos();
        } else {
            System.out.println("No hay suficientes registros en el archivo.");
        }

        String[] nombresABuscar = {
            "Alan Aldama Andre",
            "Isabel Dominguez Ochoa",
            "Ernesto Ozuna Ramirez",
            "Ada Pino Lopez",
            "Bruno Diaz Hernandez",
            "Luis Caro Durazo"
        };

        System.out.println("=== BUSQUEDA EN ARRAYLIST ===");
        buscarVariasPersonasEnLista(personas, nombresABuscar);

        HashMap<String, Personas> mapaPersonas = new HashMap<>();

        for (Personas persona : personas) {
            mapaPersonas.put(persona.getNombre(), persona);
        }

        System.out.println("=== BUSQUEDA INDIVIDUAL EN HASHMAP ===");

        Personas personaEncontradaEnHashMap =
                buscarPersonaEnHashMap(mapaPersonas, "Luis Caro Durazo");

        if (personaEncontradaEnHashMap != null) {
            personaEncontradaEnHashMap.mostrarDatos();
        } else {
            System.out.println("Persona no encontrada.");
        }

        System.out.println("=== BUSQUEDA MULTIPLE EN HASHMAP ===");
        buscarVariasPersonasEnHashMap(mapaPersonas, nombresABuscar);
    }

    public static ArrayList<Personas> cargaArchivoPersonas(String nombreArchivo) {

        ArrayList<Personas> personas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {

            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {

                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] partes = linea.split(",");

                if (partes.length == 3) {

                    String nombre = partes[0].trim();
                    String expediente = partes[1].trim();
                    int edad = Integer.parseInt(partes[2].trim());

                    personas.add(new Personas(nombre, expediente, edad));
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        return personas;
    }

    public static Personas buscarPersonaEnLista(ArrayList<Personas> personas, String nombre) {

        for (Personas persona : personas) {

            if (persona.getNombre().equalsIgnoreCase(nombre)) {
                return persona;
            }
        }

        return null;
    }

    public static void buscarVariasPersonasEnLista(ArrayList<Personas> personas, String[] nombres) {

        long startTime = System.nanoTime();

        for (String nombre : nombres) {

            Personas personaEncontrada = buscarPersonaEnLista(personas, nombre);

            if (personaEncontrada != null) {
                personaEncontrada.mostrarDatos();
            } else {
                System.out.println("No se encontró a la persona con nombre: " + nombre);
            }
        }

        long endTime = System.nanoTime();

        System.out.println("Tiempo de búsqueda: "
                + (endTime - startTime) / 1000000.0 + " ms");
    }

    public static Personas buscarPersonaEnHashMap(
            HashMap<String, Personas> mapaPersonas,
            String nombre) {

        return mapaPersonas.get(nombre);
    }

    public static void buscarVariasPersonasEnHashMap(
            HashMap<String, Personas> mapaPersonas,
            String[] nombres) {

        long startTime = System.nanoTime();

        for (String nombre : nombres) {

            Personas personaEncontrada =
                    buscarPersonaEnHashMap(mapaPersonas, nombre);

            if (personaEncontrada != null) {
                personaEncontrada.mostrarDatos();
            } else {
                System.out.println("No se encontró a la persona con nombre: " + nombre);
            }
        }

        long endTime = System.nanoTime();

        System.out.println("Tiempo de búsqueda en HashMap: "
                + (endTime - startTime) / 1000000.0 + " ms");
    }
}

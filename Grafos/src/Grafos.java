
package grafos;

/**
 *
 * @author sergi
 */

class GrafoArrays {
    private String[] vertices;
    private int numVertices;
    private int[][] matrizAdyacencia;
    private java.util.ArrayList<String> arcosDFS;
    private java.util.ArrayList<String> arcosBFS;

    public GrafoArrays(String origen) {
        creaTablaVertices(origen);
        matrizAdyacencia = new int[numVertices][numVertices];
        
        arcosDFS = new java.util.ArrayList<>();
        arcosBFS = new java.util.ArrayList<>();
        
        // Llenar la matriz con las aristas
        populateMatrizAdyacencia(origen);
    }

    private void creaTablaVertices(String origen) {
        String[] lineas = origen.split("\\R");
        vertices = new String[lineas.length * 2];
        numVertices = 0;

        for (String linea : lineas) {
            String arista = linea.trim();

            if (arista.startsWith("{")) {
                arista = arista.substring(1).trim();
            }

            int etiqueta = arista.indexOf('[');
            if (etiqueta >= 0) {
                arista = arista.substring(0, etiqueta);
            }

            String[] extremos = arista.split("--", 2);

            if (extremos.length == 2) {
                agregarVertice(limpiarVertice(extremos[0]));
                agregarVertice(limpiarVertice(extremos[1]));
            }
        }
    }

    private String limpiarVertice(String vertice) {
        return vertice.replace("{", "").replace("}", "").replace(";", "").trim();
    }

    private void agregarVertice(String vertice) {
        for (int i = 0; i < numVertices; i++)
        {
            if (vertices[i].equals(vertice))
            {
                return;
            }
        }

        vertices[numVertices] = vertice;
        numVertices++;
    }

    public void display() {
        System.out.println("Vertices del grafo: ");
        displayVertices();
        displayMatrizAdyacencia();

    }

    private void displayVertices() {
        for (int i = 0; i < numVertices; i++)
        {
            System.out.println(vertices[i]);
        }
    }

    private int obtenerIndiceVertice(String vertice) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(vertice)) {
                return i;
            }
        }
        return -1;
    }

    private void agregarArista(String vertice1, String vertice2, int peso) {

        int indice1 = obtenerIndiceVertice(vertice1);
        int indice2 = obtenerIndiceVertice(vertice2);

        if (indice1 >= 0 && indice2 >= 0) {
            matrizAdyacencia[indice1][indice2] = peso;
            matrizAdyacencia[indice2][indice1] = peso;
        }
    }

    // Procesar las lineas y llenar la matriz de adyacencia
private void populateMatrizAdyacencia(String origen) {

    String[] lineas = origen.split("\\R");

    for (String linea : lineas) {

        String arista = linea.trim();

        if (arista.startsWith("{")) {
            arista = arista.substring(1).trim();
        }

        String[] extremos = arista.split("--", 2);

        if (extremos.length == 2) {

            String vertice1 = limpiarVertice(
                extremos[0].split("\\[")[0]
            );

            String parteDerecha = extremos[1];

            // Obtener el peso de la arista
            int peso = 1;

            if (parteDerecha.contains("[")) {

                int inicio = parteDerecha.indexOf('"') + 1;
                int fin = parteDerecha.lastIndexOf('"');

                String numero = parteDerecha.substring(inicio, fin);

                peso = Integer.parseInt(numero);
            }

            // Obtener el segundo vertice
            String vertice2 = limpiarVertice(
                parteDerecha.split("\\[")[0]
            );

            // Agregar la arista a la matriz
            agregarArista(vertice1, vertice2, peso);
        }
    }
}


    // Mostrar la matriz de adyacencia
    public void displayMatrizAdyacencia() {

        System.out.println("\nMatriz de adyacencia:");

        // Encabezados de columnas
        System.out.print("        \t");

        for (int i = 0; i < numVertices; i++)
        {
            System.out.printf("%-15s", vertices[i]);
        }

        System.out.println();

        // Mostrar filas de la matriz
        for (int i = 0; i < numVertices; i++)
        {

            System.out.printf("%-15s", vertices[i]);

            for (int j = 0; j < numVertices; j++)
            {
                System.out.printf("%-15d", matrizAdyacencia[i][j]);
            }

            System.out.println();
        }
    }
    public void DFS(String verticeInicial, String VerticeFinal)
    {
        boolean[] visitado = new boolean[numVertices];
        int indiceInicial = obtenerIndiceVertice(verticeInicial);
        int indiceFinal = obtenerIndiceVertice(VerticeFinal);
        if (indiceInicial == -1 || indiceFinal == -1)
        {
            System.out.println("Uno o ambos vértices no existen en el grafo.");//Sanity check
            return;
        }
        System.out.println("Recorrido DFS desde " + verticeInicial + " hasta " + VerticeFinal + ":");
        java.util.Stack<Integer> stack = new java.util.Stack<>();
        stack.push(indiceInicial);
        while (!stack.isEmpty())
        {
            int indiceActual = stack.pop();
            if (!visitado[indiceActual])
            {
                visitado[indiceActual] = true;
                System.out.println(vertices[indiceActual] + " ");
                if (indiceActual == indiceFinal)
                {
                    System.out.println("\nSe ha alcanzado el vertice final");
                    return;
                }
            }
            //Agregar vecinos no visitados al stack
            for (int i = 0; i < numVertices; i++)
            {
                if (matrizAdyacencia[indiceActual][i] != 0 && !visitado[i])
                {
                    arcosDFS.add(vertices[indiceActual] + " -> " + vertices[i]);
                    stack.push(i);
                }
            }
        }
        //Que nodos se han visitado para DFS y BFS
        //Y que diga que hizo tantos saltos para BFS Y BFS
        //Decir cual camino es más rápido
    }
        public void BFS(String verticeInicial, String VerticeFinal)
    {
        boolean[] visitado = new boolean[numVertices];
        int indiceInicial = obtenerIndiceVertice(verticeInicial);
        int indiceFinal = obtenerIndiceVertice(VerticeFinal);
        if (indiceInicial == -1 || indiceFinal == -1)
        {
            System.out.println("Uno o ambos vértices no existen en el grafo.");//Sanity check
            return;
        }
        System.out.println("Recorrido BFS desde " + verticeInicial + " hasta " + VerticeFinal + ":");
        java.util.Queue<Integer> queue = new java.util.LinkedList<>();
        queue.add(indiceInicial);
        while (!queue.isEmpty())
        {
            int indiceActual = queue.poll();
            if (!visitado[indiceActual])
            {
                visitado[indiceActual] = true;
                System.out.println(vertices[indiceActual] + " ");
                if (indiceActual == indiceFinal)
                {
                    System.out.println("\nSe ha alcanzado el vertice final");
                    return;
                }
            }
            //Agregar vecinos no visitados al stack
            for (int i = 0; i < numVertices; i++)
            {
                if (matrizAdyacencia[indiceActual][i] != 0 && !visitado[i])
                {
                    arcosBFS.add(vertices[indiceActual] + " -> " + vertices[i]);
                    queue.add(i);
                }
            }
        }
    }
    public int contarArcos()
    {
        int total = 0;
        for (int i = 0; i < numVertices; i++){
            for (int j = i + 1; j < numVertices; j++){
                if (matrizAdyacencia[i][j] != 0)
                {
                    total++;
                }
            }
        }
        return total;
    }
    public void despliegaArcos()
    {
        System.out.println("----- ARCO DFS -----");
        if(arcosDFS != null)
        {
            for(String arco : arcosDFS)
            {
                System.out.println(arco);
            }
        }
        System.out.println("Total de arcos ocupados para DFS: " + arcosDFS.size());
        System.out.println("----- ARCO BFS -----");
        if(arcosBFS != null)
        {
            for(String arco : arcosBFS)
            {
                System.out.println(arco);
            }
        }
        System.out.println("Total de arcos ocupados para BFS: " + arcosBFS.size());
        if(arcosDFS != null && arcosBFS != null)
        {
            if(arcosDFS.size() < arcosBFS.size())
            {
                System.out.println("DFS tiene menos arcos ocupados.");
            }
            else if(arcosBFS.size() < arcosDFS.size())
            {
                System.out.println("BFS tiene menos arcos ocupados.");
            }
            else
            {
                System.out.println("DFS y BFS tienen la misma cantidad de arcos ocupados.");
            }
        }
        System.out.println("Total de vertices: " + numVertices);
        System.out.println("Total de arcos del grafo: " + contarArcos());
        
    }
}

public class Grafos {

    public static void main(String[] args) {

        String texto = cargaArchivo("grafo_salon.txt");
        System.out.println("Contenido del archivo:\n" + texto);
        GrafoArrays grafo = new GrafoArrays(texto);
        grafo.display();
        //grafo.DFS("Arcangel", "Lucia");
        //grafo.DFS("Lucia", "Arcangel");
        grafo.DFS("Luna", "Angel");
        grafo.BFS("Luna", "Angel");
        grafo.despliegaArcos();
    }

    public static String cargaArchivo(String ruta) {

        String contenido = "";

        try {
            java.io.File archivo = new java.io.File(ruta);
            java.util.Scanner scanner = new java.util.Scanner(archivo);

            while (scanner.hasNextLine()) {
                contenido += scanner.nextLine() + "\n";
            }
            scanner.close();

        } catch (java.io.FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + ruta);
        }

        return contenido;
    }
}


package grafos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
/**
 *
 * @author sergi
 */
public class GrafoListas {
    ArrayList<Nodos> nodos;
    public GrafoListas(String texto) {
        creaListaVertices(texto);
        agregaAristas(texto);
    }
    private void creaListaVertices(String texto) {
        String[] lineas = texto.split("\\R");
        nodos = new ArrayList<>();
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
                agregarVertice(extremos[0].trim());
                agregarVertice(extremos[1].trim());
            }
    }
}
    private void agregarVertice(String nombre) {
        for (Nodos nodo : nodos) {
            if (nodo.getNombre().equals(nombre)) {
                return; // El vértice ya existe
            }
        }
        nodos.add(new Nodos(nombre));
    }
    public void display() {
        for (Nodos nodo : nodos) {
            nodo.display();
        }
    }
    private void agregaAristas(String texto) {
        String[] lineas = texto.split("\\R");
        for (String linea : lineas) {
            String arista = linea.trim();
            if (arista.startsWith("{")) {
                arista = arista.substring(1).trim();
            }
            int etiqueta = arista.indexOf('[');
            int peso = 0;
            if (etiqueta >= 0) {
                String pesoStr = arista.substring(etiqueta + 7, arista.indexOf(']', etiqueta));
                pesoStr = pesoStr.replace("\"", "").trim();
                try {
                    peso = Integer.parseInt(pesoStr.trim());
                } catch (NumberFormatException e) {
                    System.out.println("Error al convertir el peso a entero: " + pesoStr);
                }
                arista = arista.substring(0, etiqueta);
            }
            String[] extremos = arista.split("--", 2);
            if (extremos.length == 2) {
                Nodos nodo1 = new Nodos(extremos[0].trim());
                Nodos nodo2 = new Nodos(extremos[1].trim());
                if (nodo1 != null && nodo2 != null) {
                    agregarAdyacente(nodo1, nodo2, peso);
                    agregarAdyacente(nodo2, nodo1, peso); // Para grafos no dirigidos
                }
            }
        }
    }
    private void agregarAdyacente(Nodos nodoOrigen, Nodos nodoDestino, int peso) {
        for (Nodos n : nodos) {
            if (n.getNombre().equals(nodoOrigen.getNombre())) {
                n.agregarAdyacente(nodoDestino, peso);
                return;
            }
        }
    }
    private Nodos buscarNodo(String nombre) {
        for (Nodos n : nodos) {
            if (n.getNombre().equals(nombre)) {
                return n;
            }
        }
        return null;
    }
    public void DFS(String verticeInicial, String verticeFinal) {
        Nodos inicio = buscarNodo(verticeInicial);
        Nodos destino = buscarNodo(verticeFinal);

        if (inicio == null || destino == null) {
            System.out.println("Uno o ambos vértices no existen en el grafo de listas.");
            return;
        }

        System.out.println("Recorrido DFS (Listas) desde " + verticeInicial + " hasta " + verticeFinal + ":");
        
        Stack<Nodos> stack = new Stack<>();
        HashSet<String> visitados = new HashSet<>();
        
        stack.push(inicio);
        boolean encontrado = false;

        while (!stack.isEmpty()) {
            Nodos actual = stack.pop();

            if (!visitados.contains(actual.getNombre())) {
                visitados.add(actual.getNombre());
                System.out.print(actual.getNombre() + " ");

                if (actual.getNombre().equals(destino.getNombre())) {
                    System.out.println("\nSe ha alcanzado el vertice final.");
                    encontrado = true;
                    break;
                }

                // Agregar los vecinos adyacentes no visitados a la pila
                ArrayList<Nodos> adyacentes = actual.getAdyacentes();
                // Recorremos en orden inverso para mantener el orden tradicional de la pila
                for (int i = adyacentes.size() - 1; i >= 0; i--) {
                    Nodos vecino = adyacentes.get(i);
                    if (!visitados.contains(vecino.getNombre())) {
                        stack.push(vecino);
                    }
                }
            }
        }

        if (!encontrado) {
            System.out.println("\nNo se encontro un camino hacia el vertice final.");
        }
    }
}

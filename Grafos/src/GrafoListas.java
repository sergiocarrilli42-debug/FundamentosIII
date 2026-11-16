package grafos;
import java.util.ArrayList;

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
                Nodos nodo1 = obtenerNodoPorNombre(extremos[0].trim());
                Nodos nodo2 = obtenerNodoPorNombre(extremos[1].trim());
                
                if (nodo1 != null && nodo2 != null) {
                    nodo1.agregarAdyacente(nodo2, peso);
                    nodo2.agregarAdyacente(nodo1, peso); // Para grafos no dirigidos
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

    private Nodos obtenerNodoPorNombre(String nombre) {
        for (Nodos nodo : nodos) {
            if (nodo.getNombre().equals(nombre)) {
                return nodo;
            }
        }
        return null; // Retorna null si no se encuentra el nodo
    } 
    public void DFS(String verticeInicial, String VerticeFinal){
        int numVertices = nodos.size();
        boolean[] visitado = new boolean[numVertices];
        Nodos nodoInicial = obtenerNodoPorNombre(verticeInicial);
        Nodos nodoFinal = obtenerNodoPorNombre(VerticeFinal);

        if (nodoInicial == null || nodoFinal == null) {
            System.out.println("Uno o ambos vertices no existen en el grafo.");
            return;
        }
        System.out.println("Recorrido DFS desde " + verticeInicial + " hasta " + VerticeFinal + ":");
        java.util.Stack<Nodos> stack = new java.util.Stack<>();
        stack.push(nodoInicial);
        while (!stack.isEmpty()) {
            Nodos nodoActual = stack.pop();
            if (!nodoActual.isVisitado()) {
                nodoActual.setVisitado(true);
                System.out.print(nodoActual.getNombre() + " ");
                if (nodoActual.getNombre().equals(nodoFinal.getNombre())) {
                    System.out.println("\nSe ha alcanzado el vertice final.");
                    return; 
                }
            }
            // Agregar vecinos no visitados al stack
            for (Nodos vecino : nodoActual.getAdyacentes()) {
                if (!vecino.isVisitado()) {
                    stack.push(vecino);
                }
            }
        }
    }
    public void BFS(String verticeInicial, String VerticeFinal) {
        int numVertices = nodos.size();
        boolean[] visitado = new boolean[numVertices];
        
        int indiceInicial = -1;
        int indiceFinal = -1;
        for (int i = 0; i < numVertices; i++) {
            if (nodos.get(i).getNombre().equals(verticeInicial)) indiceInicial = i;
            if (nodos.get(i).getNombre().equals(VerticeFinal)) indiceFinal = i;
        }

        if (indiceInicial == -1 || indiceFinal == -1) {
            System.out.println("Uno o ambos vertices no existen en el grafo.");
            return;
        }

        System.out.println("Recorrido BFS desde " + verticeInicial + " hasta " + VerticeFinal + ":");
        java.util.Queue<Integer> queue = new java.util.LinkedList<>();
        queue.add(indiceInicial); 

        while (!queue.isEmpty()) {
            int indiceActual = queue.poll();
            if (!visitado[indiceActual]) {
                visitado[indiceActual] = true;
                System.out.print(nodos.get(indiceActual).getNombre() + " ");
                if (indiceActual == indiceFinal) {
                    System.out.println("\nSe ha alcanzado el vertice final.");
                    return; 
                }
            }
            
            // Recorremos los vecinos usando la lista de adyacencia del nodo actual
            Nodos actualObj = nodos.get(indiceActual);
            for (Nodos vecinoObj : actualObj.getAdyacentes()) {
                int i = nodos.indexOf(vecinoObj);
                if (i != -1 && !visitado[i]) {
                    queue.add(i);
                }
            }
        }
    }

    public String caminoMasCorto(String verticeInicial, String verticeFinal) {
        int numVertices = nodos.size();
        int inicio = -1;
        int destino = -1;
        for (int i = 0; i < numVertices; i++) {
            if (nodos.get(i).getNombre().equals(verticeInicial)) inicio = i;
            if (nodos.get(i).getNombre().equals(verticeFinal)) destino = i;
        }

        if (inicio == -1 || destino == -1) {
            return "Uno o ambos vertices no existen en el grafo.";
        }

        int[] distancia = new int[numVertices];
        int[] anterior = new int[numVertices];
        boolean[] definitivo = new boolean[numVertices];
        
        java.util.Arrays.fill(distancia, Integer.MAX_VALUE);
        java.util.Arrays.fill(anterior, -1);
        distancia[inicio] = 0;

        for (int paso = 0; paso < numVertices; paso++) {
            int actual = -1;
            for (int i = 0; i < numVertices; i++) {
                if (!definitivo[i] && distancia[i] != Integer.MAX_VALUE
                        && (actual == -1 || distancia[i] < distancia[actual])) {
                    actual = i;
                }
            }
            if (actual == -1) {
                break; 
            }
            if (actual == destino) {
                break; 
            }
            definitivo[actual] = true;

            // Revisamos los vecinos del nodo actual
            Nodos actualObj = nodos.get(actual);
            for (Nodos vecinoObj : actualObj.getAdyacentes()) {
                int vecino = nodos.indexOf(vecinoObj);
                int peso = vecinoObj.getPeso();

                if (vecino != -1 && peso > 0 && !definitivo[vecino]
                        && distancia[actual] <= Integer.MAX_VALUE - peso) {
                    int nuevaDistancia = distancia[actual] + peso;
                    if (nuevaDistancia < distancia[vecino]) {
                        distancia[vecino] = nuevaDistancia;
                        anterior[vecino] = actual;
                    }
                }
            }
        }

        if (distancia[destino] == Integer.MAX_VALUE) {
            return "No existe un camino entre " + verticeInicial + " y " + verticeFinal + ".";
        }

        java.util.LinkedList<String> camino = new java.util.LinkedList<>();
        for (int actual = destino; actual != -1; actual = anterior[actual]) {
            camino.addFirst(nodos.get(actual).getNombre());
        }
        return "Camino mas corto: " + String.join(" -> ", camino)
                + " (costo total: " + distancia[destino] + ")";
    }
}

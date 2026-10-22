
package arbolbinnombres;

class Nodo {
    String nombre;
    int expediente;
    Nodo izquierda;
    Nodo derecha;

    public Nodo(String nombre, int expediente) {
        this.nombre = nombre;
        this.expediente = expediente;
        this.izquierda = null;
        this.derecha = null;
    }
}

class ArbolBinario {
    Nodo raiz;

    public ArbolBinario() {
        this.raiz = null;
    }

    public void insertar(String nombre, int expediente) {
        raiz = insertarRecursivo(raiz, nombre, expediente);
    }

    private Nodo insertarRecursivo(Nodo nodo, String nombre, int expediente) {
        if (nodo == null) {
            return new Nodo(nombre, expediente);
        }
        if (nombre.compareTo(nodo.nombre) < 0) {
            nodo.izquierda = insertarRecursivo(nodo.izquierda, nombre, expediente);
        } else if (nombre.compareTo(nodo.nombre) > 0) {
            nodo.derecha = insertarRecursivo(nodo.derecha, nombre, expediente);
        }
        return nodo;
    }

    public void preOrden() {
        preOrdenRecursivo(raiz);
    }

    private void preOrdenRecursivo(Nodo nodo) {
        if (nodo != null) {
            System.out.println("- " + nodo.nombre + " (Expediente: " + nodo.expediente + ")");
            preOrdenRecursivo(nodo.izquierda);
            preOrdenRecursivo(nodo.derecha);
        }
    }

    public void inOrden() {
        inOrdenRecursivo(raiz);
    }

    private void inOrdenRecursivo(Nodo nodo) {
        if (nodo != null) {
            inOrdenRecursivo(nodo.izquierda);
            System.out.println("- " + nodo.nombre + " (Expediente: " + nodo.expediente + ")");
            inOrdenRecursivo(nodo.derecha);
        }
    }

    public void inOrdenReversa() {
        inOrdenReversaRecursivo(raiz);
    }

    private void inOrdenReversaRecursivo(Nodo nodo) {
        if (nodo != null) {
            inOrdenReversaRecursivo(nodo.derecha);
            System.out.println("- " + nodo.nombre + " (Expediente: " + nodo.expediente + ")");
            inOrdenReversaRecursivo(nodo.izquierda);
        }
    }
}

public class ArbolBinNombres {
    public static void main(String[] args) {
        ArbolBinario arbol = new ArbolBinario();

        arbol.insertar("Ana", 103);
        arbol.insertar("Carlos", 101);
        arbol.insertar("Maria", 105);
        arbol.insertar("Luis", 102);

        System.out.println("Recorrido preorden:");
        arbol.preOrden();

        System.out.println("\nRecorrido en orden (Alfabetico ascendente):");
        arbol.inOrden();
        
        System.out.println("\nRecorrido en reversa(Alfabetico descendente):");
        arbol.inOrdenReversa();
    }
}

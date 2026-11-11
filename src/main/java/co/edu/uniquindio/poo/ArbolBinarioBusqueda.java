package co.edu.uniquindio.poo;

class ArbolBinarioBusqueda {
    private NodoArbol raiz;
 
    public ArbolBinarioBusqueda() {
        this.raiz = null;
    }
 
    public NodoArbol getRaiz() {
        return raiz;
    }
 
    public boolean estaVacio() {
        return raiz == null;
    }
 
    // Agrega un codigo al arbol (los codigos repetidos se ignoran)
    public void insertar(int valor) {
        raiz = insertarEnNodo(raiz, valor);
    }
 
    private NodoArbol insertarEnNodo(NodoArbol nodoActual, int valor) {
        if (nodoActual == null) {
            return new NodoArbol(valor);
        }
 
        if (valor < nodoActual.getValor()) {
            nodoActual.setIzquierdo(insertarEnNodo(nodoActual.getIzquierdo(), valor));
        } else if (valor > nodoActual.getValor()) {
            nodoActual.setDerecho(insertarEnNodo(nodoActual.getDerecho(), valor));
        }
 
        return nodoActual;
    }
 
    // Elimina un codigo. Si el nodo tiene dos hijos, se reemplaza por el
    // mayor de su lado izquierdo (el predecesor inorden)
    public void eliminar(int valor) {
        raiz = eliminarEnNodo(raiz, valor);
    }
 
    private NodoArbol eliminarEnNodo(NodoArbol nodoActual, int valor) {
        if (nodoActual == null) {
            return null;
        }
 
        if (valor < nodoActual.getValor()) {
            nodoActual.setIzquierdo(eliminarEnNodo(nodoActual.getIzquierdo(), valor));
        } else if (valor > nodoActual.getValor()) {
            nodoActual.setDerecho(eliminarEnNodo(nodoActual.getDerecho(), valor));
        } else {
            // Caso sin hijo izquierdo: lo reemplaza el hijo derecho (o nada)
            if (nodoActual.getIzquierdo() == null) {
                return nodoActual.getDerecho();
            }
 
            // Caso sin hijo derecho: lo reemplaza el hijo izquierdo
            if (nodoActual.getDerecho() == null) {
                return nodoActual.getIzquierdo();
            }
 
            // Caso con dos hijos: se busca el mayor del lado izquierdo
            NodoArbol predecesor = nodoActual.getIzquierdo();
            while (predecesor.getDerecho() != null) {
                predecesor = predecesor.getDerecho();
            }
 
            // Se copia ese valor al nodo y se borra el nodo original del predecesor
            nodoActual.setValor(predecesor.getValor());
            nodoActual.setIzquierdo(eliminarEnNodo(nodoActual.getIzquierdo(), predecesor.getValor()));
        }
 
        return nodoActual;
    }
 
    // Imprime los codigos de menor a mayor
    public void imprimirInorden() {
        imprimirInordenDesde(raiz);
        System.out.println();
    }
 
    private void imprimirInordenDesde(NodoArbol nodoActual) {
        if (nodoActual != null) {
            imprimirInordenDesde(nodoActual.getIzquierdo());
            System.out.print(nodoActual.getValor() + " ");
            imprimirInordenDesde(nodoActual.getDerecho());
        }
    }
}

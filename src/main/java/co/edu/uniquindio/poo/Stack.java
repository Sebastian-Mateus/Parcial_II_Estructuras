package co.edu.uniquindio.poo;

 public class Stack<T> {
 
    private int size;
    private Nodo<T> top;
 
    public Stack() {
        this.size = 0;
        this.top = null;
    }
 
    public boolean isEmpty() {
        return size == 0;
    }
 
    public void push(T data) {
        Nodo<T> nuevoNodo = new Nodo<>(data);
        if (!isEmpty()) {
            nuevoNodo.setSiguiente(top);
        }
        top = nuevoNodo;
        size++;
    }
 
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("La pila está vacía");
        }
        T valor = top.getValor();
        top = top.getSiguiente();
        size--;
        return valor;
    }
 
    public Stack<T> clonar() {
        Stack<T> aux = new Stack<>();
        Stack<T> clon = new Stack<>();
 
        while (!isEmpty()) {
            aux.push(this.pop());
        }
 
        while (!aux.isEmpty()) {
            T valor = aux.pop();
            clon.push(valor);
            this.push(valor);
        }
        return clon;
    }
 
}

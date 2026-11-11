package co.edu.uniquindio.poo;

class Queue<T> {
 
    private int size;
    private Nodo<T> front;
    private Nodo<T> rear;
 
    public Queue() {
        this.size = 0;
        this.front = null;
        this.rear = null;
    }
 
    public boolean isEmpty() {
        return size == 0;
    }
 
    // Agrega un valor al final de la cola
    public void enqueue(T data) {
        Nodo<T> nuevoNodo = new Nodo<>(data);
        if (isEmpty()) {
            front = nuevoNodo;
        } else {
            rear.setSiguiente(nuevoNodo);
        }
        rear = nuevoNodo;
        size++;
    }
 
    // Saca y devuelve el valor que esta al frente de la cola
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("La cola está vacía");
        }
        T valor = front.getValor();
        front = front.getSiguiente();
        size--;
        if (isEmpty()) {
            rear = null;
        }
        return valor;
    }
 
}

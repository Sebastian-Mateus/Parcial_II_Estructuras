package co.edu.uniquindio.poo;

public class ParcialII {
 
    // Devuelve una pila nueva con las n operaciones confirmadas mas recientes.
    // La pila original queda igual que al principio.
    public static Stack<Operacion> obtenerUltimasConfirmadas(Stack<Operacion> historial, int n) {
        Stack<Operacion> resultado = new Stack<Operacion>();
 
        // Si n no es positivo no hay nada que buscar
        if (n <= 0) {
            return resultado;
        }
 
        // Aqui guardamos lo que sacamos del historial para poder devolverlo despues
        Stack<Operacion> pilaRespaldo = new Stack<Operacion>();
 
        // Aqui van las confirmadas, pero quedan al reves (la mas reciente abajo)
        Stack<Operacion> pilaConfirmadasAlReves = new Stack<Operacion>();
 
        int cantidadEncontradas = 0;
 
        // Recorremos todo el historial desde el tope (la operacion mas reciente)
        while (!historial.isEmpty()) {
            Operacion operacionActual = historial.pop();
 
            // Guardamos una copia en el respaldo para no perderla
            pilaRespaldo.push(operacionActual);
 
            // Solo nos sirve si esta confirmada y todavia no llegamos a n
            if (operacionActual.estaConfirmada() && cantidadEncontradas < n) {
                pilaConfirmadasAlReves.push(operacionActual);
                cantidadEncontradas++;
            }
        }
 
        // Devolvemos todo al historial. Como el respaldo quedo invertido,
        // al pasarlo otra vez el historial vuelve a su orden original
        while (!pilaRespaldo.isEmpty()) {
            historial.push(pilaRespaldo.pop());
        }
 
        // Damos vuelta a las confirmadas para que la mas reciente quede en el tope
        while (!pilaConfirmadasAlReves.isEmpty()) {
            resultado.push(pilaConfirmadasAlReves.pop());
        }
 
        return resultado;
    }
 
    // Imprime una pila desde el tope hasta el fondo sin dañarla
    public static void mostrarPila(String titulo, Stack<Operacion> pila) {
        System.out.println(titulo + " (de tope a fondo):");
 
        if (pila.isEmpty()) {
            System.out.println("  (vacia)");
            return;
        }
 
        Stack<Operacion> pilaAuxiliar = new Stack<Operacion>();
 
        while (!pila.isEmpty()) {
            Operacion operacionActual = pila.pop();
            System.out.println("  " + operacionActual);
            pilaAuxiliar.push(operacionActual);
        }
 
        // Dejamos la pila como estaba
        while (!pilaAuxiliar.isEmpty()) {
            pila.push(pilaAuxiliar.pop());
        }
    }
 
    // Prueba del ejercicio 1 con el ejemplo del enunciado
    public static void probarEjercicio1() {
        System.out.println("===== EJERCICIO 1 =====");
 
        Stack<Operacion> historial = new Stack<Operacion>();
 
        // Se apila del fondo al tope, asi 05 queda de ultimo (en el tope)
        historial.push(new Operacion(1, EstadoOperacion.ANULADA));
        historial.push(new Operacion(2, EstadoOperacion.CONFIRMADA));
        historial.push(new Operacion(3, EstadoOperacion.CONFIRMADA));
        historial.push(new Operacion(4, EstadoOperacion.ANULADA));
        historial.push(new Operacion(5, EstadoOperacion.CONFIRMADA));
 
        mostrarPila("Historial original", historial);
 
        Stack<Operacion> resultado = obtenerUltimasConfirmadas(historial, 2);
        mostrarPila("Resultado con n = 2", resultado);
 
        // Verificamos que el historial no cambio
        mostrarPila("Historial despues de la funcion", historial);
 
        // Casos especiales
        mostrarPila("Resultado con n = 10 (hay menos)", obtenerUltimasConfirmadas(historial, 10));
        mostrarPila("Resultado con n = 0", obtenerUltimasConfirmadas(historial, 0));
        System.out.println();
    }
 
    public static void main(String[] args) {
        probarEjercicio1();
    }
}

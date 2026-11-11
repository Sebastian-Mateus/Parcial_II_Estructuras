package co.edu.uniquindio.poo;

import java.util.Queue;
import java.util.Stack;

public class ParcialII {
  // Devuelve una pila nueva con las n operaciones confirmadas mas recientes.
    // La pila original queda igual que al principio.
    public static Stack<Operacion> obtenerUltimasConfirmadas(Stack<Operacion> historial, int n) {
        Stack<Operacion> resultado = new Stack<Operacion>();
 
        // Si n no es positivo no hay nada que buscar
        if (n <= 0) {
            return resultado;
        }
 
        // Aqui se guarda lo que se saca del historial para poder devolverlo despues
        Stack<Operacion> pilaRespaldo = new Stack<Operacion>();
 
        // Aqui van las confirmadas, pero quedan al reves (la mas reciente abajo)
        Stack<Operacion> pilaConfirmadasAlReves = new Stack<Operacion>();
 
        int cantidadEncontradas = 0;
 
        // Se recorre todo el historial desde el tope (la operacion mas reciente)
        while (!historial.isEmpty()) {
            Operacion operacionActual = historial.pop();
 
            // Se guarda una copia en el respaldo para no perderla
            pilaRespaldo.push(operacionActual);
 
            // Solo sirve si esta confirmada y todavia no se llega a n
            if (operacionActual.estaConfirmada() && cantidadEncontradas < n) {
                pilaConfirmadasAlReves.push(operacionActual);
                cantidadEncontradas++;
            }
        }
 
        // Se devuelve todo al historial. Como el respaldo quedo invertido,
        // al pasarlo otra vez el historial vuelve a su orden original
        while (!pilaRespaldo.isEmpty()) {
            historial.push(pilaRespaldo.pop());
        }
 
        // Se da vuelta a las confirmadas para que la mas reciente quede en el tope
        while (!pilaConfirmadasAlReves.isEmpty()) {
            resultado.push(pilaConfirmadasAlReves.pop());
        }
 
        return resultado;
    }
 
 
    // PUNTO 2: Obtener un segmento del historial
 
    // Devuelve una pila nueva con las acciones entre inicio y fin (incluidos).
    // Las posiciones se cuentan desde el tope empezando en 1.
    // La pila original queda igual que al principio.
    public static Stack<String> obtenerSegmento(Stack<String> historial, int inicio, int fin) {
        Stack<String> resultado = new Stack<String>();
 
        // Si las posiciones no tienen sentido se devuelve la pila vacia
        if (inicio < 1 || inicio > fin) {
            return resultado;
        }
 
        // Se trabaja con una copia para no tocar la pila original
        Stack<String> copia = historial.clonar();
 
        // Aqui van las acciones del segmento, pero quedan al reves
        Stack<String> segmentoAlReves = new Stack<String>();
 
        // Se va contando en que posicion esta, empezando desde el tope
        int posicionActual = 1;
 
        while (!copia.isEmpty()) {
            String accionActual = copia.pop();
 
            // Solo se guardan las acciones que estan dentro del rango
            if (posicionActual >= inicio && posicionActual <= fin) {
                segmentoAlReves.push(accionActual);
            }
 
            posicionActual++;
        }
 
        // Al terminar, posicionActual - 1 es la cantidad total de acciones
        int cantidadTotal = posicionActual - 1;
 
        // Si fin se pasa de la cantidad que hay, el rango no es valido
        if (fin > cantidadTotal) {
            return resultado;
        }
 
        // Se da vuelta al segmento para que la accion de inicio quede en el tope
        while (!segmentoAlReves.isEmpty()) {
            resultado.push(segmentoAlReves.pop());
        }
 
        return resultado;
    }
 
 
    // PUNTO 3: Seleccion de solicitudes para una jornada
 
    // Devuelve una cola nueva con las solicitudes que caben en los minutos
    // disponibles y las saca de la cola de pendientes.
    // Primero se revisan las de prioridad alta y despues las normales.
    public static Queue<Solicitud> seleccionarSolicitudes(Queue<Solicitud> pendientes, int minutosDisponibles) {
        Queue<Solicitud> seleccionadas = new Queue<Solicitud>();
 
        // Si no hay tiempo no se elige nada y los pendientes quedan igual
        if (minutosDisponibles <= 0) {
            return seleccionadas;
        }
 
        int minutosRestantes = minutosDisponibles;
 
        // Aqui quedan las solicitudes que no se eligieron en la primera vuelta
        Queue<Solicitud> quedanPorRevisar = new Queue<Solicitud>();
 
        // Primera vuelta: solo se eligen las de prioridad alta que quepan
        while (!pendientes.isEmpty()) {
            Solicitud solicitudActual = pendientes.dequeue();
 
            if (solicitudActual.getPrioridad() == PrioridadSolicitud.ALTA
                    && solicitudActual.getDuracion() <= minutosRestantes) {
                seleccionadas.enqueue(solicitudActual);
                minutosRestantes = minutosRestantes - solicitudActual.getDuracion();
            } else {
                quedanPorRevisar.enqueue(solicitudActual);
            }
        }
 
        // Segunda vuelta: solo se eligen las de prioridad normal que quepan.
        // Las que no se eligen vuelven a la cola de pendientes en su mismo orden
        while (!quedanPorRevisar.isEmpty()) {
            Solicitud solicitudActual = quedanPorRevisar.dequeue();
 
            if (solicitudActual.getPrioridad() == PrioridadSolicitud.NORMAL
                    && solicitudActual.getDuracion() <= minutosRestantes) {
                seleccionadas.enqueue(solicitudActual);
                minutosRestantes = minutosRestantes - solicitudActual.getDuracion();
            } else {
                pendientes.enqueue(solicitudActual);
            }
        }
 
        return seleccionadas;
    }
 
 
    // PUNTO 4a: Contar equipos en un rango
 
    // Cuenta cuantos codigos estan entre limiteInferior y limiteSuperior (incluidos).
    // Aprovecha el orden del arbol para no revisar los lados que no sirven.
    public static int contarEquiposEnRango(NodoArbol raiz, int limiteInferior, int limiteSuperior) {
        // Si no hay arbol o el rango esta al reves, no hay nada que contar
        if (raiz == null || limiteInferior > limiteSuperior) {
            return 0;
        }
 
        int codigoActual = raiz.getValor();
 
        // Si el codigo es menor al limite inferior, todo el lado izquierdo
        // es todavia mas pequeño, asi que solo se busca por la derecha
        if (codigoActual < limiteInferior) {
            return contarEquiposEnRango(raiz.getDerecho(), limiteInferior, limiteSuperior);
        }
 
        // Si el codigo es mayor al limite superior, todo el lado derecho
        // es todavia mas grande, asi que solo se busca por la izquierda
        if (codigoActual > limiteSuperior) {
            return contarEquiposEnRango(raiz.getIzquierdo(), limiteInferior, limiteSuperior);
        }
 
        // El codigo esta dentro del rango: cuenta uno y se sigue por los dos lados
        int equiposIzquierda = contarEquiposEnRango(raiz.getIzquierdo(), limiteInferior, limiteSuperior);
        int equiposDerecha = contarEquiposEnRango(raiz.getDerecho(), limiteInferior, limiteSuperior);
 
        return 1 + equiposIzquierda + equiposDerecha;
    }
 
    // Imprime una cola desde el frente hasta el final sin dañarla
    public static <T> void mostrarCola(String titulo, Queue<T> cola) {
        System.out.println(titulo + " (de frente a final):");
 
        if (cola.isEmpty()) {
            System.out.println("  (vacia)");
            return;
        }
 
        Queue<T> colaAuxiliar = new Queue<T>();
 
        while (!cola.isEmpty()) {
            T elementoActual = cola.dequeue();
            System.out.println("  " + elementoActual);
            colaAuxiliar.enqueue(elementoActual);
        }
 
        // Se deja la cola como estaba
        while (!colaAuxiliar.isEmpty()) {
            cola.enqueue(colaAuxiliar.dequeue());
        }
    }
 
    // Imprime una pila desde el tope hasta el fondo sin dañarla
    public static <T> void mostrarPila(String titulo, Stack<T> pila) {
        System.out.println(titulo + " (de tope a fondo):");
 
        if (pila.isEmpty()) {
            System.out.println("  (vacia)");
            return;
        }
 
        Stack<T> pilaAuxiliar = new Stack<T>();
 
        while (!pila.isEmpty()) {
            T elementoActual = pila.pop();
            System.out.println("  " + elementoActual);
            pilaAuxiliar.push(elementoActual);
        }
 
        // Se deja la pila como estaba
        while (!pilaAuxiliar.isEmpty()) {
            pila.push(pilaAuxiliar.pop());
        }
    }
 
    // Prueba del punto 1 con el ejemplo del enunciado
    public static void probarPunto1() {
        System.out.println("===== PUNTO 1 =====");
 
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
 
        // Se verifica que el historial no cambio
        mostrarPila("Historial despues de la funcion", historial);
 
        // Casos especiales
        mostrarPila("Resultado con n = 10 (hay menos)", obtenerUltimasConfirmadas(historial, 10));
        mostrarPila("Resultado con n = 0", obtenerUltimasConfirmadas(historial, 0));
        System.out.println();
    }
 
    // Prueba del punto 2 con el ejemplo del enunciado
    public static void probarPunto2() {
        System.out.println("===== PUNTO 2 =====");
 
        Stack<String> historial = new Stack<String>();
 
        // Se apila del fondo al tope, asi A6 queda en el tope
        historial.push("A1");
        historial.push("A2");
        historial.push("A3");
        historial.push("A4");
        historial.push("A5");
        historial.push("A6");
 
        mostrarPila("Historial original", historial);
 
        mostrarPila("Segmento inicio = 2, fin = 4", obtenerSegmento(historial, 2, 4));
 
        // Se verifica que el historial no cambio
        mostrarPila("Historial despues de la funcion", historial);
 
        // Casos especiales
        mostrarPila("Segmento inicio = 3, fin = 3 (una sola)", obtenerSegmento(historial, 3, 3));
        mostrarPila("Segmento inicio = 1, fin = 6 (todo)", obtenerSegmento(historial, 1, 6));
        mostrarPila("Segmento inicio = 0, fin = 3", obtenerSegmento(historial, 0, 3));
        mostrarPila("Segmento inicio = 4, fin = 2", obtenerSegmento(historial, 4, 2));
        mostrarPila("Segmento inicio = 5, fin = 9", obtenerSegmento(historial, 5, 9));
        System.out.println();
    }
 
    // Arma la cola de pendientes del ejemplo del enunciado
    public static Queue<Solicitud> crearPendientesEjemplo() {
        Queue<Solicitud> pendientes = new Queue<Solicitud>();
 
        pendientes.enqueue(new Solicitud("S1", PrioridadSolicitud.NORMAL, 10));
        pendientes.enqueue(new Solicitud("S2", PrioridadSolicitud.ALTA, 30));
        pendientes.enqueue(new Solicitud("S3", PrioridadSolicitud.ALTA, 15));
        pendientes.enqueue(new Solicitud("S4", PrioridadSolicitud.NORMAL, 5));
 
        return pendientes;
    }
 
    // Prueba del punto 3 con el ejemplo del enunciado
    public static void probarPunto3() {
        System.out.println("===== PUNTO 3 =====");
 
        // Ejemplo del enunciado: 25 minutos
        Queue<Solicitud> pendientes = crearPendientesEjemplo();
        mostrarCola("Pendientes originales", pendientes);
 
        Queue<Solicitud> seleccionadas = seleccionarSolicitudes(pendientes, 25);
        mostrarCola("Seleccionadas con 25 minutos", seleccionadas);
        mostrarCola("Pendientes despues de seleccionar", pendientes);
 
        // Sin tiempo: no se elige nada y los pendientes no cambian
        pendientes = crearPendientesEjemplo();
        mostrarCola("Seleccionadas con 0 minutos", seleccionarSolicitudes(pendientes, 0));
        mostrarCola("Pendientes despues (0 minutos)", pendientes);
 
        // Tiempo negativo
        pendientes = crearPendientesEjemplo();
        mostrarCola("Seleccionadas con -5 minutos", seleccionarSolicitudes(pendientes, -5));
        mostrarCola("Pendientes despues (-5 minutos)", pendientes);
 
        // Mucho tiempo: se eligen todas, primero las altas y luego las normales
        pendientes = crearPendientesEjemplo();
        mostrarCola("Seleccionadas con 100 minutos", seleccionarSolicitudes(pendientes, 100));
        mostrarCola("Pendientes despues (100 minutos)", pendientes);
        System.out.println();
    }
 
    // Prueba del punto 4 con los codigos del enunciado
    public static void probarPunto4() {
        System.out.println("===== PUNTO 4 =====");
 
        ArbolBinarioBusqueda arbol = new ArbolBinarioBusqueda();
 
        // Arbol vacio
        System.out.println("Arbol vacio, rango 1 a 100: "
                + contarEquiposEnRango(arbol.getRaiz(), 1, 100));
 
        // Se insertan los codigos en el mismo orden del enunciado
        int[] codigos = {50, 30, 70, 20, 40, 60, 80, 55, 65};
        for (int posicion = 0; posicion < codigos.length; posicion++) {
            arbol.insertar(codigos[posicion]);
        }
 
        System.out.print("Recorrido inorden del arbol: ");
        arbol.imprimirInorden();
 
        // Parte a: consultas por rango
        System.out.println("Rango 30 a 65 (esperado 6): "
                + contarEquiposEnRango(arbol.getRaiz(), 30, 65));
        System.out.println("Rango 56 a 100 (esperado 4): "
                + contarEquiposEnRango(arbol.getRaiz(), 56, 100));
        System.out.println("Rango 50 a 50 (esperado 1): "
                + contarEquiposEnRango(arbol.getRaiz(), 50, 50));
        System.out.println("Rango 10 a 19 (esperado 0): "
                + contarEquiposEnRango(arbol.getRaiz(), 10, 19));
        System.out.println("Rango 1 a 100 (esperado 9): "
                + contarEquiposEnRango(arbol.getRaiz(), 1, 100));
        System.out.println("Rango 70 a 20, al reves (esperado 0): "
                + contarEquiposEnRango(arbol.getRaiz(), 70, 20));
 
        // Parte b: eliminar el 50 usando el predecesor inorden
        arbol.eliminar(50);
        System.out.print("Inorden despues de eliminar el 50: ");
        arbol.imprimirInorden();
        System.out.println("Nueva raiz: " + arbol.getRaiz().getValor());
        System.out.println();
    }
 
    public static void main(String[] args) {
        probarPunto1();
        probarPunto2();
        probarPunto3();
        probarPunto4();
    }

}
